package org.unipi.bioconnect.DTO.Doc;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class TrendAnalysisDTO {

    @Field("_id.year")
    private int year;

    @Field("_id.type")
    private String type;

    private long count;

}
