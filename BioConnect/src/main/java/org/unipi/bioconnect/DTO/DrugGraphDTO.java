package org.unipi.bioconnect.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.unipi.bioconnect.model.DrugGraph;

@Data
public class DrugGraphDTO {

    @NotNull(message = "drugBankID is required")
    private String drugBankID;

    @NotNull(message = "Name is required")
    private String name;

    public DrugGraphDTO(DrugGraph drugGraph) {
        drugBankID = drugGraph.getDrugID();
        name = drugGraph.getName();
    }
}
