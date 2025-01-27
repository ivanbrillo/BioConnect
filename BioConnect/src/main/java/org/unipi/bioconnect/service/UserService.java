package org.unipi.bioconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unipi.bioconnect.DTO.CommentDTO;
import org.unipi.bioconnect.exception.KeyException;
import org.unipi.bioconnect.repository.CommentDAO;
import org.unipi.bioconnect.repository.Document.DrugDocRepository;
import org.unipi.bioconnect.repository.Document.ProteinDocRepository;
import org.unipi.bioconnect.repository.UserRepository;

import java.util.List;
import java.util.UUID;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DatabaseOperationExecutor executor;
    @Autowired
    private CommentDAO commentDAO;
    @Autowired
    private ProteinDocRepository proteinDocRepository;
    @Autowired
    private DrugDocRepository drugDocRepository;


    public List<CommentDTO> getCommentsByUsername(String username) {
        return executor.executeWithExceptionHandling(() -> {
            if (!userRepository.existsById(username))
                throw new KeyException("User not found");

            return commentDAO.findUserComments(username);
        }, "MongoDB (my comments)");
    }

    public String addComment(String username, String comment, String elementId, boolean isProtein) {
        return executor.executeWithExceptionHandling(() -> {
            if (!userRepository.existsById(username))
                throw new KeyException("User not found");

            if (isProtein)
                addProteinComment(username, comment, elementId);
            else
                addDrugComment(username, comment, elementId);

            return "Comment added successfully";

        }, "MongoDB (add comment)");
    }

    private void addProteinComment(String username, String comment, String elementId) {
        if (!proteinDocRepository.existsById(elementId))  // if it doesn't exist:
            throw new KeyException("Protein with ID: " + elementId + " does not exist");

        CommentDTO commentDTO = new CommentDTO(UUID.randomUUID().toString(), comment, elementId, null, null);
        userRepository.addComment(username, commentDTO);
    }

    private void addDrugComment(String username, String comment, String elementId) {
        if (!drugDocRepository.existsById(elementId))  // if it doesn't exist:
            throw new KeyException("Drug with ID: " + elementId + " does not exist");

        CommentDTO commentDTO = new CommentDTO(UUID.randomUUID().toString(), comment, null, elementId, null);
        userRepository.addComment(username, commentDTO);
    }


}
