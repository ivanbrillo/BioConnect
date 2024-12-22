package org.unipi.bioconnect.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.unipi.bioconnect.model.DrugGraph;
import org.unipi.bioconnect.model.GraphModel;


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
        if(!(entity instanceof DrugGraph))
            throw new IllegalArgumentException("Parameter not instance of DrugGraph");

        save((DrugGraph) entity);
    }

    default void deleteEntityById(String id) {
        deleteById(id);
    }

}
