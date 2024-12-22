package org.unipi.bioconnect.DTO.Graph;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OppositeEffectDrugsDTO {

    BaseNodeDTO drug;
    BaseNodeDTO protein;
    String effect;

}

