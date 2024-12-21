package org.unipi.bioconnect.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Component;
import org.unipi.bioconnect.DTO.Graph.BaseNodeDTO;
import org.unipi.bioconnect.repository.DrugDocRepository;
import org.unipi.bioconnect.repository.GraphRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphUtils {

    public static Set<BaseNodeDTO> getRelationshipsUpdated(Set<BaseNodeDTO> relationships, GraphRepository graphRepository) {

        List<String> interactionIds = relationships.stream()
                .map(BaseNodeDTO::getId).toList();

        Set<BaseNodeDTO> updated = new HashSet<>(graphRepository.findEntityNamesByIds(interactionIds));

        if (relationships.size() != updated.size())
            throw new IllegalArgumentException("Some relationships refers to not existing ids");

        return updated;

    }

}
