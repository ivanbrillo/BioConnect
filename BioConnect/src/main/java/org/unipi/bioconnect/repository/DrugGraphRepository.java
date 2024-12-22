package org.unipi.bioconnect.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.unipi.bioconnect.DTO.Graph.BaseNodeDTO;
import org.unipi.bioconnect.model.DrugGraph;
import org.unipi.bioconnect.model.GraphModel;

import java.util.List;


public interface DrugGraphRepository extends Neo4jRepository<DrugGraph, String>, GraphEntityRepository {


    //1. Query - Drug target similar proteins from one given
    @Query("""
            match (d:Drug)-[]-(p:Protein)-[r:SIMILAR_TO]-(p2:Protein)
            where p2.id = $uniProtId
            return d
            """)
    List<BaseNodeDTO> getDrugTargetSimilarProtein(@Param("uniProtId") String uniProtId);


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

}
