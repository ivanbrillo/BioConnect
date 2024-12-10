package org.unipi.bioconnect.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.unipi.bioconnect.DTO.ProteinDTO;
import org.unipi.bioconnect.DTO.PublicationDTO;

import java.util.List;

@Data
@Document(collection = "Protein")
@NoArgsConstructor
public class ProteinDoc {

    @Id
    private String uniProtID;  // Field for UniProt ID
    private String name;       // Field for protein name
    private String sequence;
    private float mass;
    private List<String> pathways;
    private List<String> subcellularLocations;
    private List<PublicationDTO> publications;

    public ProteinDoc(ProteinDTO protein) {
        uniProtID = protein.getUniProtID();
        name = protein.getName();
        sequence = protein.getSequence();
        pathways = protein.getPathways();
        subcellularLocations = protein.getSubcellularLocations();
        publications = protein.getPublications();
        mass = protein.getMass();
    }

}
