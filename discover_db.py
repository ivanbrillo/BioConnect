import pandas as pd
from merger import merged_df
# Load the dataset
# file_path = 'uniprotkb_reviewed_true_AND_model_organ_2024_11_02.tsv'
# df = pd.read_csv(file_path, sep='\t', dtype=str)

df = merged_df

print(len(df))

# Count NaN values for each column
nan_counts = df.isna().sum()
print("NaN Count per Column:")
print(nan_counts, "\n")

# Get representative values for each column
print("Representative Values for Each Column:")
for column in df.columns:
    unique_values = df[column].dropna().unique()  # Get unique values excluding NaNs
    sample_values = unique_values[:5]  # Get up to 5 unique values for a quick overview
    print(f"{column}:")
    print(f"  NaN Count: {nan_counts[column]}")
    print(f"  Sample Values: {sample_values}\n")

pd.set_option('display.max_rows', None)  # Show all rows
pd.set_option('display.max_colwidth', None)  # Show full column width for text

