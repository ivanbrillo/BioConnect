import json
import csv
import os
import re
from tqdm import tqdm

# Percorsi dei file di input e output (stessa directory dello script corrente)
script_dir = os.path.dirname(os.path.abspath(__file__))
uniprot_path = os.path.join(script_dir, "CompletedUniprot.json")
proteins_interact_path = os.path.join(script_dir, "proteins_interact.json")
proteins_similar_path = os.path.join(script_dir, "Protein-Similarity.json")
protein_name_path = os.path.join(script_dir, "Protein_names.json")

nodes_csv = os.path.join(script_dir, "nodes_2.csv")
relationships_csv = os.path.join(script_dir, "relationships_2.csv")

# Funzione per abbreviare il nome della proteina
def get_protein_short_name(protein_name):
    return protein_name.split('(')[0].split(';')[0].strip()

# Funzione per semplificare il campo "Action" dei drug
def get_simplified_action(action):
    action = action.lower()
    inhibited_by_actions = [
        "antago", "antagonist", "blocker", "degradation", "downregulator", "inactivator",
        "inhibition of synthesis", "inhibitor", "inhibitory allosteric modulator", "inverse agonist",
        "incorporation into and destabilization", "negative modulator", "nist", "nucleotide exchange blocker",
        "oxidizer", "suppressor", "translocation inhibitor", "weak inhibitor", "inhibits downstream inflammation cascades",
        "antibody", "antisense oligonucleotide", "chelator", "cleavage", "d", "inducer", "other", "other/unknown"
    ]
    enhanced_by_actions = [
        "activator", "agonist", "allosteric modulator", "binder", "binding", "carrier", "chaperone",
        "cofactor", "component of", "enhancer", "gene replacement", "ligand", "ligan", "metabolizer",
        "modulator", "multitarget", "neutralizer", "partial agonist", "partial antagonist",
        "positive allosteric modulator", "positive modulator", "potentiator", "product of", "regulator",
        "stabilization", "stimulator", "substrate", "transporter", "upregulator"
    ]
    
    if action in inhibited_by_actions:
        return "INHIBITED_BY"
    elif action in enhanced_by_actions:
        return "ENHANCED_BY"
    else:
        return "UNKNOWN"

def get_disease_name_from_string(disease_string):
    # Trova il nome della malattia prima della parentesi
    match = re.search(r"DISEASE: ([^(\[]+)", disease_string)  # Cattura il nome della malattia prima della parentesi
    if match:
        disease_name = match.group(1).strip()  # Rimuove gli spazi extra
        disease_name = disease_name.rstrip(',')  # Rimuove la virgola finale, se presente
        disease_name = disease_name.rstrip('"')  # Rimuove le virgolette (se presenti)
        return disease_name
    return None

def get_mim_code_from_string(mim_string):
    match = re.search(r"\[MIM:(\d+)\]", mim_string)
    if match:
        return match.group(1)
    return None

# Funzione per ottenere la parte prima del trattino o la parte interna alle parentesi quadre
def get_base_id(protein_id):
    if '[' in protein_id and ']' in protein_id:
        return re.search(r'\[(.*?)\]', protein_id).group(1)
    return protein_id.split('-')[0]

# Leggere i file JSON
with open(uniprot_path, 'r') as json_file:
    proteins_data = json.load(json_file)

with open(proteins_interact_path, 'r') as json_file:
    proteins_interact_data = json.load(json_file)

with open(proteins_similar_path, 'r') as json_file:
    proteins_similar_data = json.load(json_file)

with open(protein_name_path, 'r') as json_file:
    protein_name_list = json.load(json_file)

# Creare un dizionario per mappare l'ID della proteina con il nome
protein_name_data = {item['_id']: item['name'] for item in protein_name_list}

# Creare i file CSV
with open(nodes_csv, mode='w', newline='') as nodes_file, \
     open(relationships_csv, mode='w', newline='') as relationships_file:

    # Writer per nodi
    nodes_writer = csv.writer(nodes_file)
    nodes_writer.writerow(["id:ID", "name", "type:LABEL"])  # Intestazione per i nodi

    # Writer per relazioni
    relationships_writer = csv.writer(relationships_file)
    relationships_writer.writerow([":START_ID", ":END_ID", ":TYPE"])  # Intestazione per le relazioni

    # Dizionario per evitare duplicati dei nodi
    nodes_csv_written = {}

    # Set per evitare duplicati delle relazioni
    relationships_csv_written = set()

    # Iterare su tutte le proteine nel dataset
    for protein in tqdm(proteins_data.get("Proteins", []), desc="Processing Proteins"):
        protein_id = protein["Entry"]
        protein_name = protein_name_data.get(protein_id, "Unknown Protein") # prendo dal nuovo file
        if protein_id and protein_name:
            if protein_id not in nodes_csv_written:
                nodes_writer.writerow([protein_id, protein_name, "Protein"])
                nodes_csv_written[protein_id] = protein_name
            elif nodes_csv_written[protein_id] != protein_name:
                print(f"Warning: Duplicate ID with different names: {protein_id} ({nodes_csv_written[protein_id]} vs {protein_name})")

        # Estrai e scrivi le malattie
        if "Involvement in disease" in protein:
            diseases = protein["Involvement in disease"].split(";")
            for disease in diseases:
                disease_name = get_disease_name_from_string(disease.strip())
                disease_mim_code = get_mim_code_from_string(disease.strip())
                if disease_name and disease_mim_code and disease_mim_code not in nodes_csv_written:
                    nodes_writer.writerow([disease_mim_code, disease_name, "Disease"])
                    nodes_csv_written[disease_mim_code] = disease_name
                    relationships_writer.writerow([protein_id, disease_mim_code, "INVOLVED_IN"])
                    relationships_csv_written.add((protein_id, disease_mim_code))

        # Iterare sui drug collegati
        for drug in protein.get("DrugBank", []):
            drug_id = drug["DrugBank_ID"]
            drug_name = drug["Name"]
            drug_action = drug["Action"]

            # Scrivere il nodo del farmaco se non è già stato scritto
            simplified_action = get_simplified_action(drug_action)
            if drug_id and drug_name:
                if drug_id not in nodes_csv_written:
                    if simplified_action != "UNKNOWN":
                        nodes_writer.writerow([drug_id, drug_name, "Drug"])
                        nodes_csv_written[drug_id] = drug_name
                elif nodes_csv_written[drug_id] != drug_name:
                    print(f"Warning: Duplicate ID with different names: {drug_id} ({nodes_csv_written[drug_id]} vs {drug_name})")

            # Scrivere la relazione tra farmaco e proteina (dal farmaco alla proteina)
            if simplified_action != "UNKNOWN" and (drug_id, protein_id) not in relationships_csv_written:
                relationships_writer.writerow([protein_id, drug_id, simplified_action])
                relationships_csv_written.add((drug_id, protein_id))

    # Relazioni tra proteine
    # ottengo gli id delle proteine collegate da proteins_interact.json
    for protein in tqdm(proteins_interact_data, desc="Processing Protein Interactions"):
        interacts_with = protein.get("Interacts with", "").split(";")
        for interact_id in interacts_with:
            interact_id = get_base_id(interact_id.strip())
            start_id = get_base_id(protein["Entry"])
            if interact_id and start_id and interact_id in nodes_csv_written and start_id in nodes_csv_written:
                # Verifica se la relazione o la sua inversa esiste già
                if (start_id, interact_id) not in relationships_csv_written and (interact_id, start_id) not in relationships_csv_written:
                    relationships_writer.writerow([
                        start_id,  # :START_ID (proteina corrente)
                        interact_id,  # :END_ID (proteina con cui interagisce)
                        "INTERACTS_WITH"  # :TYPE (relazione)
                    ])
                    relationships_csv_written.add((start_id, interact_id))
            # Scrivere il nodo della proteina con cui interagisce se non è già stato scritto
            if interact_id not in nodes_csv_written:
                for protein_entry in proteins_interact_data:
                    if get_base_id(protein_entry.get("Entry")) == interact_id:
                        protein_name = protein_entry.get("Protein names", "Unknown Protein")
                        nodes_writer.writerow([
                            interact_id,  # uniprot_id:ID
                            get_protein_short_name(protein_name),  # Nome abbreviato
                            "Protein"  # Etichetta
                        ])
                        nodes_csv_written[interact_id] = protein_name

    # Relazioni di similarità tra proteine
    for protein in tqdm(proteins_similar_data, desc="Processing Protein Similarities"):
        start_id = protein.get("Entry")  # Proteina corrente
        similar_proteins = protein.get("Protein_simily", [])  # Proteine simili

        for similar_protein in similar_proteins:
            end_id = similar_protein.get("Entry")  # ID della proteina simile

            # Scrivere la relazione SIMILAR_TO nel file relationships.csv
            if start_id and end_id:
                # Evita duplicati nelle relazioni
                if (start_id, end_id) not in relationships_csv_written and (end_id, start_id) not in relationships_csv_written:
                    relationships_writer.writerow([start_id, end_id, "SIMILAR_TO"])
                    relationships_csv_written.add((start_id, end_id))

            # Scrivere il nodo della proteina simile se non è già stato scritto
            if end_id and end_id not in nodes_csv_written:
                protein_name = similar_protein.get("Protein names", "Unknown Protein")
                nodes_writer.writerow([
                    end_id,  # ID della proteina simile
                    get_protein_short_name(protein_name),  # Nome abbreviato
                    "Protein"  # Etichetta
                ])
                nodes_csv_written[end_id] = protein_name

print("File CSV generati correttamente")