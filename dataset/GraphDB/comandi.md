Dopo aver importato i file CSV nella cartella di import di Neo4j:

- **Caricare proteine**:
    ```cypher
    LOAD CSV WITH HEADERS FROM 'file:///protein_nodes.csv' AS row
    MERGE (p:Protein {uniprot_id: row.`uniprot_id:ID`})  // Usa UniProtID come identificatore principale
    SET p.name = row.name;
    ```

- **Caricare farmaci**:
    ```cypher
    LOAD CSV WITH HEADERS FROM 'file:///drug_nodes.csv' AS row
    MERGE (d:Drug {drugbank_id: row.`drugbank_id:ID`})  // Usa DrugBankID come identificatore principale
    SET d.name = row.name;
    ```

- **Relazione Inhibit**:
    ```cypher
    LOAD CSV WITH HEADERS FROM 'file:///relationships_inhibit.csv' AS row
    MATCH (d:Drug {drugbank_id: row.`:START_ID`})  // Trova il farmaco con DrugBankID
    MATCH (p:Protein {uniprot_id: row.`:END_ID`})  // Trova la proteina con UniProtID
    CREATE (d)-[r:INHIBIT]->(p)  // Crea la relazione INHIBIT tra farmaco e proteina
    SET r.type = row.`:TYPE`;  // Imposta il tipo della relazione (INHIBIT, ENHANCE)
    ```

- **Relazione Enhance**:
    ```cypher
    LOAD CSV WITH HEADERS FROM 'file:///relationships_enhance.csv' AS row
    MATCH (d:Drug {drugbank_id: row.`:START_ID`})  // Trova il farmaco con DrugBankID
    MATCH (p:Protein {uniprot_id: row.`:END_ID`})  // Trova la proteina con UniProtID
    CREATE (d)-[r:ENHANCE]->(p)  // Crea la relazione ENHANCE tra farmaco e proteina
    SET r.type = row.`:TYPE`;  // Imposta il tipo della relazione (INHIBIT, ENHANCE)
    ```

## Da risolvere
- Trovare un modo per tipo di relazione dinamico in unico file per evitare un file diverso per ogni tipo di relazione. 
- Provare con unico file con intero codice Cypher.

## Da aggiungere
- Dettagli sui disease coinvolti.
