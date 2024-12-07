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
        return {"PMID": pmid, "Title": title, "Year": year}
    else:
        return {"PMID": pmid, "Error": f"HTTP {response.status_code}"}


def process_pubmed_data(input_file, output_file, start_index=0):
    with open(input_file, 'r') as infile:
        data = json.load(infile)
    
    
    with open(output_file, 'a') as outfile:
        
        for index, entry in enumerate(data[start_index:], start=start_index):
            pubmed_ids = entry.get("PubMed ID", "")
            if pubmed_ids:
                pmid_list = pubmed_ids.split("; ")
                publication_details = []
                
                for pmid in pmid_list:
                    details = fetch_article_details(pmid.strip())
                    publication_details.append(details)
                
                
                entry["PubMed ID"] = publication_details
            
            
            json.dump(entry, outfile, indent=4)
            if index < len(data) - 1:
                outfile.write(",\n")
            
            
            print(f"Proteina {index} processata.")
        
    print(f"Risultati salvati progressivamente in {output_file}")


input_file = "Uniprot_DocumentDB.json" 
output_file = "Uniprot_Daniel.json"
start_index = 15000
process_pubmed_data(input_file, output_file, start_index)
