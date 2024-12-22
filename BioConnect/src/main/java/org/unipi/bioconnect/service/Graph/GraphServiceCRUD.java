package org.unipi.bioconnect.service.Graph;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unipi.bioconnect.DTO.Graph.BaseNodeDTO;
import org.unipi.bioconnect.model.Graph.GraphModel;
import org.unipi.bioconnect.repository.Graph.GraphEntityRepository;
import org.unipi.bioconnect.repository.Graph.GraphHelperRepository;
import org.unipi.bioconnect.utils.GraphUtils;

import java.util.Set;

@Service
public class GraphServiceCRUD {

    @Autowired
    private GraphHelperRepository graphHelperRepository;


    private void saveEntityHelper(BaseNodeDTO entityGraphDTO, GraphEntityRepository entityRepository) {
        Set<BaseNodeDTO> relationships = entityGraphDTO.getNodeRelationships();
        GraphUtils.updateRelationships(relationships, graphHelperRepository);
        GraphModel entityModel = entityGraphDTO.getGraphModel();

        entityRepository.saveEntity(entityModel);
    }


    public void saveEntityGraph(BaseNodeDTO entityGraphDTO, GraphEntityRepository entityRepository) {

        if (entityRepository.existEntityById(entityGraphDTO.getId()))
            throw new RuntimeException(entityGraphDTO.getNodeType() + " with ID " + entityGraphDTO.getId() + " already exist");

        saveEntityHelper(entityGraphDTO, entityRepository);

    }


    public BaseNodeDTO getEntityById(String id, GraphEntityRepository entityRepository) {
        GraphModel entityGraph = entityRepository.getEntityById(id);

        if (entityGraph == null)
            throw new IllegalArgumentException("Entity with ID " + id + " does not exist");

        return entityGraph.getDTO();
    }

    public void deleteEntityById(String entityId, GraphEntityRepository entityRepository) {

        if (!entityRepository.existEntityById(entityId))
            throw new RuntimeException("Entity with ID " + entityId + " does not exist");

        entityRepository.deleteEntityById(entityId);
    }

    @Transactional
    public void updateEntity(BaseNodeDTO entityGraphDTO, GraphEntityRepository entityRepository) {

        if (!entityRepository.existEntityById(entityGraphDTO.getId()))
            throw new RuntimeException(entityGraphDTO.getNodeType() + " with ID " + entityGraphDTO.getId() + " does not exist");

        entityRepository.deleteEntityAllRelationships(entityGraphDTO.getId());
        saveEntityHelper(entityGraphDTO, entityRepository);

    }

}
