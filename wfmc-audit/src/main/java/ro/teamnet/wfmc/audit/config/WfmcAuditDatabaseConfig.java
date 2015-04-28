package ro.teamnet.wfmc.audit.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Configuration file for auditing database.
 */
@Configuration
@EnableJpaRepositories(basePackages = {"ro.teamnet.wfmc.audit.repository"},
        entityManagerFactoryRef = "wfmcAuditEntityManagerFactory",
        transactionManagerRef = "wfmcAuditTransactionManager")
@EnableTransactionManagement
public class WfmcAuditDatabaseConfig {

    private final Logger log = LoggerFactory.getLogger(WfmcAuditDatabaseConfig.class);

    @Inject
    private Environment env;


    @Bean(name = "wfmcAuditDataSource")
    public DataSource dataSource() {
        log.info("Configuring WfMC audit data source");
        RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, "wfmc.audit.datasource.");

        String dataSourceClassName = propertyResolver.getProperty("dataSourceClassName");
        if (!isPropertyValid(dataSourceClassName)) {
            String message = "Property wfmc.audit.datasource.dataSourceClassName is missing!";
            log.error(message);
            throw new ApplicationContextException(message);
        }
        String url = propertyResolver.getProperty("url");
        String jdbcUrl = propertyResolver.getProperty("jdbcUrl");
        String databaseName = propertyResolver.getProperty("databaseName");
        String serverName = propertyResolver.getProperty("serverName");

        if (!isPropertyValid(url) && !isPropertyValid(jdbcUrl)
                && (!isPropertyValid(databaseName) || !isPropertyValid(serverName))) {
            String message = "Unable to configure te wfmc audit datasource. " +
                    "Please specify the url or the databaseName and serverName for wfmc.audit.datasource.";
            log.error(message);
            throw new ApplicationContextException(message);
        }
        HikariConfig configuration = new HikariConfig();
        configuration.setDataSourceClassName(dataSourceClassName);
        if (isPropertyValid(url)) {
            configuration.addDataSourceProperty("url", url);
        } else if (isPropertyValid(jdbcUrl)) {
            configuration.addDataSourceProperty("url", jdbcUrl);
        } else {
            configuration.addDataSourceProperty("databaseName", databaseName);
            configuration.addDataSourceProperty("serverName", serverName);
        }
        configuration.addDataSourceProperty("user", propertyResolver.getProperty("username"));
        configuration.addDataSourceProperty("password", propertyResolver.getProperty("password"));

        return new HikariDataSource(configuration);
    }

    private boolean isPropertyValid(String property) {
        return property != null && !"".equals(property);
    }

    @Bean(name = "wfmcAuditLiquibase")
    public SpringLiquibase liquibase(@Qualifier("wfmcAuditDataSource") DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath*:config/liquibase/audit-changelog.xml");
        liquibase.setContexts("development, production");
        if (env.acceptsProfiles(Constants.SPRING_PROFILE_FAST)) {
            liquibase.setShouldRun(false);
        } else {
            log.info("Configuring audit liquibase");
        }
        return liquibase;
    }

    @Bean(name = "wfmcAuditEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource())
                .packages("ro.teamnet.wfmc.audit")
                .persistenceUnit("wfmcAudit")
                .build();
    }

    @Bean(name = "wfmcAuditTransactionManager")
    public PlatformTransactionManager dataSourceTransactionManager(@Qualifier("wfmcAuditEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager(entityManagerFactory);
        jpaTransactionManager.setDataSource(dataSource());
        return jpaTransactionManager;
    }
}
