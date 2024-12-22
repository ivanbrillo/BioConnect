package org.unipi.bioconnect.utils;

import org.unipi.bioconnect.DTO.Graph.BaseNodeDTO;
import org.unipi.bioconnect.repository.Graph.GraphHelperRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GraphUtils {

    public static void updateRelationships(Set<BaseNodeDTO> relationships, GraphHelperRepository graphHelperRepository) {

        List<String> interactionIds = relationships.stream()
                .map(BaseNodeDTO::getId)
                .toList();

        Map<String, String> idToNameMap = graphHelperRepository.findEntityNamesByIds(interactionIds)
                .stream()
                .collect(Collectors.toMap(
                        BaseNodeDTO::getId,
                        BaseNodeDTO::getName,
                        (existingValue, newValue) -> existingValue   // keep existing value if duplicates
                ));

        if (interactionIds.size() != idToNameMap.size()) {
            throw new IllegalArgumentException("Some relationships refer to non-existing ids");
        }

        relationships.forEach(relationship ->
                relationship.setName(idToNameMap.get(relationship.getId()))
        );
    }

}
