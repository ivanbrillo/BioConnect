package org.unipi.bioconnect.DTO.Graph;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.unipi.bioconnect.model.DiseaseGraph;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class DiseaseGraphDTO extends BaseNodeDTO<DiseaseGraph> {

    @Valid
    private Set<BaseNodeDTO<DiseaseGraph>> involve = new HashSet<>();


    public DiseaseGraphDTO(DiseaseGraph diseaseGraph) {
        id = diseaseGraph.getId();
        name = diseaseGraph.getName();
    }

    public String getNodeType() {
        return "Disease";
    }

    public Set<BaseNodeDTO<DiseaseGraph>> getNodeRelationships() {
        return new HashSet<>(involve);
    }

    public DiseaseGraph getGraphModel() {
        return new DiseaseGraph(this);
    }

}
