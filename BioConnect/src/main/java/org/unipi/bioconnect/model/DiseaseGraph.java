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
public class DiseaseGraph {

    @Id
    @Property("id")
    private String diseaseID;

    private String name;

    @Relationship(type = "INVOLVED_IN", direction = Relationship.Direction.INCOMING)
    @JsonIgnoreProperties("involve")
    List<ProteinGraph> involve = new ArrayList<>();


    public DiseaseGraph(String diseaseID, String name) {
        this.diseaseID = diseaseID;
        this.name = name;
    }

    public DiseaseGraph(DiseaseGraphDTO diseaseGraphDTO) {
        diseaseID = diseaseGraphDTO.getId();
        name = diseaseGraphDTO.getName();

        for (BaseNodeDTO involve : diseaseGraphDTO.getInvolve())
            this.involve.add(new ProteinGraph(involve.getId(), involve.getName()));

    }

}
