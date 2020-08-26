package com.prinpedia.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication(scanBasePackages = {"com.prinpedia.backend"})
@EnableElasticsearchRepositories
public class BackendApplication {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(BackendApplication.class);
        logger.info("Springboot backend start");
        SpringApplication.run(BackendApplication.class, args);
    }

}
