import json
import os

# Percorso del file JSON
json_file_path = os.path.join("dataset", "GraphDB", "CompletedUniprot.json")

def extract_unique_actions(json_file_path):
    unique_actions = set()  # Set per memorizzare azioni uniche

    # Leggi il file JSON
    with open(json_file_path, 'r') as json_file:
        proteins_data = json.load(json_file)

    # Itera su ogni proteina
    for protein in proteins_data.get("Proteins", []):
        for drug in protein.get("DrugBank", []):
            drug_action = drug.get("Action")
            if drug_action:
                unique_actions.add(drug_action)

    return unique_actions

# Estrazione delle azioni uniche
actions = extract_unique_actions(json_file_path)

# Stampa le azioni trovate
print("\nAzioni uniche trovate nel campo DrugBank:")
for action in sorted(actions):
    print(action + ",")
