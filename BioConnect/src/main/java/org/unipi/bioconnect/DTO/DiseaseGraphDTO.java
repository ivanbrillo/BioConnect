package org.unipi.bioconnect.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.unipi.bioconnect.model.DiseaseGraph;

@Data
public class DiseaseGraphDTO {

    @NotNull(message = "diseaseID is required")
    private String diseaseID;

    @NotNull(message = "Name is required")
    private String name;

    public DiseaseGraphDTO(DiseaseGraph diseaseGraph) {
        diseaseID = diseaseGraph.getDiseaseID();
        name = diseaseGraph.getName();
    }

}
