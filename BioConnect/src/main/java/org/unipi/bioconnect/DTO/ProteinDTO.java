package org.unipi.bioconnect.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class ProteinDTO {

    @NotNull(message = "uniProtID is required")
    private String uniProtID;

    @NotNull(message = "Name is required")
    private String name;

    private float mass;

    private String sequence;

    private List<String> pathways;

    private List<String> subcellularLocations;

    private List<ProteinDTO> proteinInteractions = new ArrayList<>();

    private List<ProteinDTO> proteinSimilarities = new ArrayList<>();

    private List<PublicationDTO> publications;

    private String description;

}
