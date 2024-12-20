package org.unipi.bioconnect.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Data
@Node("Disease")
@NoArgsConstructor
public class DiseaseGraph {

    @Id
    @Property("id")
    private String diseaseID;

    private String name;

    public DiseaseGraph(String diseaseID, String name) {
        this.diseaseID = diseaseID;
        this.name = name;
    }

}
