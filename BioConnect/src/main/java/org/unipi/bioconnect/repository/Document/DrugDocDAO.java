package org.unipi.bioconnect.repository.Document;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.unipi.bioconnect.DTO.Document.PatentStateAnalysisDTO;
import org.unipi.bioconnect.DTO.Document.TrendAnalysisDTO;

import java.util.List;

@Repository
public class DrugDocDAO {

    @Autowired
    private MongoTemplate mongoTemplate;


    public List<TrendAnalysisDTO> getTrendAnalysisForCategory(String category) {

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("categories").in(category)), // Match pathway
                Aggregation.project()  // make more lightweight the document for the successive unwind
                        .and("publications").as("publications"),
                Aggregation.unwind("publications", false), // Unwind publications array, not preserving empty arrays
                Aggregation.group("publications.year", "publications.type") // Group by year and type
                        .count().as("count"), // Count the number of publications in each group
                Aggregation.sort(Sort.by(Sort.Order.asc("_id.year"))) // Sort by the 'year' field within the '_id' (composite)
        );

        AggregationResults<TrendAnalysisDTO> results = mongoTemplate.aggregate(aggregation, "Drug", TrendAnalysisDTO.class);
        return results.getMappedResults();
    }


    public List<PatentStateAnalysisDTO> getExpiredPatentsByStateForCategory(String category) {

        // Get the current year to calculate expired patents
        int expiredYear = java.time.Year.now().getValue() - 20;

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("categories").is(category)),

                // Match patents older than the expired year
                Aggregation.project()
                        .and(ArrayOperators.Filter.filter("patents")
                                .as("patent")
                                .by(ComparisonOperators.Lt.valueOf("$$patent.year").lessThanValue(expiredYear))).as("patents")
                        .and("name").as("name"),

                Aggregation.unwind("patents", false),

                // Group by state (patents.country), and collect/count drug names with expired patents
                Aggregation.group("patents.country")
                        .count().as("expiredPatentCount") // Count expired patents per country
                        .addToSet("name").as("drugNames"), // Collect drug names that have expired patents

                Aggregation.sort(Sort.by(Sort.Order.desc("expiredPatentCount")))
        );

        AggregationResults<PatentStateAnalysisDTO> results = mongoTemplate.aggregate(aggregation, "Drug", PatentStateAnalysisDTO.class);
        return results.getMappedResults();
    }


}
