package org.unipi.bioconnect.controller;

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
import org.unipi.bioconnect.DTO.ProteinDTO;
import org.unipi.bioconnect.service.Document.ProteinDocService;
import org.unipi.bioconnect.service.Graph.ProteinGraphService;

@RestController
@RequestMapping("/api/admin/protein")
@Tag(name = "Protein Controller", description = "API for Protein operations")
public class ProteinController {

    @Autowired
    private ProteinGraphService proteinGraphService;
    @Autowired
    private ProteinDocService proteinDocService;

    @PostMapping("/add")
    @Operation(summary = "Add a protein to Neo4j and MongoDB databases",
                description = "Adds a new protein entry to both MongoDB and Neo4j",
                requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                        required = true,
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ProteinDTO.class),
                                examples = @ExampleObject(
                                        value = "{\n" +
                                                "  \"id\": \"P68871\",\n" +
                                                "  \"name\": \"New Hemoglobin\",\n" +
                                                "  \"description\": \"Involved in oxygen transport\"\n" +
                                                "}"
                                )
                        )
                ))
    @Transactional
    public String saveProteinById(@RequestBody @Valid ProteinDTO proteinDTO) {
        proteinGraphService.saveProteinGraph(proteinDTO.getGraph());
        proteinDocService.saveProteinDoc(proteinDTO.getDocument());
        return "Protein " + proteinDTO.getDocument().getId() + " saved correctly";
    }

    @PutMapping("/update")
    @Operation(summary = "Updates an existing protein entry in both MongoDB and Neo4j",
            description = "Updates an existing protein entry in both MongoDB and Neo4j",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProteinDTO.class),
                            examples = @ExampleObject(
                                    value = "{\n" +
                                            "  \"id\": \"P68871\",\n" +
                                            "  \"name\": \"New Name\",\n" +
                                            "  \"description\": \"Involved in oxygen transport\"\n" +
                                            "}"
                            )
                    )
            ))
    @Transactional
    public String updateProteinById(@RequestBody @Valid ProteinDTO proteinDTO) {
        proteinGraphService.updateProteinById(proteinDTO.getGraph());
        proteinDocService.updateProteinDoc(proteinDTO.getDocument());
        return "Protein " + proteinDTO.getDocument().getId() + " updated";
    }

    @DeleteMapping("/delete/{uniProtID}")
    @Operation(summary = "Delete a protein in the Neo4j and MongoDB databases by its UniProt ID",
                description = "Delete a protein in the Neo4j and MongoDB databases by its UniProt ID")
    @Transactional
    public String deleteProteinById(
            @Parameter(
                    description = "The unique identifier for the protein to be deleted",
                    example = "P68871",
                    required = true,
                    schema = @Schema(type = "string")
            ) @PathVariable String uniProtID) {
        proteinGraphService.deleteProteinById(uniProtID);
        proteinDocService.deleteProtein(uniProtID);
        return "Protein " + uniProtID + " deleted correctly";
    }


}
