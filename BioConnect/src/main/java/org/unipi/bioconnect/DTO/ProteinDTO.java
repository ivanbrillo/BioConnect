package org.unipi.bioconnect.DTO;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.Valid;
import lombok.Data;
import org.unipi.bioconnect.DTO.Document.ProteinDocDTO;
import org.unipi.bioconnect.DTO.Graph.ProteinGraphDTO;


@Data
public class ProteinDTO {

    @JsonUnwrapped
    @Valid
    private ProteinDocDTO document;

    @JsonUnwrapped
    @Valid
    private ProteinGraphDTO graph;

}
