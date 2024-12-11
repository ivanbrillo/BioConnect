package org.unipi.bioconnect.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.unipi.bioconnect.DTO.PatentDTO;
import org.unipi.bioconnect.DTO.PublicationDTO;

import java.util.List;

@Data
@Document(collection = "Drug")
@NoArgsConstructor
public class DrugDoc {

    @Id
    @Schema(description = "Unique identifier for the drug in DrugBank", example = "DB00001")
    private String drugBankID;

    @Schema(description = "Name of the drug", example = "Lepirudin")
    private String name;

    @Schema(description = "Amino acid sequence of the drug")
    private String sequence;

    @Schema(description = "Categories to which the drug belongs", example = "Anticoagulants")
    private List<String> categories;

    @Schema(description = "List of publications related to the drug")
    private List<PublicationDTO> publications;

    @Schema(description = "Toxicity information of the drug")
    private String toxicity;

    @Schema(description = "Description of the drug", example = "Lepirudin is an anticoagulant that prevents the formation of blood clots.")
    private String description;

    @Schema(description = "List of patents related to the drug")
    private List<PatentDTO> patents;


}
