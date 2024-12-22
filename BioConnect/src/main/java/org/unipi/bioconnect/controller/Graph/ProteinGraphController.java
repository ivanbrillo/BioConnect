package org.unipi.bioconnect.controller.Graph;

import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Get a protein from the Neo4j database by its UniProt ID")
    public ProteinGraphDTO getProteinById(@PathVariable String uniProtID) {
        return proteinGraphService.getProteinById(uniProtID);
    }


}
