package org.unipi.bioconnect.repository.Graph;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.unipi.bioconnect.DTO.Graph.BaseNodeDTO;
import org.unipi.bioconnect.DTO.Graph.OppositeEffectDrugsDTO;
import org.unipi.bioconnect.model.Graph.DrugGraph;
import org.unipi.bioconnect.model.Graph.GraphModel;

import java.util.List;


public interface DrugGraphRepository extends Neo4jRepository<DrugGraph, String>, GraphEntityRepository {

    @Query("MATCH (d: Drug) WHERE d.id = $drugID \n" +
            "OPTIONAL MATCH (d)<-[r1:INHIBITED_BY]-(prot1:Protein) \n" +
            "OPTIONAL MATCH (d)<-[r2:ENHANCED_BY]-(prot2:Protein) \n" +
            "RETURN d, collect(r1), collect(prot1), collect(r2), collect(prot2)")
    DrugGraph getEntityById(@Param("drugID") String drugID);

    @Query("MATCH (d: Drug {id: $drugID})-[r]-() DELETE r")
    void deleteEntityAllRelationships(@Param("drugID") String drugID);

    default boolean existEntityById(String id) {
        return existsById(id);
    }

    default void saveEntity(GraphModel entity) {
        if (!(entity instanceof DrugGraph))
            throw new IllegalArgumentException("Parameter not instance of DrugGraph");

        save((DrugGraph) entity);
    }

    default void deleteEntityById(String id) {
        deleteById(id);
    }

    //1. Query - Drug target similar proteins from one given
    @Query("""
            match (d:Drug)-[]-(p:Protein)-[r:SIMILAR_TO]-(p2:Protein)
            where p2.id = $uniProtId
            return d
            """)
    List<BaseNodeDTO> getDrugTargetSimilarProtein(@Param("uniProtId") String uniProtId);

    //4. Query - Drug opposite effects on protein
    @Query("""
            MATCH (d:Drug {id: $drugId})
            OPTIONAL MATCH (d)<-[:INHIBITED_BY]-(p1:Protein)-[:ENHANCED_BY]->(d2:Drug)
            RETURN {id: d2.id, name: d2.name} as drug, {id: p1.id, name: p1.name} as protein, 'enhancer' as effect
            UNION
            MATCH (d:Drug {id: $drugId})
            OPTIONAL MATCH (d)<-[:ENHANCED_BY]-(p2:Protein)-[:INHIBITED_BY]->(d3:Drug)
            RETURN {id: d3.id, name: d3.name} as drug, {id: p2.id, name: p2.name} as protein, 'inhibitor' as effect
            """)
    List<OppositeEffectDrugsDTO> getDrugOppositeEffectsProtein(@Param("drugId") String drugId);

}