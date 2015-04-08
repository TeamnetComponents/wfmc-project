package ro.teamnet.wfmc.audit.service;

import ro.teamnet.wfmc.audit.domain.WMErrorAudit;
import ro.teamnet.wfmc.audit.domain.WMEventAudit;
import ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit;

public interface WfmcAuditQueryService {

    WMProcessInstanceAudit findWMProcessInstanceAuditByProcessInstanceId(String processInstanceId);

    WMProcessInstanceAudit findWMProcessInstanceAuditById(Long id);

    WMProcessInstanceAudit findWMProcessInstanceAuditByProcessDefinitionBusinessName(String processDefinitionBusinessName);

    WMEventAudit findWMEventAuditByUsername(String username);
    
    WMErrorAudit findWMErrorAuditByWmProcessInstanceAudit (WMProcessInstanceAudit wmProcessInstanceAudit);
}
