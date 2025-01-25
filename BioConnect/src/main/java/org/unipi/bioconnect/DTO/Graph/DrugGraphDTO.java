package org.unipi.bioconnect.DTO.Graph;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.unipi.bioconnect.model.Graph.DrugGraph;
import org.unipi.bioconnect.model.Graph.GraphModel;
import org.unipi.bioconnect.model.Graph.ProteinGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class DrugGraphDTO extends BaseNodeDTO {

    @Valid
    private Set<BaseNodeDTO> enhance = new HashSet<>();

    @Valid
    private Set<BaseNodeDTO> inhibit = new HashSet<>();


    public DrugGraphDTO(DrugGraph drugGraph) {
        id = drugGraph.getId();
        name = drugGraph.getName();

        for (ProteinGraph d : drugGraph.getEnhance())
            enhance.add(new BaseNodeDTO(d.getId(), d.getName()));

        for (ProteinGraph d : drugGraph.getInhibit())
            inhibit.add(new BaseNodeDTO(d.getId(), d.getName()));

    }

    public String getNodeType() {
        return "Drug";
    }

    public List<Set<BaseNodeDTO>> getNodeRelationships() {
        Set<BaseNodeDTO> set = new HashSet<>();
        set.addAll(enhance);
        set.addAll(inhibit);

        List<Set<BaseNodeDTO>> relationships = new ArrayList<>();
        relationships.add(set);
        relationships.add(new HashSet<>());
        relationships.add(new HashSet<>());

        return relationships;
    }

    public GraphModel getGraphModel() {
        return new DrugGraph(this);
    }


}
