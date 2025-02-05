package com.graph.demo.Configuration;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class Neo4jConfig {


    @Bean
    public Driver neo4jDriver() {
        return GraphDatabase.driver(
                "neo4j://localhost:7687",  // Make sure the URL is correct
                AuthTokens.basic("******", "*****")  // Replace with actual credentials
        );
    }

    @Bean
    public Neo4jClient neo4jClient(Driver driver) {
        return Neo4jClient.create(driver);
    }

    @Bean
    public Neo4jTransactionManager transactionManager(Driver driver) {
        return new Neo4jTransactionManager(driver);
    }
}
