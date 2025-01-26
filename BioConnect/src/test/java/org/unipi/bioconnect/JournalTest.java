package org.unipi.bioconnect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.unipi.bioconnect.model.Document.ProteinDoc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Component
public class JournalTest implements CommandLineRunner {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void run(String... args) throws Exception {
        long startTime = System.currentTimeMillis();
        List<String> addedProteinIds = new ArrayList<>();

        // Perform database operations here
        for (int i = 0; i < 1000; i++) {
            ProteinDoc proteinDoc = new ProteinDoc();
            proteinDoc.setUniProtID(i + "g");
            mongoTemplate.save(proteinDoc);
            addedProteinIds.add(proteinDoc.getUniProtID());
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + " ms");

        // Remove all proteins added
        for (String id : addedProteinIds) {
            mongoTemplate.remove(mongoTemplate.findById(id, ProteinDoc.class));
        }
    }
}