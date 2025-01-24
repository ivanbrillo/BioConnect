package org.unipi.bioconnect.DTO.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PathwayRecurrenceDTO {

    @Field("_id")
    private String pathwayName;
    private int count;

}
