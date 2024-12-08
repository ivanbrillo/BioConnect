package org.unipi.bioconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.unipi.bioconnect.DTO.ProteinDTO;
import org.unipi.bioconnect.service.ProteinDocService;

import java.util.List;

@RestController
public class ProteinDocController {

    @Autowired
    private ProteinDocService proteinDocService;

    @PostMapping("/addDocProtein")
    public String saveProteinDoc(@RequestBody @Validated ProteinDTO proteinDTO) {
        proteinDocService.saveProteinDoc(proteinDTO);
        return "Added protein";
    }

    @GetMapping("/proteins")
    public List<ProteinDTO> getAllProteins() {
        return proteinDocService.getAllProteins();
    }


}
