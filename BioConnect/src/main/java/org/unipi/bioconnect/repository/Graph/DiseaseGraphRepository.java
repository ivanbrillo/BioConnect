package org.unipi.bioconnect.repository.Graph;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.unipi.bioconnect.DTO.Graph.BaseNodeDTO;
import org.unipi.bioconnect.model.Graph.DiseaseGraph;
import org.unipi.bioconnect.model.Graph.GraphModel;

import java.util.List;

public interface DiseaseGraphRepository extends Neo4jRepository<DiseaseGraph, String>, GraphEntityRepository {

    @Query("""
            MATCH (d: Disease) WHERE d.id = $diseaseID
            OPTIONAL MATCH (d)<-[r1:INVOLVED_IN]-(prot1:Protein)
            RETURN d, collect(r1), collect(prot1)
            """)
    DiseaseGraph getEntityById(@Param("diseaseID") String diseaseID);

    @Query("MATCH (d: Disease {id: $diseaseID})-[r]-() DELETE r")
    void deleteEntityAllRelationships(@Param("diseaseID") String diseaseID);

    default boolean existEntityById(String id) {
        return existsById(id);
    }

    default void saveEntity(GraphModel entity) {
        if (!(entity instanceof DiseaseGraph))
            throw new IllegalArgumentException("Parameter not instance of DrugGraph");

        save((DiseaseGraph) entity);
    }

    default void deleteEntityById(String id) {
        deleteById(id);
    }

    //3. Diseases linked to a particular drug
    @Query("""
            MATCH (disease:Disease)<-[:INVOLVED_IN]-(p:Protein)-[]-(drug:Drug)
            WHERE drug.id = $drugId
            RETURN disease
            """)
    List<BaseNodeDTO> getDiseaseByDrug(@Param("drugId") String drugId);

    //2. Shortest path
    @Query("""
                MATCH p = SHORTEST 1 (d1:Disease)-[*..4]-(d2:Disease)
                WHERE d1.id = $disease1Id
                  AND d2.id = $disease2Id
                  AND ALL(n IN nodes(p)[1..-1] WHERE n:Protein)
                RETURN
                    [node IN nodes(p) | node {id: node.id, name: node.name}]
            """)
    List<BaseNodeDTO> findShortestPathBetweenDiseases(@Param("disease1Id") String disease1Id, @Param("disease2Id") String disease2Id);

}
