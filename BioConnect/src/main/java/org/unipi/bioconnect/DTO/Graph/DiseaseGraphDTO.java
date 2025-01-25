package org.unipi.bioconnect.DTO.Graph;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.unipi.bioconnect.model.Graph.DiseaseGraph;
import org.unipi.bioconnect.model.Graph.ProteinGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class DiseaseGraphDTO extends BaseNodeDTO {

    @Valid
    private Set<BaseNodeDTO> involve = new HashSet<>();


    public DiseaseGraphDTO(DiseaseGraph diseaseGraph) {
        id = diseaseGraph.getId();
        name = diseaseGraph.getName();

        for (ProteinGraph p : diseaseGraph.getInvolve())
            involve.add(new BaseNodeDTO(p.getId(), p.getName()));

    }

    public String getNodeType() {
        return "Disease";
    }

    public List<Set<BaseNodeDTO>> getNodeRelationships() {
        List<Set<BaseNodeDTO>> relationships = new ArrayList<>();
        relationships.add(involve);
        relationships.add(new HashSet<>());
        relationships.add(new HashSet<>());
        return relationships;
    }

    public DiseaseGraph getGraphModel() {
        return new DiseaseGraph(this);
    }



    }
