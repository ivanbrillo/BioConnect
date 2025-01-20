package org.unipi.bioconnect.repository.Graph;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.unipi.bioconnect.model.Graph.GraphModel;
import org.unipi.bioconnect.model.Graph.ProteinGraph;


public interface ProteinGraphRepository extends Neo4jRepository<ProteinGraph, String>, GraphEntityRepository {

    @Query("MATCH (p:Protein) WHERE p.id = $uniProtID \n"
            + "OPTIONAL MATCH (p)-[r:INTERACTS_WITH]-(related:Protein) \n"
            + "OPTIONAL MATCH (p)-[r2:SIMILAR_TO]-(similar:Protein) \n"
            + "OPTIONAL MATCH (p)-[r3:INVOLVED_IN]->(d:Disease) \n"
            + "OPTIONAL MATCH (p)-[r4:INHIBITED_BY]->(drug:Drug) \n"
            + "OPTIONAL MATCH (p)-[r5:ENHANCED_BY]->(drug2:Drug) \n"
            + "RETURN p,collect(r), collect(related), collect(r2), collect(similar), collect(r3), collect(d), collect(r4), collect(drug), collect(r5), collect(drug2)")
    ProteinGraph getEntityById(@Param("uniProtID") String uniProtID);

    @Query("MATCH (p:Protein {id: $proteinId})-[r]-() DELETE r")
    void deleteEntityAllRelationships(@Param("proteinId") String proteinId);

    default boolean existEntityById(String id) {
        return existsById(id);
    }

    default GraphModel saveEntity(GraphModel entity) {
        if (!(entity instanceof ProteinGraph))
            throw new IllegalArgumentException("Parameter not instance of ProteinGraph");

        return save((ProteinGraph) entity);
    }

    default void deleteEntityById(String id) {
        deleteById(id);
    }

}
