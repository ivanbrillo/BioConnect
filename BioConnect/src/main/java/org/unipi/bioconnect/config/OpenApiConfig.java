package org.unipi.bioconnect.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

import java.util.Map;
import java.util.Set;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        final String apiTitle = "BioConnect";
        final String apiVersion = "1.0.0";
        final String description = "API documentation for BioConnect";

        return new OpenAPI().addSecurityItem(new SecurityRequirement().addList(securitySchemeName)).components(new Components().addSecuritySchemes(securitySchemeName, new SecurityScheme().name(securitySchemeName).type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))).info(new Info().title(apiTitle).version(apiVersion).description(description));
    }

    @Bean
    // traverse all OpenAPI default responses (200, 403 etc...), and keep only the 200, the other ones will be mapped manually
    public OpenApiCustomizer removeDefaultResponses() {

        Set<String> requireIdValidation = Set.of("getDrugByID", "getDrugTargetSimilarProtein", "getDrugOppositeEffectsProtein", "getProteinById", "getUserByUsername", "removeComment", "getShortestPathBetweenDiseases", "getDiseaseByDrug", "getDiseaseByID", "addProteinComment", "addDrugComment", "deleteAccount", "removeUser");


        return openApi -> {
            openApi.getPaths().forEach((pathUrl, pathItem) -> pathItem.readOperations().forEach(operation -> {
                ApiResponses newResponses = new ApiResponses();
                ApiResponse successResponse = operation.getResponses().get("200");
                newResponses.addApiResponse("200", successResponse);

                if (pathUrl.contains("/admin") || pathUrl.contains("/profile"))
                    addErrorResponse(newResponses, Map.of("ErrorCode", "9", "Error", "Unauthorized, authentication required"), "Unauthorized - Invalid credentials", "401");

                if (pathUrl.contains("/login"))
                    addErrorResponse(newResponses, Map.of("ErrorCode", "8", "Error", "Login failed: Bad credentials"), "Login failed - Bad credentials", "401");

                if (pathUrl.contains("/register"))
                    addErrorResponse(newResponses, Map.of("ErrorCode", "2", "Error", "Entity with the provided ID already exists"), "Registration failed - Bad credentials", "400");

                if (requireIdValidation.contains(operation.getOperationId()))
                    addErrorResponse(newResponses, Map.of("ErrorCode", "2", "Error", "[Protein | Drug | Disease | User | Comment] with ID {} does not exist"), "Operation not Performed - Bad Key", "400");

                if (pathUrl.contains("/api/admin/protein") || pathUrl.contains("/api/admin/drug") || pathUrl.contains("/api/admin/diseaseGraph"))
                    addErrorResponse(newResponses, Map.of("ErrorCode", "2", "Error", "[Protein | Drug | Disease] with ID {} [already exists | does not exist]"), "Operation not Performed - Bad Key", "400");

                if (pathUrl.contains("trend-analysis"))
                    addErrorResponse(newResponses, Map.of("ErrorCode", "5", "Error", "No [Drug | Protein] saved with the specified [category | pathway]: {name}"), "Error - Illegal Argument", "400");

                if (pathUrl.contains("pathway-recurrence"))
                    addErrorResponse(newResponses, Map.of("ErrorCode", "5", "Error", "Subsequence must contain only uppercase letters, got: {subsequence}"), "Error - Illegal Argument", "400");

                if (operation.getRequestBody() != null)
                    addErrorResponse(newResponses, Map.of("ErrorCode", "1", "[{property}]*", "[{property} is required]*"), "Validation Error - Field Missing", "400bis");

                if ((pathUrl.contains("/admin/drug") || pathUrl.contains("/admin/protein") || pathUrl.contains("/admin/diseaseGraph")) && operation.getRequestBody() != null)
                    addErrorResponse(newResponses, Map.of("ErrorCode", "5", "Error", "Some relationships refer to non-existing ids"), "Error - Illegal Argument", "400tris");

                addErrorResponse(newResponses, Map.of("ErrorCode", "3", "Error", "Error during the connection to the database"), "Error Connection to Database(s)", "503");
                addErrorResponse(newResponses, Map.of("ErrorCode", "4", "Error", "{description of the problem}"), "Generic Error Database(s)", "503bis");
                operation.setResponses(newResponses);  // Replace the existing responses with the new ApiResponses
            }));
        };
    }


    private static void addErrorResponse(ApiResponses newResponses, Map<String, String> response, String description, String errorCode) {
        ApiResponse resp = new ApiResponse().description(description).content(new Content().addMediaType("application/json", new MediaType().schema(new Schema<>().type("object").example(response) // Example payload
        )));

        newResponses.addApiResponse(errorCode, resp);
    }

}