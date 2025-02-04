package org.unipi.bioconnect.service.Graph;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unipi.bioconnect.DTO.Graph.BaseNodeDTO;
import org.unipi.bioconnect.DTO.Graph.DrugGraphDTO;
import org.unipi.bioconnect.DTO.Graph.OppositeEffectDrugsDTO;
import org.unipi.bioconnect.exception.KeyException;
import org.unipi.bioconnect.repository.Graph.ProteinGraphRepository;
import org.unipi.bioconnect.service.DatabaseOperationExecutor;
import org.unipi.bioconnect.repository.Graph.DrugGraphRepository;

import java.util.List;


@Service
public class DrugGraphService {

    @Autowired
    private DrugGraphRepository drugGraphRepository;
    @Autowired
    private GraphServiceCRUD graphServiceCRUD;
    @Autowired
    private DatabaseOperationExecutor executor;
    @Autowired
    private ProteinGraphRepository proteinGraphRepository;


    public DrugGraphDTO getDrugById(String drugID) {
        return (DrugGraphDTO) graphServiceCRUD.getEntityById(drugID, drugGraphRepository);
    }

    public void deleteDrugById(String drugID) {
        graphServiceCRUD.deleteEntityById(drugID, drugGraphRepository);
    }

    public void saveDrugGraph(DrugGraphDTO drugGraphDTO) {
        graphServiceCRUD.saveEntityGraph(drugGraphDTO, drugGraphRepository);
    }

    public void updateDrugById(DrugGraphDTO drugGraphDTO) {
        graphServiceCRUD.updateEntity(drugGraphDTO, drugGraphRepository);
    }

    public List<BaseNodeDTO> getDrugTargetSimilarProtein(String uniProtId) {
        return executor.executeWithExceptionHandling(() -> {
            if (!proteinGraphRepository.existEntityById(uniProtId))
                throw new KeyException("Protein with ID: " + uniProtId + " does not exist");

            return drugGraphRepository.getDrugTargetSimilarProtein(uniProtId);
        }, "Neo4j (drug similar protein)");
    }

    public List<OppositeEffectDrugsDTO> getDrugOppositeEffectsProtein(String drugId) {
        return executor.executeWithExceptionHandling(() -> {
            if (!drugGraphRepository.existEntityById(drugId))
                throw new KeyException("Drug with ID: " + drugId + " does not exist");

            return drugGraphRepository.getDrugOppositeEffectsProtein(drugId);
        }, "Neo4j (drug opposite effects)");
    }

}
