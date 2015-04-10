package ro.teamnet.wfmc.audit.aop;

import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import ro.teamnet.audit.aop.AbstractAuditingAspect;
import ro.teamnet.audit.strategy.MethodAuditingStrategyFactory;
import ro.teamnet.wfmc.audit.constants.WfmcAuditStrategy;

import javax.inject.Inject;
import java.util.Objects;

/**
 * Aspect for auditing WfMC processes.
 */
@Aspect
public class WfmcAuditingAspect extends AbstractAuditingAspect {


    @Inject
    @Qualifier(WfmcAuditStrategy.WFMC)
    private MethodAuditingStrategyFactory strategyFactory;

    private Logger log = LoggerFactory.getLogger(WfmcAuditingAspect.class);

    public MethodAuditingStrategyFactory getMethodAuditingStrategyFactory(String auditStrategy) {
        if (!Objects.equals(auditStrategy, WfmcAuditStrategy.WFMC)) {
            return null;
        }
        log.info("Loading WfMC auditing strategies.");
        return strategyFactory;
    }
}
