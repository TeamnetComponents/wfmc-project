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

    public MethodAuditingStrategyFactory getMethodAuditingStrategyFactory(String auditStrategy) {
        if (!Objects.equals(auditStrategy, WfmcAuditStrategy.WFMC)) {
            return null;
        }
        return strategyFactory;
    }

    private Logger log = LoggerFactory.getLogger(WfmcAuditingAspect.class);

//    @Around("auditableMethod() && @annotation(auditable))")
//    public Object auditMethod(ProceedingJoinPoint joinPoint, Auditable auditable) throws ClassNotFoundException {
//
//        String auditStrategy = auditable.strategy();
//        if (!Objects.equals(auditStrategy, AuditStrategy.WFMC)) {
//            return null;
//        }
//        Method auditedMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
//        String auditableType = auditable.type() == null ? auditedMethod.getName() : auditable.type();
//        log.info("__________________________________________________________________________________________________");
//        log.info("__________________________________________________________________________________________________");
//        log.info("Started auditing : " + auditableType + ", using strategy : " + auditStrategy);
//
//        AuditInfo auditInfo = new AuditInfo(auditableType, auditedMethod, joinPoint.getThis(), joinPoint.getArgs());
//        Object returnValue = null;
//
//        MethodAuditingStrategy auditingStrategy = strategyFactory.getStrategy(auditableType);
//
//        auditingStrategy.setAuditInfo(auditInfo);
//        auditingStrategy.auditMethodBeforeInvocation();
//        try {
//            returnValue = joinPoint.proceed();
//            log.info("Returned value from audited method: {}", returnValue);
//        } catch (Throwable throwable) {
//            log.warn("Could not proceed: ", throwable);
//            auditingStrategy.auditMethodInvocationError(throwable);
//            log.info("Finished saving details about the error");
//        } finally {
//            if (returnValue != null) {
//                auditingStrategy.auditMethodAfterInvocation(returnValue);
//            }
//            log.info("Finished auditing : " + auditableType + ", using strategy : " + auditStrategy);
//            log.info("__________________________________________________________________________________________________");
//            log.info("__________________________________________________________________________________________________");
//
//            return returnValue;
//        }
//    }
}
