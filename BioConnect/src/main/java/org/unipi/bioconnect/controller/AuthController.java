package org.unipi.bioconnect.controller;

import org.springframework.security.core.Authentication;
import org.unipi.bioconnect.DTO.CredentialsDTO;
import org.springframework.web.bind.annotation.*;
import org.unipi.bioconnect.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint di registrazione
    @PostMapping("/register")
    public String register(@RequestBody CredentialsDTO credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();
        return userService.register(username, password);
    }

    // Endpoint di login
    @PutMapping("/login")
    public String login(@RequestBody CredentialsDTO credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();
        return userService.login(username, password);
    }

    // logout
    @PutMapping("/logout")
    public String logout(Authentication authentication) {
        String username = authentication.getName();
        return userService.logout(username);
    }
}
