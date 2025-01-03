import json
import os
from tqdm import tqdm

script_dir = os.path.dirname(os.path.abspath(__file__))
proteins_similar_path = os.path.join(script_dir, "Protein-Similarity.json")

def get_base_id(protein_id):
    if '[' in protein_id and ']' in protein_id:
        return re.search(r'\[(.*?)\]', protein_id).group(1)
    return protein_id.split('-')[0]

with open(proteins_similar_path, 'r') as json_file:
    proteins_similar_data = json.load(json_file)

relationships_csv_written = set()

# Counter SIMILAR_TO
similar_to_count = 0

for protein in tqdm(proteins_similar_data, desc="Processing Protein Similarities"):
    start_id = protein.get("Entry")  # Current Protein
    similar_proteins = protein.get("Protein_simily", [])  # Similarity

    for similar_protein in similar_proteins:
        end_id = similar_protein.get("Entry")

        # Counter SIMILAR_TO
        if start_id and end_id:
            
            if (start_id, end_id) not in relationships_csv_written and (end_id, start_id) not in relationships_csv_written:
                similar_to_count += 1
                relationships_csv_written.add((start_id, end_id))

print(f"Numero di relazioni SIMILAR_TO: {similar_to_count}")