package org.unipi.bioconnect.controller.Document;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Operation(summary = "Trend analysis of publications related to a specific protein pathway",
            description = "Publication trends for a given pathway related to proteins. The response includes data on publication frequency over time")
    public List<TrendAnalysisDTO> getTrendAnalysisForPathway(
            @Parameter(
                    description = "The protein-related pathway for trend analysis",
                    example = "Lipid metabolism",
                    required = true,
                    schema = @Schema(type = "string")
            )
            @PathVariable String pathway) {
        return proteinDocService.getTrendAnalysisForPathway(pathway);
    }

    @GetMapping("/pathway-recurrence/{subsequence}")
    @Operation(summary = "Pathway recurrence data for a specific subsequence of a protein",
            description = "Searches for pathway recurrence information related to a given subsequence of a protein. The response includes details on the pathways in which the subsequence is involved")
    public List<PathwayRecurrenceDTO> getPathwayRecurrence(
            @Parameter(
                    description = "The subsequence of the protein to search",
                    example = "ATGT",
                    required = true,
                    schema = @Schema(type = "string")
            )
            @PathVariable String subsequence) {
        return proteinDocService.getPathwayRecurrence(subsequence);
    }

    @GetMapping("/{searchedText}")
    @Operation(summary = "Details of a specific protein document from the MongoDB collection, identified by its unique ID",
            description = "Fetches detailed information about a protein specified by the ID parameter. The response includes the protein's name and attributes")
    public List<ProteinDocDTO> searchProtein(
            @Parameter(
                    description = "The unique ID of the protein to search",
                    example = "P68871",
                    required = true,
                    schema = @Schema(type = "string")
            )
            @PathVariable String searchedText) {
        return proteinDocService.searchProteinDoc(searchedText);
    }

    @GetMapping("/getProteinsByPathwayAndLocation")
    @Operation(summary = "Proteins associated with a specific pathway and subcellular location",
            description = "Fetches proteins that are linked to a specific pathway and s")
    public List<ProteinDocDTO> getProteinsByPathwayAndLocation(
            @Parameter(
                    description = "The pathway to filter proteins by",
                    example = "Lipid metabolism",
                    required = true,
                    schema = @Schema(type = "string")
            ) @RequestParam String pathway,

            @Parameter(
                    description = "The subcellular location to filter proteins by",
                    example = "Microsome membrane",
                    required = true,
                    schema = @Schema(type = "string")
            ) @RequestParam String subcellularLocation) {
        return proteinDocService.getProteinsByPathwayAndLocation(pathway, subcellularLocation);
    }

}
