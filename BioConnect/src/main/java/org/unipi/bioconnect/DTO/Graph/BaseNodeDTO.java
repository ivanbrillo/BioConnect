package org.unipi.bioconnect.DTO.Graph;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.unipi.bioconnect.model.Graph.GraphModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseNodeDTO {

    @NotNull(message = "id is required")
    @Schema(description = "ID of object", example = "ID00000")
    protected String id;

    @Schema(description = "Name of object", example = "MyName")
    protected String name = "";

    @JsonIgnore
    public String getNodeType() {
        return "BaseNode";
    }

    @JsonIgnore
    // first element Proteins, second element Drugs, third elements Diseases
    public List<Set<BaseNodeDTO>> getNodeRelationships() {
        return new ArrayList<>();
    }

    @JsonIgnore
    public GraphModel getGraphModel() {
        return null;
    }


}
