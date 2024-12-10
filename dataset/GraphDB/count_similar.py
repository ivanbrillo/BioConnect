import json
import os
from tqdm import tqdm

# Percorso del file JSON
script_dir = os.path.dirname(os.path.abspath(__file__))
proteins_similar_path = os.path.join(script_dir, "Protein-Similarity.json")

# Funzione per ottenere la parte prima del trattino o la parte interna alle parentesi quadre
def get_base_id(protein_id):
    if '[' in protein_id and ']' in protein_id:
        return re.search(r'\[(.*?)\]', protein_id).group(1)
    return protein_id.split('-')[0]

# Leggere il file JSON
with open(proteins_similar_path, 'r') as json_file:
    proteins_similar_data = json.load(json_file)

# Set per evitare duplicati delle relazioni
relationships_csv_written = set()

# Contare le relazioni SIMILAR_TO
similar_to_count = 0

# Iterare su tutte le proteine nel dataset
for protein in tqdm(proteins_similar_data, desc="Processing Protein Similarities"):
    start_id = protein.get("Entry")  # Proteina corrente
    similar_proteins = protein.get("Protein_simily", [])  # Proteine simili

    for similar_protein in similar_proteins:
        end_id = similar_protein.get("Entry")  # ID della proteina simile

        # Contare la relazione SIMILAR_TO
        if start_id and end_id:
            # Evita duplicati nelle relazioni
            if (start_id, end_id) not in relationships_csv_written and (end_id, start_id) not in relationships_csv_written:
                similar_to_count += 1
                relationships_csv_written.add((start_id, end_id))

print(f"Numero di relazioni SIMILAR_TO: {similar_to_count}")