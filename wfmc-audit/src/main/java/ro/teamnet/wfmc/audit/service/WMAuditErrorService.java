package ro.teamnet.wfmc.audit.service;

import ro.teamnet.wfmc.audit.domain.WMErrorAudit;
import ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit;

public interface WMAuditErrorService {



    WMErrorAudit saveErrorIntoEntityWmErrorAudit(Throwable throwable,
                                                 WMProcessInstanceAudit wmProcessInstanceAudit,
                                                 String auditedMethodName);
    WMErrorAudit updateErrorEntityWmErrorAudit(WMProcessInstanceAudit wmProcessInstanceAudit);
}
