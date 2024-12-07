import json
from collections import defaultdict

def group_proteins_by_similarity(input_file, output_file):
    # Leggi il file JSON di input
    with open(input_file, 'r') as f:
        proteins = json.load(f)

    # Raggruppa le proteine per "Sequence similarities"
    similarity_groups = defaultdict(list)
    for protein in proteins:
        similarity = protein.get("Sequence similarities")
        if similarity:
            similarity_groups[similarity].append(protein)

    # Aggiungi il campo Protein_simily a ogni proteina
    for protein in proteins:
        similarity = protein.get("Sequence similarities")
        if similarity:
            similar_proteins = similarity_groups[similarity]
            protein["Protein_simily"] = [
                {
                    "Entry": p.get("Entry"),
                    "Entry Name": p.get("Entry Name"),
                    "Protein names": p.get("Protein names")
                }
                for p in similar_proteins if p != protein
            ]

    # Scrivi il risultato in un file JSON di output
    with open(output_file, 'w') as f:
        json.dump(proteins, f, indent=4)

# Esempio di utilizzo
input_file = "Uniprot_DocumentDB.json"
output_file = "Protein-Similarity.json"
group_proteins_by_similarity(input_file, output_file)
