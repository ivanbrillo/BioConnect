package org.unipi.bioconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unipi.bioconnect.DTO.CommentDTO;
import org.unipi.bioconnect.exception.KeyException;
import org.unipi.bioconnect.repository.CommentDAO;
import org.unipi.bioconnect.repository.Document.DrugDocRepository;
import org.unipi.bioconnect.repository.Document.ProteinDocRepository;
import org.unipi.bioconnect.repository.Graph.GraphHelperRepository;
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
        return executor.executeWithExceptionHandling(() -> commentDAO.findUserComments(username), "MongoDB (my comments)");
    }

    public String addComment(String username, String comment, String elementId, String type) {
        return executor.executeWithExceptionHandling(() -> {
            if (!((type.equals("protein") && proteinDocRepository.existsById(elementId)) || type.equals("drug") && drugDocRepository.existsById(elementId)))  // if it doesn't exist:
                throw new KeyException(type + " with ID: " + elementId + " does not exist");

            CommentDTO commentDTO = new CommentDTO(UUID.randomUUID().toString(), comment, elementId, null);
            userRepository.updateComment(username, commentDTO);
            return "Comment added successfully";

        }, "MongoDB (add comment)");
    }
}
