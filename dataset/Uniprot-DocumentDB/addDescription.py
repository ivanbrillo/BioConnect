import json
import re


# Carica i dati dai due file
with open('output4.json', 'r') as file1, open('UniprotDescription.json', 'r') as file2:
    data1 = json.load(file1)
    data2 = json.load(file2)

# Creare un dizionario dai dati del secondo file per un accesso rapido
# Creare un dizionario dai dati del secondo file per un accesso rapido
entry_to_function = {}
for item in data2:
    function_cc = item.get("Function [CC]", "")
    # Rimuovi parentesi graffe e il loro contenuto, compreso il punto finale
    function_cc = re.sub(r'\{.*?\}\.', '', function_cc).strip()
    entry_to_function[item["Entry"]] = function_cc

# Combina i dati
output_data = []
for item in data1:
    entry_id = item.get("_id")
    function_cc = entry_to_function.get(entry_id, "")
    
    # Aggiungi il campo "description" dopo "name"
    combined_item = {}
    for key, value in item.items():
        combined_item[key] = value
        if key == "name":
            combined_item["description"] = function_cc
    output_data.append(combined_item)

# Scrive i dati combinati nel file output.json
with open('output5.json', 'w') as output_file:
    json.dump(output_data, output_file, indent=4)

print("File output.json creato con successo!")
