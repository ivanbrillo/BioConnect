package org.unipi.bioconnect.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private String uniProtID;

    private String name;

    @Relationship(type = "INTERACTS_WITH")
    List<ProteinGraph> interacts;

    public void addInteraction(ProteinGraph protein) {

        if (interacts == null)
            interacts = new ArrayList<>();

        interacts.add(protein);

    }


    public ProteinGraph(String uniProtID, String name) {
        this.uniProtID = uniProtID;
        this.name = name;
    }
}
