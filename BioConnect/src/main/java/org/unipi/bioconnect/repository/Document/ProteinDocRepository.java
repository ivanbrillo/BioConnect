package org.unipi.bioconnect.repository.Document;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.unipi.bioconnect.DTO.Document.ProteinDocDTO;
import org.unipi.bioconnect.model.Document.ProteinDoc;

import java.util.List;


public interface ProteinDocRepository extends MongoRepository<ProteinDoc, String> {

    @Query(value = "{ '$or': [ { '_id': ?0 }, { 'name': { '$regex': ?0, '$options': 'i' } } ] }", fields = "{ '_class': 0 }")  // projection excluding class attribute (automatically added by mongo drivers)
    List<ProteinDocDTO> findByIdOrNameContainingIgnoreCase(String searchedText);

    @Query(value = "{ 'pathways': ?0, 'subcellularLocations': ?1 }", fields = "{ '_class': 0 }")
    List<ProteinDocDTO> findByPathwayAndSubcellularLocation(String pathway, String subcellularLocation);

    long deleteByUniProtID(String uniProtID);

}
