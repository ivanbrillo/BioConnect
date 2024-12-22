package org.unipi.bioconnect.DTO.Graph;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ShortestPathDTO {
    private String disease1Name;
    private String disease2Name;
    private List<String> connectingProteins;  // Lista delle proteine di collegamento
    private int pathLength;
    private List<String> fullPath;  // Eventuali dettagli del percorso

    // Costruttore per mappare direttamente il risultato
    public ShortestPathDTO(String disease1Name, String disease2Name, List<String> connectingProteins, int pathLength, List<String> fullPath) {
        this.disease1Name = disease1Name;
        this.disease2Name = disease2Name;
        this.connectingProteins = connectingProteins;
        this.pathLength = pathLength;
        this.fullPath = fullPath;
    }
}
