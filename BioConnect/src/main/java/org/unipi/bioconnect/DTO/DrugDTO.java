package org.unipi.bioconnect.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class DrugDTO {

    @Schema(description = "Unique identifier for the drug in DrugBank", example = "DB00001")
    @Field("_id")
    @NotNull(message = "drugBankID is required")
    private String drugBankID;

    @Schema(description = "Name of the drug", example = "Lepirudin")
    @NotNull(message = "Name is required")
    private String name;

    @Schema(description = "Amino acid sequence of the drug")
    private String sequence;

    @Schema(description = "Categories to which the drug belongs", example = "Anticoagulants")
    private List<String> categories;

    @Schema(description = "List of publications related to the drug")
    @Valid
    private List<PublicationDTO> publications;

    @Schema(description = "Toxicity information of the drug")
    private String toxicity;

    @Schema(description = "Description of the drug", example = "Lepirudin is an anticoagulant that prevents the formation of blood clots.")
    private String description;

    @Schema(description = "List of patents related to the drug")
    @Valid
    private List<PatentDTO> patents;

}