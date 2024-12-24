package org.unipi.bioconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.unipi.bioconnect.DTO.CredentialsDTO;
import org.unipi.bioconnect.DTO.UserDTO;
import org.unipi.bioconnect.exception.KeyException;
import org.unipi.bioconnect.model.User;
import org.unipi.bioconnect.repository.Graph.GraphHelperRepository;
import org.unipi.bioconnect.repository.UserRepository;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GraphHelperRepository graphHelperRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DatabaseOperationExecutor executor;


    public UserDTO getUserDTOByUsername(String username) {
        UserDTO userDTO = executor.executeWithExceptionHandling(() -> userRepository.findUserDTOByUsername(username), "MongoDB (user by username)");

        if (userDTO == null)
            throw new KeyException("User not found with username: " + username);

        return userDTO;
    }

    public List<String> getAllComments() {
        return executor.executeWithExceptionHandling(() -> userRepository.findAllComments(), "MongoDB (all comments)");
    }

    public List<UserDTO> getAllUsers() {
        return executor.executeWithExceptionHandling(() -> userRepository.findAllUser(), "MongoDB (all users)");
    }

    public String register(CredentialsDTO credentials) {
        credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
        User user = new User(credentials);

        executor.executeWithExceptionHandling(() -> userRepository.insert(user), "MongoDB (register)");
        return "User " + credentials.getUsername() + "correctly registered, procede to login";
    }

    public List<String> getCommentsByUsername(String username) {
        return executor.executeWithExceptionHandling(() -> userRepository.findUserComments(username), "MongoDB (my comments)");
    }

    public String addComment(String username, String comment, String elementId) {
        return executor.executeWithExceptionHandling(() -> {
            if (!graphHelperRepository.entityExistsById(elementId))
                throw new KeyException("Entity with ID: " + elementId + " does not exist");
            userRepository.updateComment(username, comment);
            return "Commento aggiunto con successo all'elemento con ID: " + elementId;
        }, "MongoDB (add comment)");
    }
}
