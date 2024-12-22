package org.unipi.bioconnect.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unipi.bioconnect.DTO.Doc.PathwayRecurrenceDTO;
import org.unipi.bioconnect.DTO.Doc.ProteinDocDTO;
import org.unipi.bioconnect.DTO.Doc.TrendAnalysisDTO;
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

    public void saveProteinDoc(ProteinDocDTO proteinDocDTO) {
        if (docRepository.existsById(proteinDocDTO.getId()))
            throw new IllegalArgumentException("Protein with ID: " + proteinDocDTO.getId() + " already exists");

        docRepository.save(new ProteinDoc(proteinDocDTO));
    }

    public void updateProteinDoc(ProteinDocDTO proteinDocDTO) {
        if (!docRepository.existsById(proteinDocDTO.getId()))
            throw new IllegalArgumentException("Protein with ID: " + proteinDocDTO.getId() + " does not exist");

        docRepository.save(new ProteinDoc(proteinDocDTO));
    }

    public void deleteProtein(String uniProtID) {
        if (docRepository.deleteByUniProtID(uniProtID) == 0)
            throw new IllegalArgumentException("No Protein with ID: " + uniProtID + " found");
    }

    public List<ProteinDocDTO> searchProteinDoc(String searchedText) {
        return docRepository.findByIdOrNameContainingIgnoreCase(searchedText);
    }

    public List<TrendAnalysisDTO> getTrendAnalysisForPathway(String pathway) {
        return docDAO.getTrendAnalysisForPathway(pathway);
    }

    public List<PathwayRecurrenceDTO> getPathwayRecurrence(String subsequence) {
        return docDAO.getPathwayRecurrence(subsequence);
    }

    public List<ProteinDocDTO> getProteinsByPathwayAndLocation(String pathway, String subsequence) {
        return docDAO.getProteinsByPathwayAndLocation(pathway, subsequence);
    }

}
