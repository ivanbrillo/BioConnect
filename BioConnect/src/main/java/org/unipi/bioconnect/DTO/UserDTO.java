package org.unipi.bioconnect.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDTO {

    @JsonIgnore
    private String id;
    private String username;
    private String role;
    private List<String> comments;


}
