package org.unipi.bioconnect.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.unipi.bioconnect.DTO.CommentDTO;
import org.unipi.bioconnect.service.AdminService;
import org.unipi.bioconnect.service.UserService;

import java.util.List;

@RestController
@RequestMapping
@Tag(name = "User Controller", description = "API for User operations")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;



    @GetMapping("/profile/my_comments")
    @Operation(summary = "All comments made by the currently logged-in user",
            description = "Fetches all comments made by the user currently logged into the system. The user must be authenticated to access this endpoint")
    public List<CommentDTO> getMyComments(Authentication authentication) {
        String username = authentication.getName(); // Get username from authentication context
        return userService.getCommentsByUsername(username);
    }




    @PostMapping("/profile/add_comment/protein")
    @Operation(summary = "Add a comment to a specific protein",
            description = "Add a comment to a specific protein")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string", example = "Comment added successfully")))
    })
    public String addProteinComment(
            Authentication authentication,

            @RequestBody @NotNull @NotBlank
            @Schema(
                    description = "Comment to add to the protein", example = "This is a sample comment about a protein", type = "string"
            ) String comment,

            @RequestParam @NotNull @NotBlank
            @Parameter(
                    description = "The ID of the protein to add the comment to", example = "P68871", schema = @Schema(type = "string")
            ) String elementId) {
        String username = authentication.getName(); // Get username from authentication context
        return userService.addComment(username, comment, elementId, true);
    }




    @PostMapping("/profile/add_comment/drug")
    @Operation(summary = "Add a comment to a specific drug",
            description = "Add a comment to a specific drug")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string", example = "Comment added successfully")))
    })
    public String addDrugComment(
            Authentication authentication,
            @RequestBody @NotNull @NotBlank
            @Schema(
                    description = "Comment to add to the protein",
                    example = "This is a sample comment about a protein", type = "string"
            ) String comment,

            @RequestParam @NotNull @NotBlank
            @Parameter(
                    description = "The ID of the protein to add the comment to",
                    example = "DB00945", schema = @Schema(type = "string")
            ) String elementId) {
        String username = authentication.getName(); // Get username from authentication context
        return userService.addComment(username, comment, elementId, false);
    }




    @DeleteMapping("/profile/removeComment/{commentId}")
    @Operation(summary = "Allows the user to remove a specific comment",
            description = "Allows the logged-in user to remove a comment by providing its unique commentId. The user must be authenticated to access this endpoint")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string", example = "Comment removed correctly")))
    })
    public String removeComment(Authentication authentication,
                                @Parameter(
                                        description = "The unique identifier of the comment to be removed", example = "39f2ae77-4091-4032-a72a-ae7d3ec02301",
                                        required = true, schema = @Schema(type = "string")
                                ) @PathVariable String commentId) {
        adminService.removeCommentByID(authentication.getName(), commentId);
        return "Comment removed correctly";
    }




    @DeleteMapping("/profile/deleteAccount")
    @Operation(summary = "Allows the user to remove his account",
            description = "Allows the user to remove his account and all the related information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "text/plain",
                    schema = @Schema(type = "string", example = "User removed correctly")))
    })
    public String deleteAccount(Authentication authentication) {
        adminService.removeUserByID(authentication.getName());
        return "User removed correctly";
    }

}
