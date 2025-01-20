package org.unipi.bioconnect.service.Graph;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unipi.bioconnect.DTO.Graph.BaseNodeDTO;
import org.unipi.bioconnect.DTO.Graph.DiseaseGraphDTO;
import org.unipi.bioconnect.service.DatabaseOperationExecutor;
import org.unipi.bioconnect.repository.Graph.DiseaseGraphRepository;
import org.unipi.bioconnect.utils.GraphUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiseaseGraphService {

    @Autowired
    private DiseaseGraphRepository diseaseGraphRepository;

    @Autowired
    private GraphServiceCRUD graphServiceCRUD;

    @Autowired
    private DatabaseOperationExecutor executor;

    public DiseaseGraphDTO getDiseaseById(String diseaseID) {
        return (DiseaseGraphDTO) graphServiceCRUD.getEntityById(diseaseID, diseaseGraphRepository);
    }

    public void deleteDiseaseById(String diseaseID) {
        graphServiceCRUD.deleteEntityById(diseaseID, diseaseGraphRepository);
    }

    public void saveDiseaseGraph(DiseaseGraphDTO diseaseGraphDTO) {
        graphServiceCRUD.saveEntityGraph(diseaseGraphDTO, diseaseGraphRepository);
    }

    public void updateDiseaseById(DiseaseGraphDTO diseaseGraphDTO) {
        graphServiceCRUD.updateEntity(diseaseGraphDTO, diseaseGraphRepository);
    }

    public List<BaseNodeDTO> getDiseaseByDrug(String drugId) {
        return executor.executeWithExceptionHandling(() -> diseaseGraphRepository.getDiseaseByDrug(drugId), "Neo4j (disease by drug)");
    }

    public List<List<BaseNodeDTO>> getShortestPathBetweenDiseases(String disease1Id, String disease2Id) {
        return executor.executeWithExceptionHandling(() -> GraphUtils.separateShortestPath(diseaseGraphRepository.findShortestPathBetweenDiseases(disease1Id, disease2Id)), "Neo4j (shortest path diseases)");
    }



}
