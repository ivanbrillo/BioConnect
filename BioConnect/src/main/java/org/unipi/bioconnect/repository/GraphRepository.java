package org.unipi.bioconnect.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.unipi.bioconnect.DTO.Graph.BaseNodeDTO;
import org.unipi.bioconnect.model.ProteinGraph;

import java.util.List;

public interface GraphRepository extends Neo4jRepository<ProteinGraph, String> {

    @Query("""
                MATCH (n)
                WHERE (n:Protein OR n:Drug OR n:Disease)
                AND n.id IN $ids
                RETURN n.id AS id, n.name AS name
            """)
    List<BaseNodeDTO> findEntityNamesByIds(@Param("ids") List<String> ids);


}
