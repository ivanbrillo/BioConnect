package org.unipi.bioconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unipi.bioconnect.DTO.ProteinDTO;
import org.unipi.bioconnect.model.ProteinGraph;
import org.unipi.bioconnect.repository.ProteinGraphRepository;

import javax.naming.NameAlreadyBoundException;
import java.util.List;


@Service
public class ProteinGraphService {

    @Autowired
    private ProteinGraphRepository graphRepository;


    @Transactional
    public void saveProteinGraph(ProteinDTO proteinDTO) {

        if(graphRepository.existsById(proteinDTO.getUniProtID()))
            throw new RuntimeException("protein already exists");

        ProteinGraph proteinGraph = new ProteinGraph(proteinDTO.getUniProtID(), proteinDTO.getName());

        for (ProteinDTO interaction : proteinDTO.getProteinInteractions()) {

            ProteinGraph existingProtein = graphRepository.findByUniProtID(interaction.getUniProtID());

            if (existingProtein != null)
                proteinGraph.addInteraction(existingProtein);

        }

        graphRepository.save(proteinGraph);
    }

    public List<ProteinGraph> getAllProteins() {
        return graphRepository.findAll();
//        return graphRepository.findAllProjectedBy();
    }


}




