package org.unipi.bioconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unipi.bioconnect.DTO.BaseNodeDTO;
import org.unipi.bioconnect.DTO.ProteinGraphDTO;
import org.unipi.bioconnect.model.ProteinGraph;
import org.unipi.bioconnect.repository.ProteinGraphRepository;

import javax.naming.NameAlreadyBoundException;
import java.util.*;


@Service
public class ProteinGraphService {

    @Autowired
    private ProteinGraphRepository graphRepository;


    // TODO sposta in helper
    public Set<BaseNodeDTO> getRelationshipsUpdated(Set<BaseNodeDTO> relationships) {

        List<String> interactionIds = relationships.stream()
                .map(BaseNodeDTO::getId).toList();

        Set<BaseNodeDTO> updated = new HashSet<>(graphRepository.findEntityNamesByIds(interactionIds));

        if (relationships.size() != updated.size())
            throw new IllegalArgumentException("Some relationships refers to not existing ids");

        return updated;

    }

    public void updateProteinGraphDTO(ProteinGraphDTO proteinGraphDTO) {
        proteinGraphDTO.setProteinInteractions(getRelationshipsUpdated(proteinGraphDTO.getProteinInteractions()));
        proteinGraphDTO.setProteinSimilarities(getRelationshipsUpdated(proteinGraphDTO.getProteinSimilarities()));
        proteinGraphDTO.setDrugEnhancedBy(getRelationshipsUpdated(proteinGraphDTO.getDrugEnhancedBy()));
        proteinGraphDTO.setDrugInhibitBy(getRelationshipsUpdated(proteinGraphDTO.getDrugInhibitBy()));
        proteinGraphDTO.setDiseaseInvolvedIn(getRelationshipsUpdated(proteinGraphDTO.getDiseaseInvolvedIn()));
    }

    public void saveProteinHelper(ProteinGraphDTO proteinGraphDTO) {
        updateProteinGraphDTO(proteinGraphDTO);
        ProteinGraph proteinGraph = new ProteinGraph(proteinGraphDTO);
        graphRepository.save(proteinGraph);
    }


    public void saveProteinGraph(ProteinGraphDTO proteinGraphDTO) {

        if (graphRepository.existsById(proteinGraphDTO.getId()))
            throw new RuntimeException("protein already exists");

        saveProteinHelper(proteinGraphDTO);

    }


    // Ottieni proteina e relazioni tramite uniprotID
    public ProteinGraphDTO getProteinById(String uniProtID) {
        ProteinGraph proteinGraph = graphRepository.findByUniProtID(uniProtID);

        if (proteinGraph == null)
            throw new IllegalArgumentException("Protein with ID " + uniProtID + " does not exist");

        return new ProteinGraphDTO(proteinGraph);
    }

    // cancellare proteina tramite id
    public void deleteProteinById(String uniProtID) {

        if (!graphRepository.existsById(uniProtID))
            throw new RuntimeException("protein does not exists");

        graphRepository.deleteById(uniProtID);
    }


    @Transactional
    public void updateProteinById(ProteinGraphDTO proteinGraphDTO) {

        if (!graphRepository.existsById(proteinGraphDTO.getId()))
            throw new RuntimeException("protein does not exists");

        graphRepository.removeAllRelationships(proteinGraphDTO.getId());
        saveProteinHelper(proteinGraphDTO);

    }


    // * TEST - Metodo per ottenere le prime tre proteine
    public List<ProteinGraph> getTopThreeProteins() {
        return graphRepository.findTopThreeProteins();
    }


}




