package org.unipi.bioconnect.DTO;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.unipi.bioconnect.DTO.Doc.ProteinDocDTO;
import org.unipi.bioconnect.DTO.Graph.ProteinGraphDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProteinDTO {

    @JsonUnwrapped
    @Valid
    private ProteinDocDTO document;

    @JsonUnwrapped
    @Valid
    private ProteinGraphDTO graph;

}
