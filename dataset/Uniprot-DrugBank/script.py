import json

# Load data from JSON files
def load_json(file_path):
    with open(file_path, 'r') as file:
        return json.load(file)

# Save data to a JSON file
def save_json(data, file_path):
    with open(file_path, 'w') as file:
        json.dump(data, file, indent=2)

# Function to find drug details by DrugBank_ID in DrugBank.json
def find_drug_in_drugbank(drugbank_data, drug_id):
    return next((drug for drug in drugbank_data['Drugs'] if drug['DrugBank_ID'] == drug_id), None)

# Function to extract relevant drug info for a given UniProt Entry
def extract_drug_info(drug, uniprot_entry):
    drug_info_list = []
    for target in drug.get('Target', []):
        if target['UniProtID'] == uniprot_entry:
            drug_info = {
                "DrugBank_ID": drug['DrugBank_ID'],
                "Name": drug['Name'],
                "Action": target.get('Action', None)
            }
            drug_info_list.append(drug_info)
    return drug_info_list

# Main function to process Uniprot data
def process_uniprot_data(uniprot_data, drugbank_data):
    for entry in uniprot_data['Proteins']:

        drugbank_ids = entry.get('DrugBank', '').split(';')
        drugbank_ids = [db_id.strip() for db_id in drugbank_ids if db_id.strip()]

        detailed_drugs = []

        for drug_id in drugbank_ids:
            
            drug_details = find_drug_in_drugbank(drugbank_data, drug_id)
            if drug_details:
                drug_targets = extract_drug_info(drug_details, entry['Entry'])
                if drug_targets:
                    detailed_drugs.extend(drug_targets)


        # Update the entry's DrugBank field with detailed drug information
        entry['DrugBank'] = detailed_drugs
    
    return uniprot_data

uniprot_data = load_json('Uniprot.json')
drugbank_data = load_json('DrugBank.json')

# Process the Uniprot data
updated_data = process_uniprot_data(uniprot_data, drugbank_data)

# Save the updated data to a new JSON file
save_json(updated_data, 'CompletedUniprot.json')

print("Data processing complete. Saved to CompletedUniprot.json.")
