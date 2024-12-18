package org.unipi.bioconnect.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.unipi.bioconnect.DTO.ProteinDTO;
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

    @PostMapping("/addGraphProtein")
    @Operation(summary = "Add a protein to the Neo4j database")
    public String saveProteinDoc(@RequestBody @Validated ProteinDTO proteinDTO) {
        try {
            proteinGraphService.saveProteinGraph(proteinDTO);
        } catch (NameAlreadyBoundException e) {
            throw new RuntimeException(e);
        }
        return "Added protein";
    }

    @GetMapping("/graphProteins")
    @Operation(summary = "Get all proteins from the Neo4j database")
    public List<ProteinGraph> getAllProteins() {
        return proteinGraphService.getAllProteins();
    }

    @GetMapping("/graphProtein/{uniProtID}")
    @Operation(summary = "Get a protein from the Neo4j database by its UniProt ID")
    public ProteinGraph getProteinByUniProtID(@PathVariable String uniProtID) {
        return proteinGraphService.getProteinByUniProtID(uniProtID);
    }

    //cancellare proteina tramite id
    @DeleteMapping("/graphProtein/{uniProtID}")
    @Operation(summary = "Delete a protein from the Neo4j database by its UniProt ID")
    public String deleteProteinByUniProtID(@PathVariable String uniProtID) {
        proteinGraphService.deleteProteinByUniProtID(uniProtID);
        return "Protein " + uniProtID + " deleted";
    }

    // Aggiornare proteina tramite tutti i campi
    @PutMapping("/graphProtein/{uniProtID}")
    @Operation(summary = "Update a protein in the Neo4j database by its UniProt ID")
    public String updateProteinByUniProtID(@RequestBody @Validated ProteinDTO proteinDTO) {
        proteinGraphService.updateProteinByUniProtID(proteinDTO);
        return "Protein " + proteinDTO.getUniProtID() + " updated";
    }

    // * SOLO PER TEST
    @GetMapping("/threeProteins")
    @Operation(summary = "Get the top three proteins from the Neo4j database")
    public List<ProteinGraph> getTopThreeProteins() {
        return proteinGraphService.getTopThreeProteins();
    }

    // * Solo per test
    @GetMapping("/checkNeo4jConnection")
    @Operation(summary = "Check the connection to the Neo4j database")
    public String checkNeo4jConnection() {
        return proteinGraphService.checkNeo4jConnection();
    }

}
