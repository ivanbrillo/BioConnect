package org.unipi.bioconnect.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDTO {

    @Field("_id")
    private String username;
    private String role;
    private List<CommentDTO> comments;

}
