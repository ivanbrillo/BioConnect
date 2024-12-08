package org.unipi.bioconnect.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Relationship;
import java.util.List;


@Data
@Node("Protein")
@NoArgsConstructor
public class ProteinGraph {

    @Id
    private String uniProtID;

    private String name;

    @Relationship(type = "INTERACTS_WITH")
    List<ProteinGraph> interacts;



}
