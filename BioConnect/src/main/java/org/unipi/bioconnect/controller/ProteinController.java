package org.unipi.bioconnect.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.unipi.bioconnect.DTO.ProteinDTO;
import org.unipi.bioconnect.service.AdminService;
import org.unipi.bioconnect.service.Document.ProteinDocService;
import org.unipi.bioconnect.service.Graph.ProteinGraphService;

@RestController
@RequestMapping("api/protein")
public class ProteinController {

    @Autowired
    private ProteinGraphService proteinGraphService;
    @Autowired
    private ProteinDocService proteinDocService;
    @Autowired
    private AdminService AdminService;


    @PostMapping("/add")
    @Operation(summary = "Add a protein to Neo4j and MongoDB databases")
    @Transactional
    public String saveProteinGraph(@RequestBody @Valid ProteinDTO proteinDTO) {
        proteinGraphService.saveProteinGraph(proteinDTO.getGraph());
        proteinDocService.saveProteinDoc(proteinDTO.getDocument());

        return "Protein " + proteinDTO.getDocument().getId() + " saved correctly";
    }

    @PutMapping("/update")
    @Operation(summary = "Update a protein in the Neo4j and MongoDB databases")
    @Transactional
    public String updateProteinById(@RequestBody @Valid ProteinDTO proteinDTO) {
        proteinGraphService.updateProteinById(proteinDTO.getGraph());
        proteinDocService.updateProteinDoc(proteinDTO.getDocument());
        return "Protein " + proteinDTO.getDocument().getId() + " updated";
    }

    @DeleteMapping("/delete/{uniProtID}")
    @Operation(summary = "Delete a protein in the Neo4j and MongoDB databases by its UniProt ID")
    @Transactional
    public String deleteProteinById(@PathVariable String uniProtID) {
        proteinGraphService.deleteProteinById(uniProtID);
        proteinDocService.deleteProtein(uniProtID);
        AdminService.deleteCommentsByElementID(uniProtID);
        return "Protein " + uniProtID + " deleted correctly";
    }


}
