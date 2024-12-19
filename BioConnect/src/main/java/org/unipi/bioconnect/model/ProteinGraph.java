package org.unipi.bioconnect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;


@Data
@Node("Protein")
@NoArgsConstructor
//@AllArgsConstructor
public class ProteinGraph {

    @Id
    @Property("id")
    private String uniProtID;

    private String name;

    @Relationship(type = "INTERACTS_WITH")
    @JsonIgnoreProperties({"interacts", "interacts2"})
    List<ProteinGraph> interacts = new ArrayList<>();

    @Relationship(type = "INTERACTS_WITH", direction = Relationship.Direction.INCOMING)
    @JsonIgnoreProperties({"interacts", "interacts2"})
    List<ProteinGraph> interacts2 = new ArrayList<>();


    public void addInteraction(ProteinGraph protein) {
        interacts.add(null); //TODO
    }


    public ProteinGraph(String uniProtID, String name) {
        this.uniProtID = uniProtID;
        this.name = name;
    }

    public void clearInteractions() {
        interacts.clear();
    }
}