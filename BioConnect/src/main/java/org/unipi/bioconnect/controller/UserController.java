package org.unipi.bioconnect.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unipi.bioconnect.DTO.UserDTO;
import org.unipi.bioconnect.model.User;
import org.unipi.bioconnect.repository.UserRepository;
import org.unipi.bioconnect.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

// * PANNELLO ADMIN

    @GetMapping("/admin/users")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/admin/users/{username}")
    public UserDTO getUserByUsername(@PathVariable String username) {
        return userService.getUserDTOByUsername(username);
    }

    @GetMapping("/admin/users/comments")
    public List<String> getAllComments() {
        return userService.getAllComments();
    }


// * PANNELLO USER

    @GetMapping("/comments/{my_id}")
    public List<String> getMyComments(@PathVariable String my_id) {
        return userService.getUserDTOById(my_id).getComments();
    }
}
