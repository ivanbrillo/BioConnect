package org.unipi.bioconnect.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Protein")
@NoArgsConstructor
public class ProteinDoc {

    @Id
    private String uniProtID;  // Field for UniProt ID
    private String name;       // Field for protein name

}
