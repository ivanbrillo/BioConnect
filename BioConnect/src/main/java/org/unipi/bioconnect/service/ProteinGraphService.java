package org.unipi.bioconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unipi.bioconnect.DTO.ProteinDTO;
import org.unipi.bioconnect.model.ProteinGraph;
import org.unipi.bioconnect.repository.ProteinGraphRepository;

import javax.naming.NameAlreadyBoundException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProteinGraphService {

    @Autowired
    private ProteinGraphRepository graphRepository;

    @Autowired
    private Neo4jClient neo4jClient;


    @Transactional
    public void saveProteinGraph(ProteinDTO proteinDTO) {

        if(graphRepository.existsById(proteinDTO.getUniProtID()))
            throw new RuntimeException("protein already exists");

        ProteinGraph proteinGraph = new ProteinGraph(proteinDTO.getUniProtID(), proteinDTO.getName());

        for (ProteinDTO interaction : proteinDTO.getProteinInteractions()) {

            ProteinGraph existingProtein = graphRepository.findByUniProtID(interaction.getUniProtID());

            if (existingProtein != null)
                proteinGraph.addInteraction(existingProtein);

        }

        graphRepository.save(proteinGraph);
    }

    //find all da errori di java heap, evitare senza limitazioni
    public List<ProteinGraph> getAllProteins() {
        return graphRepository.findAll();
//        return graphRepository.findAllProjectedBy();
    }

    // Metodo per ottenere le prime tre proteine
    public List<ProteinGraph> getTopThreeProteins() {
        return graphRepository.findTopThreeProteins();
    }

    public String checkNeo4jConnection() {
        try {
            neo4jClient.query("RETURN 1").run();
            return "Connected to Neo4j";
        } catch (Exception e) {
            return "Failed to connect to Neo4j: " + e.getMessage();
        }
    }


}




