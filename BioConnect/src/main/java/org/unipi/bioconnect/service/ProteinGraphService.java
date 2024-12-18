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


@Service
public class ProteinGraphService {

    @Autowired
    private ProteinGraphRepository graphRepository;

    @Autowired
    private Neo4jClient neo4jClient;

    // Salva proteina e relazioni nel GraphDB
    @Transactional
    public void saveProteinGraph(ProteinDTO proteinDTO) throws NameAlreadyBoundException {

        // Controllo se esiste già la proteina altrimenti la creo
        if(graphRepository.existsById(proteinDTO.getUniProtID()))
            throw new NameAlreadyBoundException("protein already exists");
        else {
            ProteinGraph proteinGraph = new ProteinGraph(proteinDTO.getUniProtID(), proteinDTO.getName());
            // Aggiungo le interazioni alla proteina se già esistono
            for (ProteinDTO interaction : proteinDTO.getProteinInteractions()) {
                ProteinGraph existingProtein = graphRepository.findByUniProtID(interaction.getUniProtID());
                if (existingProtein != null){
                    proteinGraph.addInteraction(existingProtein);
                } else {
                    throw new IllegalArgumentException("Protein with ID " + interaction.getUniProtID() + " does not exist");
                }
            }
            System.out.println("Saving protein: " + proteinGraph);
            graphRepository.save(proteinGraph);
        }

    }

    // Ottieni proteina e relazioni tramite uniprotID
    public ProteinGraph getProteinByUniProtID(String uniProtID) {
        ProteinGraph proteinGraph = graphRepository.findProteinGraphByUniProtID(uniProtID);
        if (proteinGraph == null) {
            throw new IllegalArgumentException("Protein with ID " + uniProtID + " does not exist");
        }
        return proteinGraph;
    }

    // cancellare proteina tramite id
    public void deleteProteinByUniProtID(String uniProtID) {
        graphRepository.deleteProteinGraphByUniProtID(uniProtID);
    }

    // aggiornare proteina esistente
    public void updateProteinByUniProtID(ProteinDTO proteinDTO) {
        ProteinGraph proteinGraph = graphRepository.findProteinGraphByUniProtID(proteinDTO.getUniProtID());
        if (proteinGraph == null) {
            throw new IllegalArgumentException("Protein with ID " + proteinDTO.getUniProtID() + " does not exist");
        }
        proteinGraph.setName(proteinDTO.getName());

        // Cancella le relazioni esistenti nel database
        graphRepository.deleteInteractionsByUniProtID(proteinDTO.getUniProtID());
        proteinGraph.clearInteractions();
        // Aggiungi le nuove relazioni
        for (ProteinDTO interaction : proteinDTO.getProteinInteractions()) {
            ProteinGraph existingProtein = graphRepository.findByUniProtID(interaction.getUniProtID());
            if (existingProtein != null) {
                proteinGraph.addInteraction(existingProtein);
            } else {
                throw new IllegalArgumentException("Protein with ID " + interaction.getUniProtID() + " does not exist");
            }
        }

        graphRepository.save(proteinGraph);
    }

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




