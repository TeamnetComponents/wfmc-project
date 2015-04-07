package ro.teamnet.wfmc.audit.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.teamnet.audit.annotation.Auditable;
import ro.teamnet.audit.aop.AbstractAuditingAspect;
import ro.teamnet.audit.constants.AuditStrategy;
import ro.teamnet.audit.util.AuditInfo;
import ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit;

import java.util.Objects;

/**
* Aspect for auditing WfMC processes.
*/
@Aspect
public class WfmcAuditingAspect extends AbstractAuditingAspect {

    private Logger log = LoggerFactory.getLogger(WfmcAuditingAspect.class);

    @Around("auditableMethod() && @annotation(auditable))")
    public Object auditMethod(ProceedingJoinPoint joinPoint, Auditable auditable) throws ClassNotFoundException {
        Object returnValue = null;
        String auditStrategy = auditable.strategy();
        if (!Objects.equals(auditStrategy, AuditStrategy.WFMC)) {
            return returnValue;
        }
        String auditableType = auditable.type();
        log.info("Started auditing : " + auditableType + ", using strategy : " + auditStrategy);

        AuditInfo auditInfo = AuditInfo.getInstance(auditableType, joinPoint);

        WMProcessInstanceAudit wmProcessInstanceAudit = null; //will be returned by the audit service
        //TODO: save audit info into the audit entities
        // Choose entity building strategy using auditableType, then call builder, build audit entities and
        // save audit entities. Return the created WMProcessInstanceAudit.

        try {
            returnValue = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.warn("Could not proceed: ", throwable);
            //TODO: save error to db and return the created instance
            // Call a service method that sets the error on the wmProcessInstanceAudit instance & saves the updated wmProcessInstanceAudit
        } finally {
            log.info("Finished auditing : " + auditableType + ", using strategy : " + auditStrategy);
            return returnValue;
        }
    }
}
