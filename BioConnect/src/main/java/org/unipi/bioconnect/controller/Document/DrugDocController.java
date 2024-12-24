package org.unipi.bioconnect.controller.Document;

import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Search for a drug by id or name")
    public List<DrugDocDTO> searchDrug(@PathVariable String searchedText) {
        return drugDocService.searchDrugDoc(searchedText);
    }

    @GetMapping("/trend-analysis/{category}")
    @Operation(summary = "Get trend analysis for a drug category")
    public List<TrendAnalysisDTO> getTrendAnalysisForCategory(@PathVariable String category) {
        return drugDocService.getTrendAnalysisForCategory(category);
    }

    @GetMapping("/expired-patents/{category}")
    @Operation(summary = "Get expired patents by state for a drug category")
    public List<PatentStateAnalysisDTO> getExpiredPatentsByStateForCategory(@PathVariable String category) {
        return drugDocService.getExpiredPatentsByStateForCategory(category);
    }

}
