package org.unipi.bioconnect.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.unipi.bioconnect.DTO.Graph.BaseNodeDTO;
import org.unipi.bioconnect.DTO.Graph.ShortestPathDTO;
import org.unipi.bioconnect.model.DiseaseGraph;
import org.unipi.bioconnect.model.GraphModel;

import java.util.List;

public interface DiseaseGraphRepository extends Neo4jRepository<DiseaseGraph, String>, GraphEntityRepository{

    @Query("MATCH (d: Disease) WHERE d.id = $diseaseID \n" +
            "OPTIONAL MATCH (d)<-[r1:INVOLVED_IN]-(prot1:Protein) \n" +
            "RETURN d, collect(r1), collect(prot1)")
    DiseaseGraph getEntityById(@Param("diseaseID") String diseaseID);

    @Query("MATCH (d: Disease {id: $diseaseID})-[r]-() DELETE r")
    void deleteEntityAllRelationships(@Param("diseaseID") String diseaseID);

    default boolean existEntityById(String id) {
        return existsById(id);
    }

    default void saveEntity(GraphModel entity) {
        if(!(entity instanceof DiseaseGraph))
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
            MATCH p = shortestPath((disease1:Disease)-[*..4]-(disease2:Disease))
            WHERE
              disease1.id = $disease1Id AND
              disease2.id = $disease2Id AND
              ALL(node IN nodes(p) WHERE node:Disease OR node:Protein) AND
              size([n IN nodes(p) WHERE n:Protein]) > 1
            RETURN
              disease1.name AS disease1Name,
              disease2.name AS disease2Name,
              [node IN nodes(p) WHERE node:Protein | node.name] AS connectingProteins,
              length(p) AS pathLength,
              [node IN nodes(p) | node.name] AS fullPath  // Aggiunta del percorso completo
            ORDER BY pathLength ASC
            LIMIT 1;
        """)
    List<ShortestPathDTO> findShortestPathBetweenDiseases(@Param("disease1Id") String disease1Id, @Param("disease2Id") String disease2Id);

}
