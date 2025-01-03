import json
import re


def modify_json(input_file, output_file):
    with open(input_file, 'r') as f:
        data = json.load(f)

    # Function to change the first letter of each field to lowercase
    def rename_fields(item):
        new_item = {}
        for key, value in item.items():

            new_key = key[0].lower() + key[1:] if key else key

            if isinstance(value, dict):
                value = rename_fields(value)
            
            elif isinstance(value, list):
                value = [rename_fields(i) if isinstance(i, dict) else i for i in value]
            new_item[new_key] = value
        return new_item

    modified_data = [rename_fields(item) for item in data]

    with open(output_file, 'w') as f:
        json.dump(modified_data, f, indent=4)


import json


def modify_json2(input_file, output_file, pub_fields_to_keep, patent_fields_to_keep):
    with open(input_file, 'r') as f:
        data = json.load(f)

    # Function to retain only specific fields in the 'publications' and 'patents' arrays
    def filter_publications_and_patents(item):

        if "references" in item:
            item["references"] = [
                {key: pub[key] for key in pub_fields_to_keep if key in pub} for pub in item["references"]
            ]

            item["references"] = [ref for ref in item["references"] if "year" in ref and ref["year"] != "No year"]

            for reference in item["references"]:
                reference["year"] = int(reference["year"])

        # Filter the fields in the 'patents' array
        if "patents" in item:
            item["patents"] = [
                {key: patent[key] for key in patent_fields_to_keep if key in patent} for patent in item["patents"]
            ]

            item["patents"] = [ref for ref in item["patents"] if "date" in ref]

            for patent in item["patents"]:
                patent["year"] = int(patent["date"].split("-")[0])
                del patent["date"]  # Remove the "date" field

            seen_countries = set()
            unique_patents = []
            for patent in sorted(item["patents"], key=lambda p: p["year"], reverse=True):
                if patent["country"] not in seen_countries:
                    seen_countries.add(patent["country"])
                    unique_patents.append(patent)
            item["patents"] = unique_patents

        if "references" in item:
            item["publications"] = item.pop("references")

        if "drugBank_ID" in item:
            item["_id"] = item.pop("drugBank_ID")


        return item

    processed_data = [filter_publications_and_patents(item) for item in data]

    with open(output_file, 'w') as f:
        json.dump(processed_data, f, indent=4)


# Example usage
input_file = "DrugBank_DocumentDB.json"
output_file = "output.json"

modify_json(input_file, output_file)
modify_json2(output_file, output_file, ["type", "title", "year"], ["country", "date"])
