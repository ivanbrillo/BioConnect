package org.unipi.bioconnect.repository;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import org.springframework.data.mongodb.repository.Update;
import org.unipi.bioconnect.DTO.UserDTO;
import org.unipi.bioconnect.model.User;

import java.util.List;


public interface UserRepository extends MongoRepository<User, String> {

    UserDTO findUserDTOByUsername(String username);

    @Query(value = "{}", fields = "{ '_class': 0, '_password': 0 }")
    List<UserDTO> findAllUser();

    @Query("{'_id' : ?0}")
    @Update("{ '$push': { 'comments': ?1 } }")
    void updateComment(String username, String comment);

    @Aggregation(pipeline = {
            "{ $match: { comments: { $exists: true, $ne: null } } }",
            "{ $unwind: '$comments' }",
            "{ $project: { _id: 0, comments: 1 } }"
    })
    List<String> findAllComments();

    @Aggregation(pipeline = {
            "{ $match: { comments: { $exists: true, $ne: null }, _id: ?0 } }",
            "{ $unwind: '$comments' }",
            "{ $project: { _id: 0, comments: 1 } }"
    })
    List<String> findUserComments(String username);
}
