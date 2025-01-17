package org.unipi.bioconnect.repository.Document;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.unipi.bioconnect.DTO.Document.DrugDocDTO;
import org.unipi.bioconnect.model.Document.DrugDoc;

import java.util.List;

public interface DrugDocRepository extends MongoRepository<DrugDoc, String> {

    @Query(value = "{ '$or': [ { '_id': ?0 }, { 'name': { '$regex': ?0, '$options': 'i' } } ] }", fields = "{ '_class': 0 }")  // projection excluding class attribute (automatically added by mongo drivers)
    List<DrugDocDTO> findByIdOrNameContainingIgnoreCase(String searchedText);

    long deleteByDrugBankID(String drugBankID);

}
