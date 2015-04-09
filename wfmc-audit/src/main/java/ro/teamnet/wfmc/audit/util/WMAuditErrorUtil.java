package ro.teamnet.wfmc.audit.util;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import ro.teamnet.wfmc.audit.domain.WMErrorAudit;
import ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit;
import ro.teamnet.wfmc.audit.repository.ErrorAuditRepository;

import javax.inject.Inject;
import java.lang.reflect.Method;

/**
 * An utility class that returns the object saved into the WMErrorAudit entity
 */
@Component
public class WMAuditErrorUtil {

    @Inject
    private ErrorAuditRepository errorAuditRepository;

    public WMAuditErrorUtil() {
    }

    /**
     * Populate the WMErrorAudit fields and proceed with the save. It returns the object.
     *
     * @param throwable              object exception that is thrown
     * @param wmProcessInstanceAudit object returned by the audit service
     * @param auditedMethodName      the name of the method that throws the exception
     * @return the object saved into the WMErrorAudit
     */
    public WMErrorAudit saveErrorIntoEntityWmErrorAudit(Throwable throwable,
                                                        WMProcessInstanceAudit wmProcessInstanceAudit,
                                                        String auditedMethodName) {
        WMErrorAudit errorAudit = new WMErrorAudit();
        errorAudit.setDescription(throwable.toString());
        errorAudit.setMessage(throwable.getMessage());
        errorAudit.setStackTrace(ExceptionUtils.getStackTrace(throwable));
        errorAudit.setWmProcessInstanceAudit(wmProcessInstanceAudit);
        errorAudit.setAuditedOperation(auditedMethodName);

//        Timestamp timestamp = new Timestamp(DateTime.now().getMillis());
//        errorAudit.setOccurrenceTime(timestamp);
//        errorAudit.setWmProcessInstanceAudit(wmProcessInstanceAudit);

        WMErrorAudit savedError = errorAuditRepository.save(errorAudit);
        return savedError;
    }

    public WMErrorAudit updateErrorEntityWmErrorAudit(WMProcessInstanceAudit wmProcessInstanceAudit) {
        WMErrorAudit errorAudit = new WMErrorAudit();
        errorAudit.setWmProcessInstanceAudit(wmProcessInstanceAudit);

        return errorAuditRepository.save(errorAudit);
    }
}
