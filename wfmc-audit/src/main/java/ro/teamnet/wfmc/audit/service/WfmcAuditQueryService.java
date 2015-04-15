package ro.teamnet.wfmc.audit.service;

import ro.teamnet.wfmc.audit.domain.*;

public interface WfmcAuditQueryService {

    WMProcessInstanceAudit findByProcessInstanceId(String processInstanceId);

    WMProcessInstanceAudit findWMProcessInstanceAuditByProcessDefinitionBusinessName(String processDefinitionBusinessName);

    WMEventAudit findWMEventAuditByUsername(String username);

    WMErrorAudit findWMErrorAuditByWmProcessInstanceAudit(WMProcessInstanceAudit wmProcessInstanceAudit);

    WMEventAuditAttribute findWMEventAuditAttributeByAttributeValue(String attributeValue);

    WMAttributeAuditProcessInstance findWMAttributeAuditProcessInstanceByWMProcessInstanceAudit(WMProcessInstanceAudit wmProcessInstanceAudit);
}
