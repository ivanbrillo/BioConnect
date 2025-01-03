import json
import re


with open('output4.json', 'r') as file1, open('UniprotDescription.json', 'r') as file2:
    data1 = json.load(file1)
    data2 = json.load(file2)

entry_to_function = {}
for item in data2:
    function_cc = item.get("Function [CC]", "")
    function_cc = re.sub(r'\{.*?\}\.', '', function_cc).strip()
    entry_to_function[item["Entry"]] = function_cc

output_data = []
for item in data1:
    entry_id = item.get("_id")
    function_cc = entry_to_function.get(entry_id, "")
    
    # Add "description" after "name"
    combined_item = {}
    for key, value in item.items():
        combined_item[key] = value
        if key == "name":
            combined_item["description"] = function_cc
    output_data.append(combined_item)

with open('output5.json', 'w') as output_file:
    json.dump(output_data, output_file, indent=4)

print("File output.json creato con successo!")
