package org.unipi.bioconnect.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.unipi.bioconnect.DTO.PathwayRecurrenceDTO;
import org.unipi.bioconnect.DTO.ProteinDTO;
import org.unipi.bioconnect.DTO.TrendAnalysisDTO;

import java.util.List;


@Repository
public class ProteinDocDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<TrendAnalysisDTO> getTrendAnalysisForPathway(String pathway) {

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("pathways").in(pathway)), // Match pathway
                Aggregation.unwind("publications", false), // Unwind publications array, not preserving empty arrays
                Aggregation.group("publications.year", "publications.type") // Group by year and type
                        .count().as("count"), // Count the number of publications in each group
                Aggregation.sort(Sort.by(Sort.Order.asc("_id.year"))) // Sort by the 'year' field within the '_id' (composite)
        );

        AggregationResults<TrendAnalysisDTO> results = mongoTemplate.aggregate(aggregation, "Protein", TrendAnalysisDTO.class);
        return results.getMappedResults();
    }


    public List<PathwayRecurrenceDTO> getPathwayRecurrence(String subsequence) {

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("sequence").regex(subsequence, "i")), // Match sequence with subsequence
                Aggregation.unwind("pathways", false), // Unwind pathways array, preserving documents with empty arrays
                Aggregation.group("pathways") // Group by pathways
                        .count().as("count"), // Count the occurrences of each pathway
                Aggregation.sort(Sort.by(Sort.Order.desc("count"))) // Sort by count descending
        );

        AggregationResults<PathwayRecurrenceDTO> results = mongoTemplate.aggregate(aggregation, "Protein", PathwayRecurrenceDTO.class);
        return results.getMappedResults();
    }

    public List<ProteinDTO> getProteinsByPathwayAndLocation(String pathway, String subcellularLocation) {

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("pathways").is(pathway)),
                Aggregation.match(Criteria.where("subcellularLocations").is(subcellularLocation)),
                Aggregation.project()
                        .andExclude("_class")
        );

        AggregationResults<ProteinDTO> results = mongoTemplate.aggregate(aggregation, "Protein", ProteinDTO.class);
        return results.getMappedResults();
    }


}
