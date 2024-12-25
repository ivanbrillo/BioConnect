package org.unipi.bioconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.unipi.bioconnect.DTO.CredentialsDTO;
import org.unipi.bioconnect.DTO.UserDTO;
import org.unipi.bioconnect.exception.KeyException;
import org.unipi.bioconnect.model.Role;
import org.unipi.bioconnect.model.User;
import org.unipi.bioconnect.repository.Graph.GraphHelperRepository;
import org.unipi.bioconnect.repository.UserRepository;

import java.util.List;


@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;
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

    public String register(CredentialsDTO credentials, Role role) {
        credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
        User user = new User(credentials, role);

        executor.executeWithExceptionHandling(() -> userRepository.insert(user), "MongoDB (register)");
        return "User " + credentials.getUsername() + " correctly registered, procede to login";
    }

}
