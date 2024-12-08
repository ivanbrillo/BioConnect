package org.unipi.bioconnect.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;


@Data
public class ProteinDTO {

    @NotNull(message = "uniProtID is required")
    private String uniProtID;

    @NotNull(message = "Name is required")
    private String name;

    private double mass;

    private String sequence;

    private List<String> pathways;

    private List<String> subcellularLocations;

    private String description;

}
