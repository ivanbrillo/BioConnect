package org.unipi.bioconnect.DTO.Graph;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OppositeEffectDrugsDTO {
    private String id1;
    private String name1;
    private String effect1;

    private String id2;
    private String name2;
    private String effect2;

    public OppositeEffectDrugsDTO(String id1, String id2, String name1, String name2, String effect1, String effect2) {
        this.id1 = id1;
        this.id2 = id2;
        this.name1 = name1;
        this.name2 = name2;
        this.effect1 = effect1;
        this.effect2 = effect2;
    }



}

