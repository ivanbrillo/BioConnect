import pandas as pd

# Read both CSV files
df1 = pd.read_csv('uniprotkb_reviewed_true_AND_model_organ_2024_11_02.tsv', sep='\t', dtype=str)  # File with DrugCentral column
df2 = pd.read_csv('drug.target.interaction.tsv', sep='\t', dtype=str)  # File with ACCESSION column

print(len(df1))
print(len(df2))

df1['DrugCentral'] = df1['DrugCentral'].str.rstrip(';').str.strip()


# Clean and standardize the columns to be merged on
df1['DrugCentral'] = df1['DrugCentral'].str.strip().str.upper().astype(str)
df2['ACCESSION'] = df2['ACCESSION'].str.strip().str.upper().astype(str)

# Split the ACCESSION column on '|' and explode it to create separate rows
df2_exploded = df2.assign(ACCESSION=df2['ACCESSION'].str.split('|')).explode('ACCESSION')


pd.set_option('display.max_rows', None)  # Show all rows
pd.set_option('display.max_colwidth', None)  # Show full column width for text
#
# print(df2_exploded["ACCESSION"])

# Perform the merge/join
merged_df = df1.merge(
    df2_exploded,
    left_on='DrugCentral',
    right_on='ACCESSION',
    how='left'
)

print(len(merged_df))

# Step 1: Drop the specified columns (e.g., 'B' and 'C')
merged_df = merged_df.drop(columns=['DrugCentral', 'ACCESSION'])

# Step 2: Remove duplicate rows
merged_df = merged_df.drop_duplicates()


# # Check the result
# print("Merged DataFrame Preview:")
# print(merged_df.head())
