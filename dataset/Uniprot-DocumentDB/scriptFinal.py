import json


def modify_json(input_file, output_file):
    with open(input_file, 'r') as f:
        data = json.load(f)

    for item in data:
        # if "pathways" in item:
        #     # Create a corrected pathways list
        #     corrected_pathways = []
        #     for path in item["pathways"]:
        #         if path.endswith("."):
        #             print(f"Fixing entry: {path}")
        #             corrected_pathways.append(path[:-1])  # Remove the last character
        #         else:
        #             corrected_pathways.append(path)
        #     # Update pathways in item
        #     item["pathways"] = corrected_pathways

        if "publications" in item:
            for reference in item["publications"]:
                reference["year"] = int(reference["year"])

    with open(output_file, 'w') as f:
        json.dump(data, f, indent=4)


# Example usage
input_file = "output5.json"
output_file = "output6.json"

modify_json(input_file, output_file)
