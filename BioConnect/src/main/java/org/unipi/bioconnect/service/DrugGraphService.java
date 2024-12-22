package org.unipi.bioconnect.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unipi.bioconnect.DTO.Graph.BaseNodeDTO;
import org.unipi.bioconnect.DTO.Graph.DrugGraphDTO;
import org.unipi.bioconnect.model.DrugGraph;
import org.unipi.bioconnect.repository.DrugGraphRepository;

import java.util.List;

@Service
public class DrugGraphService {

    @Autowired
    private DrugGraphRepository drugGraphRepository;

    @Autowired
    private GraphServiceCRUD graphServiceCRUD;

    public DrugGraphDTO getDrugById(String drugID) {
        return (DrugGraphDTO) graphServiceCRUD.getEntityById(drugID, drugGraphRepository);
    }

    public void deleteDrugById(String drugID) {
        graphServiceCRUD.deleteEntityById(drugID, drugGraphRepository);
    }

    public void saveDrugGraph(DrugGraphDTO drugGraphDTO) {
        graphServiceCRUD.saveEntityGraph(drugGraphDTO, drugGraphRepository);
    }

    @Transactional
    public void updateDrugById(DrugGraphDTO drugGraphDTO) {
         graphServiceCRUD.updateEntity(drugGraphDTO, drugGraphRepository);
    }

    public List<BaseNodeDTO> getDrugTargetSimilarProtein(String uniProtId) {
        return drugGraphRepository.getDrugTargetSimilarProtein(uniProtId);
    }

}
