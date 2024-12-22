package org.unipi.bioconnect.DTO.Graph;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OppositeEffectDrugsDTO {

    private BaseNodeDTO drug;
    private BaseNodeDTO protein;
    private String effect;

}

