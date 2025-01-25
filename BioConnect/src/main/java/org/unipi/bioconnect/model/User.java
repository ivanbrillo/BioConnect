package org.unipi.bioconnect.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.neo4j.core.schema.Id;
import org.unipi.bioconnect.DTO.CommentDTO;
import org.unipi.bioconnect.DTO.CredentialsDTO;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "User")
public class User {

    @Id
    private String username;

    private String password;
    private Role role;
    private List<CommentDTO> comments = new ArrayList<>();


    public User(CredentialsDTO credentialsDTO, Role role) {
        username = credentialsDTO.getUsername();
        password = credentialsDTO.getPassword();
        this.role = role;
    }

}
