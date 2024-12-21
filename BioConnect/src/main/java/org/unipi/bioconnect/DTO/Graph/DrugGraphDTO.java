package org.unipi.bioconnect.DTO.Graph;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.unipi.bioconnect.model.DrugGraph;
import org.unipi.bioconnect.model.ProteinGraph;

import java.util.HashSet;
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
        id = drugGraph.getDrugID();
        name = drugGraph.getName();

        for (ProteinGraph d : drugGraph.getEnhance())
            enhance.add(new BaseNodeDTO(d.getUniProtID(), d.getName()));

        for (ProteinGraph d : drugGraph.getInhibit())
            inhibit.add(new BaseNodeDTO(d.getUniProtID(), d.getName()));

    }
}
