package org.unipi.bioconnect.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.unipi.bioconnect.DTO.Graph.ProteinGraphDTO;
import org.unipi.bioconnect.model.ProteinGraph;
import org.unipi.bioconnect.service.ProteinGraphService;

import javax.naming.NameAlreadyBoundException;
import java.util.List;


@RestController
@RequestMapping("api/")
@Tag(name = "Protein Neo4j Controller", description = "API for Protein Neo4j operations")
public class ProteinGraphController {

    @Autowired
    private ProteinGraphService proteinGraphService;

    @GetMapping("/graphProtein/{uniProtID}")
    @Operation(summary = "Get a protein from the Neo4j database by its UniProt ID")
    public ProteinGraphDTO getProteinById(@PathVariable String uniProtID) {
        return proteinGraphService.getProteinById(uniProtID);
    }


}
