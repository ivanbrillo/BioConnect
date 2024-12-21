package org.unipi.bioconnect.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.unipi.bioconnect.DTO.DrugDTO;
import org.unipi.bioconnect.service.DrugGraphService;

@RestController
@RequestMapping("api/drug")
public class DrugController {

    @Autowired
    private DrugGraphService drugGraphService;

    @PostMapping("/add")
    @Operation(summary = "Add a drug to Neo4j and MongoDB databases")
    @Transactional
    public DrugDTO saveDrugGraph(@RequestBody @Valid DrugDTO drugDTO) {

        drugGraphService.saveDrugGraph(drugDTO.getGraph());

        //TODO save drug doc
//        drugDocService.saveDrugDoc(drugDTO.getDocument());

        return drugDTO;

    }

    @PutMapping("/update")
    @Operation(summary = "Update a drug in the Neo4j and MongoDB databases")
    @Transactional
    public String updateDrugById(@RequestBody @Valid DrugDTO drugDTO) {
        drugGraphService.updateDrugById(drugDTO.getGraph());

        //TODO add document update
        return "Drug " + drugDTO.getDocument().getId() + " updated";
    }


    @DeleteMapping("/delete/{drugID}")
    @Operation(summary = "Delete a drug in the Neo4j and MongoDB databases by its drug ID")
    @Transactional
    public String deleteDrugById(@PathVariable String drugID) {
        drugGraphService.deleteDrugById(drugID);

        //TODO add document delete
        return "Drug " + drugID + " deleted";
    }

}
