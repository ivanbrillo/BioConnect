package org.unipi.bioconnect.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.unipi.bioconnect.DTO.Doc.DrugDocDTO;
import org.unipi.bioconnect.model.DrugDoc;

import java.util.List;

public interface DrugDocRepository extends MongoRepository<DrugDoc, String> {

    @Query(value = "{ '$or': [ { '_id': ?0 }, { 'name': { '$regex': ?0, '$options': 'i' } } ] }", fields = "{ '_class': 0 }")
    List<DrugDocDTO> findByIdOrNameContainingIgnoreCase(String searchedText);

    long deleteByDrugBankID(String drugBankID);

}
