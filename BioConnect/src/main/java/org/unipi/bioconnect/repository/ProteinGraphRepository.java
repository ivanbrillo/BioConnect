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
            "RETURN p.uniProtID AS uniProtID, " +
            "p.name AS name, " +
            "COLLECT(interactedProtein.uniProtID) AS interactingProteins")
    List<ProteinDTO> findAllProjectedBy();

    // ? Forse si può togliere e usare quella sotto ?
    @Query("MATCH (p:Protein) WHERE p.uniProtID = $uniProtID RETURN p")
    ProteinGraph findByUniProtID(@Param("uniProtID") String uniProtID);

    // ! automatica senza query custom ma con probabile problema id()
    ProteinGraph findProteinGraphByUniProtID(String uniProtID);

    // ! automatica senza query custom ma con probabile problema id()
    void deleteProteinGraphByUniProtID(String uniProtID);

    // * Cancella tutte le interazioni di una proteina nel db (per update method)
    @Query("MATCH (p:Protein {uniProtID: $uniProtID})-[r:INTERACTS_WITH]->() DELETE r")
    void deleteInteractionsByUniProtID(@Param("uniProtID") String uniProtID);

    // ? togliere ?
    @Query("MATCH (p:Protein {uniProtID: $uniProtID}) " +
        "OPTIONAL MATCH (p)-[:INTERACTS_WITH]->(interactedProtein:Protein) " +
        "RETURN p.uniProtID AS uniProtID, p.name AS name, COLLECT(interactedProtein.uniProtID) AS interacts")
    ProteinGraph findProjectedByUniProtID(@Param("uniProtID") String uniProtID);

    // * TEST
    @Query("MATCH (p:Protein) " +
            "OPTIONAL MATCH (p)-[:INTERACTS_WITH]->(interactedProtein:Protein) " +
            "RETURN p.uniProtID AS uniProtID, " +
            "p.name AS name, " +
            "COLLECT(interactedProtein.uniProtID) AS interactingProteins " +
            "LIMIT 3")
    List<ProteinGraph> findTopThreeProteins();
}
