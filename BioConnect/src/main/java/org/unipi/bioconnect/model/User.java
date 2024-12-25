package org.unipi.bioconnect.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.neo4j.core.schema.Id;
import org.unipi.bioconnect.DTO.CredentialsDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Document(collection = "Users")
public class User {

//    @Id
//    private String id = UUID.randomUUID().toString();

    @Id
    private String username;

    private String password;
    private Role role;
    private List<String> comments = new ArrayList<>();


    public User(CredentialsDTO credentialsDTO, Role role) {
        username = credentialsDTO.getUsername();
        password = credentialsDTO.getPassword();
        this.role = role;
    }

}
