package org.unipi.bioconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unipi.bioconnect.DTO.Graph.BaseNodeDTO;
import org.unipi.bioconnect.model.GraphModel;
import org.unipi.bioconnect.repository.GraphEntityRepository;
import org.unipi.bioconnect.repository.GraphHelperRepository;
import org.unipi.bioconnect.repository.GraphRepository;
import org.unipi.bioconnect.utils.GraphUtils;

import java.util.Set;

@Service
public class GraphServiceCRUD {


    @Autowired
    private GraphHelperRepository graphHelperRepository;


    private <T extends GraphModel> void saveEntityHelper(BaseNodeDTO<T> entityGraphDTO, GraphEntityRepository<T> entityRepository) {
        Set<BaseNodeDTO<T>> relationships = entityGraphDTO.getNodeRelationships();
        GraphUtils.updateRelationships(relationships, graphHelperRepository);
        GraphModel entityModel = entityGraphDTO.getGraphModel();

        entityRepository.save(entityModel);
    }


    public void saveEntityGraph(BaseNodeDTO entityGraphDTO, GraphEntityRepository entityRepository) {

        System.out.println(entityGraphDTO.getId());

        if (entityRepository.existsById(entityGraphDTO.getId()))
            throw new RuntimeException(entityGraphDTO.getNodeType() + " with ID " + entityGraphDTO.getId() + " already exist");

        saveEntityHelper(entityGraphDTO, entityRepository);

    }


    public BaseNodeDTO getEntityById(String id, GraphHelperRepository entityRepository) {
        GraphModel entityGraph = entityRepository.findByEntityId(id);

        if (entityGraph == null)
            throw new IllegalArgumentException("Entity with ID " + id + " does not exist");

        return entityGraph.getDTO();
    }

//    public void deleteProteinById(String uniProtID) {
//
//        if (!proteinGraphRepository.existsById(uniProtID))
//            throw new RuntimeException("Protein with ID " + uniProtID + " does not exist");
//
//        proteinGraphRepository.deleteById(uniProtID);
//    }
//
//
//    @Transactional
//    public void updateProteinById(ProteinGraphDTO proteinGraphDTO) {
//
//        if (!proteinGraphRepository.existsById(proteinGraphDTO.getId()))
//            throw new RuntimeException("Protein with ID " + proteinGraphDTO.getId() + " does not exist");
//
//        proteinGraphRepository.removeAllRelationships(proteinGraphDTO.getId());
//        saveProteinHelper(proteinGraphDTO);
//
//    }

}
