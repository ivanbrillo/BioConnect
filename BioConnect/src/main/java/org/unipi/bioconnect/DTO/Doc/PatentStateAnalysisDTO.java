package org.unipi.bioconnect.DTO.Doc;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class PatentStateAnalysisDTO {

    @Field("_id")
    private String state;  // Country where the patent was filed
    private int expiredPatentCount;  // Count of expired patents in that state
    List<String> drugNames;
}
