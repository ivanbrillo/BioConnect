package org.unipi.bioconnect.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PublicationDTO {
    @NotNull(message = "publication name is required")
    String name;
    @DecimalMin(value = "1850", message="publication date cannot be before 1850 or not specified")
    int year;
    @NotNull(message = "publication type is required")
    String type;
}
