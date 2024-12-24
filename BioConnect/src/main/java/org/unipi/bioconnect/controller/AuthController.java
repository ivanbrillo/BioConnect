package org.unipi.bioconnect.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.unipi.bioconnect.DTO.CredentialsDTO;
import org.springframework.web.bind.annotation.*;
import org.unipi.bioconnect.service.UserService;

@RestController
@Tag(name = "Authorization Controller", description = "API for authentication operations")
public class AuthController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public String register(@RequestBody @Valid CredentialsDTO credentials) {
        return userService.register(credentials);
    }

    @GetMapping("/successLogin")
    public String loginSuccess() {
        return "User logged in successfully";
    }

    @GetMapping("/successLogout")
    public String logoutSuccess() {
        return "User logged out successfully";
    }

    @PostMapping("/unsuccessfulLogin")
    public String loginFailure() {
        return "User failed to log in";
    }

}
