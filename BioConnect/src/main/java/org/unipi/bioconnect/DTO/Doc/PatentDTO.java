package org.unipi.bioconnect.DTO.Doc;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class PatentDTO {

    @NotBlank(message="patent state cannot be blank")
    @NotNull(message="patent state must be specified")
    private String country;

    @DecimalMin(value = "1850", message="patent date cannot be before 1850 or not specified")
    private int year;

}
