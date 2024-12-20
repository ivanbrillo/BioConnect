package org.unipi.bioconnect.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseNodeDTO {

    @NotNull(message = "id is required")
    protected String id;

    @NotNull(message = "Name is required")
    protected String name;

}
