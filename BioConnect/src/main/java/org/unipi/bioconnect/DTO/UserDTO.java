package org.unipi.bioconnect.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDTO {

    @Field("_id")
    @Schema(description = "Name of the user" , example = "Mario")
    private String username;

    @Schema(description = "Role of the user" , example = "REGISTERED")
    private String role;

    private List<CommentDTO> comments;

}
