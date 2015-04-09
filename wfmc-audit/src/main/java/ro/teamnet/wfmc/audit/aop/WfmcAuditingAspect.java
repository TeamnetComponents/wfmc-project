package ro.teamnet.wfmc.audit.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wfmc.audit.WMAEventCode;
import ro.teamnet.audit.annotation.Auditable;
import ro.teamnet.audit.aop.AbstractAuditingAspect;
import ro.teamnet.audit.constants.AuditStrategy;
import ro.teamnet.audit.util.AuditInfo;
import ro.teamnet.wfmc.audit.constants.WfmcAuditedMethod;
import ro.teamnet.wfmc.audit.constants.WfmcAuditedParameter;
import ro.teamnet.wfmc.audit.domain.WMErrorAudit;
import ro.teamnet.wfmc.audit.domain.WMEventAuditProcessInstance;
import ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit;
import ro.teamnet.wfmc.audit.service.WfmcAuditService;
import ro.teamnet.wfmc.audit.util.WMAuditErrorUtil;
import ro.teamnet.wfmc.audit.util.WfmcPreviousState;

import javax.inject.Inject;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Aspect for auditing WfMC processes.
 */
@Aspect
public class WfmcAuditingAspect extends AbstractAuditingAspect {

    @Inject
    private WMAuditErrorUtil auditErrorUtil;
    @Inject
    private WfmcAuditService wfmcAuditService;

    private Logger log = LoggerFactory.getLogger(WfmcAuditingAspect.class);

    @Around("auditableMethod() && @annotation(auditable))")
    public Object auditMethod(ProceedingJoinPoint joinPoint, Auditable auditable) throws ClassNotFoundException {

        String auditStrategy = auditable.strategy();
        if (!Objects.equals(auditStrategy, AuditStrategy.WFMC)) {
            return null;
        }
        Method auditedMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String auditableType = auditable.type() == null ? auditedMethod.getName() : auditable.type();
        log.info("Started auditing : " + auditableType + ", using strategy : " + auditStrategy);

        AuditInfo auditInfo = new AuditInfo(auditableType, auditedMethod, joinPoint.getThis(), joinPoint.getArgs());
        String auditedMethodName = auditInfo.getMethod().getName();
        if (!auditedMethodName.equals(WfmcAuditedMethod.CREATE_PROCESS_INSTANCE)) {
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                log.warn("Could not proceed: ", throwable);
                return null;
            }
        }

        WMErrorAudit wmErrorAudit = null;
        String username = getUserIdentification(auditInfo);

        WMEventAuditProcessInstance wmEventAuditProcessInstance = wfmcAuditService.convertAndSaveCreateProcessInstance(
                (String) auditInfo.getArgumentsByParameterDescription().get(WfmcAuditedParameter.PROCESS_DEFINITION_ID),
                (String) auditInfo.getArgumentsByParameterDescription().get(WfmcAuditedParameter.PROCESS_INSTANCE_NAME),
                null, WfmcPreviousState.CREATE_PROCESS_INSTANCE, WMAEventCode.CREATED_PROCESS_INSTANCE_INT, username);

        Object returnValue = null;

        try {
            returnValue = joinPoint.proceed();
            log.info("Returned value from audited method: {}", returnValue);
        } catch (Throwable throwable) {
            log.warn("Could not proceed: ", throwable);
            // Call a service method that sets the error on the wmProcessInstanceAudit instance & saves the updated wmProcessInstanceAudit

            WMProcessInstanceAudit wmProcessInstanceAudit = wmEventAuditProcessInstance.getWmProcessInstanceAudit();
            wmErrorAudit = auditErrorUtil.saveErrorIntoEntityWmErrorAudit(throwable, wmProcessInstanceAudit, auditedMethodName);
            log.info("Finished saving details about the error");
        } finally {
            //singura valoare care se modifica este processInstanceId, care provine din returnValue
            if (returnValue != null) {
                WMProcessInstanceAudit wmProcessInstanceAudit = wmEventAuditProcessInstance.getWmProcessInstanceAudit();
                wmProcessInstanceAudit.setProcessInstanceId(returnValue.toString());
                wfmcAuditService.updateProcessInstance(wmProcessInstanceAudit);
            }
            log.info("Finished auditing : " + auditableType + ", using strategy : " + auditStrategy);
            return returnValue;
        }
    }

    private String getUserIdentification(AuditInfo auditInfo) {
        return (String) auditInfo.invokeMethodOnInstance("getUserIdentification");
    }
}
