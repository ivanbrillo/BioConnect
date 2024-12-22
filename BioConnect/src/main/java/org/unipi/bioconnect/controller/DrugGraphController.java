package org.unipi.bioconnect.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unipi.bioconnect.DTO.Graph.DrugGraphDTO;
import org.unipi.bioconnect.service.DrugGraphService;


@RestController
@RequestMapping("api/drugGraph")
@Tag(name = "Drug Neo4j Controller", description = "API for Drug Neo4j operations")
public class DrugGraphController {

    @Autowired
    private DrugGraphService drugGraphService;

    @GetMapping("/{drugID}")
    @Operation(summary = "Get a drug from the Neo4j database by its drug ID")
    public DrugGraphDTO getDrugByID(@PathVariable String drugID) {
        return drugGraphService.getDrugById(drugID);
    }

}
