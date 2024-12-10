//1. Query - Drug target similar proteins from one given
match (d:Drug)-[]-(p:Protein)-[:SIMILAR_TO]->(p2:Protein)
where toLower(p2.name) CONTAINS toLower("hemoglobin")
return d,p,p2
limit 10

//2. Shortest Path Diseases
MATCH p = shortestPath((disease1:Disease)-[*..4]-(disease2:Disease))
WHERE toLower(disease1.name) CONTAINS toLower("Sickle cell") AND toLower(disease2.name) CONTAINS toLower("Alzheimer disease")
RETURN p, length(p) AS pathLength;

//3. Diseases linked to a particular drug
MATCH (disease:Disease)<-[:INVOLVED_IN]-(p:Protein)-[]-(drug:Drug)
WHERE drug.name CONTAINS "Acetylsalicylic acid"
RETURN disease, drug, p


//4. Drugs with opposite effects on a same protein
MATCH (d_inibith:Drug)<-[:INHIBITED_BY]-(p:Protein)-[:ENHANCED_BY]->(d_enhance:Drug)
WHERE p.name = "Hemoglobin subunit beta"
RETURN d_inibith as DRUG_INIBITH, p, d_enhance as DRUG_ENHANCE