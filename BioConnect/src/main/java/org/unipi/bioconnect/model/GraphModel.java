package org.unipi.bioconnect.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.unipi.bioconnect.DTO.Graph.BaseNodeDTO;

@Data
@NoArgsConstructor
public abstract class GraphModel {

    @Id
    @Property("id")
    protected String id;

    protected String name;

    public abstract BaseNodeDTO getDTO();

}
