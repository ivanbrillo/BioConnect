package org.unipi.bioconnect.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unipi.bioconnect.DTO.Graph.DrugGraphDTO;
import org.unipi.bioconnect.model.DrugGraph;
import org.unipi.bioconnect.repository.DrugGraphRepository;
import org.unipi.bioconnect.repository.GraphRepository;
import org.unipi.bioconnect.utils.GraphUtils;

@Service
public class DrugGraphService {

    @Autowired
    private DrugGraphRepository drugGraphRepository;

    @Autowired
    private GraphRepository graphRepository;


    public DrugGraphDTO getDrugById(String drugID) {
        DrugGraph drugGraph = drugGraphRepository.findByDrugId(drugID);

        if (drugGraph == null)
            throw new IllegalArgumentException("Drug with ID " + drugID + " does not exist");

        return new DrugGraphDTO(drugGraph);
    }

    public void deleteDrugById(String drugID) {

        if (!drugGraphRepository.existsById(drugID))
            throw new RuntimeException("Drug with ID " + drugID + " does not exist");

        drugGraphRepository.deleteById(drugID);
    }


    private void updateDrugGraphDTO(DrugGraphDTO drugGraphDTO) {
        drugGraphDTO.setInhibit(GraphUtils.getRelationshipsUpdated(drugGraphDTO.getInhibit(), graphRepository));
        drugGraphDTO.setEnhance(GraphUtils.getRelationshipsUpdated(drugGraphDTO.getEnhance(), graphRepository));
    }

    private void saveProteinHelper(DrugGraphDTO drugGraphDTO) {
        updateDrugGraphDTO(drugGraphDTO);
        DrugGraph drugGraph = new DrugGraph(drugGraphDTO);
        drugGraphRepository.save(drugGraph);
    }


    public void saveDrugGraph(DrugGraphDTO drugGraphDTO) {

        if (drugGraphRepository.existsById(drugGraphDTO.getId()))
            throw new RuntimeException("Drug with ID " + drugGraphDTO.getId() + " already exist");

        saveProteinHelper(drugGraphDTO);

    }

    @Transactional
    public void updateDrugById(DrugGraphDTO drugGraphDTO) {

        if (!drugGraphRepository.existsById(drugGraphDTO.getId()))
            throw new RuntimeException("Drug with ID " + drugGraphDTO.getId() + " does not exist");

        drugGraphRepository.removeAllRelationships(drugGraphDTO.getId());
        saveProteinHelper(drugGraphDTO);

    }

}
