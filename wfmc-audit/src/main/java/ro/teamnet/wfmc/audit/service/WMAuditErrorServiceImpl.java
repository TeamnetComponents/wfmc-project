package ro.teamnet.wfmc.audit.util;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import ro.teamnet.wfmc.audit.domain.WMErrorAudit;
import ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit;
import ro.teamnet.wfmc.audit.repository.ErrorAuditRepository;
import ro.teamnet.wfmc.audit.service.WMAuditErrorService;

import javax.inject.Inject;

/**
 * An utility class that returns the object saved into the WMErrorAudit entity
 */

// TODO turn into a @Service

@Service
public class WMAuditErrorServiceImpl implements WMAuditErrorService {

    @Inject
    private ErrorAuditRepository errorAuditRepository;

    public WMAuditErrorServiceImpl() {
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
        errorAudit.setOccurrenceTime(new DateTime());

        return errorAuditRepository.save(errorAudit);
    }

    public WMErrorAudit updateErrorEntityWmErrorAudit(WMProcessInstanceAudit wmProcessInstanceAudit) {
        WMErrorAudit errorAudit = new WMErrorAudit();
        errorAudit.setWmProcessInstanceAudit(wmProcessInstanceAudit);

        return errorAuditRepository.save(errorAudit);
    }
}
