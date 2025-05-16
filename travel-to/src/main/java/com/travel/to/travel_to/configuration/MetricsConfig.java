package com.travel.to.travel_to.configuration;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.core.instrument.config.MeterFilter;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore({
    org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration.class,
    org.springframework.boot.actuate.autoconfigure.observation.ObservationAutoConfiguration.class
})
public class MetricsConfig {

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCustomizer() {
        return registry -> registry.config()
            .meterFilter(MeterFilter.deny(id ->
                id.getName().startsWith("process.") ||
                    id.getName().startsWith("system.")
            ))
            .commonTags("application", "TravelTo");
    }

    @Bean
    @ConditionalOnMissingBean
    public ProcessorMetrics processorMetrics() {
        return new NoOpProcessorMetrics();
    }

    static class NoOpProcessorMetrics extends ProcessorMetrics {
        @Override
        public void bindTo(@NotNull MeterRegistry registry) {
            // Intentionally do nothing
        }
    }
}