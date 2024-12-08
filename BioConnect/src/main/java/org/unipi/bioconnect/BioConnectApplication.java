package org.unipi.bioconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.config.EnableNeo4jAuditing;

@SpringBootApplication
public class BioConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BioConnectApplication.class, args);
	}

}
