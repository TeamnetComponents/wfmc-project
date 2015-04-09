package ro.teamnet.wfmc.audit.build;

import org.joda.time.DateTime;
import org.wfmc.audit.WMAEventCode;
import org.wfmc.wapi.WMWorkItemState;
import ro.teamnet.wfmc.audit.domain.*;


//TODO refractoring method name

public class AuditEntityBuilder {

    public WMEventAuditProcessInstance createwmEventAuditProcessInstance(WMProcessInstanceAudit wmProcessInstanceAudit, String previousState, Integer eventCode, String username) {

        WMEventAuditProcessInstance wmEventAuditProcessInstance = new WMEventAuditProcessInstance();
        wmEventAuditProcessInstance.setWmProcessInstanceAudit(wmProcessInstanceAudit);
        wmEventAuditProcessInstance.setPreviousState(previousState);
        wmEventAuditProcessInstance.setEventCode(eventCode);
        wmEventAuditProcessInstance.setUsername(username);
        wmEventAuditProcessInstance.setCurrentDate(new DateTime());

        return wmEventAuditProcessInstance;
    }

    public WMProcessInstanceAudit createwmProcessInstanceAudit(String processInstanceId, String processDefinitionId) {

        WMProcessInstanceAudit wmProcessInstanceAudit = new WMProcessInstanceAudit();
        wmProcessInstanceAudit.setProcessInstanceId(processInstanceId);
        wmProcessInstanceAudit.setProcessDefinitionId(processDefinitionId);

        return wmProcessInstanceAudit;
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
        wmEventAuditWorkItem.setCurrentDate(new DateTime());
        wmEventAuditWorkItem.setWmWorkItemAudit(wmWorkItemAudit);

        return wmEventAuditWorkItem;
    }

    public WMEventAuditWorkItem createwmEventAuditWorkItem2(String username, WMWorkItemAudit wmWorkItemAudit) {

        WMEventAuditWorkItem wmEventAuditWorkItem = new WMEventAuditWorkItem();
        wmEventAuditWorkItem.setWorkItemState(WMWorkItemState.OPEN_RUNNING_TAG);
        wmEventAuditWorkItem.setEventCode(WMAEventCode.REASSIGNED_WORK_ITEM.value());
        wmEventAuditWorkItem.setCurrentDate(new DateTime());
        wmEventAuditWorkItem.setUsername(username);
        wmEventAuditWorkItem.setWmWorkItemAudit(wmWorkItemAudit);

        return wmEventAuditWorkItem;
    }

    public WMProcessInstanceAudit createwmProcessInstanceAudit2(String processInstanceId) {

        WMProcessInstanceAudit wmProcessInstanceAudit = new WMProcessInstanceAudit();
        wmProcessInstanceAudit.setProcessInstanceId(processInstanceId);

        return wmProcessInstanceAudit;
    }

    public WMProcessInstanceAudit createwmProcessInstanceAudit3(String processInstanceId, String processDefinitionId, String procInstName) {

        WMProcessInstanceAudit wmProcessInstanceAudit = new WMProcessInstanceAudit();
        wmProcessInstanceAudit.setProcessInstanceId(processInstanceId);
        wmProcessInstanceAudit.setProcessDefinitionId(processDefinitionId);
        wmProcessInstanceAudit.setProcessDefinitionBusinessName(procInstName);

        return wmProcessInstanceAudit;
    }

    public WMAttributeAudit createwmAttributeAudit(String attributeName) {

        WMAttributeAudit wmAttributeAudit = new WMAttributeAudit();
        wmAttributeAudit.setAttributeName(attributeName);

        return wmAttributeAudit;
    }

    public WMEventAuditAttribute createwmEventAuditAttribute(Object attributeValue, String username, WMAttributeAudit wmAttributeAudit) {

        WMEventAuditAttribute wmEventAuditAttribute = new WMEventAuditAttribute();
        wmEventAuditAttribute.setCurrentDate(new DateTime());
        wmEventAuditAttribute.setEventCode(WMAEventCode.ASSIGNED_ACTIVITY_INSTANCE_ATTRIBUTES.value());
        wmEventAuditAttribute.setUsername(username);
        wmEventAuditAttribute.setAttributeValue(attributeValue.toString());
        wmEventAuditAttribute.setWmAttributeAudit(wmAttributeAudit);

        return wmEventAuditAttribute;
    }

//    public WMEventAudit createwmEventAudit(Integer eventCode, String username, java.sql.Date currentDate, WMEventAuditProcessInstance wmEventAuditProcessInstance) {
//
//        WMEventAudit wmEventAudit = new WMEventAudit();
//        wmEventAudit.setEventCode(eventCode);
//        wmEventAudit.setUsername(username);
//        wmEventAudit.setCurrentDate(currentDate);
//
//        return wmEventAudit;
//    }

}
