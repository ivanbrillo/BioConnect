package org.unipi.bioconnect.controller.Document;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.unipi.bioconnect.DTO.Document.PathwayRecurrenceDTO;
import org.unipi.bioconnect.DTO.Document.ProteinDocDTO;
import org.unipi.bioconnect.DTO.Document.TrendAnalysisDTO;
import org.unipi.bioconnect.service.Document.ProteinDocService;

import java.util.List;

@RestController
@RequestMapping("api/proteinDoc")
@Tag(name = "Protein MongoDB Controller", description = "API for Protein MongoDB operations")
public class ProteinDocController {

    @Autowired
    private ProteinDocService proteinDocService;

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

    @GetMapping("/{searchedText}")
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

}
