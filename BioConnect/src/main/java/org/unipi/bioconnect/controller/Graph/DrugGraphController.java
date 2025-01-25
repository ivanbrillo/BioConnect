package org.unipi.bioconnect.controller.Graph;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unipi.bioconnect.DTO.Graph.BaseNodeDTO;
import org.unipi.bioconnect.DTO.Graph.DrugGraphDTO;
import org.unipi.bioconnect.DTO.Graph.OppositeEffectDrugsDTO;
import org.unipi.bioconnect.service.Graph.DrugGraphService;

import java.util.List;


@RestController
@RequestMapping("api/drugGraph")
@Tag(name = "Drug Neo4j Controller", description = "API for Drug Neo4j operations")
public class DrugGraphController {

    @Autowired
    private DrugGraphService drugGraphService;

    @GetMapping("/{drugID}")
    @Operation(summary = "Get a drug from the Neo4j database by its drug ID",
            description = "Fetches the details of a drug specified by the ID parameter in the request.")
    public DrugGraphDTO getDrugByID(
            @Parameter(
                    description = "The unique ID of the drug to retrieve",
                    example = "DB00945",
                    required = true,
                    schema = @Schema(type = "string")
            ) @PathVariable String drugID) {
        return drugGraphService.getDrugById(drugID);
    }

    @GetMapping("/targetSimilarProtein/{uniProtId}")
    @Operation(summary = "Get drugs targeting proteins similar to a specified protein",
            description = "Fetches data on proteins that are similar to the one identified by the specified ID parameter. This helps in finding potential drug targets related to the given protein")
    public List<BaseNodeDTO> getDrugTargetSimilarProtein(
            @Parameter(
                    description = "The unique ID of the protein for which similar targets are to be retrieved",
                    example = "P68871",
                    required = true,
                    schema = @Schema(type = "string")
            )@PathVariable String uniProtId) {
        return drugGraphService.getDrugTargetSimilarProtein(uniProtId);
    }

    @GetMapping("/oppositeEffectsOnProtein/{drugId}")
    @Operation(summary = "Get information about drugs with opposite effects on proteins, based on the specified drug ID",
            description = "Fetches data on drugs that have opposite effects on proteins associated with the drug identified " +
                    "by the specified ID parameter. This endpoint returns a list of drugs and their corresponding proteins with opposite effects")
    public List<OppositeEffectDrugsDTO> getDrugOppositeEffectsProtein(
            @Parameter(
                    description = "The unique ID of the drug for which drugs with opposite effects on associated proteins are to be retrieved",
                    example = "DB00945",
                    required = true,
                    schema = @Schema(type = "string")
            )@PathVariable String drugId) {
        return drugGraphService.getDrugOppositeEffectsProtein(drugId);
    }
}
