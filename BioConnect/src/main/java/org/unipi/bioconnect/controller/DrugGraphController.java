package org.unipi.bioconnect.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unipi.bioconnect.DTO.Graph.BaseNodeDTO;
import org.unipi.bioconnect.DTO.Graph.DrugGraphDTO;
import org.unipi.bioconnect.DTO.Graph.OppositeEffectDrugsDTO;
import org.unipi.bioconnect.model.DrugGraph;
import org.unipi.bioconnect.service.DrugGraphService;

import java.util.List;


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

    @GetMapping("/targetSimilarProtein/{uniProtId}")
    public List<BaseNodeDTO> getDrugTargetSimilarProtein(@PathVariable String uniProtId) {
        return drugGraphService.getDrugTargetSimilarProtein(uniProtId);
    }

    //! funziona ma non mi convince
    @GetMapping("/oppositeEffectsOnProtein/{proteinId}")
    public List<OppositeEffectDrugsDTO> getDrugOppositeEffectsProtein(@PathVariable String proteinId) {
        return drugGraphService.getDrugOppositeEffectsProtein(proteinId);
    }
}
