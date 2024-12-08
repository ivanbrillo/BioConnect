package org.unipi.bioconnect.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.unipi.bioconnect.DTO.ProteinDTO;
import org.unipi.bioconnect.model.ProteinGraph;

import java.util.List;

public interface ProteinGraphRepository extends Neo4jRepository<ProteinGraph, String> {
    @Query("MATCH (p:Protein) RETURN p.uniProtID AS uniProtID, p.name AS name")
    List<ProteinDTO> findAllProjectedBy();
}
