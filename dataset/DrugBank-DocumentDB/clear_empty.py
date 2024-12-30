import json

def count_empty_values(item):
    empty_count = 0
    empty_fields = []

    def count_empty(item, path=""):
        nonlocal empty_count
        if item is None or item == "" or (isinstance(item, list) and all(v in [None, "", []] for v in item)):
            empty_count += 1
            if len(empty_fields) < 5:
                empty_fields.append(path)
        elif isinstance(item, dict):
            for key, value in item.items():
                count_empty(value, f"{path}.{key}" if path else key)
        elif isinstance(item, list):
            for index, value in enumerate(item):
                count_empty(value, f"{path}[{index}]")

    count_empty(item)
    return empty_count, empty_fields

def clean_empty_values(item):
    if isinstance(item, dict):
        return {
            k: clean_empty_values(v)
            for k, v in item.items()
            if not (v is None or v == "" or (isinstance(v, list) and all(x in [None, "", []] for x in v)))
        }
    elif isinstance(item, list):
        # Pulizia degli elementi della lista
        cleaned_list = [clean_empty_values(v) for v in item]
        # Rimuove liste che contengono solo elementi vuoti
        return [v for v in cleaned_list if not (v is None or v == "" or (isinstance(v, list) and all(x in [None, "", []] for x in v)))]
    else:
        return item

file_path = 'dataset/DrugBank-DocumentDB/output.json'
output_path = 'dataset/DrugBank-DocumentDB/output2.json'

with open(file_path, 'r') as file:
    data = json.load(file)

# Count empty values before cleanup
empty_values_count_before, empty_fields_before = count_empty_values(data)
print(f'Total empty values in the file before cleanup: {empty_values_count_before}')
print('First 5 empty fields before cleanup:')
for field in empty_fields_before:
    print(field)

# Remove empty values
cleaned_data = clean_empty_values(data)

# Count empty values after cleanup
empty_values_count_after, empty_fields_after = count_empty_values(cleaned_data)
print(f'Total empty values in the file after cleanup: {empty_values_count_after}')
print('First 5 empty fields after cleanup:')
for field in empty_fields_after:
    print(field)

# Save cleaned data to new file
with open(output_path, 'w') as file:
    json.dump(cleaned_data, file, indent=4)
