package org.unipi.bioconnect.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;


@Data
public class ProteinDTO {

    @Field("_id")
    @NotNull(message = "uniProtID is required")
    private String uniProtID;

    @NotNull(message = "Name is required")
    private String name;

    private float mass;

    private String sequence;

    private List<String> pathways;

    private List<String> subcellularLocations;

    @Valid
    private List<ProteinDTO> proteinInteractions = new ArrayList<>();

    @Valid
    private List<ProteinDTO> proteinSimilarities = new ArrayList<>();

    @Valid
    private List<PublicationDTO> publications;

}
