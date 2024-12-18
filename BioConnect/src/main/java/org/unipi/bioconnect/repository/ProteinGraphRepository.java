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

    // ? Forse si può togliere e usare quella sotto ?
    @Query("MATCH (p:Protein) WHERE p.uniProtID = $uniProtID RETURN p")
    ProteinGraph findByUniProtID(@Param("uniProtID") String uniProtID);

    // ! automatica da problemi di java heap space
    @Query("MATCH (p:Protein {id: $id}) " +
            "OPTIONAL MATCH (p)-[r:INTERACTS_WITH*1]->(interactedProtein:Protein) " +
            "WHERE p.id <> interactedProtein.id " +
            "RETURN p, collect(r), collect(interactedProtein)")
    ProteinGraph findProteinGraphById(String id);

    // ! automatica da problemi di java heap space
    @Query("MATCH (p:Protein {id: $id}) RETURN p.id AS id, p.name AS name")
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
