package org.unipi.bioconnect.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProteinDTO {

    @JsonUnwrapped
    @Valid
    private ProteinDocDTO document;

    @JsonUnwrapped
    @Valid
//    @JsonIgnoreProperties({"id", "name"})   // otherwise duplicated properties with document when serializing
    private ProteinGraphDTO graph;

    public ProteinGraphDTO getGraph() {
        graph.setId(document.getId());
        graph.setName(document.getName());
        return graph;
    }

}
