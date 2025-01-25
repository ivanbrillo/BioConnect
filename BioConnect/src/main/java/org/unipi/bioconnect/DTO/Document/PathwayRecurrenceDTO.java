package org.unipi.bioconnect.DTO.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PathwayRecurrenceDTO {

    @Field("_id")
    @Schema(description = "Pathway name", example = "Sulfur metabolism")
    private String pathwayName;

    @Schema(description = "Number of pathways having the specified subsequence" , example = "5")
    private int count;
}
