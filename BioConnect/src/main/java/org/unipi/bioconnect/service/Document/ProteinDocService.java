package org.unipi.bioconnect.service.Document;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unipi.bioconnect.DTO.Document.PathwayRecurrenceDTO;
import org.unipi.bioconnect.DTO.Document.ProteinDocDTO;
import org.unipi.bioconnect.DTO.Document.TrendAnalysisDTO;
import org.unipi.bioconnect.service.AdminService;
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
    @Autowired
    private AdminService AdminService;

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

//    @Transactional(value = "mongoTransactionManager")
    public void deleteProtein(String uniProtID) {
        long numDeleted = executor.executeWithExceptionHandling(() -> docRepository.deleteByUniProtID(uniProtID), "MongoDB (delete)");

        if (numDeleted == 0)
            throw new KeyException("No Protein with ID: " + uniProtID + " found");

        AdminService.deleteCommentsByElementID(uniProtID, true);
    }

    public List<ProteinDocDTO> searchProteinDoc(String searchedText) {
        return executor.executeWithExceptionHandling(() -> docRepository.findByIdOrNameContainingIgnoreCase(searchedText), "MongoDB (search)");
    }

    public List<TrendAnalysisDTO> getTrendAnalysisForPathway(String pathway) {
        List<TrendAnalysisDTO> trend = executor.executeWithExceptionHandling(() -> docDAO.getTrendAnalysisForPathway(pathway), "MongoDB (trend analysis)");

        if (trend.isEmpty())
            throw new IllegalArgumentException("No protein saved with the specified pathway: " + pathway);

        return trend;

    }

    public List<PathwayRecurrenceDTO> getPathwayRecurrence(String subsequence) {
        if (!subsequence.matches("^[A-Z]+$"))  // amino acids subsequence must be uppercase letters
            throw new IllegalArgumentException("Subsequence must contain only uppercase letters, got: " + subsequence);

        return executor.executeWithExceptionHandling(() -> docDAO.getPathwayRecurrence(subsequence), "MongoDB (pathway recurrence)");
    }

    public List<ProteinDocDTO> getProteinsByPathwayAndLocation(String pathway, String subcellularLocation) {
        return executor.executeWithExceptionHandling(() -> docRepository.findByPathwayAndSubcellularLocation(pathway, subcellularLocation), "MongoDB (find by pathway and location)");
    }

}
