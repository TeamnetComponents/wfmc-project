package ro.teamnet.wfmc.audit.service;

import ro.teamnet.wfmc.audit.domain.*;

/**
 * Created by Ioan.Ivan on 3/26/2015.
 */
public interface WfmcAuditService {

    WMEventAuditWorkItem convertAndSaveReassignWorkItem(WMProcessInstanceAudit wmProcessInstanceAudit, String processInstanceId, String workItemId, String sourceUser, String targetUser, String username);

    WMProcessInstanceAudit saveProcessInstanceAudit(String procDefId, String procInstName, String processInstanceId);

    WMEventAuditProcessInstance saveEventAuditProcessInstance(WMProcessInstanceAudit wmProcessInstanceAudit, int eventCode, String username);

    WMProcessInstanceAudit updateProcessInstance(WMProcessInstanceAudit wmProcessInstanceAudit);

    WMEventAuditAttribute convertAndSaveAssignAttributeAudit(String attributeName, Object attributeValue, String username, WMProcessInstanceAudit wmProcessInstanceAudit);

    WMEventAuditProcessInstance convertAndSaveAbortProcessInstance(WMProcessInstanceAudit wmProcessInstanceAudit, Integer eventCode, String username);

    WMWorkItemAudit savewmWorkItemAudit(String workItemId, WMProcessInstanceAudit wmProcessInstanceAudit);

    WMAttributeAuditWorkItem savewmAttributeAuditWorkItem(String attributeName, WMWorkItemAudit wmWorkItemAudit);

    WMEventAuditAttribute savewmEventAuditAttribute(Object attributeValue, String username, WMAttributeAuditWorkItem wmAttributeAuditWorkItem);

    WMEventAuditWorkItem savewmEventAuditWorkItem(String workItemState, WMWorkItemAudit wmWorkItemAudit);

}
