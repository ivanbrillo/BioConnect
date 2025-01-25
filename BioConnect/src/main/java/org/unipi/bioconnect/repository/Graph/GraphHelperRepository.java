package org.unipi.bioconnect.repository.Graph;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.unipi.bioconnect.DTO.Graph.BaseNodeDTO;
import org.unipi.bioconnect.model.Graph.GraphModel;

import java.util.List;

public interface GraphHelperRepository extends Neo4jRepository<GraphModel, String> {

    @Query("""
                MATCH (n: Protein)
                WHERE n.id IN $ids
                RETURN n.id AS id, n.name AS name
            """)
    List<BaseNodeDTO> findProteinNamesByIds(@Param("ids") List<String> ids);

    @Query("""
                MATCH (n: Drug)
                WHERE n.id IN $ids
                RETURN n.id AS id, n.name AS name
            """)
    List<BaseNodeDTO> findDrugNamesByIds(@Param("ids") List<String> ids);

    @Query("""
                MATCH (n: Disease)
                WHERE n.id IN $ids
                RETURN n.id AS id, n.name AS name
            """)
    List<BaseNodeDTO> findDiseaseNamesByIds(@Param("ids") List<String> ids);


}
