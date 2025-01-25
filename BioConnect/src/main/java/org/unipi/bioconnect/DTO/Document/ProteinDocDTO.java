package org.unipi.bioconnect.DTO.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProteinDocDTO {

    @Field("_id")
    @NotNull(message = "uniProtID is required")
    @Schema(description = "UniProt ID of the protein", example = "P12345")
    private String id;

    @Schema(description = "Name of the protein", example = "Hemoglobin")
    private String name;

    @Schema(description = "mass of the protein", example = "64.5")
    private float mass;

    @Schema(description = "sequence of the protein", example = "MGLSDGEWQLVLNVWGKV...")
    private String sequence;

    @Schema(description = "Pathways of the protein", example = "[\"Sulfur metabolism\", \"Glutathione metabolism\"]")
    private List<String> pathways;

    @Schema(description = "subcellular locations of the protein", example = "[\"Cytoplasm\", \"Nucleus\"]")
    private List<String> subcellularLocations;

    @Valid
    @Schema(description = "Protein publications")
    private List<PublicationDTO> publications;

}
