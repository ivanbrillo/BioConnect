package org.unipi.bioconnect.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.unipi.bioconnect.DTO.CommentDTO;
import org.unipi.bioconnect.model.User;

import java.util.List;

@Repository
public class CommentDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<CommentDTO> findAllComments() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("comments", false),  // Unwind comments array, not preserving empty arrays
                Aggregation.addFields().addField("comments.username").withValue("$_id").build(),
                Aggregation.project("comments._id", "comments.comment", "comments.elementId", "comments.username")
        );

        AggregationResults<CommentDTO> results = mongoTemplate.aggregate(aggregation, "Users", CommentDTO.class);
        return results.getMappedResults();
    }

    public List<CommentDTO> findUserComments(String username) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("_id").is(username)),
                Aggregation.unwind("comments", false),  // Unwind comments array, not preserving empty arrays
                Aggregation.project("comments._id", "comments.comment", "comments.elementId")  // no password, role or class
        );

        AggregationResults<CommentDTO> results = mongoTemplate.aggregate(aggregation, "Users", CommentDTO.class);
        return results.getMappedResults();
    }

}
