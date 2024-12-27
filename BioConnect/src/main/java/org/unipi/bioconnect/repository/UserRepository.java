package org.unipi.bioconnect.repository;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import org.springframework.data.mongodb.repository.Update;
import org.unipi.bioconnect.DTO.CommentDTO;
import org.unipi.bioconnect.DTO.UserDTO;
import org.unipi.bioconnect.model.User;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends MongoRepository<User, String> {

    UserDTO findUserDTOByUsername(String username);

    @Query(value = "{}", fields = "{ '_class': 0, '_password': 0 }")
    List<UserDTO> findAllUser();

    @Query("{'_id' : ?0}")
    @Update("{ '$push': { 'comments': ?1 } }")
    void updateComment(String username, CommentDTO comment);

    @Aggregation(pipeline = {
            "{ $match: { comments: { $exists: true, $ne: null }, _id: ?0 } }",
            "{ $unwind: '$comments' }",
            "{ $project: { comment: '$comments.comment' } }"
    })
    List<String> findUserComments(String username);

    @Query(value = "{ '_id': ?0, 'comments._id' : ?1 }")
    @Update("{ '$pull' : { 'comments' : { '_id' : ?1 } } }")
    void deleteCommentById(String user, String commentId);

    @Query("{ '_id': ?0, 'comments._id': ?1, 'comments': { $size: { $gte: 1 } } }")
    boolean existsByUserAndComment(String userId, String commentId);
}
