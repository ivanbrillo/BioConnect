package org.unipi.bioconnect.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.unipi.bioconnect.DTO.Doc.PathwayRecurrenceDTO;
import org.unipi.bioconnect.DTO.Doc.ProteinDocDTO;
import org.unipi.bioconnect.DTO.Doc.TrendAnalysisDTO;
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
    public String saveProteinDoc(@RequestBody @Validated ProteinDocDTO proteinDocDTO) {
        proteinDocService.saveProteinDoc(proteinDocDTO);
        return "Added protein";
    }

    @GetMapping("/trend-analysis/{pathway}")
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
    @Operation(summary = "Search for a protein by id or name")
    public List<ProteinDocDTO> searchProtein(@PathVariable String searchedText) {
        return proteinDocService.searchProteinDoc(searchedText);
    }


    @GetMapping("/getProteinsByPathwayAndLocation")
    @Operation(summary = "Get proteins by pathway and subcellular location")
    public List<ProteinDocDTO> getProteinsByPathwayAndLocation(
            @RequestParam String pathway,
            @RequestParam String subcellularLocation) {

        return proteinDocService.getProteinsByPathwayAndLocation(pathway, subcellularLocation);
    }

    // ! mostrare tutte le proteine è impossibile in dataset grande
    @GetMapping("/proteins")
    @Operation(summary = "Get all proteins")
    public List<ProteinDocDTO> getAllProteins() {
        return proteinDocService.getAllProteins();
    }


    // TODO add @NotNull around
    // TODO delete also in graphDB
    @DeleteMapping("/deleteProtein/{uniProtID}")
    public String deleteProtein(@PathVariable @NotBlank String uniProtID) {
        return proteinDocService.deleteProtein(uniProtID);
    }


}
