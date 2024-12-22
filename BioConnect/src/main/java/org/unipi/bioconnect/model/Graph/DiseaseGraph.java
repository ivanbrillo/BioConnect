package org.unipi.bioconnect.model.Graph;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.unipi.bioconnect.DTO.Graph.BaseNodeDTO;
import org.unipi.bioconnect.DTO.Graph.DiseaseGraphDTO;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Node("Disease")
@NoArgsConstructor
public class DiseaseGraph extends GraphModel {

    @Relationship(type = "INVOLVED_IN", direction = Relationship.Direction.INCOMING)
    @JsonIgnoreProperties("involve")
    List<ProteinGraph> involve = new ArrayList<>();


    public DiseaseGraph(String diseaseID, String name) {
        this.id = diseaseID;
        this.name = name;
    }

    public DiseaseGraph(DiseaseGraphDTO diseaseGraphDTO) {
        id = diseaseGraphDTO.getId();
        name = diseaseGraphDTO.getName();

        for (BaseNodeDTO inv : diseaseGraphDTO.getInvolve())
            involve.add(new ProteinGraph(inv.getId(), inv.getName()));
    }

    public BaseNodeDTO getDTO() {
        return new DiseaseGraphDTO(this);
    }

}
