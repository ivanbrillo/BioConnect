import json

def count_empty_values(item):
    empty_count = 0

    def count_empty(item):
        nonlocal empty_count
        if item is None or item == "" or (isinstance(item, list) and len(item) == 0):
            empty_count += 1
        elif isinstance(item, dict):
            for value in item.values():
                count_empty(value)
        elif isinstance(item, list):
            for value in item:
                count_empty(value)

    count_empty(item)
    return empty_count

def remove_empty_fields(item):
    if isinstance(item, dict):
        return {k: remove_empty_fields(v) for k, v in item.items() if v not in [None, "", []]}
    elif isinstance(item, list):
        return [remove_empty_fields(v) for v in item if v not in [None, "", []]]
    else:
        return item

file_path = 'c:/Users/danie/OneDrive/Desktop/AIDE - UniPi LM/1 Anno/1sem. Large Scale/Progetto/medical_db/dataset/Uniprot-DocumentDB/output6.json'
output_path = 'c:/Users/danie/OneDrive/Desktop/AIDE - UniPi LM/1 Anno/1sem. Large Scale/Progetto/medical_db/dataset/Uniprot-DocumentDB/output7.json'

with open(file_path, 'r') as file:
    data = json.load(file)

empty_values_count_before = count_empty_values(data)
print(f'Total empty values before cleaning: {empty_values_count_before}')

cleaned_data = remove_empty_fields(data)

empty_values_count_after = count_empty_values(cleaned_data)
print(f'Total empty values after cleaning: {empty_values_count_after}')

with open(output_path, 'w') as file:
    json.dump(cleaned_data, file, indent=4)