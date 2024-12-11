# Installazione
- Aprite Neo4j desktop e create un nuovo local DBMS
- Mettete i due file csv nella cartella di import del dbms:
    - [nodes.csv](./nodes.csv)
    - [relationships.csv](./relationships.csv)
    - cliccare sui tre punti accanto al dbms corrente, Open folder e poi import, incollate in quella cartella
- Dopo aver importato i file CSV nella cartella di import di Neo4j:
    - ⛔O eseguite il codice di load_cypher ma ci mette moooolto tempo
    - ✅Oppure andate nella cartella bin all'interno del dbms ed eseguite il comando (ovviamente cambiando i percorsi dei due file)

```bash
neo4j-admin database import full ^
     --nodes="C:\Users\danie\.Neo4jDesktop\relate-data\dbmss\dbms-d0c7f232-59a5-46f7-8886-7610afb73ff7\import\nodes.csv" ^
     --relationships="C:\Users\danie\.Neo4jDesktop\relate-data\dbmss\dbms-d0c7f232-59a5-46f7-8886-7610afb73ff7\import\relationships.csv" ^
     --overwrite-destination ^
     --verbose
```
- nel file delle [query neo4j](./query%20Neo4J%20Bioconnet.cypher) trovate le query che abbiamo descritto nel PowerPoint
  - Potete eseguirle avviando neo4j browser

### Da risolvere
- [x] Trovare un modo per tipo di relazione dinamico in unico file per evitare un file diverso per ogni tipo di relazione.
- [x] Provare con unico file con intero codice Cypher.
- [x] Aggiunge virgolette alla fine di una malattia e non si riesce a risolvere senza incasinare gli altri.
- [x] Un farmaco rimane solo
- [x] Togliere interazioni duplicate tra due proteine
- [x] Modificare questo file

### Da aggiungere
- [x] Dettagli sui disease coinvolti.
- [x] Interazioni con altre proteine.
- [x] proteine simili

### Dubbi
- [x] Interazioni Drug-Protein o viceversa?
- [x] Come definiamo il similar to tra protein-protein?