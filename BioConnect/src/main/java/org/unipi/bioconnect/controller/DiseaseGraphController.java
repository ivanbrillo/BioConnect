package org.unipi.bioconnect.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.unipi.bioconnect.DTO.Graph.DiseaseGraphDTO;
import org.unipi.bioconnect.service.DiseaseGraphService;

@RestController
@RequestMapping("api/diseaseGraph")
public class DiseaseGraphController {

    @Autowired
    private DiseaseGraphService diseaseGraphService;

    @PostMapping("/add")
    @Operation(summary = "Add a disease to Neo4j database")
    @Transactional
    public DiseaseGraphDTO saveDiseaseGraph(@RequestBody @Valid DiseaseGraphDTO diseaseGraphDTO) {
        diseaseGraphService.saveDiseaseGraph(diseaseGraphDTO);
        return diseaseGraphDTO;

    }

    @PutMapping("/update")
    @Operation(summary = "Update a disease in the Neo4j database")
    @Transactional
    public String updateDiseaseById(@RequestBody @Valid DiseaseGraphDTO diseaseGraphDTO) {
        diseaseGraphService.updateDiseaseById(diseaseGraphDTO);
        return "Disease " + diseaseGraphDTO.getId() + " updated";
    }


    @DeleteMapping("/delete/{diseaseID}")
    @Operation(summary = "Delete a disease in the Neo4j database by its disease ID")
    @Transactional
    public String deleteDiseaseById(@PathVariable String diseaseID) {
        diseaseGraphService.deleteDiseaseById(diseaseID);
        return "Disease " + diseaseID + " deleted";
    }

    @GetMapping("/{diseaseID}")
    @Operation(summary = "Get a disease from the Neo4j database by its disease ID")
    public DiseaseGraphDTO getDiseaseByID(@PathVariable String diseaseID) {
        return diseaseGraphService.getDiseaseById(diseaseID);
    }


}