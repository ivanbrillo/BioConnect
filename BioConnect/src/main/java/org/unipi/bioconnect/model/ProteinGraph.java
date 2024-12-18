package org.unipi.bioconnect.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;


@Data
@Node("Protein")
@NoArgsConstructor
@AllArgsConstructor
public class ProteinGraph {

    @Id
    private String id;

    private String name;

    @Relationship(type = "INTERACTS_WITH", direction = Relationship.Direction.OUTGOING)
    List<ProteinGraph> interacts = new ArrayList<>();

    public void addInteraction(ProteinGraph protein) {
        interacts.add(protein);
    }


    public ProteinGraph(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void clearInteractions() {
        interacts.clear();
    }
}
