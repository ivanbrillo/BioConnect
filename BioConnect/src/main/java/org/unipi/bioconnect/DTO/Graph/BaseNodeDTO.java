package org.unipi.bioconnect.DTO.Graph;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.unipi.bioconnect.model.GraphModel;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseNodeDTO<T extends GraphModel> {

    @NotNull(message = "id is required")
    protected String id;

    protected String name = "";

    @JsonIgnore
    public String getNodeType() {
        return "BaseNode";
    }

    @JsonIgnore
    public Set<BaseNodeDTO<T>> getNodeRelationships() {
        return new HashSet<>();
    }

    @JsonIgnore
    public T getGraphModel() {
        return null;
    }


}
