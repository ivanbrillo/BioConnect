package org.unipi.bioconnect.controller.Graph;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.unipi.bioconnect.DTO.Graph.ProteinGraphDTO;
import org.unipi.bioconnect.service.Graph.ProteinGraphService;


@RestController
@RequestMapping("api/proteinGraph")
@Tag(name = "Protein Neo4j Controller", description = "API for Protein Neo4j operations")
public class ProteinGraphController {

    @Autowired
    private ProteinGraphService proteinGraphService;

    @GetMapping("/{uniProtID}")
    @Operation(summary = "Get details of a specific protein, identified by its unique ID",
            description = "Fetches information about the protein specified by the ID parameter. The response includes the protein's interactions, similar proteins, drugs that enhance or inhibit the protein, and diseases the protein is involved in")
    public ProteinGraphDTO getProteinById(
            @Parameter(
                    description = "The unique ID of the protein to retrieve",
                    example = "P68871",
                    required = true,
                    schema = @Schema(type = "string")
            )
            @PathVariable String uniProtID) {
        return proteinGraphService.getProteinById(uniProtID);
    }
}
