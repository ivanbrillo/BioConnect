package org.unipi.bioconnect.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Document(collection = "Users")
public class User {
    // TODO: generalizzare e creare le singole entità
    // Comment è associata solo a RegisteredUser che è l'unico che può commentare
    // oppure gestire a livello applicativo con
    // public boolean canAddComments() {
    //        return Role.REGISTERED.equals(this.role);
    //    }

    @Id
    private String id = UUID.randomUUID().toString();

    private String name;

    @NotNull
    @Field("username")
    @Indexed(unique = true)
    private String username;

    @NotNull
    private String password;

    private String email;

    private Role role;

    @Getter
    private boolean loggedIn;

    // ? I commenti sono un entity separato?
    private List<String> comments; // username-id_elementocommentato-commento test

    // Metodo helper per generare il formato dei commenti
    public void addComment(String elementId, String commentText) {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        if (elementId == null || commentText == null || elementId.isEmpty() || commentText.isEmpty()) {
            throw new IllegalArgumentException("Element ID e Comment Text non possono essere vuoti");
        }
        String formattedComment = String.format("%s-%s-%s", username, elementId, commentText);
        comments.add(formattedComment);
    }

}
