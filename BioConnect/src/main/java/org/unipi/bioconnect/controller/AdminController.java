package org.unipi.bioconnect.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.unipi.bioconnect.DTO.CommentDTO;
import org.unipi.bioconnect.DTO.CredentialsDTO;
import org.unipi.bioconnect.DTO.UserDTO;
import org.unipi.bioconnect.model.Role;
import org.unipi.bioconnect.service.AdminService;

import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return adminService.getAllUsers();
    }

    @GetMapping("/users/{username}")
    public UserDTO getUserByUsername(@PathVariable String username) {
        return adminService.getUserDTOByUsername(username);
    }

    @GetMapping("/users/comments")
    public List<CommentDTO> getAllComments() {
        return adminService.getAllComments();
    }

    @DeleteMapping("/users/removeComment/{user}/{commentId}")
    public String removeComment(@PathVariable String user, @PathVariable String commentId) {
        adminService.removeCommentByID(user, commentId);
        return "Comment removed correctly";
    }


    @PostMapping("/registerAdmin")
    @Operation(summary = "Register a new admin")
    public String register(@RequestBody @Valid CredentialsDTO credentials) {
        return adminService.register(credentials, Role.ADMIN);
    }

}
