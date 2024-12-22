package org.unipi.bioconnect.service.Graph;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unipi.bioconnect.DTO.Graph.ProteinGraphDTO;
import org.unipi.bioconnect.repository.Graph.ProteinGraphRepository;


@Service
public class ProteinGraphService {

    @Autowired
    private ProteinGraphRepository proteinGraphRepository;

    @Autowired
    private GraphServiceCRUD graphServiceCRUD;


    public ProteinGraphDTO getProteinById(String proteinID) {
        return (ProteinGraphDTO) graphServiceCRUD.getEntityById(proteinID, proteinGraphRepository);
    }

    public void deleteProteinById(String proteinID) {
        graphServiceCRUD.deleteEntityById(proteinID, proteinGraphRepository);
    }

    public void saveProteinGraph(ProteinGraphDTO proteinGraphDTO) {
        graphServiceCRUD.saveEntityGraph(proteinGraphDTO, proteinGraphRepository);
    }

    @Transactional
    public void updateProteinById(ProteinGraphDTO proteinGraphDTO) {
        graphServiceCRUD.updateEntity(proteinGraphDTO, proteinGraphRepository);
    }

}




