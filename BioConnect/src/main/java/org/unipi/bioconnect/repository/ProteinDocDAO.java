package org.unipi.bioconnect.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
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


}
