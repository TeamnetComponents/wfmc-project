package ro.teamnet.wfmc.audit.service;

import ro.teamnet.wfmc.audit.domain.*;

public interface WfmcAuditQueryService {

    WMProcessInstanceAudit findWMProcessInstanceAuditByProcessInstanceId(String processInstanceId);

    WMProcessInstanceAudit findWMProcessInstanceAuditByProcessDefinitionBusinessName(String processDefinitionBusinessName);

    WMEventAudit findWMEventAuditByUsername(String username);

    WMEventAudit findWMEventAuditByEventCode(int eventCode);

    WMErrorAudit findWMErrorAuditByWmProcessInstanceAudit(WMProcessInstanceAudit wmProcessInstanceAudit);

    WMEventAuditAttribute findWMEventAuditAttributeByAttributeValue(String attributeValue);

    WMAttributeAuditProcessInstance findWMAttributeAuditProcessInstanceByWMProcessInstanceAudit(WMProcessInstanceAudit wmProcessInstanceAudit);

    WMWorkItemAudit findWMWorkItemAuditByWorkItemId(String workItemId);

    WMEventAuditWorkItem findWMEventAuditWorkItemByWmWorkItemAudit(WMWorkItemAudit wmWorkItemAudit);

    WMAttributeAuditWorkItem findWMAttributeAuditWorkItemByWmWorkItemAudit(WMWorkItemAudit wmWorkItemAudit);

    WMEventAuditProcessInstance findWMEventAuditProcessInstanceByWmProcessInstanceAudit(WMProcessInstanceAudit wmProcessInstanceAudit);

}
