package org.unipi.bioconnect.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unipi.bioconnect.DTO.ProteinDTO;
import org.unipi.bioconnect.service.ProteinGraphService;

@RestController
@RequestMapping("api/")
public class ProteinController {

    @Autowired
    private ProteinGraphService proteinGraphService;

    @PostMapping("/addProtein")
    @Operation(summary = "Add a protein to Neo4j and MongoDB databases")
    @Transactional
    public ProteinDTO saveProteinGraph(@RequestBody @Validated ProteinDTO proteinDTO) {

        proteinGraphService.saveProteinGraph(proteinDTO.getGraph());

        return proteinDTO;

    }


}
