package org.unipi.bioconnect.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.unipi.bioconnect.DTO.ProteinDTO;
import org.unipi.bioconnect.model.ProteinGraph;

import java.util.List;

public interface ProteinGraphRepository extends Neo4jRepository<ProteinGraph, String> {
    @Query("MATCH (p:Protein) " +
            "OPTIONAL MATCH (p)-[:INTERACTS_WITH]->(interactedProtein:Protein) " +
            "RETURN p.uniProtID AS uniProtID, " +
            "p.name AS name, " +
            "COLLECT(interactedProtein.uniProtID) AS interactingProteins")
    List<ProteinDTO> findAllProjectedBy();

   @Query("MATCH (p:Protein) " +
    "OPTIONAL MATCH (p)-[:INTERACTS_WITH]->(interactedProtein:Protein) " +
    "RETURN p.uniProtID AS uniProtID, " +
    "p.name AS name, " +
    "COLLECT(interactedProtein.uniProtID) AS interactingProteins " +
    "LIMIT 3")
    List<ProteinGraph> findTopThreeProteins();

    ProteinGraph findByUniProtID(String uniProtID);


}
