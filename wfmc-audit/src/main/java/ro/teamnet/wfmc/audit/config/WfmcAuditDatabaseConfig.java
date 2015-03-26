package ro.teamnet.wfmc.audit.config;

import liquibase.integration.spring.SpringLiquibase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Inject;
import javax.sql.DataSource;

/**
 * Configuration file for auditing database.
 */

@Configuration
@EnableJpaRepositories("ro.teamnet.wfmc.audit.repository")
@EnableTransactionManagement
@EntityScan("ro.teamnet.wfmc.audit.domain")
public class WfmcAuditDatabaseConfig {

    private final Logger log = LoggerFactory.getLogger(WfmcAuditDatabaseConfig.class);

    @Inject
    private Environment env;

    @Bean
    @ConfigurationProperties(prefix = "wfmcAudit.datasource")
    public DataSource dataSource() {
        log.info("Configuring Data source");
        return DataSourceBuilder.create().build();
    }

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource());
        liquibase.setChangeLog("classpath*:config/liquibase/audit-changelog.xml");
        liquibase.setContexts("development, production");
        if (env.acceptsProfiles(Constants.SPRING_PROFILE_FAST)) {
            liquibase.setShouldRun(false);
        } else {
            log.info("Configuring Liquibase");
        }
        return liquibase;
    }
}
