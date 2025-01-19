package org.unipi.bioconnect.controller.Graph;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.unipi.bioconnect.DTO.Graph.BaseNodeDTO;
import org.unipi.bioconnect.DTO.Graph.DiseaseGraphDTO;
import org.unipi.bioconnect.service.Graph.DiseaseGraphService;

import java.util.List;

@RestController
@RequestMapping("api/diseaseGraph")
public class DiseaseGraphController {

    @Autowired
    private DiseaseGraphService diseaseGraphService;

    @PostMapping("/add")
    @Operation(summary = "Add a disease to Neo4j database")
    public String saveDiseaseGraph(@RequestBody @Valid DiseaseGraphDTO diseaseGraphDTO) {
        diseaseGraphService.saveDiseaseGraph(diseaseGraphDTO);
        return "Disease " + diseaseGraphDTO.getId() + " saved";
    }

    @PutMapping("/update")
    @Operation(summary = "Update a disease in the Neo4j database")
    public String updateDiseaseById(@RequestBody @Valid DiseaseGraphDTO diseaseGraphDTO) {
        diseaseGraphService.updateDiseaseById(diseaseGraphDTO);
        return "Disease " + diseaseGraphDTO.getId() + " updated";
    }

    @DeleteMapping("/delete/{diseaseID}")
    @Operation(summary = "Delete a disease in the Neo4j database by its disease ID")
    public String deleteDiseaseById(@PathVariable String diseaseID) {
        diseaseGraphService.deleteDiseaseById(diseaseID);
        return "Disease " + diseaseID + " deleted";
    }

    @GetMapping("/{diseaseID}")
    @Operation(summary = "Get a disease from the Neo4j database by its disease ID")
    public DiseaseGraphDTO getDiseaseByID(@PathVariable String diseaseID) {
        return diseaseGraphService.getDiseaseById(diseaseID);
    }

    //* Advanced Operations
    @GetMapping("/diseaseByDrug/{drugId}")
    @Operation(summary = "Get diseases linked to a particular drug")
    public List<BaseNodeDTO> getDiseaseByDrug(@PathVariable String drugId) {
        return diseaseGraphService.getDiseaseByDrug(drugId);
    }

    @GetMapping("/shortestPath/{disease1Id}/{disease2Id}")
    @Operation(summary = "Get the shortest path between two diseases")
    public List<BaseNodeDTO> getShortestPathBetweenDiseases(@PathVariable String disease1Id, @PathVariable String disease2Id) {
        return diseaseGraphService.getShortestPathBetweenDiseases(disease1Id, disease2Id);
    }


}
