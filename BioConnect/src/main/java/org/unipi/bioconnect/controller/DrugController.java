package org.unipi.bioconnect.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.unipi.bioconnect.DTO.DrugDTO;
import org.unipi.bioconnect.service.Document.DrugDocService;
import org.unipi.bioconnect.service.Graph.DrugGraphService;
import org.unipi.bioconnect.service.AdminService;

@RestController
@RequestMapping("api/drug")
public class DrugController {

    @Autowired
    private DrugGraphService drugGraphService;

    @Autowired
    private DrugDocService drugDocService;

    @Autowired
    private AdminService AdminService;


    @PostMapping("/add")
    @Operation(summary = "Add a drug to Neo4j and MongoDB databases")
    @Transactional
    public String saveDrugGraph(@RequestBody @Valid DrugDTO drugDTO) {
        drugGraphService.saveDrugGraph(drugDTO.getGraph());
        drugDocService.saveDrugDoc(drugDTO.getDocument());
        return "Drug " + drugDTO.getDocument().getId() + " saved";
    }

    @PutMapping("/update")
    @Operation(summary = "Update a drug in the Neo4j and MongoDB databases")
    @Transactional
    public String updateDrugById(@RequestBody @Valid DrugDTO drugDTO) {
        drugGraphService.updateDrugById(drugDTO.getGraph());
        drugDocService.updateDrugById(drugDTO.getDocument());
        return "Drug " + drugDTO.getDocument().getId() + " updated";
    }

    @DeleteMapping("/delete/{drugID}")
    @Operation(summary = "Delete a drug in the Neo4j and MongoDB databases by its drug ID")
    @Transactional
    public String deleteDrugById(@PathVariable String drugID) {
        drugGraphService.deleteDrugById(drugID);
        drugDocService.deleteDrugById(drugID);
        AdminService.deleteCommentsByElementID(drugID);
        return "Drug " + drugID + " deleted";
    }

}
