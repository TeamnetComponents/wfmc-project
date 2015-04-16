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

    /**
     * Create a new {@link WMProcessInstanceAudit} object by another {@link WMProcessInstanceAudit} built on call <code>createProcessInstance()</code>
     *
     * @param wmProcessInstanceAudit
     * @return
     */
    public WMProcessInstanceAudit createwmProcessInstanceAudit(WMProcessInstanceAudit wmProcessInstanceAudit) {

        WMProcessInstanceAudit newwmProcessInstanceAudit = new WMProcessInstanceAudit();
        newwmProcessInstanceAudit.setProcessInstanceId(wmProcessInstanceAudit.getProcessInstanceId());
        newwmProcessInstanceAudit.setProcessDefinitionId(wmProcessInstanceAudit.getProcessDefinitionId());
        newwmProcessInstanceAudit.setProcessDefinitionBusinessName(wmProcessInstanceAudit.getProcessDefinitionBusinessName());

        return newwmProcessInstanceAudit;
    }

    /**
     * Create an {@link WMEventAuditProcessInstance} used for {@link ro.teamnet.wfmc.audit.strategy.AbortProcessInstanceAuditingStrategy} to save audit into database
     * @param wmProcessInstanceAudit
     * @param eventCode
     * @param username
     * @return the instance for further operations
     */
    public WMEventAuditProcessInstance createwmEventAuditProcessInstance(WMProcessInstanceAudit wmProcessInstanceAudit, Integer eventCode, String username) {

        WMEventAuditProcessInstance wmEventAuditProcessInstance = new WMEventAuditProcessInstance();
        wmEventAuditProcessInstance.setWmProcessInstanceAudit(wmProcessInstanceAudit);
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

    public WMEventAuditWorkItem createwmEventAuditWorkItem(String username, WMWorkItemAudit wmWorkItemAudit) {
        WMEventAuditWorkItem wmEventAuditWorkItem = new WMEventAuditWorkItem();
        wmEventAuditWorkItem.setUsername(username);
        wmEventAuditWorkItem.setEventCode(WMAEventCode.COMPLETED_WORK_ITEM.value());
        wmEventAuditWorkItem.setEventDate(new DateTime());
        wmEventAuditWorkItem.setWmWorkItemAudit(wmWorkItemAudit);
        wmEventAuditWorkItem.setWorkItemState(WMWorkItemState.OPEN_RUNNING_TAG);

        return wmEventAuditWorkItem;
    }


    /**
     * Create an {@link WMEventAuditWorkItem} object by a {@link WMEventAuditWorkItem#wmWorkItemAudit} and a
     * {@link WMEventAuditWorkItem#workItemState}
     * @param username
     * @param wmWorkItemAudit
     * @return an instance for further operations.
     */
    public WMEventAuditWorkItem createwmEventAuditWorkItemForReassign(String username, WMWorkItemAudit wmWorkItemAudit) {

        WMEventAuditWorkItem wmEventAuditWorkItem = new WMEventAuditWorkItem();
        wmEventAuditWorkItem.setWorkItemState(WMWorkItemState.OPEN_RUNNING_TAG);
        wmEventAuditWorkItem.setEventCode(WMAEventCode.REASSIGNED_WORK_ITEM.value());
        wmEventAuditWorkItem.setEventDate(new DateTime());
        wmEventAuditWorkItem.setUsername(username);
        wmEventAuditWorkItem.setWmWorkItemAudit(wmWorkItemAudit);

        return wmEventAuditWorkItem;
    }

    public WMAttributeAuditWorkItem createwmAttributeAudit(String attributeName, WMWorkItemAudit wmWorkItemAudit) {

        WMAttributeAuditWorkItem wmAttributeAuditWorkItem = new WMAttributeAuditWorkItem();
        wmAttributeAuditWorkItem.setWmWorkItemAudit(wmWorkItemAudit);
        wmAttributeAuditWorkItem.setAttributeName(attributeName);

        return wmAttributeAuditWorkItem;
    }

    /**
     * Create an {@link WMAttributeAuditProcessInstance} object by a {@link WMAttributeAuditProcessInstance#attributeName} and
     * a {@link WMProcessInstanceAudit} object
     * @param attributeName
     * @param wmProcessInstanceAudit
     * @return an instance of the object for further operations
     */
    public WMAttributeAuditProcessInstance createwmAttributeAudit(String attributeName, WMProcessInstanceAudit wmProcessInstanceAudit) {

        WMAttributeAuditProcessInstance wmAttributeAuditProcessInstance = new WMAttributeAuditProcessInstance();
        wmAttributeAuditProcessInstance.setWmProcessInstanceAudit(wmProcessInstanceAudit);
        wmAttributeAuditProcessInstance.setAttributeName(attributeName);

        return wmAttributeAuditProcessInstance;
    }

    /**
     * Create an {@link WMEventAuditAttribute} object with an {@link WMEventAuditAttribute#attributeValue},
     * {@link WMAttributeAudit} object and a {@link WMEventAudit} object populated with {@link WMEventAudit#username},
     * {@link WMEventAudit#eventCode} and {@link WMEventAudit#eventDate}
     * @param attributeValue
     * @param username
     * @param wmAttributeAuditProcessInstance
     * @return an instance for further operations
     */
    public WMEventAuditAttribute createwmEventAuditAttribute(Object attributeValue, String username, WMAttributeAuditProcessInstance wmAttributeAuditProcessInstance) {

        WMEventAuditAttribute wmEventAuditAttribute = new WMEventAuditAttribute();
        wmEventAuditAttribute.setEventDate(new DateTime());
        wmEventAuditAttribute.setEventCode(WMAEventCode.ASSIGNED_ACTIVITY_INSTANCE_ATTRIBUTES.value());
        wmEventAuditAttribute.setUsername(username);
        wmEventAuditAttribute.setAttributeValue(attributeValue.toString());
        wmEventAuditAttribute.setWmAttributeAudit(wmAttributeAuditProcessInstance);

        return wmEventAuditAttribute;
    }

    /**
     * Save an {@link WMEventAuditAttribute} object with an {@link WMEventAuditAttribute#attributeValue},
     * {@link WMAttributeAudit} object and a {@link WMEventAudit} object populated with {@link WMEventAudit#username},
     * {@link WMEventAudit#eventCode} and {@link WMEventAudit#eventDate}
     * @param attributeValue
     * @param username
     * @param wmAttributeAuditWorkItem
     * @return an instance for further operations
     */
    public WMEventAuditAttribute createwmEventAuditAttribute(Object attributeValue, String username, WMAttributeAuditWorkItem wmAttributeAuditWorkItem) {

        WMEventAuditAttribute wmEventAuditAttribute = new WMEventAuditAttribute();
        wmEventAuditAttribute.setEventDate(new DateTime());
        wmEventAuditAttribute.setEventCode(WMAEventCode.ASSIGNED_ACTIVITY_INSTANCE_ATTRIBUTES.value());
        wmEventAuditAttribute.setUsername(username);
        wmEventAuditAttribute.setAttributeValue(attributeValue.toString());
        wmEventAuditAttribute.setWmAttributeAudit(wmAttributeAuditWorkItem);

        return wmEventAuditAttribute;
    }
}
