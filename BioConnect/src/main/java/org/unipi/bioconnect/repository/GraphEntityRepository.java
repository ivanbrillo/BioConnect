package org.unipi.bioconnect.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.unipi.bioconnect.model.GraphModel;

public interface GraphEntityRepository<T extends GraphModel> extends Neo4jRepository<T, String> {

}
