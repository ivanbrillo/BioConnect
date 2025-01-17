package org.unipi.bioconnect.repository.Document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.unipi.bioconnect.DTO.Document.PathwayRecurrenceDTO;
import org.unipi.bioconnect.DTO.Document.ProteinDocDTO;
import org.unipi.bioconnect.DTO.Document.TrendAnalysisDTO;

import java.util.List;


@Repository
public class ProteinDocDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<TrendAnalysisDTO> getTrendAnalysisForPathway(String pathway) {

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("pathways").in(pathway)), // Match pathway
                Aggregation.project()  // make more lightweight the document for the successive unwind
                        .and("publications").as("publications"),
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
                Aggregation.match(Criteria.where("sequence").regex(subsequence, "i")), // Match sequence with subsequence, case-insensitive
                Aggregation.project()  // make more lightweight the document for the successive unwind
                        .and("pathways").as("pathways"),
                Aggregation.unwind("pathways", false), // Unwind pathways array, preserving documents with empty arrays
                Aggregation.group("pathways") // Group by pathways
                        .count().as("count"), // Count the occurrences of each pathway
                Aggregation.sort(Sort.by(Sort.Order.desc("count"))) // Sort by count descending
        );

        AggregationResults<PathwayRecurrenceDTO> results = mongoTemplate.aggregate(aggregation, "Protein", PathwayRecurrenceDTO.class);
        return results.getMappedResults();
    }


}
