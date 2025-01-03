import json

def transform_target_actions(data):
    
    for drug in data["Drugs"]:
        if "Target_Actions" in drug:
            target_actions = drug["Target_Actions"]
            
            targets = target_actions.split(";")
            
            formatted_targets = []
            
            for target in targets:
                if ":" in target:
                    uniprot_id, action = target.split(":")

                    formatted_targets.append({
                        "UniProtID": uniprot_id,
                        "Action": action
                    })
            
            drug["Target"] = formatted_targets
            del drug["Target_Actions"]

    return data

with open('DrugBank_init.json', 'r') as file:
    data = json.load(file)

transformed_data = transform_target_actions(data)

with open('DrugBank.json', 'w') as file:
    json.dump(transformed_data, file, indent=2)

print("Conversione completata! File salvato come 'DrugBank.json'.")
