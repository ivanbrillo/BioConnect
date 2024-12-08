package org.unipi.bioconnect.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unipi.bioconnect.DTO.ProteinDTO;
import org.unipi.bioconnect.model.ProteinDoc;
import org.unipi.bioconnect.repository.ProteinDocRepository;

import java.util.List;

@Service
public class ProteinDocService {

    @Autowired
    private ProteinDocRepository docRepository;

    public void saveProteinDoc(ProteinDTO proteinDTO) {

        ProteinDoc proteinDoc = new ProteinDoc();
        proteinDoc.setUniProtID(proteinDTO.getUniProtID());
        proteinDoc.setName(proteinDTO.getName());

        docRepository.save(proteinDoc);
    }

    public List<ProteinDTO> getAllProteins() {
        return docRepository.findAllProjectedBy();
    }

}
