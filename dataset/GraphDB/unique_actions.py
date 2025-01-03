import json
import os

json_file_path = os.path.join("dataset", "GraphDB", "CompletedUniprot.json")

def extract_unique_actions(json_file_path):
    unique_actions = set()  

    with open(json_file_path, 'r') as json_file:
        proteins_data = json.load(json_file)

    for protein in proteins_data.get("Proteins", []):
        for drug in protein.get("DrugBank", []):
            drug_action = drug.get("Action")
            if drug_action:
                unique_actions.add(drug_action)

    return unique_actions

# Estract unique action
actions = extract_unique_actions(json_file_path)

print("\nAzioni uniche trovate nel campo DrugBank:")
for action in sorted(actions):
    print(action + ",")
