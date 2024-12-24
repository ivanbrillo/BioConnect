package org.unipi.bioconnect.service.Document;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unipi.bioconnect.DTO.Document.PathwayRecurrenceDTO;
import org.unipi.bioconnect.DTO.Document.ProteinDocDTO;
import org.unipi.bioconnect.DTO.Document.TrendAnalysisDTO;
import org.unipi.bioconnect.service.DatabaseOperationExecutor;
import org.unipi.bioconnect.exception.KeyException;
import org.unipi.bioconnect.model.Document.ProteinDoc;
import org.unipi.bioconnect.repository.Document.ProteinDocDAO;
import org.unipi.bioconnect.repository.Document.ProteinDocRepository;

import java.util.List;

@Slf4j
@Service
public class ProteinDocService {

    @Autowired
    private ProteinDocRepository docRepository;

    @Autowired
    private ProteinDocDAO docDAO;

    @Autowired
    private DatabaseOperationExecutor executor;

    public void saveProteinDoc(ProteinDocDTO proteinDocDTO) {
        executor.executeWithExceptionHandling(() -> docRepository.insert(new ProteinDoc(proteinDocDTO)), "MongoDB (save)");
    }

    public void updateProteinDoc(ProteinDocDTO proteinDocDTO) {
        executor.executeWithExceptionHandling(() -> {
            if (!docRepository.existsById(proteinDocDTO.getId()))
                throw new KeyException("Protein with ID: " + proteinDocDTO.getId() + " does not exist");
            return docRepository.save(new ProteinDoc(proteinDocDTO));
        }, "MongoDB (update)");
    }

    public void deleteProtein(String uniProtID) {
        executor.executeWithExceptionHandling(() -> {
            if (docRepository.deleteByUniProtID(uniProtID) == 0)
                throw new KeyException("No Protein with ID: " + uniProtID + " found");
            return 1;
        }, "MongoDB (delete)");
    }

    public List<ProteinDocDTO> searchProteinDoc(String searchedText) {
        return executor.executeWithExceptionHandling(() -> docRepository.findByIdOrNameContainingIgnoreCase(searchedText), "MongoDB (search)");
    }

    public List<TrendAnalysisDTO> getTrendAnalysisForPathway(String pathway) {
        return executor.executeWithExceptionHandling(() -> docDAO.getTrendAnalysisForPathway(pathway), "MongoDB (trend analysis)");
    }

    public List<PathwayRecurrenceDTO> getPathwayRecurrence(String subsequence) {
        return executor.executeWithExceptionHandling(() -> docDAO.getPathwayRecurrence(subsequence), "MongoDB (pathway recurrence)");
    }

    public List<ProteinDocDTO> getProteinsByPathwayAndLocation(String pathway, String subsequence) {
        return executor.executeWithExceptionHandling(() -> docDAO.getProteinsByPathwayAndLocation(pathway, subsequence), "MongoDB (protein recurrence)");
    }

}
