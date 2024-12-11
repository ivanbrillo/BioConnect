package org.unipi.bioconnect.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PathwayRecurrenceDTO {
    private String _id;
    private int count;
}
