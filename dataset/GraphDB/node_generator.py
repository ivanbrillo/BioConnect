import json
import csv
import os

# Percorsi dei file di input e output (stessa directory dello script corrente)
script_dir = os.path.dirname(os.path.abspath(__file__))
json_file_path = os.path.join(script_dir, "Hemoglobin.json")
protein_nodes_csv = os.path.join(script_dir, "protein_nodes.csv")
drug_nodes_csv = os.path.join(script_dir, "drug_nodes.csv")
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

# Leggere il file JSON
with open(json_file_path, 'r') as json_file:
    protein_data = json.load(json_file)

# Creare i file CSV
with open(protein_nodes_csv, mode='w', newline='') as protein_file, \
     open(drug_nodes_csv, mode='w', newline='') as drug_file, \
     open(relationships_inhibit_csv, mode='w', newline='') as relationships_inhibit_file, \
     open(relationships_enhance_csv, mode='w', newline='') as relationships_enhance_file:

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
        action = drug.get("Action", "Unknown")

        # Scrivere il nodo del farmaco se non è già stato scritto
        if drug_id not in drug_ids_written:
            drug_writer.writerow([drug_id, drug_name, "Drug"])
            drug_ids_written.add(drug_id)

        # Scrivere la relazione tra farmaco e proteina (dal farmaco alla proteina)
        simplified_action = simplify_action(action)
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