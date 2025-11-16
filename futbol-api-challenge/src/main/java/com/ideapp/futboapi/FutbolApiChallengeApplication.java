package com.ideapp.futboapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.ideapp.futboapi.repository")
public class FutbolApiChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FutbolApiChallengeApplication.class, args);
    }

}