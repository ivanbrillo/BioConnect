package org.unipi.bioconnect.model;

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
    private String drugBankID;

    private String name;

    private String sequence;

    private List<String> categories;

    private List<PublicationDTO> publications;

    private String toxicity;

    private String description;

    private List<PatentDTO> patents;


}
