package ro.teamnet.wfmc.audit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ro.teamnet.wfmc.audit.aop.WfmcAuditAspect;

/**
 * Configuration file for auditing aspects.
 */

@Configuration
@EnableAspectJAutoProxy
public class WfmcAuditAspectConfig {

    @Bean
    public WfmcAuditAspect wfMCAuditAspect() {
        return new WfmcAuditAspect();
    }
}
