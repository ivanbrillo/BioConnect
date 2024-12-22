package org.unipi.bioconnect.model.Graph;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.unipi.bioconnect.DTO.Graph.BaseNodeDTO;
import org.unipi.bioconnect.DTO.Graph.DrugGraphDTO;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Node("Drug")
@NoArgsConstructor
public class DrugGraph extends GraphModel {

    @Relationship(type = "INHIBITED_BY", direction = Relationship.Direction.INCOMING)
    @JsonIgnoreProperties("inhibit")
    List<ProteinGraph> inhibit = new ArrayList<>();

    @Relationship(type = "ENHANCED_BY", direction = Relationship.Direction.INCOMING)
    @JsonIgnoreProperties("enhance")
    List<ProteinGraph> enhance = new ArrayList<>();

    public DrugGraph(String drugID, String name) {
        this.id = drugID;
        this.name = name;
    }

    public DrugGraph(DrugGraphDTO drugGraphDTO) {
        id = drugGraphDTO.getId();
        name = drugGraphDTO.getName();

        for (BaseNodeDTO involve : drugGraphDTO.getInhibit())
            inhibit.add(new ProteinGraph(involve.getId(), involve.getName()));

        for (BaseNodeDTO involve : drugGraphDTO.getEnhance())
            enhance.add(new ProteinGraph(involve.getId(), involve.getName()));
    }

    public BaseNodeDTO getDTO() {
        return new DrugGraphDTO(this);
    }

}
