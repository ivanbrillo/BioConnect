package org.unipi.bioconnect.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.unipi.bioconnect.model.DiseaseGraph;
import org.unipi.bioconnect.model.GraphModel;

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
}
