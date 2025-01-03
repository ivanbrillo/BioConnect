import json

def process_references(references):
    result = []
    entries = references.split(';;')
    
    for entry in entries:
        entry = entry.strip('[]')
        parts = entry.split('::', 1)
        if len(parts) < 2:
            continue
        ref_type, ref_data = parts
        ref_parts = ref_data.split('::', 1)
        
        if ref_type == 'article':
            if len(ref_parts) < 2:
                continue
            id_, rest = ref_parts
            if ':' not in rest:
                continue
            pubmedid, citation = rest.split('::', 1)
            result.append({
                "type": "article",
                "id": id_,
                "pubmedid": pubmedid,
                "citation": citation
            })
        elif ref_type == 'textbook':
            if len(ref_parts) < 2:
                continue
            id_, rest = ref_parts
            if ':' not in rest:
                continue
            isbn, citation = rest.split('::', 1)
            result.append({
                "type": "textbook",
                "id": id_,
                "isbn": isbn,
                "citation": citation
            })
        elif ref_type == 'link':
            if len(ref_parts) < 2:
                continue
            id_, rest = ref_parts
            if ':' not in rest:
                continue
            title, url = rest.split('::', 1)
            result.append({
                "type": "link",
                "id": id_,
                "title": title,
                "url": url
            })
        elif ref_type == 'attachment':
            if len(ref_parts) < 2:
                continue
            id_, rest = ref_parts
            if ':' not in rest:
                continue
            title, url = rest.split('::', 1)
            result.append({
                "type": "attachment",
                "id": id_,
                "title": title,
                "url": url
            })
    
    return result


def process_patents(patents):
    result = []
    entries = patents.split(';;')
    
    for entry in entries:
        parts = entry.strip('[]').split('::')
        if len(parts) == 3:
            patent_obj = {
                "id": parts[0],
                "country": parts[1],
                "date": parts[2]
            }
            result.append(patent_obj)
    
    return result

def process_json(input_file, output_file):
    with open(input_file, 'r') as infile:
        data = json.load(infile)
    
    for item in data:

        if "Categories" in item:
            item["Categories"] = item["Categories"].split(';;')
        

        if "Patents" in item:
            item["Patents"] = process_patents(item["Patents"])
        

        if "References" in item:
            item["References"] = process_references(item["References"])
    
    with open(output_file, 'w') as outfile:
        json.dump(data, outfile, indent=4)
    
    print(f"File processato e salvato come '{output_file}'")

input_file = 'DrugBank_complete.json'
output_file = 'DrugBank_DocumentDB.json'
process_json(input_file, output_file)
