package org.unipi.bioconnect.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.unipi.bioconnect.DTO.Graph.BaseNodeDTO;
import org.unipi.bioconnect.DTO.Graph.DrugGraphDTO;

import java.util.ArrayList;
import java.util.List;

@Data
@Node("Drug")
@NoArgsConstructor
public class DrugGraph {

    @Id
    @Property("id")
    private String drugID;

    private String name;

    @Relationship(type = "INHIBITED_BY", direction = Relationship.Direction.INCOMING)
    @JsonIgnoreProperties("inhibit")
    List<ProteinGraph> inhibit = new ArrayList<>();

    @Relationship(type = "ENHANCED_BY", direction = Relationship.Direction.INCOMING)
    @JsonIgnoreProperties("enhance")
    List<ProteinGraph> enhance = new ArrayList<>();

    public DrugGraph(String drugID, String name) {
        this.drugID = drugID;
        this.name = name;
    }

    public DrugGraph(DrugGraphDTO drugGraphDTO) {
        drugID = drugGraphDTO.getId();
        name = drugGraphDTO.getName();

        for (BaseNodeDTO involve : drugGraphDTO.getInhibit())
            inhibit.add(new ProteinGraph(involve.getId(), involve.getName()));

        for (BaseNodeDTO involve : drugGraphDTO.getEnhance())
            enhance.add(new ProteinGraph(involve.getId(), involve.getName()));
    }
}
