package org.unipi.bioconnect.DTO.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatentStateAnalysisDTO {

    @Field("_id")
    private String state;  // Country where the patent was filed

    private int expiredPatentCount;  // Count of expired patents in that state
    List<String> drugNames;

}
