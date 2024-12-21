package org.unipi.bioconnect.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.unipi.bioconnect.DTO.Doc.ProteinDocDTO;
import org.unipi.bioconnect.DTO.Doc.PublicationDTO;

import java.util.List;

@Data
@Document(collection = "Protein")
@NoArgsConstructor
public class ProteinDoc {

    @Id
    @Schema(description = "Field for UniProt ID")
    private String uniProtID;

    @Schema(description = "Field for protein name")
    private String name;

    @Schema(description = "Field for protein sequence")
    private String sequence;

    @Schema(description = "Field for protein mass")
    private float mass;

    @Schema(description = "Field for protein pathways")
    private List<String> pathways;

    @Schema(description = "Field for protein subcellular locations")
    private List<String> subcellularLocations;

    @Schema(description = "Field for protein publications")
    private List<PublicationDTO> publications;

    public ProteinDoc(ProteinDocDTO protein) {
        uniProtID = protein.getId();
        name = protein.getName();
        sequence = protein.getSequence();
        pathways = protein.getPathways();
        subcellularLocations = protein.getSubcellularLocations();
        publications = protein.getPublications();
        mass = protein.getMass();
    }

}
