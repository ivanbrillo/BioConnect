package org.unipi.bioconnect.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDTO {

    String _id;
    String comment;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String username;

}
