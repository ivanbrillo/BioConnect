package org.unipi.bioconnect.DTO;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.Valid;
import lombok.Data;
import org.unipi.bioconnect.DTO.Document.DrugDocDTO;
import org.unipi.bioconnect.DTO.Graph.DrugGraphDTO;


@Data
public class DrugDTO {

    @JsonUnwrapped
    @Valid
    private DrugDocDTO document;

    @JsonUnwrapped
    @Valid
    private DrugGraphDTO graph;

}