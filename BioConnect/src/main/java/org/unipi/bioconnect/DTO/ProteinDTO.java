package org.unipi.bioconnect.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "UniProt ID of the protein", example = "P12345")
    private String uniProtID;

    @NotNull(message = "Name is required")
    @Schema(description = "Name of the protein", example = "Hemoglobin")
    private String name;

    @Schema(description = "mass of the protein", example = "64.5")
    private float mass;

    @Schema(description = "sequence of the protein", example = "MGLSDGEWQLVLNVWGKV...")
    private String sequence;

    @Schema(description = "pathways of the protein", example = "['pathway1', 'pathway2']")
    private List<String> pathways;

    @Schema(description = "subcellular locations of the protein", example = "['location1', 'location2']")
    private List<String> subcellularLocations;

    @Valid
    @Schema(description = "Protein interactions", example = "[{...}, {...}]")
    private List<ProteinDTO> proteinInteractions = new ArrayList<>();

    @Valid
    @Schema(description = "Protein similarities", example = "[{...}, {...}]")
    private List<ProteinDTO> proteinSimilarities = new ArrayList<>();

    @Valid
    @Schema(description = "Protein publications", example = "[{...}, {...}]")
    private List<PublicationDTO> publications;

}
