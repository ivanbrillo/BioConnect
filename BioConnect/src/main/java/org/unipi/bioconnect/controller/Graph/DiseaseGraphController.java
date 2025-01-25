package org.unipi.bioconnect.controller.Graph;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.unipi.bioconnect.DTO.Graph.BaseNodeDTO;
import org.unipi.bioconnect.DTO.Graph.DiseaseGraphDTO;
import org.unipi.bioconnect.service.Graph.DiseaseGraphService;

import java.util.List;

@RestController
@RequestMapping("api")
@Tag(name = "Disease Neo4j Controller", description = "API for Disease Neo4j operations")
public class DiseaseGraphController {

    @Autowired
    private DiseaseGraphService diseaseGraphService;

    @PostMapping("/admin/diseaseGraph/add")
    @Operation(summary = "Add a new disease node to the graph database",
                description = "Adds a new disease to the graph database by providing its details in the request body",
                requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                        required = true,
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = DiseaseGraphDTO.class),
                                examples = @ExampleObject(
                                        value = "{\n" +
                                                "  \"id\": \"0000\",\n" +
                                                "  \"name\": \"NewDisease\"\n" +
                                                "}"
                                )
                        )
                ))
    public String saveDiseaseGraph(@RequestBody @Valid DiseaseGraphDTO diseaseGraphDTO) {
        diseaseGraphService.saveDiseaseGraph(diseaseGraphDTO);
        return "Disease " + diseaseGraphDTO.getId() + " saved";
    }

    @PutMapping("/admin/diseaseGraph/update")
    @Operation(summary = "Updates the details of an existing disease node in the graph database",
            description = "Updates an existing disease node by providing its ID and the new details in the request body",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DiseaseGraphDTO.class),
                            examples = @ExampleObject(
                                    value = "{\n" +
                                            "  \"id\": \"0000\",\n" +
                                            "  \"name\": \"New Name\"\n" +
                                            "}"
                            )
                    )
            ))
    public String updateDiseaseById(@RequestBody @Valid DiseaseGraphDTO diseaseGraphDTO) {
        diseaseGraphService.updateDiseaseById(diseaseGraphDTO);
        return "Disease " + diseaseGraphDTO.getId() + " updated";
    }

    @DeleteMapping("/admin/diseaseGraph/delete/{diseaseID}")
    @Operation(summary = "Delete a specific disease node from the graph database identified by its unique ID",
                description = "Removes the disease node specified by the ID parameter from the database")
    public String deleteDiseaseById(
            @Parameter(
                    description = "The unique ID of the disease to delete",
                    example = "612645",
                    required = true,
                    schema = @Schema(type = "string")
            )
            @PathVariable String diseaseID) {
        diseaseGraphService.deleteDiseaseById(diseaseID);
        return "Disease " + diseaseID + " deleted";
    }

    @GetMapping("/diseaseGraph/{diseaseID}")
    @Operation(summary = "Get details of a specific disease identified by its unique ID.",
            description = "Fetches information about the disease specified by the ID parameter. The response includes the disease's name and the proteins involved in the disease.")
    public DiseaseGraphDTO getDiseaseByID(
            @Parameter(
                    description = "The unique ID of the disease to retrieve.",
                    example = "612645",
                    required = true,
                    schema = @Schema(type = "string")
            )
            @PathVariable String diseaseID) {
        return diseaseGraphService.getDiseaseById(diseaseID);
    }

    //* Advanced Operations
    @GetMapping("/diseaseGraph/diseaseByDrug/{drugId}")
    @Operation(summary = "All diseases linked to a specific drug identified by the drug's unique ID",
                description = "Fetches a list of diseases associated with the drug specified by the ID parameter")
    public List<BaseNodeDTO> getDiseaseByDrug(
            @Parameter(
                    description = "The unique ID of the drug to retrieve diseases",
                    example = "DB00945",
                    required = true,
                    schema = @Schema(type = "string")
            )
            @PathVariable String drugId) {
        return diseaseGraphService.getDiseaseByDrug(drugId);
    }

    @GetMapping("/diseaseGraph/shortestPath/{disease1Id}/{disease2Id}")
    @Operation(summary = "Shortest path between two diseases in the graph identified by their unique IDs",
                description = "Computes and returns the shortest path between two diseases, as specified by the ID parameters")
    public List<List<BaseNodeDTO>> getShortestPathBetweenDiseases(
            @Parameter(
                    description = "The unique ID of the starting disease",
                    example = "619562",
                    required = true,
                    schema = @Schema(type = "string")
            ) @PathVariable String disease1Id,

            @Parameter(
                    description = "The unique ID of the target disease",
                    example = "612645",
                    required = true,
                    schema = @Schema(type = "string")
            ) @PathVariable String disease2Id) {
        return diseaseGraphService.getShortestPathBetweenDiseases(disease1Id, disease2Id);
    }


}
