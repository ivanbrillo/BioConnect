package org.unipi.bioconnect.DTO.Graph;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OppositeEffectDrugsDTO {

    private BaseNodeDTO drug;
    private BaseNodeDTO protein;

    @Schema(description = "Type of effect between protein and drug" , example = "enhancer")
    private String effect;

}

