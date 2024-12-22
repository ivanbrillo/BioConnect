package org.unipi.bioconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unipi.bioconnect.DTO.Graph.DiseaseGraphDTO;
import org.unipi.bioconnect.repository.DiseaseGraphRepository;

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
    
}
