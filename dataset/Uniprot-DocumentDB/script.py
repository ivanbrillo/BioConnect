import json
import re


def remove_protein_name(text):
    return re.sub(r'\s*\(.*?\)', '', text)

def process_pathway(text):
    if text.startswith("PATHWAY: "):
        
        cleaned_text = re.sub(r'PATHWAY: ', '', text)
        cleaned_text = re.sub(r'\{.*?\}\.', '', cleaned_text).strip()
        cleaned_text = re.sub(r'\{.*?\}', '', cleaned_text).strip()
        
        cleaned_text = cleaned_text.rstrip('.')
        entries = [item.strip() for item in cleaned_text.split(';') if item.strip()]
        return [entry.capitalize() for entry in entries]
    
    return []


def process_developmental_stage(text):
    if text.startswith("DEVELOPMENTAL STAGE: "):

        cleaned_text = re.sub(r'DEVELOPMENTAL STAGE: ', '', text)
        cleaned_text = re.sub(r'\{.*?\}\.', '', cleaned_text).strip()
        cleaned_text = cleaned_text.rstrip('.')
        return cleaned_text.strip()
    
    return text



def process_subcellular_location(text):
    if text.startswith("SUBCELLULAR LOCATION: "):
        
        cleaned_text = re.sub(r'SUBCELLULAR LOCATION: ', '', text)
        cleaned_text = re.sub(r'Note=.*', '', cleaned_text).strip()
        cleaned_text = re.sub(r'\[.*?\]:', '', cleaned_text)
        cleaned_text = re.sub(r'\{.*?\}', '', cleaned_text).strip()
        cleaned_text = re.sub(r';\s*\.', '.', cleaned_text)
        cleaned_text = cleaned_text.rstrip('.')

        entries = [item.strip().lstrip(';').strip() for item in cleaned_text.split('.') if item.strip()]
        return entries
    return []


def process_sequence_similarities(text):
    if text.startswith("SIMILARITY: "):
        cleaned_text = re.sub(r'SIMILARITY: ', '', text)
        cleaned_text = re.sub(r'\{.*?\}\.', '', cleaned_text).strip()
        cleaned_text = re.sub(r'^Belongs to the ', '', cleaned_text)
        cleaned_text = cleaned_text.capitalize()
        cleaned_text = cleaned_text.rstrip('.')
        return cleaned_text.strip()
    
    return text


def process_json(input_file, output_file):
    with open(input_file, 'r', encoding='utf-8') as f:
        data = json.load(f)

    
    for entry in data:
        if "Protein names" in entry:
            entry["Protein names"] = remove_protein_name(entry["Protein names"])
        if "Pathway" in entry:
            entry["Pathway"] = process_pathway(entry["Pathway"])
        if "Developmental stage" in entry:
            entry["Developmental stage"] = process_developmental_stage(entry["Developmental stage"])
        if "Subcellular location [CC]" in entry:
            entry["Subcellular location [CC]"] = process_subcellular_location(entry["Subcellular location [CC]"])
        if "Sequence similarities" in entry:
            entry["Sequence similarities"] = process_sequence_similarities(entry["Sequence similarities"])
        

    
    with open(output_file, 'w', encoding='utf-8') as f:
        json.dump(data, f, indent=4, ensure_ascii=False)

input_file = 'UniProt.json'
output_file = 'UniProt_DocumentDB.json'
process_json(input_file, output_file)
