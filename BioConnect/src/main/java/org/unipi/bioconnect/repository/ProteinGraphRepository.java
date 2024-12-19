package org.unipi.bioconnect.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.unipi.bioconnect.DTO.ProteinDTO;
import org.unipi.bioconnect.model.ProteinGraph;

import java.util.List;

public interface ProteinGraphRepository extends Neo4jRepository<ProteinGraph, String> {

    // ? Togliere ?
    @Query("MATCH (p:Protein) " +
            "OPTIONAL MATCH (p)-[:INTERACTS_WITH]->(interactedProtein:Protein) " +
            "RETURN p.id AS id, " +
            "p.name AS name, " +
            "COLLECT(interactedProtein.id) AS interactingProteins")
    List<ProteinDTO> findAllProjectedBy();

    @Query("MATCH (p:Protein) WHERE p.id = $uniProtID \n" +
            "OPTIONAL MATCH (p)-[r:INTERACTS_WITH]-(related:Protein) \n" +
            "OPTIONAL MATCH (p)-[r2:SIMILAR_TO]-(similar:Protein) \n" +
            "OPTIONAL MATCH (p)-[r3:INVOLVED_IN]->(d:Disease) \n" +
            "OPTIONAL MATCH (p)-[r4:INHIBITED_BY]->(drug:Drug) \n" +
            "OPTIONAL MATCH (p)-[r5:ENHANCED_BY]->(drug2:Drug) \n" +
            "RETURN p,collect(r), collect(related), collect(r2), collect(similar), collect(r3), collect(d), collect(r4), collect(drug), collect(r5), collect(drug2)")
    ProteinGraph findByUniProtID(@Param("uniProtID") String uniProtID);

    // ! automatica da problemi di java heap space
    @Query("MATCH (p:Protein) WHERE p.id = $uniProtID \n" +
            "OPTIONAL MATCH (p)-[r:INTERACTS_WITH]-(related:Protein) \n" +
            "RETURN p,collect(r), collect(related)")
    ProteinGraph findProteinGraphById(String uniProtID);

    // ! automatica da problemi di java heap space, controllare se funziona con query custom
    @Query("MATCH (p:Protein {id: $id}) DELETE p")
    void deleteProteinGraphById(String id);

    // * Cancella tutte le interazioni di una proteina nel db (per update method)
    @Query("MATCH (p:Protein {id: $id})-[r:INTERACTS_WITH]->() DELETE r")
    void deleteInteractionsById(@Param("id") String id);

    // ? togliere ?
    @Query("MATCH (p:Protein {id: $id}) " +
        "OPTIONAL MATCH (p)-[:INTERACTS_WITH]->(interactedProtein:Protein) " +
        "RETURN p.id AS id, p.name AS name, COLLECT(interactedProtein.id) AS interacts")
    ProteinGraph findProjectedById(@Param("id") String id);

    // * TEST
    @Query("MATCH (p:Protein) " +
            "OPTIONAL MATCH (p)-[:INTERACTS_WITH]->(interactedProtein:Protein) " +
            "RETURN p.id AS id, " +
            "p.name AS name, " +
            "COLLECT(interactedProtein.id) AS interactingProteins " +
            "LIMIT 3")
    List<ProteinGraph> findTopThreeProteins();
}
