import json
import csv
import os
import re

# Percorsi dei file di input e output (stessa directory dello script corrente)
script_dir = os.path.dirname(os.path.abspath(__file__))
json_file_path = os.path.join(script_dir, "Hemoglobin.json")
protein_nodes_csv = os.path.join(script_dir, "protein_nodes.csv")
drug_nodes_csv = os.path.join(script_dir, "drug_nodes.csv")
diseases_csv = os.path.join(script_dir, "diseases.csv")
relationships_disease_csv = os.path.join(script_dir, "relationships_disease.csv")
relationships_inhibit_csv = os.path.join(script_dir, "relationships_inhibit.csv")
relationships_enhance_csv = os.path.join(script_dir, "relationships_enhance.csv")

# Funzione per abbreviare il nome della proteina
def extract_short_name(protein_name):
    return protein_name.split('(')[0].split(';')[0].strip()

# Funzione per semplificare il campo "Action" dei drug
def simplify_action(action):
    if action.lower() in ["inhibitor", "antagonist", "oxidizer", "inducer"]:
        return "INHIBIT"
    elif action.lower() in ["agonist", "activator", "binder", "enhancer"]:
        return "ENHANCE"
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



# Leggere il file JSON
with open(json_file_path, 'r') as json_file:
    protein_data = json.load(json_file)

# Creare i file CSV
with open(protein_nodes_csv, mode='w', newline='') as protein_file, \
     open(drug_nodes_csv, mode='w', newline='') as drug_file, \
     open(relationships_inhibit_csv, mode='w', newline='') as relationships_inhibit_file, \
     open(relationships_enhance_csv, mode='w', newline='') as relationships_enhance_file, \
     open(diseases_csv, mode='w', newline='') as diseases_file, \
     open(relationships_disease_csv, mode='w', newline='') as relationships_treat_file:
    
    # Writer per nodi delle proteine
    protein_writer = csv.writer(protein_file)
    protein_writer.writerow(["uniprot_id:ID", "name", ":LABEL"])  # Intestazione per i nodi delle proteine

    # Writer per nodi dei farmaci
    drug_writer = csv.writer(drug_file)
    drug_writer.writerow(["drugbank_id:ID", "name", ":LABEL"])  # Intestazione per i nodi dei farmaci

    # Writer per le relazioni INHIBIT
    relationships_inhibit_writer = csv.writer(relationships_inhibit_file)
    relationships_inhibit_writer.writerow([":START_ID", ":END_ID", ":TYPE"])  # Intestazione per le relazioni INHIBIT

    # Writer per le relazioni ENHANCE
    relationships_enhance_writer = csv.writer(relationships_enhance_file)
    relationships_enhance_writer.writerow([":START_ID", ":END_ID", ":TYPE"])  # Intestazione per le relazioni ENHANCE

    # Writer per nodi delle malattie
    diseases_writer = csv.writer(diseases_file)
    diseases_writer.writerow(["name", ":LABEL"])  # Intestazione per i nodi delle malattie
    diseases_written = set()

    # Writer per le relazioni protein-disease
    relationships_disease_csv_writer = csv.writer(relationships_treat_file)
    relationships_disease_csv_writer.writerow([":START_ID", ":END_ID", ":TYPE"])  # Intestazione per le relazioni protein-disease
    relationships_disease_csv_written = set()

    # Estrai e scrivi le malattie
    if "Involvement in disease" in protein_data:
        diseases = protein_data["Involvement in disease"].split(";")
        for disease in diseases:
            disease_name = extract_disease_name(disease.strip())
            if disease_name not in diseases_written:
                diseases_writer.writerow([disease_name, "Disease"])  # Scrivi il nodo malattia
                diseases_written.add(disease_name)
                relationships_disease_csv_writer.writerow([protein_data["Entry"], disease_name, "INVOLVED_IN"])
                relationships_disease_csv_written.add((protein_data["Entry"], disease_name))


    # Scrivere il nodo della proteina
    protein_writer.writerow([
        protein_data["Entry"],                     # uniprot_id:ID
        extract_short_name(protein_data["Protein names"]),  # Nome abbreviato
        "Protein"                                 # Etichetta
    ])

    # Set per evitare duplicati nei nodi dei farmaci
    drug_ids_written = set()

    # Iterare sui drug collegati
    for drug in protein_data.get("DrugBank", []):
        drug_id = drug["DrugBank_ID"]
        drug_name = drug["Name"]
        drug_action = drug["Action"]

        # Scrivere il nodo del farmaco se non è già stato scritto
        if drug_id not in drug_ids_written:
            if drug_action != "NULL":
                drug_writer.writerow([drug_id, drug_name, "Drug"])
                drug_ids_written.add(drug_id)

        # Scrivere la relazione tra farmaco e proteina (dal farmaco alla proteina)
        simplified_action = simplify_action(drug_action)
        if simplified_action == "INHIBIT":
            relationships_inhibit_writer.writerow([
                drug_id,                # :START_ID (farmaco)
                protein_data["Entry"],  # :END_ID (proteina)
                simplified_action       # :TYPE (relazione semplificata, INHIBIT)
            ])
        elif simplified_action == "ENHANCE":
            relationships_enhance_writer.writerow([
                drug_id,                # :START_ID (farmaco)
                protein_data["Entry"],  # :END_ID (proteina)
                simplified_action       # :TYPE (relazione semplificata, ENHANCE)
            ])

       


print(f"File dei nodi delle proteine creato: {protein_nodes_csv}")
print(f"File dei nodi dei farmaci creato: {drug_nodes_csv}")
print(f"File delle relazioni INHIBIT creato: {relationships_inhibit_csv}")
print(f"File delle relazioni ENHANCE creato: {relationships_enhance_csv}")
print(f"File delle malattie creato: {diseases_csv}")
print(f"File delle relazioni TREAT creato: {relationships_disease_csv}")
