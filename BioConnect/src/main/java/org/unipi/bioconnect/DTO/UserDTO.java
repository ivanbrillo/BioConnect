package org.unipi.bioconnect.DTO;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    private String id;
    private String username;
    private String role;
    private List<String> comments;

    public UserDTO(String id, String username, String role, List<String> comments) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.comments = comments;
    }
}
