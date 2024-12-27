package org.unipi.bioconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unipi.bioconnect.DTO.CommentDTO;
import org.unipi.bioconnect.exception.KeyException;
import org.unipi.bioconnect.repository.CommentDAO;
import org.unipi.bioconnect.repository.Graph.GraphHelperRepository;
import org.unipi.bioconnect.repository.UserRepository;

import java.util.List;
import java.util.UUID;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GraphHelperRepository graphHelperRepository;
    @Autowired
    private DatabaseOperationExecutor executor;



    public List<String> getCommentsByUsername(String username) {
        return executor.executeWithExceptionHandling(() -> userRepository.findUserComments(username), "MongoDB (my comments)");
    }

    public String addComment(String username, String comment, String elementId) {
        return executor.executeWithExceptionHandling(() -> {
            if (!graphHelperRepository.entityExistsById(elementId))
                throw new KeyException("Entity with ID: " + elementId + " does not exist");

            CommentDTO commentDTO = new CommentDTO(UUID.randomUUID().toString(), comment, null);

            userRepository.updateComment(username, commentDTO);
            return "Comment added successfully";
        }, "MongoDB (add comment)");
    }
}
