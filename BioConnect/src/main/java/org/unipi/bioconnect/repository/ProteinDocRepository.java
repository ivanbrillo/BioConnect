package org.unipi.bioconnect.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.unipi.bioconnect.DTO.ProteinDocDTO;
import org.unipi.bioconnect.model.ProteinDoc;

import java.util.List;


public interface ProteinDocRepository extends MongoRepository<ProteinDoc, String> {

    @Query(value = "{}", fields = "{ '_class': 0 }")
    List<ProteinDocDTO> findAllProjectedBy();

    @Query(value = "{ '$or': [ { '_id': ?0 }, { 'name': { '$regex': ?0, '$options': 'i' } } ] }", fields = "{ '_class': 0 }")
    List<ProteinDocDTO> findByIdOrNameContainingIgnoreCase(String searchedText);

    long deleteByUniProtID(String uniProtID);


}
