package org.unipi.bioconnect.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.unipi.bioconnect.DTO.Graph.BaseNodeDTO;
import org.unipi.bioconnect.model.DrugGraph;

import java.util.List;

public interface DrugGraphRepository extends Neo4jRepository<DrugGraph, String> {

    @Query("MATCH (d: Drug) WHERE d.id = $drugID \n" +
            "OPTIONAL MATCH (d)<-[r1:INHIBITED_BY]-(prot1:Protein) \n" +
            "OPTIONAL MATCH (d)<-[r2:ENHANCED_BY]-(prot2:Protein) \n" +
            "RETURN d, collect(r1), collect(prot1), collect(r2), collect(prot2)")
    DrugGraph findByDrugId(@Param("drugID") String drugID);

    @Query("""
                MATCH (n)
                WHERE n:Protein AND n.id IN $ids
                RETURN n.id AS id, n.name AS name
            """)
    List<BaseNodeDTO> findEntityNamesByIds(@Param("ids") List<String> ids);


}
