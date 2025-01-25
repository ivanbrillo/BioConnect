package org.unipi.bioconnect.DTO.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatentStateAnalysisDTO {

    @Field("_id")
    @Schema(description = "Country where the patent was filed", example = "United States")
    private String state;  // Country where the patent was filed

    @Schema(description = "Count of expired patents in that state", example = "5")
    private int expiredPatentCount;  // Count of expired patents in that state

    @Schema(description = "List of drug name", example = "[\"Enoxaparin\", \"Prasugrel\"]")
    List<String> drugNames;
}
