package org.unipi.bioconnect.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDTO {

    private String id;
    private String username;
    private String role;
    private List<String> comments;

}
