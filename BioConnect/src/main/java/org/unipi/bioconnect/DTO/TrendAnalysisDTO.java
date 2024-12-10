package org.unipi.bioconnect.DTO;

import lombok.Data;

@Data
public class TrendAnalysisDTO {

    private TrendKey _id;
    private long count;

    @Data
    public static class TrendKey {
        private int year;
        private String type;
    }

}
