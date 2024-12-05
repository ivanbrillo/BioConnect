// Caricare proteine
LOAD CSV WITH HEADERS FROM 'file:///protein_nodes.csv' AS row
MERGE (p:Protein {uniprot_id: row.`uniprot_id:ID`})  // Usa UniProtID come identificatore principale
SET p.name = row.name;

// Caricare farmaci
LOAD CSV WITH HEADERS FROM 'file:///drug_nodes.csv' AS row
MERGE (d:Drug {drugbank_id: row.`drugbank_id:ID`})  // Usa DrugBankID come identificatore principale
SET d.name = row.name;

// Relazione Inhibit
LOAD CSV WITH HEADERS FROM 'file:///relationships_inhibit.csv' AS row
MATCH (d:Drug {drugbank_id: row.`:START_ID`})  // Trova il farmaco con DrugBankID
MATCH (p:Protein {uniprot_id: row.`:END_ID`})  // Trova la proteina con UniProtID
CREATE (d)-[r:INHIBIT]->(p)  // Crea la relazione INHIBIT tra farmaco e proteina
SET r.type = row.`:TYPE`;  // Imposta il tipo della relazione (INHIBIT, ENHANCE)

// Relazione Enhance
LOAD CSV WITH HEADERS FROM 'file:///relationships_enhance.csv' AS row
MATCH (d:Drug {drugbank_id: row.`:START_ID`})  // Trova il farmaco con DrugBankID
MATCH (p:Protein {uniprot_id: row.`:END_ID`})  // Trova la proteina con UniProtID
CREATE (d)-[r:ENHANCE]->(p)  // Crea la relazione ENHANCE tra farmaco e proteina
SET r.type = row.`:TYPE`;  // Imposta il tipo della relazione (INHIBIT, ENHANCE)

// Caricare malattie
LOAD CSV WITH HEADERS FROM 'file:///diseases.csv' AS row
MERGE (d:Disease {name: row.name})
SET d.label = row.`:LABEL`;

// Relazione Disease
LOAD CSV WITH HEADERS FROM 'file:///relationships_disease.csv' AS row
MATCH (p:Protein {uniprot_id: row.`:START_ID`})  // Trova la proteina con UniProtID
MATCH (d:Disease {name: row.`:END_ID`})  // Trova la malattia con il nome
CREATE (p)-[r:INVOLVED_IN]->(d)  // Crea la relazione INVOLVED_IN tra proteina e malattia
SET r.type = row.`:TYPE`;  // Imposta il tipo della relazione