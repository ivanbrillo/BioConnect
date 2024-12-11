import json


def modify_json(input_file, output_file):
    with open(input_file, 'r') as f:
        data = json.load(f)a

    for item in data:
        if "pathways" in item:
            # Create a corrected pathways list
            corrected_pathways = []
            for path in item["pathways"]:
                if path.endswith("."):
                    print(f"Fixing entry: {path}")
                    corrected_pathways.append(path[:-1])  # Remove the last character
                else:
                    corrected_pathways.append(path)
            # Update pathways in item
            item["pathways"] = corrected_pathways

    with open(output_file, 'w') as f:
        json.dump(data, f, indent=4)


# Example usage
input_file = "output4.json"  # Replace with your input file name
output_file = "output5.json"  # Replace with your desired output file name

modify_json(input_file, output_file)
