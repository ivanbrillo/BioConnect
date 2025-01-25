package org.unipi.bioconnect.service.Graph;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unipi.bioconnect.DTO.Graph.BaseNodeDTO;
import org.unipi.bioconnect.service.DatabaseOperationExecutor;
import org.unipi.bioconnect.exception.KeyException;
import org.unipi.bioconnect.model.Graph.GraphModel;
import org.unipi.bioconnect.repository.Graph.GraphEntityRepository;
import org.unipi.bioconnect.repository.Graph.GraphHelperRepository;
import org.unipi.bioconnect.utils.GraphUtils;

import java.util.List;
import java.util.Set;

@Service
public class GraphServiceCRUD {

    @Autowired
    private GraphHelperRepository graphHelperRepository;

    @Autowired
    private DatabaseOperationExecutor executor;

    private GraphModel saveEntityHelper(BaseNodeDTO entityGraphDTO, GraphEntityRepository entityRepository) {
        List<Set<BaseNodeDTO>> relationships = entityGraphDTO.getNodeRelationships();
        GraphUtils.updateRelationships(relationships, graphHelperRepository);
        GraphModel entityModel = entityGraphDTO.getGraphModel();
        return entityRepository.saveEntity(entityModel);
    }

    public void saveEntityGraph(BaseNodeDTO entityGraphDTO, GraphEntityRepository entityRepository) {
        executor.executeWithExceptionHandling(() -> {
            if (entityRepository.existEntityById(entityGraphDTO.getId()))
                throw new KeyException(entityGraphDTO.getNodeType() + " with ID " + entityGraphDTO.getId() + " already exist");

            return saveEntityHelper(entityGraphDTO, entityRepository);
        }, "Neo4j (save)");
    }

    public BaseNodeDTO getEntityById(String id, GraphEntityRepository entityRepository) {
        GraphModel entityGraph = executor.executeWithExceptionHandling(() -> entityRepository.getEntityById(id), "Neo4j (get)");

        if (entityGraph == null) throw new KeyException("Entity with ID " + id + " does not exist");
        return entityGraph.getDTO();
    }

    public void deleteEntityById(String entityId, GraphEntityRepository entityRepository) {
        executor.executeWithExceptionHandling(() -> {
            if (!entityRepository.existEntityById(entityId))
                throw new KeyException("Entity with ID " + entityId + " does not exist");

            entityRepository.deleteEntityById(entityId);
            return 1;
        }, "Neo4j (delete)");
    }

    @Transactional
    public void updateEntity(BaseNodeDTO entityGraphDTO, GraphEntityRepository entityRepository) {
        executor.executeWithExceptionHandling(() -> {
            if (!entityRepository.existEntityById(entityGraphDTO.getId()))
                throw new KeyException(entityGraphDTO.getNodeType() + " with ID " + entityGraphDTO.getId() + " does not exist");

            entityRepository.deleteEntityAllRelationships(entityGraphDTO.getId());
            return saveEntityHelper(entityGraphDTO, entityRepository);
        }, "Neo4j (update)");
    }

}
