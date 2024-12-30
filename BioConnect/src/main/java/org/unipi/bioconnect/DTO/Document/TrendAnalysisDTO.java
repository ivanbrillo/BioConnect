package org.unipi.bioconnect.DTO.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // Esclude i campi nulli
public class TrendAnalysisDTO {

    @Field("_id.year")
    private int year;

    @Field("_id.type")
    private String type;

    private long count;

}
