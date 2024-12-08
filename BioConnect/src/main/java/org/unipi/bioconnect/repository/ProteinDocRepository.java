package org.unipi.bioconnect.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.unipi.bioconnect.DTO.ProteinDTO;
import org.unipi.bioconnect.model.ProteinDoc;

import java.util.List;


public interface ProteinDocRepository extends MongoRepository<ProteinDoc, String> {

    @Query(value = "{}", fields = "{ 'uniProtID': 1, 'name': 1 }")
    List<ProteinDTO> findAllProjectedBy();

}
