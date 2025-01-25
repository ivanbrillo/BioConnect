package org.unipi.bioconnect.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Admin Controller", description = "API for Admin operations")
public class AdminController {

    @Autowired
    private AdminService adminService;




    @GetMapping("/users")
    @Operation(summary = "List of all users in the system",
                description = "Fetches a list of all users registered in the system. This includes user details such as their ID, name, and role")
    public List<UserDTO> getAllUsers() {
        return adminService.getAllUsers();
    }




    @GetMapping("/users/{username}")
    @Operation(summary = "Details of a specific user by their username",
                description = "Details of a specific user by their username")
    public UserDTO getUserByUsername(
            @Parameter(
                    description = "The username of the user to retrieve details",
                    example = "test_user1",
                    required = true,
                    schema = @Schema(type = "string")
            ) @PathVariable String username) {
        return adminService.getUserDTOByUsername(username);
    }




    @GetMapping("/users/comments")
    @Operation(summary = "All comments made by users",
                description = "Fetches all comments made by users, including details such as comment content, user who posted it and any associated metadata")
    public List<CommentDTO> getAllComments() {
        return adminService.getAllComments();
    }




    @DeleteMapping("/users/removeComment/{user}/{commentId}")
    @Operation(summary = "Removes a comment made by a specific user, identified by the user ID and comment ID",
                description = "Removes a comment made by the user specified by userID, with the commentID parameter identifying the specific comment to be deleted")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string", example = "Comment removed correctly")))
    })
    public String removeComment(
            @Parameter(
                    description = "The unique ID of the user who made the comment",
                    example = "test_user1",
                    required = true,
                    schema = @Schema(type = "string")
            ) @PathVariable String user,
            @Parameter(
                    description = "The unique ID of the comment to be removed",
                    example = "0a5f1499-f0b3-4342-91da-f7f3858ebc43",
                    required = true,
                    schema = @Schema(type = "string")
            )@PathVariable String commentId) {
        adminService.removeCommentByID(user, commentId);
        return "Comment removed correctly";
    }



    @PostMapping("/registerAdmin")
    @Operation(summary = "Register a new admin",
            description = "Registers a new admin by providing a username and password",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CredentialsDTO.class),
                            examples = @ExampleObject(
                                    value = "{\n" +
                                            "  \"username\": \"admin2\",\n" +
                                            "  \"password\": \"password\"\n" +
                                            "}"
                            )
                    )
            ))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string", example = "User {username} correctly registered, procede to login")))
    })
    public String register(@RequestBody @Valid CredentialsDTO credentials) {
        return adminService.register(credentials, Role.ADMIN);
    }



    @DeleteMapping("/users/removeUser/{user}")
    @Operation(summary = "Removes a User",
            description = "Removes a User and its related information (eg. comments)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string", example = "User removed correctly")))
    })
    public String removeUser(
            @Parameter(
                    description = "The unique ID of the user to delete",
                    example = "test_user1",
                    required = true,
                    schema = @Schema(type = "string")
            ) @PathVariable String user) {
        adminService.removeUserByID(user);
        return "User removed correctly";
    }

}
