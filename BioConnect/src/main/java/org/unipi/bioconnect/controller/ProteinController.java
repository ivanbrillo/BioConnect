package org.unipi.bioconnect.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.unipi.bioconnect.DTO.ProteinDTO;
import org.unipi.bioconnect.DTO.ProteinGraphDTO;
import org.unipi.bioconnect.service.ProteinDocService;
import org.unipi.bioconnect.service.ProteinGraphService;

@RestController
@RequestMapping("api/")
public class ProteinController {

    @Autowired
    private ProteinGraphService proteinGraphService;
    @Autowired
    private ProteinDocService proteinDocService;


    @PostMapping("/addProtein")
    @Operation(summary = "Add a protein to Neo4j and MongoDB databases")
    @Transactional
    public ProteinDTO saveProteinGraph(@RequestBody @Valid ProteinDTO proteinDTO) {

        proteinGraphService.saveProteinGraph(proteinDTO.getGraph());
        proteinDocService.saveProteinDoc(proteinDTO.getDocument());

        return proteinDTO;

    }

    @PutMapping("/updateProtein")
    @Operation(summary = "Update a protein in the Neo4j and MongoDB databases by its UniProt ID")
    @Transactional
    public String updateProteinById(@RequestBody @Valid ProteinDTO proteinDTO) {
        proteinGraphService.updateProteinById(proteinDTO.getGraph());

        //TODO add document update
        return "Protein " + proteinDTO.getDocument().getId() + " updated";
    }

    @DeleteMapping("/deleteProtein/{uniProtID}")
    @Operation(summary = "Delete a protein in the Neo4j and MongoDB databases by its UniProt ID")
    @Transactional
    public String deleteProteinById(@PathVariable String uniProtID) {
        proteinGraphService.deleteProteinById(uniProtID);

        //TODO add document delete
        return "Protein " + uniProtID + " deleted";
    }


}
