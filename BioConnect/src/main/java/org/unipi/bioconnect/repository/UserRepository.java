package org.unipi.bioconnect.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.unipi.bioconnect.DTO.UserDTO;
import org.unipi.bioconnect.model.User;


public interface UserRepository extends MongoRepository<User, String> {

    UserDTO findUserDTOByUsername(String username);

    User findUserByUsername(String username);
}
