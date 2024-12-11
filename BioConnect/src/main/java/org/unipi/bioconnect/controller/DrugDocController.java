package org.unipi.bioconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.unipi.bioconnect.DTO.DrugDTO;
import org.unipi.bioconnect.DTO.PatentStateAnalysisDTO;
import org.unipi.bioconnect.DTO.TrendAnalysisDTO;
import org.unipi.bioconnect.service.DrugDocService;

import java.util.List;

@RestController
public class DrugDocController {

    @Autowired
    private DrugDocService drugDocService;

    @GetMapping("/searchDrug/{searchedText}")
    public List<DrugDTO> searchDrug(@PathVariable String searchedText) {
        return drugDocService.searchDrugDoc(searchedText);
    }

    @GetMapping("/drug/trend-analysis/{category}")
    public List<TrendAnalysisDTO> getTrendAnalysisForPathway(@PathVariable String category) {
        return drugDocService.getTrendAnalysisForCategory(category);
    }

    @GetMapping("/drug/expired-patents/{category}")
    public List<PatentStateAnalysisDTO> getExpiredPatentsByStateForCategory(@PathVariable String category) {
        return drugDocService.getExpiredPatentsByStateForCategory(category);
    }

}
