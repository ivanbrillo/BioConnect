package org.unipi.bioconnect.repository.Graph;

import org.unipi.bioconnect.model.Graph.GraphModel;


public interface GraphEntityRepository {

    GraphModel getEntityById(String id);

    boolean existEntityById(String id);

    void saveEntity(GraphModel entity);

    void deleteEntityById(String id);

    void deleteEntityAllRelationships(String id);

}
