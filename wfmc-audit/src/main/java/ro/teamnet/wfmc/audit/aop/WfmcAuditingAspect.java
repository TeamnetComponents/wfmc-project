package ro.teamnet.wfmc.audit.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import ro.teamnet.audit.annotation.Auditable;
import ro.teamnet.audit.aop.AbstractAuditingAspect;
import ro.teamnet.audit.constants.AuditStrategy;
import ro.teamnet.audit.util.AuditInfo;
import ro.teamnet.wfmc.audit.constants.WfmcAuditedMethod;
import ro.teamnet.wfmc.audit.domain.WMEventAuditProcessInstance;
import ro.teamnet.wfmc.audit.service.WfmcAuditService;
import ro.teamnet.wfmc.audit.util.WMAuditErrorUtil;

import javax.inject.Inject;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Aspect for auditing WfMC processes.
 */
@Aspect
public class WfmcAuditingAspect extends AbstractAuditingAspect {

    @Inject
    private MyService myService;
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

        if (!auditableType.equals(WfmcAuditedMethod.CREATE_PROCESS_INSTANCE)) {
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                log.warn("Could not proceed: ", throwable);
                return null;
            }
        }



        WMEventAuditProcessInstance wmEventAuditProcessInstance = myService.saveMethodInfoBeforeCalling(auditInfo);
        Object returnValue = null;
        try {
            returnValue = joinPoint.proceed();
            log.info("Returned value from audited method: {}", returnValue);
        } catch (Throwable throwable) {
            log.warn("Could not proceed: ", throwable);
            myService.saveError(auditInfo, wmEventAuditProcessInstance, throwable);
            log.info("Finished saving details about the error");
        } finally {
            if (returnValue != null) {

                myService.updateMethodInfo(wmEventAuditProcessInstance, returnValue);
            }
            log.info("Finished auditing : " + auditableType + ", using strategy : " + auditStrategy);
            return returnValue;
        }



//        CreateProcessInstanceAuditingStrategy methodAuditingStrategy = new CreateProcessInstanceAuditingStrategy();
//        methodAuditingStrategy.setAuditInfo(auditInfo);
//        methodAuditingStrategy.saveMethodInfoBeforeCalling();
//        Object returnValue = null;
//        try {
//            returnValue = joinPoint.proceed();
//            log.info("Returned value from audited method: {}", returnValue);
//        } catch (Throwable throwable) {
//            log.warn("Could not proceed: ", throwable);
//            methodAuditingStrategy.saveError(throwable);
//            log.info("Finished saving details about the error");
//        } finally {
//            if (returnValue != null) {
//                methodAuditingStrategy.updateMethodInfo(returnValue);
//            }
//            log.info("Finished auditing : " + auditableType + ", using strategy : " + auditStrategy);
//            return returnValue;
//        }
    }
}
