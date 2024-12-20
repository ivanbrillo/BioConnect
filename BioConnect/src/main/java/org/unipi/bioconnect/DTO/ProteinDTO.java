package org.unipi.bioconnect.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProteinDTO {

    @JsonUnwrapped
    private ProteinDocDTO document;

    @JsonUnwrapped
//    @JsonIgnoreProperties({"id", "name"})   // otherwise duplicated properties with document when serializing
    private ProteinGraphDTO graph;

}
