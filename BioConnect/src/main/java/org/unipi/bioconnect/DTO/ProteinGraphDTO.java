package org.unipi.bioconnect.DTO;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.unipi.bioconnect.model.DiseaseGraph;
import org.unipi.bioconnect.model.DrugGraph;
import org.unipi.bioconnect.model.ProteinGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ProteinGraphDTO extends BaseNodeDTO{

    @Valid
    private Set<BaseNodeDTO> proteinInteractions = new HashSet<>();

    @Valid
    private Set<BaseNodeDTO> proteinSimilarities = new HashSet<>();

    @Valid
    private List<BaseNodeDTO> drugEnhancedBy = new ArrayList<>();

    @Valid
    private List<BaseNodeDTO> drugInhibitBy = new ArrayList<>();

    @Valid
    private List<BaseNodeDTO> diseaseInvolvedIn = new ArrayList<>();


    public ProteinGraphDTO(ProteinGraph proteinGraph) {

        id = proteinGraph.getUniProtID();
        name = proteinGraph.getName();

        for (ProteinGraph p : proteinGraph.getInteracts())
            proteinInteractions.add(new BaseNodeDTO(p.getUniProtID(), p.getName()));

        for (ProteinGraph p : proteinGraph.getInteracts2())
            proteinInteractions.add(new BaseNodeDTO(p.getUniProtID(), p.getName()));

        for (ProteinGraph p : proteinGraph.getSimilar())
            proteinSimilarities.add(new BaseNodeDTO(p.getUniProtID(), p.getName()));

        for (ProteinGraph p : proteinGraph.getSimilar2())
            proteinSimilarities.add(new BaseNodeDTO(p.getUniProtID(), p.getName()));

        for (DrugGraph d : proteinGraph.getInhibitedBy())
            drugInhibitBy.add(new BaseNodeDTO(d.getDrugID(), d.getName()));

        for (DrugGraph d : proteinGraph.getEnhancedBy())
            drugEnhancedBy.add(new BaseNodeDTO(d.getDrugID(), d.getName()));

        for (DiseaseGraph d : proteinGraph.getInvolved())
            diseaseInvolvedIn.add(new BaseNodeDTO(d.getDiseaseID(), d.getName()));

    }


}
