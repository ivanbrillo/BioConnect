package org.unipi.bioconnect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Version;
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
    @JsonIgnoreProperties({"interacts", "interacts2", "similar", "similar2", "involved", "inhibitedBy", "enhancedBy"})
    List<ProteinGraph> interacts = new ArrayList<>();

    @Relationship(type = "INTERACTS_WITH", direction = Relationship.Direction.INCOMING)
    @JsonIgnoreProperties({"interacts", "interacts2", "similar", "similar2", "involved", "inhibitedBy", "enhancedBy"})
    List<ProteinGraph> interacts2 = new ArrayList<>();

    @Relationship(type = "SIMILAR_TO")
    @JsonIgnoreProperties({"interacts", "interacts2", "similar", "similar2", "involved", "inhibitedBy", "enhancedBy"})
    List<ProteinGraph> similar = new ArrayList<>();

    @Relationship(type = "SIMILAR_TO", direction = Relationship.Direction.INCOMING)
    @JsonIgnoreProperties({"interacts", "interacts2", "similar", "similar2", "involved", "inhibitedBy", "enhancedBy"})
    List<ProteinGraph> similar2 = new ArrayList<>();

    @Relationship(type = "INVOLVED_IN") //per malattie
    @JsonIgnoreProperties("involved")
    List<DiseaseGraph> involved = new ArrayList<>();

    @Relationship(type = "INHIBITED_BY") //per farmaci
    @JsonIgnoreProperties("inhibitedBy")
    List<DrugGraph> inhibitedBy = new ArrayList<>();

    @Relationship(type = "ENHANCED_BY") //per farmaci
    @JsonIgnoreProperties("enhancedBy")
    List<DrugGraph> enhancedBy = new ArrayList<>();

    // TODO server a qualcosa? per un warning
    @Version
    private Long version;

    // ! da modificare con due liste
    public void addInteraction(ProteinGraph protein) {
        interacts.add(protein);
    }


    public ProteinGraph(String uniProtID, String name) {
        this.uniProtID = uniProtID;
        this.name = name;
    }

    public void clearInteractions() {
        interacts.clear();
    }
}