import pandas as pd

df = pd.read_excel('drug_df.xlsx')

print(df["targets"].unique())
