package org.unipi.bioconnect.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.unipi.bioconnect.model.DiseaseGraph;

public interface DiseaseGraphRepository  extends Neo4jRepository<DiseaseGraph, String> {

    @Query("MATCH (d: diseaseID) WHERE d.id = diseaseID \n" +
            "OPTIONAL MATCH (d)<-[r1:INVOLVED_IN]-(prot1:Protein) \n" +
            "RETURN d, collect(r1), collect(prot1)")
    DiseaseGraph findByDrugId(@Param("diseaseID") String diseaseID);

    @Query("MATCH (d: Disease {id: diseaseID})-[r]-() DELETE r")
    void removeAllRelationships(@Param("diseaseID") String diseaseID);
}
