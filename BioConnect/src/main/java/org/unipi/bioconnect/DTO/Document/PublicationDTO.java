package org.unipi.bioconnect.DTO.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PublicationDTO {

    @NotNull(message = "publication title is required")
    @NotBlank(message = "title cannot be blank")
    @Schema(description = "Title of publication", example = "A comparison of lepirudin and argatroban outcomes")
    private String title;

    @DecimalMin(value = "1850", message = "publication date cannot be before 1850 or not specified")
    @Schema(description = "Year of publication", example = "2005")
    private int year;


    @NotNull(message = "publication type is required")
    @NotBlank(message = "publication type cannot be blank")
    @Schema(description = "Type of publication", example = "article")
    @Pattern(regexp = "^(publication|URL|book)$", message = "publication type must be either 'publication', 'URL' or 'book'")
    private String type;

}
