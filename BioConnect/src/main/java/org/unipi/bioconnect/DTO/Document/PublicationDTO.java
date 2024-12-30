package org.unipi.bioconnect.DTO.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // Esclude i campi nulli
public class PublicationDTO {

    @NotNull(message = "publication title is required")
    @NotBlank(message = "title cannot be blank")
    private String title;

    @DecimalMin(value = "1850", message = "publication date cannot be before 1850 or not specified")
    private int year;

    @NotNull(message = "publication type is required")
    @NotBlank(message = "publication type cannot be blank")
    private String type;

}
