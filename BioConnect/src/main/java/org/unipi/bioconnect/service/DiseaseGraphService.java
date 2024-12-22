package org.unipi.bioconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unipi.bioconnect.DTO.Graph.DiseaseGraphDTO;
import org.unipi.bioconnect.model.DiseaseGraph;
import org.unipi.bioconnect.model.DiseaseGraph;
import org.unipi.bioconnect.repository.DiseaseGraphRepository;
import org.unipi.bioconnect.repository.DiseaseGraphRepository;
import org.unipi.bioconnect.repository.GraphRepository;
import org.unipi.bioconnect.utils.GraphUtils;

@Service
public class DiseaseGraphService {

    @Autowired
    private DiseaseGraphRepository diseaseGraphRepository;

    @Autowired
    private GraphRepository graphRepository;


    public DiseaseGraphDTO getDiseaseById(String diseaseID) {
        DiseaseGraph diseaseGraph = diseaseGraphRepository.findByDrugId(diseaseID);

        if (diseaseGraph == null)
            throw new IllegalArgumentException("Disease with ID " + diseaseID + " does not exist");

        return new DiseaseGraphDTO(diseaseGraph);
    }

    public void deleteDiseaseById(String diseaseID) {

        if (!diseaseGraphRepository.existsById(diseaseID))
            throw new RuntimeException("Disease with ID " + diseaseID + " does not exist");

        diseaseGraphRepository.deleteById(diseaseID);
    }


    public void updateDiseaseGraphDTO(DiseaseGraphDTO diseaseGraphDTO) {
        diseaseGraphDTO.setInvolve(GraphUtils.getRelationshipsUpdated(diseaseGraphDTO.getInvolve(), graphRepository));
    }

    public void saveDiseaseHelper(DiseaseGraphDTO diseaseGraphDTO) {
        updateDiseaseGraphDTO(diseaseGraphDTO);
        DiseaseGraph diseaseGraph = new DiseaseGraph(diseaseGraphDTO);
        diseaseGraphRepository.save(diseaseGraph);
    }


    public void saveDiseaseGraph(DiseaseGraphDTO diseaseGraphDTO) {

        if (diseaseGraphRepository.existsById(diseaseGraphDTO.getId()))
            throw new RuntimeException("Disease with ID " + diseaseGraphDTO.getId() + " already exist");

        saveDiseaseHelper(diseaseGraphDTO);

    }

    @Transactional
    public void updateDiseaseById(DiseaseGraphDTO diseaseGraphDTO) {

        if (!diseaseGraphRepository.existsById(diseaseGraphDTO.getId()))
            throw new RuntimeException("Disease with ID " + diseaseGraphDTO.getId() + " does not exist");

        diseaseGraphRepository.removeAllRelationships(diseaseGraphDTO.getId());
        saveDiseaseHelper(diseaseGraphDTO);

    }

}
