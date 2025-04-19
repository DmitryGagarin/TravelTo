package com.travel.to.travel_to.configuration;

import io.micrometer.core.instrument.MeterRegistry;
import org.hibernate.SessionFactory;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class HibernateMetricsConfig {

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> hibernateMetrics(EntityManagerFactory entityManagerFactory) {
        return registry -> {
            SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
            // Basic metrics will be auto-registered by hibernate-micrometer
            // Add any custom metrics here if needed
        };
    }
}