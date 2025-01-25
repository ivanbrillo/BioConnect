package org.unipi.bioconnect.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;


@Data
@AllArgsConstructor
public class CommentDTO {

    @Schema(description = "Id of the comment" , example = "4495b772-68f4")
    String _id;

    @Schema(description = "Text of comment" , example = "This is my comment")
    String comment;

    @Schema(description = "Id of the commented object" , example = "P68871")
    String elementId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Username user who commented" , example = "Mario")
    String username;
}
