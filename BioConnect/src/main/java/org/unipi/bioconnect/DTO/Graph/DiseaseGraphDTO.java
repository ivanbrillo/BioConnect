package org.unipi.bioconnect.DTO.Graph;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.unipi.bioconnect.model.DiseaseGraph;
import org.unipi.bioconnect.model.ProteinGraph;

import java.util.HashSet;
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

    public Set<BaseNodeDTO> getNodeRelationships() {
        return new HashSet<>(involve);
    }

    public DiseaseGraph getGraphModel() {
        return new DiseaseGraph(this);
    }



    }
