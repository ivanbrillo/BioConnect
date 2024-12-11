package org.unipi.bioconnect.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.unipi.bioconnect.DTO.PatentStateAnalysisDTO;
import org.unipi.bioconnect.DTO.TrendAnalysisDTO;

import java.util.List;

@Repository
public class DrugDocDAO {

    @Autowired
    private MongoTemplate mongoTemplate;


    public List<TrendAnalysisDTO> getTrendAnalysisForCategory(String category) {

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("categories").in(category)), // Match pathway
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
                // Match drugs with the specific category
                Aggregation.match(Criteria.where("categories").in(category)),

                // Unwind the patents array (so we can work with each patent individually)
                Aggregation.unwind("patents", false),

                // Add 'name' field to the document to make it available for grouping
                Aggregation.addFields().addField("drugName").withValue("$$ROOT.name").build(),

                // Convert the 'year' field to an integer and filter patents older than the expired year
                Aggregation.project()
                        .andExpression("toInt(patents.year)").as("patentYear")
                        .and("patents.country").as("patents.country")
                        .and("drugName").as("drugName"), // Include drug name in the projection

                // Match patents older than the expired year
                Aggregation.match(Criteria.where("patentYear").lt(expiredYear)),

                // Group by state (patents.country), and collect drug names with expired patents
                Aggregation.group("patents.country")
                        .count().as("expiredPatentCount") // Count expired patents per country
                        .addToSet("drugName").as("drugNames"), // Collect drug names that have expired patents

                // Sort by the number of expired patents (descending, most expired patents first)
                Aggregation.sort(Sort.by(Sort.Order.desc("expiredPatentCount")))
        );
        // Run the aggregation on the 'Drug' collection
        AggregationResults<PatentStateAnalysisDTO> results = mongoTemplate.aggregate(aggregation, "Drug", PatentStateAnalysisDTO.class);

        // Return the list of results (states and their corresponding expired patents count)
        return results.getMappedResults();
    }


}
