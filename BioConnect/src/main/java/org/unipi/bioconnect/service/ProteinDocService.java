package org.unipi.bioconnect.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.unipi.bioconnect.DTO.PathwayRecurrenceDTO;
import org.unipi.bioconnect.DTO.ProteinDTO;
import org.unipi.bioconnect.DTO.TrendAnalysisDTO;
import org.unipi.bioconnect.model.ProteinDoc;
import org.unipi.bioconnect.repository.ProteinDocDAO;
import org.unipi.bioconnect.repository.ProteinDocRepository;

import java.util.List;

@Service
public class ProteinDocService {

    @Autowired
    private ProteinDocRepository docRepository;

    @Autowired
    private ProteinDocDAO docDAO;

    public void saveProteinDoc(ProteinDTO proteinDTO) {
        ProteinDoc proteinDoc = new ProteinDoc(proteinDTO);
        docRepository.save(proteinDoc);
    }

    public List<ProteinDTO> searchProteinDoc(String searchedText) {
        return docRepository.findByIdOrNameContainingIgnoreCase(searchedText);
    }

    public List<TrendAnalysisDTO> getTrendAnalysisForPathway(String pathway) {
        return docDAO.getTrendAnalysisForPathway(pathway);
    }

    public List<PathwayRecurrenceDTO> getPathwayRecurrence(String subsequence) {
        return docDAO.getPathwayRecurrence(subsequence);
    }

    public List<ProteinDTO> getAllProteins() {
        return docRepository.findAllProjectedBy();
    }

}
