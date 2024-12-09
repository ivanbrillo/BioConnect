package org.unipi.bioconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.unipi.bioconnect.DTO.ProteinDTO;
import org.unipi.bioconnect.model.ProteinGraph;
import org.unipi.bioconnect.service.ProteinGraphService;

import java.util.List;


@RestController
public class ProteinGraphController {

    @Autowired
    private ProteinGraphService proteinGraphService;

    @PostMapping("/addGraphProtein")
    public String saveProteinDoc(@RequestBody @Validated ProteinDTO proteinDTO) {
        proteinGraphService.saveProteinGraph(proteinDTO);
        return "Added protein";
    }

    @GetMapping("/graphProteins")
    public List<ProteinGraph> getAllProteins() {
        return proteinGraphService.getAllProteins();
    }

}
