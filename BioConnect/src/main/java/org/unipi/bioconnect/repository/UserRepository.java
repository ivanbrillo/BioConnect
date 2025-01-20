package org.unipi.bioconnect.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import org.springframework.data.mongodb.repository.Update;
import org.unipi.bioconnect.DTO.CommentDTO;
import org.unipi.bioconnect.DTO.UserDTO;
import org.unipi.bioconnect.model.User;

import java.util.List;


public interface UserRepository extends MongoRepository<User, String> {

    UserDTO findUserDTOByUsername(String username);

    @Query(value = "{}", fields = "{ '_class': 0, '_password': 0 }")
    List<UserDTO> findAllUser();

    @Query("{'_id' : ?0}")
    @Update("{ '$push': { 'comments': ?1 } }")
    void addComment(String username, CommentDTO comment);

    @Query(value = "{ '_id': ?0, 'comments._id' : ?1 }")
    @Update("{ '$pull' : { 'comments' : { '_id' : ?1 } } }")
    void deleteCommentById(String user, String commentId);

    @Query(value = "{ '_id': ?0, 'comments._id': ?1 }")
    CommentDTO getByUserAndComment(String userId, String commentId);

    @Query(value = "{ 'comments.elementId': ?0 }")
    @Update("{ '$pull': { 'comments': { 'elementId': ?0 } } }")
    void deleteCommentsByElementId(String elementId);

}
