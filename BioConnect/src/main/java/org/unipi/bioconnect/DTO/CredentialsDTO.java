package org.unipi.bioconnect.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;



@Data
public class CredentialsDTO {

    @NotNull
    @NotBlank
    @Schema(description = "Name of the user" , example = "Mario")
    private String username;

    @NotNull
    @NotBlank
    @Schema(description = "Encrypted password of the user" , example = "$2a$10$VBcMlUgL.13azfwGdncM5ekTf0BNRdzixb6rJ.vKTWKMezxOj7mcG")
    private String password;

}
