package org.unipi.bioconnect.DTO.Graph;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.unipi.bioconnect.model.DiseaseGraph;
import org.unipi.bioconnect.model.ProteinGraph;

import java.util.HashSet;
import java.util.Set;

@Data
public class DiseaseGraphDTO extends BaseNodeDTO{

    @Valid
    private Set<BaseNodeDTO> involve = new HashSet<>();


    public DiseaseGraphDTO(DiseaseGraph diseaseGraph) {
        id = diseaseGraph.getDiseaseID();
        name = diseaseGraph.getName();

        for (ProteinGraph p : diseaseGraph.getInvolve())
            involve.add(new BaseNodeDTO(p.getUniProtID(), p.getName()));
    }



    }
