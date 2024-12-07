
//Caricare nodi
LOAD CSV WITH HEADERS FROM 'file:///nodes.csv' AS row
CALL apoc.merge.node(
    [row.type],               // Label dinamica del nodo (Protein, Disease, Drug)
    {id: row.`id:ID`},        // Chiave primaria per evitare duplicati
    {                         // Proprietà del nodo
        id: row.`id:ID`,
        name: row.name
    }
) YIELD node
RETURN node;


//Caricare relazioni
LOAD CSV WITH HEADERS FROM 'file:///relationships.csv' AS row
MATCH (startNode {id: row.`:START_ID`})  // Trova il nodo iniziale
MATCH (endNode {id: row.`:END_ID`})    // Trova il nodo finale
CALL apoc.merge.relationship(
    startNode,        // Nodo iniziale
    row.`:TYPE`,      // Tipo di relazione dinamico
    {},               // Nessuna proprietà chiave (opzionale)
    {},               // Nessuna proprietà aggiuntiva (opzionale)
    endNode           // Nodo finale
) YIELD rel
RETURN rel;