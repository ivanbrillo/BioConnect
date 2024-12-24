package org.unipi.bioconnect.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.unipi.bioconnect.DTO.CredentialsDTO;
import org.unipi.bioconnect.DTO.UserDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Document(collection = "Users")
public class User {

    @Id
    private String id = UUID.randomUUID().toString();

    @Indexed(unique = true)
    private String username;

    private String password;

    private String email;

    private Role role;

    private List<String> comments = new ArrayList<>();


    public User(CredentialsDTO credentialsDTO) {
        username = credentialsDTO.getUsername();
        password = credentialsDTO.getPassword();
        role = Role.valueOf("REGISTERED");
    }


    public void addComment(String elementId, String commentText) {
        if (elementId == null || commentText == null || elementId.isEmpty() || commentText.isEmpty()) {
            throw new IllegalArgumentException("Element ID and Comment Text cannot be null or empty");
        }

        String formattedComment = String.format("%s-%s-%s", username, elementId, commentText);
        comments.add(formattedComment);
    }

}
