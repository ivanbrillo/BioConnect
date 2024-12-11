package org.unipi.bioconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unipi.bioconnect.DTO.DrugDTO;
import org.unipi.bioconnect.repository.DrugDocRepository;

import java.util.List;

@Service
public class DrugDocService {

    @Autowired
    private DrugDocRepository docRepository;

//    @Autowired
//    private ProteinDocDAO docDAO;


    public List<DrugDTO> searchDrugDoc(String searchedText) {
        return docRepository.findByIdOrNameContainingIgnoreCase(searchedText);
    }

}
