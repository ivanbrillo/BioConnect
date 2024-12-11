package org.unipi.bioconnect.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.unipi.bioconnect.DTO.ProteinDTO;
import org.unipi.bioconnect.model.ProteinGraph;
import org.unipi.bioconnect.service.ProteinGraphService;

import java.util.List;


@RestController
@RequestMapping("api/proteinGraph")
@Tag(name = "Protein Neo4j Controller", description = "API for Protein Neo4j operations")
public class ProteinGraphController {

    @Autowired
    private ProteinGraphService proteinGraphService;

    @PostMapping("/addGraphProtein")
    @Operation(summary = "Add a protein to the Neo4j database")
    public String saveProteinDoc(@RequestBody @Validated ProteinDTO proteinDTO) {
        proteinGraphService.saveProteinGraph(proteinDTO);
        return "Added protein";
    }

    @GetMapping("/graphProteins")
    @Operation(summary = "Get all proteins from the Neo4j database")
    public List<ProteinGraph> getAllProteins() {
        return proteinGraphService.getAllProteins();
    }

//SOLO PER TEST
    @GetMapping("/threeProteins")
    @Operation(summary = "Get the top three proteins from the Neo4j database")
    public List<ProteinGraph> getTopThreeProteins() {
        return proteinGraphService.getTopThreeProteins();
    }

    @GetMapping("/checkNeo4jConnection")
    @Operation(summary = "Check the connection to the Neo4j database")
    public String checkNeo4jConnection() {
        return proteinGraphService.checkNeo4jConnection();
    }

}
