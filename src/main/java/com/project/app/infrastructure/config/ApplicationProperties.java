package com.project.app.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Micro Project.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    // jhipster-needle-application-properties-property

    // jhipster-needle-application-properties-property-getter

    // jhipster-needle-application-properties-property-class

    private final Liquibase liquibase = new Liquibase();

    // jhipster-needle-application-properties-property

    public Liquibase getLiquibase() {
        return liquibase;
    }

    // jhipster-needle-application-properties-property-getter

    public static class Liquibase {

        private Boolean asyncStart = true;

        public Boolean getAsyncStart() {
            return asyncStart;
        }

        public void setAsyncStart(Boolean asyncStart) {
            this.asyncStart = asyncStart;
        }
    }
}
