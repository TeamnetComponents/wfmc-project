package ro.teamnet.wfmc.audit.service;

import ro.teamnet.wfmc.audit.domain.*;

public interface WfmcAuditQueryService {

    WMProcessInstanceAudit findByProcessInstanceId(String processInstanceId);

    WMProcessInstanceAudit findWMProcessInstanceAuditByProcessDefinitionBusinessName(String processDefinitionBusinessName);

    WMEventAudit findByUsername(String username);

    WMEventAudit findByEventCode(int eventCode);

    WMErrorAudit findWMErrorAuditByWmProcessInstanceAudit(WMProcessInstanceAudit wmProcessInstanceAudit);

    WMEventAuditAttribute findWMEventAuditAttributeByAttributeValue(String attributeValue);

    WMAttributeAuditProcessInstance findWMAttributeAuditProcessInstanceByWMProcessInstanceAudit(WMProcessInstanceAudit wmProcessInstanceAudit);

    WMWorkItemAudit findWMWorkItemAuditByWorkItemId(String workItemId);

    WMEventAuditWorkItem findWMEventAuditWorkItemByWorkItemState(String workItemState);

    WMEventAuditWorkItem findWMEventAuditWorkItemByWmWorkItemAudit(WMWorkItemAudit wmWorkItemAudit);

    WMEventAuditProcessInstance findWMEventAuditProcessInstanceByWmProcessInstanceAudit(WMProcessInstanceAudit wmProcessInstanceAudit);

    WMEventAuditProcessInstance findWMEventAuditProcessInstanceByPreviousState(String previousState);

}
