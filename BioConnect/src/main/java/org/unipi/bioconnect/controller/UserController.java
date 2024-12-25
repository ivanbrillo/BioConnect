package org.unipi.bioconnect.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.unipi.bioconnect.service.UserService;

import java.util.List;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile/my_comments")
    public List<String> getMyComments(Authentication authentication) {
        String username = authentication.getName(); // Get username from authentication context
        return userService.getCommentsByUsername(username);
    }

    @PostMapping("/profile/add_comment")
    public String addComment(Authentication authentication, @RequestBody @NotNull @NotBlank String comment, @RequestParam @NotNull @NotBlank String elementId) {
        String username = authentication.getName(); // Get username from authentication context
        return userService.addComment(username, comment, elementId);
    }
}
