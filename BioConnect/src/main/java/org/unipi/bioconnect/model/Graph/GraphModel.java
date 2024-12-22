package org.unipi.bioconnect.model.Graph;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.unipi.bioconnect.DTO.Graph.BaseNodeDTO;

@Data
public abstract class GraphModel {

    @Id
    protected String id;

    protected String name;

    public abstract BaseNodeDTO getDTO();

}
