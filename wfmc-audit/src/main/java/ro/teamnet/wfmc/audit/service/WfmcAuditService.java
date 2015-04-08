package ro.teamnet.wfmc.audit.service;

import ro.teamnet.wfmc.audit.domain.WMEventAuditAttribute;
import ro.teamnet.wfmc.audit.domain.WMEventAuditProcessInstance;
import ro.teamnet.wfmc.audit.domain.WMEventAuditWorkItem;
import ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit;

/**
 * Created by Ioan.Ivan on 3/26/2015.
 */
public interface WfmcAuditService {

    WMEventAuditWorkItem convertAndSaveReassignWorkItem(String processInstanceId, String workItemId, String sourceUser, String targetUser, String username, String processDefinitionId);

    WMEventAuditWorkItem convertAndSaveCompleteWorkItem(String processInstanceId, String workItemId, String username, String processDefinitionId);

    WMEventAuditAttribute convertAndSaveAssignWorkItemAttribute(String processInstanceId, String workItemId, String attributeName, Object attributeValue, String username);

    WMEventAuditProcessInstance convertAndSaveCreateProcessInstance(String procDefId, String procInstName, String processInstanceId, String previousState, int eventCode, String username);
    
    WMProcessInstanceAudit updateProcessInstance(WMProcessInstanceAudit wmProcessInstanceAudit);
    
}
