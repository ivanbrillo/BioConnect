package org.unipi.bioconnect.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unipi.bioconnect.DTO.Graph.DrugGraphDTO;
import org.unipi.bioconnect.repository.DrugGraphRepository;
import org.unipi.bioconnect.repository.GraphHelperRepository;

@Service
public class DrugGraphService {

    @Autowired
    private DrugGraphRepository drugGraphRepository;

    @Autowired
    private GraphHelperRepository graphHelperRepository;

    @Autowired
    private GraphServiceCRUD graphServiceCRUD;


    public DrugGraphDTO getDrugById(String drugID) {
        return (DrugGraphDTO) graphServiceCRUD.getEntityById(drugID, graphHelperRepository);
    }

    public void deleteDrugById(String drugID) {

        if (!drugGraphRepository.existsById(drugID))
            throw new RuntimeException("Drug with ID " + drugID + " does not exist");

        drugGraphRepository.deleteById(drugID);
    }


//    public void updateDrugGraphDTO(DrugGraphDTO drugGraphDTO) {
//        drugGraphDTO.setInhibit(GraphUtils.getRelationshipsUpdated(drugGraphDTO.getInhibit(), graphRepository));
//        drugGraphDTO.setEnhance(GraphUtils.getRelationshipsUpdated(drugGraphDTO.getEnhance(), graphRepository));
//    }

//    public void saveProteinHelper(DrugGraphDTO drugGraphDTO) {
//        updateDrugGraphDTO(drugGraphDTO);
//        DrugGraph drugGraph = new DrugGraph(drugGraphDTO);
//        drugGraphRepository.save(drugGraph);
//    }


    public void saveDrugGraph(DrugGraphDTO drugGraphDTO) {

        graphServiceCRUD.saveEntityGraph(drugGraphDTO, drugGraphRepository);
//        if (drugGraphRepository.existsById(drugGraphDTO.getId()))
//            throw new RuntimeException("Drug with ID " + drugGraphDTO.getId() + " already exist");

//        saveProteinHelper(drugGraphDTO);

    }

    @Transactional
    public void updateDrugById(DrugGraphDTO drugGraphDTO) {

        if (!drugGraphRepository.existsById(drugGraphDTO.getId()))
            throw new RuntimeException("Drug with ID " + drugGraphDTO.getId() + " does not exist");

        drugGraphRepository.removeAllRelationships(drugGraphDTO.getId());
//        saveProteinHelper(drugGraphDTO);

    }

}
