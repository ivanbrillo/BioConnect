package org.unipi.bioconnect.service.Graph;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unipi.bioconnect.DTO.Graph.BaseNodeDTO;
import org.unipi.bioconnect.DTO.Graph.DiseaseGraphDTO;
import org.unipi.bioconnect.repository.Graph.DiseaseGraphRepository;

import java.util.List;

@Service
public class DiseaseGraphService {

    @Autowired
    private DiseaseGraphRepository diseaseGraphRepository;

    @Autowired
    private GraphServiceCRUD graphServiceCRUD;

    public DiseaseGraphDTO getDiseaseById(String diseaseID) {
        return (DiseaseGraphDTO) graphServiceCRUD.getEntityById(diseaseID, diseaseGraphRepository);
    }

    public void deleteDiseaseById(String diseaseID) {
        graphServiceCRUD.deleteEntityById(diseaseID, diseaseGraphRepository);
    }

    public void saveDiseaseGraph(DiseaseGraphDTO diseaseGraphDTO) {
        graphServiceCRUD.saveEntityGraph(diseaseGraphDTO, diseaseGraphRepository);
    }

    @Transactional
    public void updateDiseaseById(DiseaseGraphDTO diseaseGraphDTO) {
        graphServiceCRUD.updateEntity(diseaseGraphDTO, diseaseGraphRepository);
    }

    public List<BaseNodeDTO> getDiseaseByDrug(String drugId) {
        return diseaseGraphRepository.getDiseaseByDrug(drugId);
    }

    public List<BaseNodeDTO> getShortestPathBetweenDiseases(String disease1Id, String disease2Id) {
        return diseaseGraphRepository.findShortestPathBetweenDiseases(disease1Id, disease2Id);
    }
}
