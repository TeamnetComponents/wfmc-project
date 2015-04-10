package ro.teamnet.wfmc.audit.build;

import org.joda.time.DateTime;
import org.wfmc.audit.WMAEventCode;
import org.wfmc.wapi.WMWorkItemState;
import ro.teamnet.wfmc.audit.domain.*;

public class AuditEntityBuilder {

    /**
     * Create an {@link WMProcessInstanceAudit} object by a {@link WMProcessInstanceAudit#processInstanceId}, a
     * {@link WMProcessInstanceAudit#processDefinitionId} and a {@link WMProcessInstanceAudit#processDefinitionBusinessName}
     *
     * @param processInstanceId
     * @param processDefinitionId
     * @param processDefinitionBusinessName
     */
    public WMProcessInstanceAudit createwmProcessInstanceAudit(String processInstanceId, String processDefinitionId, String processDefinitionBusinessName) {

        WMProcessInstanceAudit wmProcessInstanceAudit = new WMProcessInstanceAudit();
        wmProcessInstanceAudit.setProcessInstanceId(processInstanceId);
        wmProcessInstanceAudit.setProcessDefinitionId(processDefinitionId);
        wmProcessInstanceAudit.setProcessDefinitionBusinessName(processDefinitionBusinessName);

        return wmProcessInstanceAudit;
    }

    public WMEventAuditProcessInstance createwmEventAuditProcessInstance(WMProcessInstanceAudit wmProcessInstanceAudit, String previousState, Integer eventCode, String username) {

        WMEventAuditProcessInstance wmEventAuditProcessInstance = new WMEventAuditProcessInstance();
        wmEventAuditProcessInstance.setWmProcessInstanceAudit(wmProcessInstanceAudit);
        wmEventAuditProcessInstance.setPreviousState(previousState);
        wmEventAuditProcessInstance.setEventCode(eventCode);
        wmEventAuditProcessInstance.setUsername(username);
        wmEventAuditProcessInstance.setEventDate(new DateTime());

        return wmEventAuditProcessInstance;
    }

    public WMWorkItemAudit createwmWorkItemAudit(String workItemId, WMProcessInstanceAudit wmProcessInstanceAudit) {

        WMWorkItemAudit wmWorkItemAudit = new WMWorkItemAudit();
        wmWorkItemAudit.setWorkItemId(workItemId);
        wmWorkItemAudit.setWmProcessInstanceAudit(wmProcessInstanceAudit);

        return wmWorkItemAudit;

    }

    public WMEventAuditWorkItem createwmEventAuditWorkItemForComplete(String username, WMWorkItemAudit wmWorkItemAudit) {
        WMEventAuditWorkItem wmEventAuditWorkItem = new WMEventAuditWorkItem();
        wmEventAuditWorkItem.setUsername(username);
        wmEventAuditWorkItem.setEventCode(WMAEventCode.COMPLETED_WORK_ITEM.value());
        wmEventAuditWorkItem.setEventDate(new DateTime());
        wmEventAuditWorkItem.setWmWorkItemAudit(wmWorkItemAudit);

        return wmEventAuditWorkItem;
    }

    public WMEventAuditWorkItem createwmEventAuditWorkItemForReassign(String username, WMWorkItemAudit wmWorkItemAudit) {

        WMEventAuditWorkItem wmEventAuditWorkItem = new WMEventAuditWorkItem();
        wmEventAuditWorkItem.setWorkItemState(WMWorkItemState.OPEN_RUNNING_TAG);
        wmEventAuditWorkItem.setEventCode(WMAEventCode.REASSIGNED_WORK_ITEM.value());
        wmEventAuditWorkItem.setEventDate(new DateTime());
        wmEventAuditWorkItem.setUsername(username);
        wmEventAuditWorkItem.setWmWorkItemAudit(wmWorkItemAudit);

        return wmEventAuditWorkItem;
    }

    public WMAttributeAudit createwmAttributeAudit(String attributeName) {

        WMAttributeAudit wmAttributeAudit = new WMAttributeAudit();
        wmAttributeAudit.setAttributeName(attributeName);

        return wmAttributeAudit;
    }

    public WMEventAuditAttribute createwmEventAuditAttribute(Object attributeValue, String username, WMAttributeAudit wmAttributeAudit) {

        WMEventAuditAttribute wmEventAuditAttribute = new WMEventAuditAttribute();
        wmEventAuditAttribute.setEventDate(new DateTime());
        wmEventAuditAttribute.setEventCode(WMAEventCode.ASSIGNED_ACTIVITY_INSTANCE_ATTRIBUTES.value());
        wmEventAuditAttribute.setUsername(username);
        wmEventAuditAttribute.setAttributeValue(attributeValue.toString());
        wmEventAuditAttribute.setWmAttributeAudit(wmAttributeAudit);

        return wmEventAuditAttribute;
    }
}
