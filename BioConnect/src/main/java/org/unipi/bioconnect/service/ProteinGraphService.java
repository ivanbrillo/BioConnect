package org.unipi.bioconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unipi.bioconnect.DTO.ProteinDTO;
import org.unipi.bioconnect.model.ProteinDoc;
import org.unipi.bioconnect.model.ProteinGraph;
import org.unipi.bioconnect.repository.ProteinDocRepository;
import org.unipi.bioconnect.repository.ProteinGraphRepository;

import java.util.List;

@Service
public class ProteinGraphService {

    @Autowired
    private ProteinGraphRepository graphRepository;


    public void saveProteinGraph(ProteinDTO proteinDTO) {

        ProteinGraph proteinGraph = new ProteinGraph();
        proteinGraph.setUniProtID(proteinDTO.getUniProtID());
        proteinGraph.setName(proteinDTO.getName());

        graphRepository.save(proteinGraph);
    }

    public List<ProteinDTO> getAllProteins() {
        return graphRepository.findAllProjectedBy();
    }


}




