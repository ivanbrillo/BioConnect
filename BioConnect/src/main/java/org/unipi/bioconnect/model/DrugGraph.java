package org.unipi.bioconnect.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Data
@Node("Drug")
@NoArgsConstructor
public class DrugGraph {

    @Id
    @Property("id")
    private String drugID;

    private String name;

    public DrugGraph(String drugID, String name) {
        this.drugID = drugID;
        this.name = name;
    }

}
