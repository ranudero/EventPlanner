package com.example.eventplanner.repositories;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.example.eventplanner.domain")
@EnableJpaRepositories(basePackages = "com.example.eventplanner.repositories")
public class JpaTestConfiguration {
}
