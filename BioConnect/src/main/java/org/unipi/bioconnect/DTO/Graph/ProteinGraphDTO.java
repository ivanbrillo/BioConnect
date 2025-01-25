package org.unipi.bioconnect.DTO.Graph;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.unipi.bioconnect.model.Graph.DiseaseGraph;
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
public class ProteinGraphDTO extends BaseNodeDTO {

    @Valid
    private Set<BaseNodeDTO> proteinInteractions = new HashSet<>();

    @Valid
    private Set<BaseNodeDTO> proteinSimilarities = new HashSet<>();

    @Valid
    private Set<BaseNodeDTO> drugEnhancedBy = new HashSet<>();

    @Valid
    private Set<BaseNodeDTO> drugInhibitBy = new HashSet<>();

    @Valid
    private Set<BaseNodeDTO> diseaseInvolvedIn = new HashSet<>();


    public ProteinGraphDTO(ProteinGraph proteinGraph) {

        id = proteinGraph.getId();
        name = proteinGraph.getName();

        for (ProteinGraph p : proteinGraph.getInteracts())
            proteinInteractions.add(new BaseNodeDTO(p.getId(), p.getName()));

        for (ProteinGraph p : proteinGraph.getInteracts2())
            proteinInteractions.add(new BaseNodeDTO(p.getId(), p.getName()));

        for (ProteinGraph p : proteinGraph.getSimilar())
            proteinSimilarities.add(new BaseNodeDTO(p.getId(), p.getName()));

        for (ProteinGraph p : proteinGraph.getSimilar2())
            proteinSimilarities.add(new BaseNodeDTO(p.getId(), p.getName()));

        for (DrugGraph d : proteinGraph.getInhibitedBy())
            drugInhibitBy.add(new BaseNodeDTO(d.getId(), d.getName()));

        for (DrugGraph d : proteinGraph.getEnhancedBy())
            drugEnhancedBy.add(new BaseNodeDTO(d.getId(), d.getName()));

        for (DiseaseGraph d : proteinGraph.getInvolved())
            diseaseInvolvedIn.add(new BaseNodeDTO(d.getId(), d.getName()));

    }

    public String getNodeType() {
        return "Protein";
    }

    public List<Set<BaseNodeDTO>> getNodeRelationships() {
        Set<BaseNodeDTO> setProtein = new HashSet<>();
        Set<BaseNodeDTO> setDrug = new HashSet<>();

        setProtein.addAll(proteinInteractions);
        setProtein.addAll(proteinSimilarities);
        setDrug.addAll(drugInhibitBy);
        setDrug.addAll(drugEnhancedBy);

        List<Set<BaseNodeDTO>> relationships = new ArrayList<>();
        relationships.add(setProtein);
        relationships.add(setDrug);
        relationships.add(diseaseInvolvedIn);

        return relationships;
    }

    public GraphModel getGraphModel() {
        return new ProteinGraph(this);
    }
}
