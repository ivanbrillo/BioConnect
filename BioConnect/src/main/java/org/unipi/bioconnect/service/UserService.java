package org.unipi.bioconnect.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.unipi.bioconnect.DTO.UserDTO;
import org.unipi.bioconnect.model.Role;
import org.unipi.bioconnect.model.User;
import org.unipi.bioconnect.repository.DrugDocRepository;
import org.unipi.bioconnect.repository.ProteinDocRepository;
import org.unipi.bioconnect.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProteinDocRepository proteinDocRepository;
    private final DrugDocRepository drugDocRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ProteinDocRepository proteinDocRepository, DrugDocRepository drugDocRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.proteinDocRepository = proteinDocRepository;
        this.drugDocRepository = drugDocRepository;
    }

    public UserDTO getUserDTOByUsername(String username) {
        UserDTO userDTO = userRepository.findUserDTOByUsername(username);
        if (userDTO == null) {
            throw new RuntimeException("User not found with username: " + username);
        }
        return userDTO;
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

    public String register(String username, String password) {
        User user = new User();
        if (userRepository.findUserByUsername(username) != null) {
            return "Username già esistente";
        }
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.valueOf("REGISTERED"));
        user.setLoggedIn(true);
        userRepository.save(user);
        return "Utente " + username + " registrato con successo";
    }

    public String login(String username, String password) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            return "Utente non trovato con username: " + username;
        }
        if (passwordEncoder.matches(password, user.getPassword())) {
            user.setLoggedIn(true);
            userRepository.save(user);
            return "Utente " + username + " loggato con successo";
        } else {
            return "Password errata";
        }
    }

    // logout
    public String logout(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new RuntimeException("Utente non trovato con username: " + username);
        }
        user.setLoggedIn(false);
        userRepository.save(user);
        return "Utente " + username + " è ora offline";
    }

    public List<String> getCommentsByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        //controllo che utente sia loggato
        if (!user.isLoggedIn()) {
            throw new RuntimeException("Utente non loggato");
        }
        // Restituisci i commenti dell'utente
        return user.getComments();
    }

    public String addComment(String username, String comment, String elementId) {
        User user = userRepository.findUserByUsername(username);
        //controllo che utente sia loggato
        if (!user.isLoggedIn()) {
            throw new RuntimeException("Utente non loggato");
        }
        //controllo se esiste l'elemento commentato
        if (proteinDocRepository.findById(elementId).isEmpty() && drugDocRepository.findById(elementId).isEmpty()) {
            throw new RuntimeException("Elemento non trovato con ID: " + elementId);
        }

        if (user == null) {
            throw new RuntimeException("Utente non trovato con username: " + username);
        }
        user.addComment(elementId, comment);
        userRepository.save(user);

        return "Commento aggiunto con successo all'elemento con ID: " + elementId;
    }
}
