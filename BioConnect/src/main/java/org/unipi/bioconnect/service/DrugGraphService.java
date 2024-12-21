package org.unipi.bioconnect.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unipi.bioconnect.DTO.Graph.BaseNodeDTO;
import org.unipi.bioconnect.DTO.Graph.DrugGraphDTO;
import org.unipi.bioconnect.model.DrugGraph;
import org.unipi.bioconnect.repository.DrugGraphRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DrugGraphService {

    @Autowired
    private DrugGraphRepository graphRepository;


    public DrugGraphDTO getDrugById(String drugID) {
        DrugGraph drugGraph = graphRepository.findByDrugId(drugID);

        if (drugGraph == null)
            throw new IllegalArgumentException("Drug with ID " + drugID + " does not exist");

        return new DrugGraphDTO(drugGraph);
    }


    // TODO sposta in helper
    public Set<BaseNodeDTO> getRelationshipsUpdated(Set<BaseNodeDTO> relationships) {

        List<String> interactionIds = relationships.stream()
                .map(BaseNodeDTO::getId).toList();

        Set<BaseNodeDTO> updated = new HashSet<>(graphRepository.findEntityNamesByIds(interactionIds));

        if (relationships.size() != updated.size())
            throw new IllegalArgumentException("Some relationships refers to not existing ids");

        return updated;

    }

    public void updateDrugGraphDTO(DrugGraphDTO drugGraphDTO) {
        drugGraphDTO.setInhibit(getRelationshipsUpdated(drugGraphDTO.getInhibit()));
        drugGraphDTO.setEnhance(getRelationshipsUpdated(drugGraphDTO.getEnhance()));
    }

    public void saveProteinHelper(DrugGraphDTO drugGraphDTO) {
        updateDrugGraphDTO(drugGraphDTO);
        DrugGraph drugGraph = new DrugGraph(drugGraphDTO);
        graphRepository.save(drugGraph);
    }


    public void saveDrugGraph(DrugGraphDTO drugGraphDTO) {

        if (graphRepository.existsById(drugGraphDTO.getId()))
            throw new RuntimeException("protein already exists");

        saveProteinHelper(drugGraphDTO);

    }

}
