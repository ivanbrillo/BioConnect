package org.unipi.bioconnect.service;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.unipi.bioconnect.DTO.UserDTO;
import org.unipi.bioconnect.model.User;
import org.unipi.bioconnect.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getUserDTOByUsername(String username) {
        return userRepository.findUserDTOByUsername(username);
    }

    public List<String> getAllComments() {
        return userRepository.findAll().stream()
                .map(user -> Optional.ofNullable(user.getComments()).orElse(Collections.emptyList()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getRole().toString(),
                        Optional.ofNullable(user.getComments()).orElse(Collections.emptyList())
                ))
                .collect(Collectors.toList());
    }

    public UserDTO getUserDTOById(String id) {
        User user = userRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Utente non trovato con ID: " + id));
        return new UserDTO(user.getId(), user.getUsername(), user.getRole().toString(), user.getComments());
    }

}
