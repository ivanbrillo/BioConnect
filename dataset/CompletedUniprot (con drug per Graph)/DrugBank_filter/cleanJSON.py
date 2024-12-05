import json

def transform_target_actions(data):
    # Itera su ogni farmaco nella lista "Drugs"
    for drug in data["Drugs"]:
        if "Target_Actions" in drug:
            # Ottieni il valore di "Target_Actions"
            target_actions = drug["Target_Actions"]
            
            # Dividi per ";" per ottenere i vari target
            targets = target_actions.split(";")
            
            # Lista per contenere i nuovi target formattati
            formatted_targets = []
            
            # Itera su ciascun target e suddividi in UniProtID e Actions
            for target in targets:
                if ":" in target:
                    uniprot_id, action = target.split(":")
                    # Aggiungi il target formattato alla lista
                    formatted_targets.append({
                        "UniProtID": uniprot_id,
                        "Action": action  # Mettendo l'azione in una lista
                    })
            
            # Sostituisci "Target_Actions" con "Target"
            drug["Target"] = formatted_targets
            del drug["Target_Actions"]  # Rimuovi la chiave originale

    return data

# Leggi il file JSON
with open('DrugBank_init.json', 'r') as file:
    data = json.load(file)

# Trasforma il JSON
transformed_data = transform_target_actions(data)

# Scrivi il file JSON trasformato
with open('DrugBank.json', 'w') as file:
    json.dump(transformed_data, file, indent=2)

print("Conversione completata! File salvato come 'DrugBank.json'.")
