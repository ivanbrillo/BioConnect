import json
import re


def modify_json(input_file, output_file):
    with open(input_file, 'r') as f:
        data = json.load(f)

    # Function to change the first letter of each field to lowercase
    def rename_fields(item):
        # Create a new dictionary with lowercase field names
        new_item = {}
        for key, value in item.items():
            # Convert the first character of the key to lowercase
            new_key = key[0].lower() + key[1:] if key else key
            # If the value is a dictionary, recursively rename its keys
            if isinstance(value, dict):
                value = rename_fields(value)
            # If the value is a list, check if it contains dictionaries and rename fields in those
            elif isinstance(value, list):
                value = [rename_fields(i) if isinstance(i, dict) else i for i in value]
            new_item[new_key] = value
        return new_item

    # Apply the renaming function to each item in the data
    modified_data = [rename_fields(item) for item in data]

    # Save the modified data to the output file
    with open(output_file, 'w') as f:
        json.dump(modified_data, f, indent=4)


import json


def modify_json2(input_file, output_file, pub_fields_to_keep, patent_fields_to_keep):
    with open(input_file, 'r') as f:
        data = json.load(f)

    # Function to retain only specific fields in the 'publications' and 'patents' arrays
    def filter_publications_and_patents(item):
        # Filter the fields in the 'publications' array
        if "references" in item:
            item["references"] = [
                {key: pub[key] for key in pub_fields_to_keep if key in pub} for pub in item["references"]
            ]
            # Keep only those references that have the 'year' field
            item["references"] = [ref for ref in item["references"] if "year" in ref and ref["year"] != "No year"]

            for reference in item["references"]:
                reference["year"] = int(reference["year"])

        # Filter the fields in the 'patents' array
        if "patents" in item:
            item["patents"] = [
                {key: patent[key] for key in patent_fields_to_keep if key in patent} for patent in item["patents"]
            ]
            # Keep only those patents that have the 'date' field
            item["patents"] = [ref for ref in item["patents"] if "date" in ref]

            for patent in item["patents"]:
                patent["year"] = int(patent["date"].split("-")[0])  # Extract the year
                del patent["date"]  # Remove the "date" field

            # Sort patents by year and keep only the most recent patent for each country
            seen_countries = set()
            unique_patents = []
            for patent in sorted(item["patents"], key=lambda p: p["year"], reverse=True):
                if patent["country"] not in seen_countries:
                    seen_countries.add(patent["country"])
                    unique_patents.append(patent)
            item["patents"] = unique_patents

        # Rename 'references' to 'publications'
        if "references" in item:
            item["publications"] = item.pop("references")

        # Rename 'drugBank_ID' to 'drugBankID'
        if "drugBank_ID" in item:
            item["_id"] = item.pop("drugBank_ID")

        # if "sequence" in item:
        #     sequence = item["sequence"]
        #     # Regex to remove any lowercase words before the sequence and keep only the sequence part
        #     match = re.search(r"[a-z]+([A-Z].*)", sequence)
        #     if match:
        #         item["sequence"] = match.group(1).strip()  # Keep everything after the lowercase word

        return item

    # Process each item in the data and apply the filtering function
    processed_data = [filter_publications_and_patents(item) for item in data]

    # Save the modified data to the output file
    with open(output_file, 'w') as f:
        json.dump(processed_data, f, indent=4)


# Example usage
# input_file = "output4.json"  # Replace with your input file name
# output_file = "output5.json"  # Replace with your desired output file name
# pub_fields_to_keep = ["title", "year", "type"]  # Replace with the actual fields you want to keep in 'publications'
# patent_fields_to_keep = ["country", "year"]  # Replace with the actual fields you want to keep in 'patents'
#
# modify_json2(input_file, output_file, pub_fields_to_keep, patent_fields_to_keep)

# Example usage
input_file = "DrugBank_DocumentDB.json"  # Replace with your input file name
output_file = "output.json"  # Replace with your desired output file name

modify_json(input_file, output_file)
modify_json2(output_file, output_file, ["type", "title", "year"], ["country", "date"])
