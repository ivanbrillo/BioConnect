package org.unipi.bioconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.unipi.bioconnect.DTO.CommentDTO;
import org.unipi.bioconnect.DTO.CredentialsDTO;
import org.unipi.bioconnect.DTO.UserDTO;
import org.unipi.bioconnect.exception.KeyException;
import org.unipi.bioconnect.model.Role;
import org.unipi.bioconnect.model.User;
import org.unipi.bioconnect.repository.CommentDAO;
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
    @Autowired
    private CommentDAO commentDAO;


    public UserDTO getUserDTOByUsername(String username) {
        UserDTO userDTO = executor.executeWithExceptionHandling(() -> userRepository.findUserDTOByUsername(username), "MongoDB (user by username)");

        if (userDTO == null)
            throw new KeyException("User not found with username: " + username);

        return userDTO;
    }

    public List<CommentDTO> getAllComments() {
        return executor.executeWithExceptionHandling(() -> commentDAO.findAllComments(), "MongoDB (all comments)");
    }

    public void removeCommentByID(String user, String id) {
        executor.executeWithExceptionHandling(() -> {
            if (userRepository.getByUserAndComment(user, id) == null)
                throw new KeyException("Comment or User not found");

            userRepository.deleteCommentById(user, id);
            return 1;
        }, "MongoDB (remove comment)");
    }

    public void removeUserByID(String user) {
        executor.executeWithExceptionHandling(() -> {
            if (! userRepository.existsById(user))
                throw new KeyException("User not found");

            userRepository.deleteById(user);
            return 1;
        }, "MongoDB (remove user)");
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

    public void deleteCommentsByElementID(String elementID) {
        executor.executeWithExceptionHandling(() -> {
            userRepository.deleteCommentsByElementId(elementID);
            return 1;
        }, "MongoDB (delete comments)");
    }

}
