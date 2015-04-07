package ro.teamnet.audit.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import ro.teamnet.wfmc.audit.domain.WMErrorAudit;
import org.apache.commons.lang.exception.ExceptionUtils;
import ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit;
import ro.teamnet.wfmc.audit.repository.ErrorAuditRepository;

import javax.inject.Inject;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * An util class that returns the object saved into the WMErrorAudit entity
 */
public class AuditError {

    @Inject
    private ErrorAuditRepository errorAuditRepository;

    public AuditError(){}

    /**
     * Populate the WMErrorAudit fields and proceed with the save. It returns the object.
     *
     * @param throwable object exception that is thrown
     * @param wmProcessInstanceAudit object returned by the audit service
     * @param proceedingJoinPoint to get the name of the method that throws the exception
     * @return the object saved into the WMErrorAudit
     */
    public WMErrorAudit saveErrorIntoEntityWmErrorAudit(Throwable throwable,
                                                        WMProcessInstanceAudit wmProcessInstanceAudit,
                                                        ProceedingJoinPoint proceedingJoinPoint) {
        WMErrorAudit errorAudit = new WMErrorAudit();
        errorAudit.setDescription(throwable.toString());
        errorAudit.setMessage(throwable.getMessage());
        errorAudit.setStackTrace(ExceptionUtils.getStackTrace(throwable));

        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method auditedMethod = methodSignature.getMethod();
        errorAudit.setAuditedOperation(auditedMethod.getName());

        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        errorAudit.setOccurrenceTime(timestamp);
        errorAudit.setWmProcessInstanceAudit(wmProcessInstanceAudit);

        return errorAuditRepository.save(errorAudit);
    }
}
