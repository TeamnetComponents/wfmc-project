package ro.teamnet.wfmc.audit.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ro.teamnet.wfmc.audit.aop.WfmcAuditAspect;
import ro.teamnet.wfmc.audit.service.AuditSampleService;
import ro.teamnet.wfmc.audit.service.AuditSampleServiceImpl;
import ro.teamnet.wfmc.audit.service.WfmcAuditService;
import ro.teamnet.wfmc.audit.service.WfmcAuditServiceImpl;

/**
 * Configuration file for auditing aspects.
 */

@Configuration
@EnableAspectJAutoProxy
public class WfmcAuditAspectConfig {
    private final Logger log = LoggerFactory.getLogger(WfmcAuditAspectConfig.class);

    @Bean
    public WfmcAuditAspect wfMCAuditAspect() {
        log.info("Configuring aspect");
        return new WfmcAuditAspect();
    }

    @Bean
    public WfmcAuditService wfmcAuditService(){
        return new WfmcAuditServiceImpl();
    }

    @Bean
    public AuditSampleService sampleService(){
        return new AuditSampleServiceImpl();
    }
}
