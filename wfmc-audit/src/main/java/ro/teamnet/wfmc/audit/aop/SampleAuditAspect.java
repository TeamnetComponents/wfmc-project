package ro.teamnet.wfmc.audit.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wfmc.wapi.WMConnectInfo;
import ro.teamnet.audit.annotation.Auditable;
import ro.teamnet.audit.aop.AbstractAuditingAspect;
import ro.teamnet.audit.constants.AuditStrategy;
import ro.teamnet.audit.util.AuditInfo;
import ro.teamnet.audit.util.AuditParameterInfo;
import ro.teamnet.wfmc.audit.domain.*;
import ro.teamnet.wfmc.audit.service.AuditSampleService;
import ro.teamnet.wfmc.audit.service.WfmcAuditService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Audit aspect sample.
 */
@Aspect
public class SampleAuditAspect extends AbstractAuditingAspect {

    @Inject
    public WfmcAuditService wfmcAuditService;
    @Inject
    public AuditSampleService sampleService;

    private Logger log = LoggerFactory.getLogger(SampleAuditAspect.class);

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
        AuditParameterInfo auditParameterInfo = new AuditParameterInfo();
        ArrayList<Object> myArguments =  auditParameterInfo.doSomething(auditInfo, joinPoint);
        // TODO : return type of returned object
        try {
            returnValue = joinPoint.proceed();
            WMEventAuditProcessInstance wmEventAuditProcessInstance = wfmcAuditService.convertAndSaveCreateProcessInstance(
                    (String) myArguments.get(0),(String) myArguments.get(1),returnValue.toString(), "null", new Integer(1), "Gogu");
            log.info("EventAudit some properties: {}", wmEventAuditProcessInstance.getWmProcessInstanceAudit().getProcessInstanceId());
        } catch (Throwable throwable) {
            log.warn("Could not proceed: ", throwable);
            // Call a service method that sets the error on the wmProcessInstanceAudit instance & saves the updated wmProcessInstanceAudit
            /*AuditError auditError = new AuditError();
            WMErrorAudit wmErrorAudit = auditError
                    .saveErrorIntoEntityWmErrorAudit(throwable, wmProcessInstanceAudit, joinPoint);
            log.info("Finished saving details about the error");
            return wmErrorAudit;*/
        } finally {
            log.info("Finished auditing : " + auditableType + ", using strategy : " + auditStrategy);
            return returnValue;
        }
    }
}
