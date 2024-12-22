package org.unipi.bioconnect.DTO.Document;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
public class PathwayRecurrenceDTO {

    @Field("_id")
    private String pathwayName;
    private int count;

}
