package org.unipi.bioconnect.controller.Document;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unipi.bioconnect.DTO.Document.DrugDocDTO;
import org.unipi.bioconnect.DTO.Document.PatentStateAnalysisDTO;
import org.unipi.bioconnect.DTO.Document.TrendAnalysisDTO;
import org.unipi.bioconnect.service.Document.DrugDocService;

import java.util.List;

@RestController
@RequestMapping("api/drugDoc")
@Tag(name = "Drug MongoDB Controller", description = "API for Drug MongoDB operations")
public class DrugDocController {

    @Autowired
    private DrugDocService drugDocService;




    @GetMapping("/{searchedText}")
    @Operation(summary = "Details of a specific drug identified by its unique ID or name",
            description = "Fetches information about a drug specified by its unique ID or name. The response includes details such as the drug's ID, name, and other relevant attributes")
    public List<DrugDocDTO> searchDrug(
            @Parameter(
                    description = "The unique ID or name of the drug to retrieve", example = "DB00945",
                    required = true, schema = @Schema(type = "string")
            ) @PathVariable String searchedText) {
        return drugDocService.searchDrugDoc(searchedText);
    }




    @GetMapping("/trend-analysis/{category}")
    @Operation(summary = "Publication analysis trends for a specific drug category",
            description = "Fetches publication analysis data, such as trends and frequency of publications, for drugs in a specific category. The response includes analysis related to publications over time for the specified drug category")
    public List<TrendAnalysisDTO> getTrendAnalysisForCategory(
            @Parameter(
                    description = "The drug category to retrieve publication analysis for.", example = "Analgesics",
                    required = true, schema = @Schema(type = "string")
            ) @PathVariable String category) {
        return drugDocService.getTrendAnalysisForCategory(category);
    }




    @GetMapping("/expired-patents/{category}")
    @Operation(summary = "List of expired patents by a specific drug category",
            description = "Fetches information on expired patents related to drugs in a specified category. The response includes the drug IDs and names associated with expired patents in that category")
    public List<PatentStateAnalysisDTO> getExpiredPatentsByStateForCategory(
            @Parameter(
                    description = "The drug category to filter expired patents by",  example = "Anticoagulants",
                    required = true, schema = @Schema(type = "string")
            ) @PathVariable String category) {
        return drugDocService.getExpiredPatentsByStateForCategory(category);
    }

}
