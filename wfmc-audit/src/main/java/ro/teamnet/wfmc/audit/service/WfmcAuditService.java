package ro.teamnet.wfmc.audit.service;

import ro.teamnet.wfmc.audit.domain.WMEventAuditAttribute;
import ro.teamnet.wfmc.audit.domain.WMEventAuditWorkItem;

/**
 * Created by Ioan.Ivan on 3/26/2015.
 */
public interface WfmcAuditService {

    public WMEventAuditWorkItem convertReassignWorkItem (String processInstanceId, String workItemId, String sourceUser, String targetUser, String username, String processDefinitionId);

    public WMEventAuditWorkItem convertCompleteWorkItem (String processInstanceId, String workItemId, String username, String processDefinitionId);

    public WMEventAuditAttribute convertAssignWorkItemAttribute (String processInstanceId, String workItemId, String attributeName, Object attributeValue, String username);
}