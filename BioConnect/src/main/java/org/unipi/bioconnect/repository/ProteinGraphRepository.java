package org.unipi.bioconnect.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.unipi.bioconnect.DTO.Graph.BaseNodeDTO;
import org.unipi.bioconnect.model.ProteinGraph;

import java.util.List;
import java.util.Map;

public interface ProteinGraphRepository extends Neo4jRepository<ProteinGraph, String> {


    @Query("MATCH (p:Protein) WHERE p.id = $uniProtID \n" +
            "OPTIONAL MATCH (p)-[r:INTERACTS_WITH]-(related:Protein) \n" +
            "OPTIONAL MATCH (p)-[r2:SIMILAR_TO]-(similar:Protein) \n" +
            "OPTIONAL MATCH (p)-[r3:INVOLVED_IN]->(d:Disease) \n" +
            "OPTIONAL MATCH (p)-[r4:INHIBITED_BY]->(drug:Drug) \n" +
            "OPTIONAL MATCH (p)-[r5:ENHANCED_BY]->(drug2:Drug) \n" +
            "RETURN p,collect(r), collect(related), collect(r2), collect(similar), collect(r3), collect(d), collect(r4), collect(drug), collect(r5), collect(drug2)")
    ProteinGraph findByUniProtID(@Param("uniProtID") String uniProtID);


    @Query("""
                MATCH (n)
                WHERE (n:Protein OR n:Drug OR n:Disease)
                AND n.id IN $ids
                RETURN n.id AS id, n.name AS name
            """)
    List<BaseNodeDTO> findEntityNamesByIds(@Param("ids") List<String> ids);


    @Query("MATCH (p:Protein {id: $proteinId})-[r]-() DELETE r")
    void removeAllRelationships(@Param("proteinId") String proteinId);

    // * TEST
    @Query("MATCH (p:Protein) " +
            "OPTIONAL MATCH (p)-[:INTERACTS_WITH]->(interactedProtein:Protein) " +
            "RETURN p.id AS id, " +
            "p.name AS name, " +
            "COLLECT(interactedProtein.id) AS interactingProteins " +
            "LIMIT 3")
    List<ProteinGraph> findTopThreeProteins();
}
