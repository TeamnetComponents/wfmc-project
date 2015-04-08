package ro.teamnet.wfmc.audit.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wfmc.audit.WMAEventCode;
import ro.teamnet.audit.annotation.Auditable;
import ro.teamnet.audit.aop.AbstractAuditingAspect;
import ro.teamnet.audit.constants.AuditStrategy;
import ro.teamnet.audit.util.AuditInfo;
import ro.teamnet.audit.util.AuditParameterInfo;
import ro.teamnet.wfmc.audit.constants.WfmcAuditedMethod;
import ro.teamnet.wfmc.audit.domain.*;
import ro.teamnet.wfmc.audit.repository.ProcessInstanceAuditRepository;
import ro.teamnet.wfmc.audit.service.AuditSampleService;
import ro.teamnet.wfmc.audit.service.WfmcAuditService;
import ro.teamnet.wfmc.audit.util.WfmcAuditError;
import ro.teamnet.wfmc.audit.util.WfmcPreviousState;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Audit aspect sample.
 */
@Aspect
public class SampleAuditAspect extends AbstractAuditingAspect {

    @Inject
    WfmcAuditError auditError;
    @Inject
    private WfmcAuditService wfmcAuditService;
    @Inject
    private ProcessInstanceAuditRepository processInstanceAuditRepository;

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
        if (!auditInfo.getMethod().getName().equals(WfmcAuditedMethod.CREATE_PROCESS_INSTANCE)) {
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                log.warn("Could not proceed: ", throwable);
                return returnValue;
            }
        }
        AuditParameterInfo auditParameterInfo = new AuditParameterInfo();
        ArrayList<Object> myArguments = auditParameterInfo.doSomething(auditInfo, joinPoint);
        WMErrorAudit wmErrorAudit = null;
        WMProcessInstanceAudit wmProcessInstanceAudit = null;
        // TODO : return type of returned object

        String username = getUserIdentification(auditInfo);
        WMEventAuditProcessInstance wmEventAuditProcessInstance = wfmcAuditService.convertAndSaveCreateProcessInstance(
                (String) myArguments.get(0), (String) myArguments.get(1), null, WfmcPreviousState.CREATE_PROCESS_INSTANCE, WMAEventCode.CREATED_PROCESS_INSTANCE_INT, username);
        try {
            returnValue = joinPoint.proceed();
            log.info("EventAudit some properties: {}", wmEventAuditProcessInstance.getWmProcessInstanceAudit().getProcessInstanceId());
        } catch (Throwable throwable) {
            log.warn("Could not proceed: ", throwable);
            // Call a service method that sets the error on the wmProcessInstanceAudit instance & saves the updated wmProcessInstanceAudit

            wmProcessInstanceAudit = wmEventAuditProcessInstance.getWmProcessInstanceAudit();
            wmErrorAudit = auditError.saveErrorIntoEntityWmErrorAudit(throwable, wmProcessInstanceAudit, joinPoint);
            log.info("Finished saving details about the error");
        } finally {
            //singura valoare care se modifica este processInstanceId, care provine din returnValue
            if (returnValue != null) {
                wmProcessInstanceAudit = wmEventAuditProcessInstance.getWmProcessInstanceAudit();
                wmProcessInstanceAudit.setProcessInstanceId(returnValue.toString());
                wfmcAuditService.updateProcessInstance(wmProcessInstanceAudit);

                //implicit si in eroare se updateaza obiectul
                if (wmErrorAudit != null) {
                    wmErrorAudit.setWmProcessInstanceAudit(wmProcessInstanceAudit);
                    auditError.updateErrorEntityWmErrorAudit(wmProcessInstanceAudit);
                }
            }
            log.info("Finished auditing : " + auditableType + ", using strategy : " + auditStrategy);
            return returnValue;
        }
    }

    private String getUserIdentification(AuditInfo auditInfo) {
        return (String) auditInfo.invokeMethodOnInstance("getUserIdentification");
    }
}
