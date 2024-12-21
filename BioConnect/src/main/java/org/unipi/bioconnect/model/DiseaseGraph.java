package org.unipi.bioconnect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.unipi.bioconnect.DTO.Graph.BaseNodeDTO;
import org.unipi.bioconnect.DTO.Graph.DiseaseGraphDTO;
import org.unipi.bioconnect.DTO.Graph.DrugGraphDTO;

import java.util.ArrayList;
import java.util.List;

@Data
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
