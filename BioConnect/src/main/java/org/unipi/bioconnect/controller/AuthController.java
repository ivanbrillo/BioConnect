package org.unipi.bioconnect.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.unipi.bioconnect.DTO.CredentialsDTO;
import org.springframework.web.bind.annotation.*;
import org.unipi.bioconnect.jwt.JwtTokenProvider;
import org.unipi.bioconnect.model.Role;
import org.unipi.bioconnect.service.AdminService;

@RestController
@Tag(name = "Authorization Controller", description = "API for authentication operations")
public class AuthController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public String register(@RequestBody @Valid CredentialsDTO credentials) {
        return adminService.register(credentials, Role.REGISTERED);
    }

    @PostMapping("/login")
    public String login(@RequestBody CredentialsDTO credentialsDTO) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                credentialsDTO.getUsername(),
                credentialsDTO.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String role = hasRole("ADMIN") ? "ADMIN" : "REGISTERED";

        return jwtTokenProvider.createToken(credentialsDTO.getUsername(), role);
    }

    private static boolean hasRole(String roleName) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_" + roleName));
    }

}
