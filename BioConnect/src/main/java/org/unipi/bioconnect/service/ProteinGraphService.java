package org.unipi.bioconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unipi.bioconnect.DTO.BaseNodeDTO;
import org.unipi.bioconnect.DTO.ProteinDocDTO;
import org.unipi.bioconnect.DTO.ProteinGraphDTO;
import org.unipi.bioconnect.model.ProteinGraph;
import org.unipi.bioconnect.repository.ProteinGraphRepository;

import javax.naming.NameAlreadyBoundException;
import java.util.List;


@Service
public class ProteinGraphService {

    @Autowired
    private ProteinGraphRepository graphRepository;

    @Autowired
    private Neo4jClient neo4jClient;

    //TODO serve transactional? non credo
    public void saveProteinGraph(ProteinGraphDTO proteinGraphDTO) {

        if(graphRepository.existsById(proteinGraphDTO.getId()))
            throw new RuntimeException("protein already exists");
        else {
            ProteinGraph proteinGraph = new ProteinGraph(proteinGraphDTO.getId(), proteinGraphDTO.getName());

            for (BaseNodeDTO interaction : proteinGraphDTO.getProteinInteractions()) {

//                ProteinGraph existingProtein = graphRepository.findProteinGraphById(interaction.getId());
//                if (existingProtein != null){
                    proteinGraph.addInteraction(new ProteinGraph(interaction.getId(), interaction.getName()));
//                } else {
//                    throw new IllegalArgumentException("Protein with ID " + interaction.getId() + " does not exist");
//                }
            }
            System.out.println("Saving protein: " + proteinGraph);
            graphRepository.save(proteinGraph);
        }

    }

    // Ottieni proteina e relazioni tramite uniprotID
    public ProteinGraphDTO getProteinById(String uniProtID) {
        ProteinGraph proteinGraph = graphRepository.findByUniProtID(uniProtID);

        if (proteinGraph == null)
            throw new IllegalArgumentException("Protein with ID " + uniProtID + " does not exist");

        return new ProteinGraphDTO(proteinGraph);
    }

    // cancellare proteina tramite id
    public void deleteProteinById(String uniProtID) {
        graphRepository.deleteProteinGraphById(uniProtID);
    }

    // aggiornare proteina esistente
//    public void updateProteinById(ProteinDocDTO proteinDocDTO) {
//        ProteinGraph proteinGraph = graphRepository.findProteinGraphById(proteinDocDTO.getId());
//        if (proteinGraph == null) {
//            throw new IllegalArgumentException("Protein with ID " + proteinDocDTO.getId() + " does not exist");
//        }
//        proteinGraph.setName(proteinDocDTO.getName());
//
//        // Cancella le relazioni esistenti nel database
//        graphRepository.deleteInteractionsById(proteinDocDTO.getId());
//        proteinGraph.clearInteractions();
//        // Aggiungi le nuove relazioni
//        for (ProteinDocDTO interaction : proteinDocDTO.getProteinInteractions()) {
//            ProteinGraph existingProtein = graphRepository.findProteinGraphById(interaction.getId());
//            if (existingProtein != null) {
//                proteinGraph.addInteraction(existingProtein);
//            } else {
//                throw new IllegalArgumentException("Protein with ID " + interaction.getId() + " does not exist");
//            }
//        }
//
//        graphRepository.save(proteinGraph);
//    }

    // ! find all da errori di java heap, evitare senza limitazioni
    public List<ProteinGraph> getAllProteins() {
        return graphRepository.findAll();
        //return graphRepository.findAllProjectedBy();
    }

    // * TEST - Metodo per ottenere le prime tre proteine
    public List<ProteinGraph> getTopThreeProteins() {
        return graphRepository.findTopThreeProteins();
    }

    // * TEST - Metodo per controllare la connessione a Neo4j
    public String checkNeo4jConnection() {
        try {
            neo4jClient.query("RETURN 1").run();
            return "Connected to Neo4j";
        } catch (Exception e) {
            return "Failed to connect to Neo4j: " + e.getMessage();
        }
    }


}




