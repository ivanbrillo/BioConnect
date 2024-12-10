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
                Aggregation.unwind("publications", true), // Unwind the publications array, with the 'true' flag to preserve empty arrays
                Aggregation.match(
                        new Criteria().andOperator(
                                Criteria.where("publications.year").exists(true), // Ensure 'year' exists
                                Criteria.where("publications.year").ne("No year") // Ensure 'year' is not "No year"
                        )
                ),
                Aggregation.group("publications.year", "publications.type") // Group by year and type
                        .count().as("count"), // Count the number of publications
                Aggregation.sort(Sort.by(Sort.Order.asc("_id"))) // Sort by the grouped field (which will be _id, containing year)
        );


        System.out.println(pathway);
        AggregationResults<TrendAnalysisDTO> results = mongoTemplate.aggregate(aggregation, "Protein", TrendAnalysisDTO.class);
        System.out.println(results.getMappedResults());
        return results.getMappedResults();
    }


}
