package org.unipi.bioconnect.DTO.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrendAnalysisDTO {

    @Field("_id.year")
    @Schema(description = "Year of publication", example = "2005")
    private int year;

    @Field("_id.type")
    @Schema(description = "Type of publication", example = "article")
    private String type;

    @Schema(description = "Number of publications for this year" , example = "5")
    private long count;

}
