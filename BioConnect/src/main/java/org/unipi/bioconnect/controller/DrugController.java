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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.unipi.bioconnect.DTO.DrugDTO;
import org.unipi.bioconnect.service.Document.DrugDocService;
import org.unipi.bioconnect.service.Graph.DrugGraphService;

@RestController
@RequestMapping("/api/admin/drug")
@Tag(name = "Drug Controller", description = "API for Drug operations")
public class DrugController {

    @Autowired
    private DrugGraphService drugGraphService;

    @Autowired
    private DrugDocService drugDocService;



    @PostMapping("/add")
    @Operation(summary = "Add a new drug entry to both MongoDB and Neo4j",
            description = "Add a new drug entry to both MongoDB and Neo4j",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = DrugDTO.class)
                    )
            ))
    @Transactional
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string", example = "Drug {id} saved")))
    })
    public String saveDrugById(@RequestBody @Valid DrugDTO drugDTO) {
        drugGraphService.saveDrugGraph(drugDTO.getGraph());
        drugDocService.saveDrugDoc(drugDTO.getDocument());
        return "Drug " + drugDTO.getDocument().getId() + " saved";
    }




    @PutMapping("/update")
    @Operation(summary = "Update the details of an existing drug in both MongoDB and Neo4j",
            description = "Updates the details of an existing drug in both MongoDB and Neo4j",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DrugDTO.class)
                    )
            ))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string", example = "Drug {id} updated")))
    })
    @Transactional
    public String updateDrugById(@RequestBody @Valid DrugDTO drugDTO) {
        drugGraphService.updateDrugById(drugDTO.getGraph());
        drugDocService.updateDrugById(drugDTO.getDocument());
        return "Drug " + drugDTO.getDocument().getId() + " updated";
    }




    @DeleteMapping("/delete/{drugID}")
    @Operation(summary = "Delete a drug in the Neo4j and MongoDB databases by its drug ID",
            description = "Delete a drug in the Neo4j and MongoDB databases by its drug ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string", example = "Drug {id} deleted")))
    })
    @Transactional
    public String deleteDrugById(
            @Parameter(
                    description = "The unique identifier for the drug to delete", example = "DB00000",
                    required = true, schema = @Schema(type = "string")
            ) @PathVariable String drugID) {
        drugGraphService.deleteDrugById(drugID);
        drugDocService.deleteDrugById(drugID);
        return "Drug " + drugID + " deleted";
    }

}
