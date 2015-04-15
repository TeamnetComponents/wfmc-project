package ro.teamnet.wfmc.audit.service;

import ro.teamnet.wfmc.audit.domain.*;

/**
 * Created by Ioan.Ivan on 3/26/2015.
 */
public interface WfmcAuditService {

    WMEventAuditWorkItem convertAndSaveReassignWorkItem(String processInstanceId, String workItemId, String sourceUser, String targetUser, String username, String processDefinitionId, String processBusinessName);
    //WMEventAuditWorkItem convertAndSaveReassignWorkItem(String workItemId, String sourceUser, String targetUser, String username, WMProcessInstanceAudit wmProcessInstanceAudit);
    WMEventAuditWorkItem convertAndSaveCompleteWorkItem(String processInstanceId, String workItemId, String username, String processDefinitionId);

    //WMEventAuditAttribute convertAndSaveAssignWorkItemAttribute(String processInstanceId, String workItemId, String attributeName, Object attributeValue, String username);

    WMProcessInstanceAudit saveProcessInstanceAudit(String procDefId, String procInstName, String processInstanceId);

    WMEventAuditProcessInstance saveEventAuditProcessInstance(WMProcessInstanceAudit wmProcessInstanceAudit, String previousState, int eventCode, String username);

    WMProcessInstanceAudit updateProcessInstance(WMProcessInstanceAudit wmProcessInstanceAudit);

    //WMEventAuditAttribute convertAndSaveAssignAttributeAudit(String attributeName, Object attributeValue, String username);

    WMEventAuditAttribute convertAndSaveAssignAttributeAudit(String attributeName, Object attributeValue, String username, WMProcessInstanceAudit wmProcessInstanceAudit);

    WMEventAuditProcessInstance convertAndSaveAbortProcessInstance(WMProcessInstanceAudit wmProcessInstanceAudit, String previousState, Integer eventCode,String username);
}