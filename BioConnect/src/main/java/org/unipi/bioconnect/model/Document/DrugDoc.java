package org.unipi.bioconnect.model.Document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.unipi.bioconnect.DTO.Document.DrugDocDTO;
import org.unipi.bioconnect.DTO.Document.PatentDTO;
import org.unipi.bioconnect.DTO.Document.PublicationDTO;

import java.util.List;

@Data
@Document(collection = "Drug")
@NoArgsConstructor
public class DrugDoc {

    @Id
    @Schema(description = "Unique identifier for the drug in DrugBank")
    private String drugBankID;

    @Schema(description = "Name of the drug")
    private String name;

    @Schema(description = "Amino acid sequence of the drug")
    private String sequence;

    @Schema(description = "Categories to which the drug belongs")
    private List<String> categories;

    @Schema(description = "List of publications related to the drug")
    private List<PublicationDTO> publications;

    @Schema(description = "Toxicity information of the drug")
    private String toxicity;

    @Schema(description = "Description of the drug")
    private String description;

    @Schema(description = "List of patents related to the drug")
    private List<PatentDTO> patents;

    public DrugDoc(DrugDocDTO drugDocDTO) {
        drugBankID = drugDocDTO.getId();
        name = drugDocDTO.getName();
        sequence = drugDocDTO.getSequence();
        categories = drugDocDTO.getCategories();
        publications = drugDocDTO.getPublications();
        toxicity = drugDocDTO.getToxicity();
        description = drugDocDTO.getDescription();
        patents = drugDocDTO.getPatents();
    }

}
