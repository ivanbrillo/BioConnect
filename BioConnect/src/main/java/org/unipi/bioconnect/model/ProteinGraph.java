package org.unipi.bioconnect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.unipi.bioconnect.DTO.Graph.BaseNodeDTO;
import org.unipi.bioconnect.DTO.Graph.ProteinGraphDTO;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Node("Protein")
@NoArgsConstructor
public class ProteinGraph extends GraphModel {

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

    @Relationship(type = "INVOLVED_IN")
    @JsonIgnoreProperties("involved")
    List<DiseaseGraph> involved = new ArrayList<>();

    @Relationship(type = "INHIBITED_BY")
    @JsonIgnoreProperties("inhibit")
    List<DrugGraph> inhibitedBy = new ArrayList<>();

    @Relationship(type = "ENHANCED_BY")
    @JsonIgnoreProperties("enhance")
    List<DrugGraph> enhancedBy = new ArrayList<>();


    public ProteinGraph(ProteinGraphDTO proteinGraphDTO) {
        id = proteinGraphDTO.getId();
        name = proteinGraphDTO.getName();

        for (BaseNodeDTO interaction : proteinGraphDTO.getProteinInteractions())
            interacts.add(new ProteinGraph(interaction.getId(), interaction.getName()));

        for (BaseNodeDTO sim : proteinGraphDTO.getProteinSimilarities())
            similar.add(new ProteinGraph(sim.getId(), sim.getName()));

        for (BaseNodeDTO enhance : proteinGraphDTO.getDrugEnhancedBy())
            enhancedBy.add(new DrugGraph(enhance.getId(), enhance.getName()));

        for (BaseNodeDTO inhibit : proteinGraphDTO.getDrugInhibitBy())
            inhibitedBy.add(new DrugGraph(inhibit.getId(), inhibit.getName()));

        for (BaseNodeDTO involve : proteinGraphDTO.getDiseaseInvolvedIn())
            involved.add(new DiseaseGraph(involve.getId(), involve.getName()));

    }

    public ProteinGraph(String uniProtID, String name) {
        this.id = uniProtID;
        this.name = name;
    }

    public BaseNodeDTO getDTO() {
        return new ProteinGraphDTO(this);
    }

}