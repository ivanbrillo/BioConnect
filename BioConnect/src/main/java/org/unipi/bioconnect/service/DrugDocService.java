package org.unipi.bioconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unipi.bioconnect.DTO.Doc.DrugDocDTO;
import org.unipi.bioconnect.DTO.Doc.PatentStateAnalysisDTO;
import org.unipi.bioconnect.DTO.Doc.TrendAnalysisDTO;
import org.unipi.bioconnect.model.DrugDoc;
import org.unipi.bioconnect.repository.DrugDocDAO;
import org.unipi.bioconnect.repository.DrugDocRepository;

import java.util.List;

@Service
public class DrugDocService {

    @Autowired
    private DrugDocRepository docRepository;

    @Autowired
    private DrugDocDAO docDAO;

    public void saveDrugDoc(DrugDocDTO drugDocDTO) {
        if (docRepository.existsById(drugDocDTO.getId()))
            throw new IllegalArgumentException("Drug with ID: " + drugDocDTO.getId() + " already exists");

        docRepository.save(new DrugDoc(drugDocDTO));
    }

    public void updateDrugById(DrugDocDTO drugDocDTO) {
        if (!docRepository.existsById(drugDocDTO.getId()))
            throw new IllegalArgumentException("Drug with ID: " + drugDocDTO.getId() + " does not exist");

        docRepository.save(new DrugDoc(drugDocDTO));
    }

    public void deleteDrugById(String drugBankID) {
        if (docRepository.deleteByDrugBankID(drugBankID) == 0)
            throw new IllegalArgumentException("No Drug with ID: " + drugBankID + " found");
    }

    public List<DrugDocDTO> searchDrugDoc(String searchedText) {
        return docRepository.findByIdOrNameContainingIgnoreCase(searchedText);
    }

    public List<TrendAnalysisDTO> getTrendAnalysisForCategory(String category) {
        return docDAO.getTrendAnalysisForCategory(category);
    }

    public List<PatentStateAnalysisDTO> getExpiredPatentsByStateForCategory(String category) {
        return docDAO.getExpiredPatentsByStateForCategory(category);
    }

}
