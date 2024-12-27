package org.unipi.bioconnect.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.unipi.bioconnect.DTO.CredentialsDTO;
import org.springframework.web.bind.annotation.*;
import org.unipi.bioconnect.model.Role;
import org.unipi.bioconnect.service.AdminService;

@RestController
@Tag(name = "Authorization Controller", description = "API for authentication operations")
public class AuthController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public String register(@RequestBody @Valid CredentialsDTO credentials) {
        return adminService.register(credentials, Role.REGISTERED);
    }

    //Using custom login and logout endpoints in order to display it in swagger
    @PostMapping("/login")
    public String login(@RequestBody @Valid CredentialsDTO credentials, HttpServletRequest request) {

        // Authenticate the user, will throw exception if not present in the db
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        return "Login successful for user: " + authentication.getName();
    }


    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        if (request.getSession(false) == null)
            throw new RuntimeException("User is not logged in");

        request.getSession().invalidate();
        SecurityContextHolder.clearContext();

        var cookie = new jakarta.servlet.http.Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);            // Marks the cookie for deletion
        response.addCookie(cookie);

        return "Logout successful";
    }

}
