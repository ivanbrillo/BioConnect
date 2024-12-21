package org.unipi.bioconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unipi.bioconnect.DTO.Graph.ProteinGraphDTO;
import org.unipi.bioconnect.model.ProteinGraph;
import org.unipi.bioconnect.repository.GraphHelperRepository;
import org.unipi.bioconnect.repository.ProteinGraphRepository;

import java.util.*;


@Service
public class ProteinGraphService {

    @Autowired
    private ProteinGraphRepository proteinGraphRepository;

    @Autowired
    private GraphHelperRepository graphHelperRepository;

    @Autowired
    private GraphServiceCRUD graphServiceCRUD;


//    public void updateProteinGraphDTO(ProteinGraphDTO proteinGraphDTO) {
//        proteinGraphDTO.setProteinInteractions(GraphUtils.getRelationshipsUpdated(proteinGraphDTO.getProteinInteractions(), graphRepository));
//        proteinGraphDTO.setProteinSimilarities(GraphUtils.getRelationshipsUpdated(proteinGraphDTO.getProteinSimilarities(), graphRepository));
//        proteinGraphDTO.setDrugEnhancedBy(GraphUtils.getRelationshipsUpdated(proteinGraphDTO.getDrugEnhancedBy(), graphRepository));
//        proteinGraphDTO.setDrugInhibitBy(GraphUtils.getRelationshipsUpdated(proteinGraphDTO.getDrugInhibitBy(), graphRepository));
//        proteinGraphDTO.setDiseaseInvolvedIn(GraphUtils.getRelationshipsUpdated(proteinGraphDTO.getDiseaseInvolvedIn(), graphRepository));
//    }

//    public void saveProteinHelper(ProteinGraphDTO proteinGraphDTO) {
//        updateProteinGraphDTO(proteinGraphDTO);
//        ProteinGraph proteinGraph = new ProteinGraph(proteinGraphDTO);
//        proteinGraphRepository.save(proteinGraph);
//    }


    public void saveProteinGraph(ProteinGraphDTO proteinGraphDTO) {

        if (proteinGraphRepository.existsById(proteinGraphDTO.getId()))
            throw new RuntimeException("Protein with ID " + proteinGraphDTO.getId() + " already exist");

//        saveProteinHelper(proteinGraphDTO);

    }


    public ProteinGraphDTO getProteinById(String uniProtID) {
        return (ProteinGraphDTO) graphServiceCRUD.getEntityById(uniProtID, proteinGraphRepository);
    }

    public void deleteProteinById(String uniProtID) {

        if (!proteinGraphRepository.existsById(uniProtID))
            throw new RuntimeException("Protein with ID " + uniProtID + " does not exist");

        proteinGraphRepository.deleteById(uniProtID);
    }


    @Transactional
    public void updateProteinById(ProteinGraphDTO proteinGraphDTO) {

        if (!proteinGraphRepository.existsById(proteinGraphDTO.getId()))
            throw new RuntimeException("Protein with ID " + proteinGraphDTO.getId() + " does not exist");

        proteinGraphRepository.removeAllRelationships(proteinGraphDTO.getId());
//        saveProteinHelper(proteinGraphDTO);

    }


    // * TEST - Metodo per ottenere le prime tre proteine
    public List<ProteinGraph> getTopThreeProteins() {
        return proteinGraphRepository.findTopThreeProteins();
    }


}




