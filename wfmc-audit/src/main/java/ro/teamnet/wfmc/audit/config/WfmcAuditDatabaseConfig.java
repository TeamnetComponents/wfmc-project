package ro.teamnet.wfmc.audit.config;

import liquibase.integration.spring.SpringLiquibase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Configuration file for auditing database.
 */

@Configuration
@EnableJpaRepositories("ro.teamnet.wfmc.audit.repository")
@EnableTransactionManagement
@EntityScan("ro.teamnet.wfmc.audit.domain")
public class WfmcAuditDatabaseConfig implements EnvironmentAware {

    private final Logger log = LoggerFactory.getLogger(WfmcAuditDatabaseConfig.class);

    private Environment env;
    private RelaxedPropertyResolver propertyResolver;

    @Override
    public void setEnvironment(Environment env) {
        this.env = env;
        this.propertyResolver = new RelaxedPropertyResolver(env, "spring.wfmcAuditDatasource.");
    }

    public void setEnv(Environment env) {
        this.env = env;
    }

    public void setPropertyResolver(RelaxedPropertyResolver propertyResolver) {
        this.propertyResolver = propertyResolver;
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath*:config/liquibase/audit-changelog.xml");
        liquibase.setContexts("development, production");
        if (env.acceptsProfiles(Constants.SPRING_PROFILE_FAST)) {
            liquibase.setShouldRun(false);
        } else {
            log.debug("Configuring Liquibase");
        }
        return liquibase;
    }
}
