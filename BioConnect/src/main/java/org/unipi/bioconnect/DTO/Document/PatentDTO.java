package org.unipi.bioconnect.DTO.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatentDTO {

    @NotBlank(message="patent state cannot be blank")
    @NotNull(message="patent state must be specified")
    @Schema(description = "Country of patent", example = "United States")
    private String country;

    @DecimalMin(value = "1850", message="patent date cannot be before 1850 or not specified")
    @Schema(description = "Year of patent", example = "1992")
    private int year;

}
