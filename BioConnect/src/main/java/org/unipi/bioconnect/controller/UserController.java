package org.unipi.bioconnect.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.unipi.bioconnect.DTO.UserDTO;
import org.unipi.bioconnect.service.UserService;

import java.util.List;

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

    @GetMapping("/profile/my_comments")
    public List<String> getMyComments(Authentication authentication) {
        String username = authentication.getName(); // Prendi il nome utente dal contesto
        return userService.getCommentsByUsername(username);
    }

    @PostMapping("/profile/add_comment")
    public String addComment(Authentication authentication, @RequestBody String comment, @RequestParam String elementId) {
        String username = authentication.getName(); // Prendi il nome utente dal contesto
        return userService.addComment(username, comment, elementId);
    }
}
