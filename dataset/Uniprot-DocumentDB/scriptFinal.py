import json


def modify_json(input_file, output_file):
    with open(input_file, 'r') as f:
        data = json.load(f)

    for item in data:
        # Remove unwanted fields
        # fields_to_remove = [
        #     "Length", "Sequence similarities",
        #     "Developmental stage", "Gene Names", "Entry Name"
        # ]
        # for field in fields_to_remove:
        #     item.pop(field, None)

        # Modify PubMed ID list
        if "publications" in item:
            for pubmed in item["publications"]:
                if "year" not in pubmed :
                    print(f"Entry f{pubmed} is missing 'year' field.")
                    # item["publications"].remove(pubmed)
                # pubmed.pop("PMID", None)
                # pubmed["type"] = "article"

        # Rename "PubMed ID" to keep only the modified structure
        # item["PubMed ID"] = item.pop("PubMed ID", [])

    # with open(output_file, 'w') as f:
    #     json.dump(data, f, indent=4)


# Example usage
input_file = "output.json"  # Replace with your input file name
output_file = "output4.json"  # Replace with your desired output file name

modify_json(input_file, output_file)
