package org.unipi.bioconnect.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.unipi.bioconnect.DTO.CredentialsDTO;
import org.springframework.web.bind.annotation.*;
import org.unipi.bioconnect.service.UserService;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authorization Controller", description = "API for authentication operations")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint di registrazione
    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public String register(@RequestBody CredentialsDTO credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();
        return userService.register(username, password);
    }

    // Endpoint di login
    @PutMapping("/login")
    @Operation(summary = "Login a user")
    public String login(@RequestBody CredentialsDTO credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();
        return userService.login(username, password);
    }

    // logout
    @PutMapping("/logout")
    @Operation(summary = "Logout a user")
    public String logout(Authentication authentication) {
        String username = authentication.getName();
        return userService.logout(username);
    }
}
