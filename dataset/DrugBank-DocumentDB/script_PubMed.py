import requests
import json
from xml.etree import ElementTree as ET

# Funzione per ottenere i dettagli di un articolo da PubMed API
def fetch_article_details(pmid):
    url = f"https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=pubmed&id={pmid}&retmode=xml"
    response = requests.get(url)
    
    if response.status_code == 200:
        root = ET.fromstring(response.content)
        title = root.find(".//ArticleTitle").text if root.find(".//ArticleTitle") is not None else "No title"
        year = root.find(".//PubDate/Year").text if root.find(".//PubDate/Year") is not None else "No year"
        return {"Title": title, "Year": year}
    else:
        return {"Error": f"HTTP {response.status_code}"}


def process_pubmed_data(input_file, output_file, start_index=0):
    with open(input_file, 'r') as infile:
        data = json.load(infile)
    
    
    with open(output_file, 'a') as outfile:
        
        for index, entry in enumerate(data[start_index:], start=start_index):
            # Trova tutti i riferimenti
            references = entry.get("References", [])
            
            # Processa i riferimenti
            processed_references = []
            for ref in references:
                if ref.get("type") == "article" and ref.get("pubmedid"):
                    # Aggiungi dettagli PubMed per gli articoli
                    details = fetch_article_details(ref["pubmedid"])
                    processed_ref = {**ref, **details}
                    processed_references.append(processed_ref)
                else:
                    # Mantieni gli altri riferimenti invariati
                    processed_references.append(ref)
            
            # Sostituisci i riferimenti originali con quelli processati
            entry["References"] = processed_references
            
            
            json.dump(entry, outfile, indent=4)
            if index < len(data) - 1:
                outfile.write(",\n")
            
            
            print(f"Documento {index} processato.")
        
    print(f"Risultati salvati progressivamente in {output_file}")


input_file = "DrugBank_filtered.json"
output_file = "DrugBank_DocumentDB.json"
start_index = 0  # Indice di partenza 
process_pubmed_data(input_file, output_file, start_index)