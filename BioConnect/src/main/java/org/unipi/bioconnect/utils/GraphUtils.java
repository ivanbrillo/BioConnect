package org.unipi.bioconnect.utils;

import org.unipi.bioconnect.DTO.Graph.BaseNodeDTO;
import org.unipi.bioconnect.repository.Graph.GraphHelperRepository;

import java.util.ArrayList;
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

    // Method to separate shortest path in a flat list e.g. [D1 P1 P2 D2 D1 P3 P4 D3] -> [[D1 P1 P2 D2], [D1 P3 P4 D3]]
    public static List<List<BaseNodeDTO>> separateShortestPath(List<BaseNodeDTO> nodes) {
        if (nodes == null || nodes.isEmpty())
            return new ArrayList<>();

        List<List<BaseNodeDTO>> result = new ArrayList<>();
        List<BaseNodeDTO> currentList = new ArrayList<>();

        // Get the last element of the main list - this is our separator
        BaseNodeDTO lastElement = nodes.get(nodes.size() - 1);   // last disease element, common for all shortest path

        for (BaseNodeDTO node : nodes) {
            currentList.add(node);

            if (node.getName().equals(lastElement.getName())) {   // new shortest path
                result.add(new ArrayList<>(currentList));
                currentList = new ArrayList<>();
            }
        }

        return result;
    }

}
