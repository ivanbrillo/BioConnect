import json
import csv
import os
import re

# Percorsi dei file di input e output (stessa directory dello script corrente)
script_dir = os.path.dirname(os.path.abspath(__file__))
hemoglobin_path = os.path.join(script_dir, "Hemoglobin.json")
all_proteins_path = os.path.join(script_dir, "proteins_interact.json")

nodes_csv = os.path.join(script_dir, "nodes.csv")
relationships_csv = os.path.join(script_dir, "relationships.csv")

# protein_nodes_csv = os.path.join(script_dir, "protein_nodes.csv")
# drug_nodes_csv = os.path.join(script_dir, "drug_nodes.csv")
# diseases_csv = os.path.join(script_dir, "diseases.csv")
# relationships_proteins_csv = os.path.join(script_dir, "relationships_proteins.csv")
# relationships_disease_csv = os.path.join(script_dir, "relationships_disease.csv")
# relationships_inhibit_csv = os.path.join(script_dir, "relationships_inhibit.csv")
# relationships_enhance_csv = os.path.join(script_dir, "relationships_enhance.csv")

# Funzione per abbreviare il nome della proteina
def extract_short_name(protein_name):
    return protein_name.split('(')[0].split(';')[0].strip()

# Funzione per semplificare il campo "Action" dei drug
def simplify_action(action):
    if action.lower() in ["inhibitor", "antagonist", "oxidizer", "inducer"]:
        return "INHIBITED_BY"
    elif action.lower() in ["agonist", "activator", "binding", "binder", "enhancer"]:
        return "ENHANCED_BY"
    else:
        return "UNKNOWN"

def extract_disease_name(disease_string):
    # Trova il nome della malattia prima della parentesi
    match = re.search(r"DISEASE: ([^(\[]+)", disease_string)  # Cattura il nome della malattia prima della parentesi
    if match:
        disease_name = match.group(1).strip()  # Rimuove gli spazi extra
        disease_name = disease_name.rstrip(',')  # Rimuove la virgola finale, se presente
        disease_name = disease_name.rstrip('"')  # Rimuove le virgolette (se presenti)
        return disease_name
    return None

def extract_mim_code(mim_string):
    match = re.search(r"\[MIM:(\d+)\]", mim_string)
    if match:
        return match.group(1)
    return None



# Leggere il file JSON
with open(hemoglobin_path, 'r') as json_file:
    hemoglobin_data = json.load(json_file)

with open(all_proteins_path, 'r') as json_file:
    all_proteins_data = json.load(json_file)

# Creare i file CSV
with open(nodes_csv, mode='w', newline='') as nodes_file, \
     open(relationships_csv, mode='w', newline='') as relationships_file:

    # Writer per nodi
    nodes_writer = csv.writer(nodes_file)
    nodes_writer.writerow(["id:ID", "name", "type"])  # Intestazione per i nodi

    # Writer per relazioni
    relationships_writer = csv.writer(relationships_file)
    relationships_writer.writerow([":START_ID", ":END_ID", ":TYPE"])  # Intestazione per le relazioni

    # Set per evitare duplicati dei nodi e delle relazioni
    nodes_csv_written = set()
    relationships_csv_written = set()

    # Scrivere i nodi delle proteine se non sono già stati inseriti
    if hemoglobin_data["Entry"] not in nodes_csv_written:
        nodes_writer.writerow([
            hemoglobin_data["Entry"],                     # uniprot_id:ID
            extract_short_name(hemoglobin_data["Protein names"]),  # Nome abbreviato
            "Protein"                                 # Etichetta
        ])
        nodes_csv_written.add(hemoglobin_data["Entry"])

    # Estrai e scrivi le malattie
    if "Involvement in disease" in hemoglobin_data:
        diseases = hemoglobin_data["Involvement in disease"].split(";")
        for disease in diseases:
            disease_name = extract_disease_name(disease.strip())
            disease_mim_code = extract_mim_code(disease.strip())
            if disease_name not in nodes_csv_written:
                nodes_writer.writerow([disease_mim_code,disease_name, "Disease"])  # Scrivi il nodo malattia
                nodes_csv_written.add(disease_name)
                relationships_writer.writerow([hemoglobin_data["Entry"], disease_mim_code, "INVOLVED_IN"])
                relationships_csv_written.add((hemoglobin_data["Entry"], disease_mim_code))

    # Iterare sui drug collegati
    for drug in hemoglobin_data.get("DrugBank", []):
        drug_id = drug["DrugBank_ID"]
        drug_name = drug["Name"]
        drug_action = drug["Action"]

        # Scrivere il nodo del farmaco se non è già stato scritto
        simplified_action = simplify_action(drug_action)
        if drug_id not in nodes_csv_written:
            if simplified_action != "UNKNOWN":
                nodes_writer.writerow([drug_id, drug_name, "Drug"])
                nodes_csv_written.add(drug_id)

        # Scrivere la relazione tra farmaco e proteina (dal farmaco alla proteina)
        if simplified_action != "UNKNOWN" and (drug_id, hemoglobin_data["Entry"]) not in relationships_csv_written:
            relationships_writer.writerow([hemoglobin_data["Entry"], drug_id, simplified_action])
            relationships_csv_written.add((drug_id, hemoglobin_data["Entry"]))


    # Relazioni tra proteine
    # ottengo gli id delle proteine collegate da proteins_interact.json
    for protein in all_proteins_data:
        if protein.get("Entry") == hemoglobin_data["Entry"]:
            interacts_with = protein.get("Interacts with", "").split(";")
            for interact_id in interacts_with:
                interact_id = interact_id.strip()
                if interact_id:
                    relationships_writer.writerow([
                        hemoglobin_data["Entry"],  # :START_ID (proteina corrente)
                        interact_id,               # :END_ID (proteina con cui interagisce)
                        "INTERACTS_WITH"           # :TYPE (relazione)
                    ])
                # Scrivere il nodo della proteina con cui interagisce se non è già stato scritto
                if interact_id not in nodes_csv_written:
                    for protein_data in all_proteins_data:
                        if protein_data.get("Entry") == interact_id:
                            nodes_writer.writerow([
                                interact_id,                     # uniprot_id:ID
                                extract_short_name(protein_data["Protein names"]),  # Nome abbreviato
                                "Protein"                       # Etichetta
                            ])
                            nodes_csv_written.add(interact_id)
       


print("File CSV generati correttamente")