package org.unipi.bioconnect.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.unipi.bioconnect.DTO.PathwayRecurrenceDTO;
import org.unipi.bioconnect.DTO.ProteinDTO;
import org.unipi.bioconnect.DTO.TrendAnalysisDTO;
import org.unipi.bioconnect.model.ProteinDoc;
import org.unipi.bioconnect.service.ProteinDocService;

import java.util.List;

@RestController
@RequestMapping("api/proteinDoc")
@Tag(name = "Protein MongoDB Controller", description = "API for Protein MongoDB operations")
public class ProteinDocController {

    @Autowired
    private ProteinDocService proteinDocService;

    @PostMapping("/addDocProtein")
    @Operation(summary = "Add a protein to the MongoDB database")
    public String saveProteinDoc(@RequestBody @Validated ProteinDTO proteinDTO) {
        proteinDocService.saveProteinDoc(proteinDTO);
        return "Added protein";
    }

    @GetMapping("/protein/trend-analysis/{pathway}")
    @Operation(summary = "Get trend analysis for a protein pathway")
    public List<TrendAnalysisDTO> getTrendAnalysisForPathway(@PathVariable String pathway) {
        return proteinDocService.getTrendAnalysisForPathway(pathway);
    }

    @GetMapping("/pathway-recurrence/{subsequence}")
    @Operation(summary = "Get pathway recurrence for a protein subsequence")
    public List<PathwayRecurrenceDTO> getPathwayRecurrence(@PathVariable String subsequence) {
        return proteinDocService.getPathwayRecurrence(subsequence);
    }


    @GetMapping("/searchProtein/{searchedText}")
    @Operation(summary = "Search for a protein by name or pathway")
    public List<ProteinDTO> searchProtein(@PathVariable String searchedText) {
        return proteinDocService.searchProteinDoc(searchedText);
    }


    @GetMapping("/getProteinsByPathwayAndLocation")
    @Operation(summary = "Get proteins by pathway and subcellular location")
    public List<ProteinDTO> getProteinsByPathwayAndLocation(
            @RequestParam String pathway,
            @RequestParam String subcellularLocation) {

        return proteinDocService.getProteinsByPathwayAndLocation(pathway, subcellularLocation);
    }


    @GetMapping("/proteins")
    @Operation(summary = "Get all proteins")
    public List<ProteinDTO> getAllProteins() {
        return proteinDocService.getAllProteins();
    }


}
