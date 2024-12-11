package org.unipi.bioconnect.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class DrugDTO {

    @Field("_id")
    @NotNull(message = "drugBankID is required")
    private String drugBankID;

    @NotNull(message = "Name is required")
    private String name;

    private String sequence;

    private List<String> categories;

    @Valid
    private List<PublicationDTO> publications;

    private String toxicity;

    private String description;

    @Valid
    private List<PatentDTO> patents;

}
