package org.unipi.bioconnect.service.Document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unipi.bioconnect.DTO.Document.DrugDocDTO;
import org.unipi.bioconnect.DTO.Document.PatentStateAnalysisDTO;
import org.unipi.bioconnect.DTO.Document.TrendAnalysisDTO;
import org.unipi.bioconnect.service.AdminService;
import org.unipi.bioconnect.service.DatabaseOperationExecutor;
import org.unipi.bioconnect.exception.KeyException;
import org.unipi.bioconnect.model.Document.DrugDoc;
import org.unipi.bioconnect.repository.Document.DrugDocDAO;
import org.unipi.bioconnect.repository.Document.DrugDocRepository;

import java.util.List;

@Service
public class DrugDocService {

    @Autowired
    private DrugDocRepository docRepository;
    @Autowired
    private DrugDocDAO docDAO;
    @Autowired
    private DatabaseOperationExecutor executor;
    @Autowired
    private org.unipi.bioconnect.service.AdminService AdminService;

    public void saveDrugDoc(DrugDocDTO drugDocDTO) {
        executor.executeWithExceptionHandling(() -> docRepository.insert(new DrugDoc(drugDocDTO)), "MongoDB (save)");
    }

    public void updateDrugById(DrugDocDTO drugDocDTO) {
        executor.executeWithExceptionHandling(() -> {
            if (!docRepository.existsById(drugDocDTO.getId()))
                throw new KeyException("Drug with ID: " + drugDocDTO.getId() + " does not exist");
            return docRepository.save(new DrugDoc(drugDocDTO));
        }, "MongoDB (update)");
    }

    @Transactional(value = "mongoTransactionManager")
    public void deleteDrugById(String drugBankID) {
        long numDeleted = executor.executeWithExceptionHandling(() -> docRepository.deleteByDrugBankID(drugBankID), "MongoDB (delete)");

        if (numDeleted == 0)
            throw new KeyException("No Drug with ID: " + drugBankID + " found");

        AdminService.deleteCommentsByElementID(drugBankID, false);
    }

    public List<DrugDocDTO> searchDrugDoc(String searchedText) {
        return executor.executeWithExceptionHandling(() -> docRepository.findByIdOrNameContainingIgnoreCase(searchedText), "MongoDB (search)");
    }

    public List<TrendAnalysisDTO> getTrendAnalysisForCategory(String category) {
        List<TrendAnalysisDTO> trend = executor.executeWithExceptionHandling(() -> docDAO.getTrendAnalysisForCategory(category), "MongoDB (trend analysis)");

        if (trend.isEmpty())
            throw new IllegalArgumentException("No Drug saved with the specified category: " + category);

        return trend;

    }

    public List<PatentStateAnalysisDTO> getExpiredPatentsByStateForCategory(String category) {
        return executor.executeWithExceptionHandling(() -> docDAO.getExpiredPatentsByStateForCategory(category), "MongoDB (expired patents)");
    }

}
