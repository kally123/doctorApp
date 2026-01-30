package com.healthapp.ehr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableReactiveMongoAuditing
@EnableScheduling
public class EhrServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EhrServiceApplication.class, args);
    }
}
