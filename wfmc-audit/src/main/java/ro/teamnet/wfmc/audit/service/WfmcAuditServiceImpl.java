package ro.teamnet.wfmc.audit.service;

import org.wfmc.audit.WMAEventCode;
import org.wfmc.wapi.WMWorkItemState;
import ro.teamnet.wfmc.audit.domain.*;

import java.util.Date;

/**
 * Created by Ioan.Ivan on 3/26/2015.
 */
public class WfmcAuditServiceImpl implements WfmcAuditService{

    public WMEventAuditWorkItem convertReassignWorkItem (String processInstanceId, String workItemId, String sourceUser, String targetUser, String username, String processDefinitionId) {

        WMProcessInstanceAudit wmProcessInstanceAudit = new WMProcessInstanceAudit();
        wmProcessInstanceAudit.setProcessInstanceId(processInstanceId);
        wmProcessInstanceAudit.setProcessDefinitionId(processDefinitionId);

        WMWorkItemAudit wmWorkItemAudit = new WMWorkItemAudit();
        wmWorkItemAudit.setWorkItemId(workItemId);
        wmWorkItemAudit.setWmProcessInstanceAudit(wmProcessInstanceAudit);

        WMEventAuditWorkItem wmEventAuditWorkItem = new WMEventAuditWorkItem();
        wmEventAuditWorkItem.setWorkItemState(WMWorkItemState.OPEN_RUNNING_TAG);
        wmEventAuditWorkItem.setEventCode(WMAEventCode.REASSIGNED_WORK_ITEM.value());
        wmEventAuditWorkItem.setCurrentDate(new Date());
        wmEventAuditWorkItem.setUsername(username);
        wmEventAuditWorkItem.setWmWorkItemAudit(wmWorkItemAudit);

        return wmEventAuditWorkItem;
    }

    public WMEventAuditWorkItem convertCompleteWorkItem (String processInstanceId, String workItemId, String username, String processDefinitionId) {

        WMProcessInstanceAudit wmProcessInstanceAudit = new WMProcessInstanceAudit();
        wmProcessInstanceAudit.setProcessInstanceId(processInstanceId);
        wmProcessInstanceAudit.setProcessDefinitionId(processDefinitionId);

        WMWorkItemAudit wmWorkItemAudit = new WMWorkItemAudit();
        wmWorkItemAudit.setWorkItemId(workItemId);
        wmWorkItemAudit.setWmProcessInstanceAudit(wmProcessInstanceAudit);

        WMEventAuditWorkItem wmEventAuditWorkItem = new WMEventAuditWorkItem();
        wmEventAuditWorkItem.setUsername(username);
        wmEventAuditWorkItem.setEventCode(WMAEventCode.COMPLETED_WORK_ITEM.value());
        wmEventAuditWorkItem.setCurrentDate(new Date());
        wmEventAuditWorkItem.setWmWorkItemAudit(wmWorkItemAudit);

        return wmEventAuditWorkItem;
    }

    public WMEventAuditAttribute convertAssignWorkItemAttribute (String processInstanceId, String workItemId, String attributeName, Object attributeValue, String username) {

        WMProcessInstanceAudit wmProcessInstanceAudit = new WMProcessInstanceAudit();
        wmProcessInstanceAudit.setProcessInstanceId(processInstanceId);

        WMAttributeAudit wmAttributeAudit = new WMAttributeAudit();
        wmAttributeAudit.setAttributeName(attributeName);

        WMEventAuditAttribute wmEventAuditAttribute = new WMEventAuditAttribute();
        wmEventAuditAttribute.setCurrentDate(new Date());
        wmEventAuditAttribute.setEventCode(WMAEventCode.ASSIGNED_ACTIVITY_INSTANCE_ATTRIBUTES.value());
        wmEventAuditAttribute.setUsername(username);
        wmEventAuditAttribute.setAttributeValue(attributeValue.toString());
        wmEventAuditAttribute.setWmAttributeAudit(wmAttributeAudit);

        return wmEventAuditAttribute;
    }
}
