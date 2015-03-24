package org.wfmc.service;

import org.wfmc.audit.*;
import org.wfmc.service.utils.DatabaseAuditHelper;
import org.wfmc.wapi.*;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * Created by andras on 3/17/2015.
 */
public class AuditWorkflowHandler {

    public void createProcessInstanceAudit(String procDefId, String procInstName, String tempInstId, DataSource dataSource,
        String username){
        WMACreateProcessInstanceData wmaCreateProcessInstanceData = new WMACreateProcessInstanceData();
        wmaCreateProcessInstanceData.setProcessDefinitionBusinessName(procInstName);

        wmaCreateProcessInstanceData.setProcessDefinitionId(procDefId);
        wmaCreateProcessInstanceData.setInitialProcessInstanceId(tempInstId);
        wmaCreateProcessInstanceData.setCurrentProcessInstanceId(tempInstId);
        wmaCreateProcessInstanceData.setEventCode(WMAEventCode.CREATED_PROCESS_INSTANCE);
        wmaCreateProcessInstanceData.setProcessState(WMProcessInstanceState.OPEN_NOTRUNNING_NOTSTARTED_TAG);
        wmaCreateProcessInstanceData.setUserId(username);
        DatabaseAuditHelper databaseAuditHelper = new DatabaseAuditHelper();
        databaseAuditHelper.insertCreateProcessInstanceAudit(dataSource, wmaCreateProcessInstanceData);
    }

    public void startProcessInstanceAudit (String initialProcessInstanceId, String currentProcessInstanceId, DataSource dataSource, String username, WMProcessInstance processInstance) {
        WMACreateProcessInstanceData wmaCreateProcessInstanceData = new WMACreateProcessInstanceData();
        wmaCreateProcessInstanceData.setProcessDefinitionBusinessName(processInstance.getName());

        wmaCreateProcessInstanceData.setProcessDefinitionId(processInstance.getProcessDefinitionId());
        wmaCreateProcessInstanceData.setInitialProcessInstanceId(initialProcessInstanceId);
        wmaCreateProcessInstanceData.setCurrentProcessInstanceId(currentProcessInstanceId);
        wmaCreateProcessInstanceData.setEventCode(WMAEventCode.STARTED_PROCESS_INSTANCE);
        wmaCreateProcessInstanceData.setProcessState(WMProcessInstanceState.OPEN_RUNNING_TAG);
        wmaCreateProcessInstanceData.setUserId(username);

        DatabaseAuditHelper databaseAuditHelper = new DatabaseAuditHelper();
        databaseAuditHelper.insertCreateProcessInstanceAudit(dataSource, wmaCreateProcessInstanceData);
        databaseAuditHelper.updateIdCreateProcessInstanceAudit(dataSource, initialProcessInstanceId, currentProcessInstanceId);
    }

    public void assignProcessInstanceAttributeAudit (String procInstId, String attrName, Object attrValue, DataSource dataSource, String username,
                                                     WMProcessInstance processInstance, Object previousProcessInstanceAttributeValue) {
        WMAAssignProcessInstanceAttributeData wmaAssignProcessInstanceAttributeData = new WMAAssignProcessInstanceAttributeData();
        wmaAssignProcessInstanceAttributeData.setAttributeName(attrName);
        wmaAssignProcessInstanceAttributeData.setAttributeType(WMAttribute.DEFAULT_TYPE);
        if ((attrValue.getClass().isArray())) {
            String values = Arrays.toString((String[]) attrValue);
            wmaAssignProcessInstanceAttributeData.setNewAttributeLength(values.length());
            wmaAssignProcessInstanceAttributeData.setNewAttributeValue(values);
        } else {
            wmaAssignProcessInstanceAttributeData.setNewAttributeLength(attrValue.toString().length());
            wmaAssignProcessInstanceAttributeData.setNewAttributeValue(attrValue.toString());
        }
        if (previousProcessInstanceAttributeValue == null) {
            wmaAssignProcessInstanceAttributeData.setPreviousAttributeValue(null);
            wmaAssignProcessInstanceAttributeData.setPreviousAttributeLength(0);
        } else if ((previousProcessInstanceAttributeValue.getClass().isArray())) {
            String values = Arrays.toString((String[]) previousProcessInstanceAttributeValue);
            wmaAssignProcessInstanceAttributeData.setPreviousAttributeLength(values.length());
            wmaAssignProcessInstanceAttributeData.setPreviousAttributeValue(values);
        } else {
            wmaAssignProcessInstanceAttributeData.setPreviousAttributeLength(previousProcessInstanceAttributeValue.toString().length());
            wmaAssignProcessInstanceAttributeData.setPreviousAttributeValue(previousProcessInstanceAttributeValue.toString());
        }

        wmaAssignProcessInstanceAttributeData.setProcessDefinitionId(processInstance.getProcessDefinitionId());
        wmaAssignProcessInstanceAttributeData.setCurrentProcessInstanceId(procInstId);
        wmaAssignProcessInstanceAttributeData.setInitialProcessInstanceId(procInstId);
        wmaAssignProcessInstanceAttributeData.setEventCode(WMAEventCode.ASSIGNED_PROCESS_INSTANCE_ATTRIBUTE);
        wmaAssignProcessInstanceAttributeData.setProcessState(WMProcessInstanceState.OPEN_NOTRUNNING_SUSPENDED_TAG);
        wmaAssignProcessInstanceAttributeData.setUserId(username);

        DatabaseAuditHelper databaseAuditHelper = new DatabaseAuditHelper();
        databaseAuditHelper.insertAssignProcessInstanceAttributeAudit(dataSource, wmaAssignProcessInstanceAttributeData);
    }

    public void abortProcessInstanceAudit (String processInstanceId, DataSource dataSource, String username, WMProcessInstance processInstance) {
        WMAChangeProcessInstanceStateData wmaChangeProcessInstanceStateData = new WMAChangeProcessInstanceStateData();
        wmaChangeProcessInstanceStateData.setPreviousProcessState(WMProcessInstanceState.OPEN_RUNNING_TAG);
        wmaChangeProcessInstanceStateData.setNewProcessState(WMProcessInstanceState.CLOSED_ABORTED_TAG);

        wmaChangeProcessInstanceStateData.setProcessDefinitionId(processInstance.getProcessDefinitionId());
        wmaChangeProcessInstanceStateData.setCurrentProcessInstanceId(null);
        wmaChangeProcessInstanceStateData.setInitialProcessInstanceId(processInstanceId);
        wmaChangeProcessInstanceStateData.setEventCode(WMAEventCode.ABORTED_PROCESS_INSTANCE);
        wmaChangeProcessInstanceStateData.setProcessState(WMProcessInstanceState.CLOSED_ABORTED_TAG);
        wmaChangeProcessInstanceStateData.setUserId(username);

        DatabaseAuditHelper databaseAuditHelper = new DatabaseAuditHelper();
        databaseAuditHelper.insertAbortProcessInstanceAudit(dataSource, wmaChangeProcessInstanceStateData);
    }

    public void assignWorkItemAttributeAudit (String procInstId, String workItemId, String attrName, Object attrValue, DataSource dataSource, String username, WMProcessInstance processInstance, Object previousProcessInstanceAttributeValue) {
        WMAAssignWorkItemAttributeData wmaAssignWorkItemAttributeData = new WMAAssignWorkItemAttributeData();
        wmaAssignWorkItemAttributeData.setActivityState(WMWorkItemState.OPEN_SUSPENDED_TAG);
        wmaAssignWorkItemAttributeData.setAttributeName(attrName);
        wmaAssignWorkItemAttributeData.setAttributeType(WMAttribute.DEFAULT_TYPE);
        if ((attrValue.getClass().isArray())) {
            String values = Arrays.toString((String[]) attrValue);
            wmaAssignWorkItemAttributeData.setNewAttributeLength(values.length());
            wmaAssignWorkItemAttributeData.setNewAttributeValue(values);
        } else {
            wmaAssignWorkItemAttributeData.setNewAttributeLength(attrValue.toString().length());
            wmaAssignWorkItemAttributeData.setNewAttributeValue(attrValue.toString());
        }
        if (previousProcessInstanceAttributeValue == null) {
            wmaAssignWorkItemAttributeData.setPreviousAttributeValue(null);
            wmaAssignWorkItemAttributeData.setPreviousAttributeLength(0);
        } else if ((previousProcessInstanceAttributeValue.getClass().isArray())) {
            String values = Arrays.toString((String[]) previousProcessInstanceAttributeValue);
            wmaAssignWorkItemAttributeData.setPreviousAttributeLength(values.length());
            wmaAssignWorkItemAttributeData.setPreviousAttributeValue(values);
        } else {
            wmaAssignWorkItemAttributeData.setPreviousAttributeLength(previousProcessInstanceAttributeValue.toString().length());
            wmaAssignWorkItemAttributeData.setPreviousAttributeValue(previousProcessInstanceAttributeValue.toString());
        }

        wmaAssignWorkItemAttributeData.setWorkItemId(workItemId);
        wmaAssignWorkItemAttributeData.setProcessDefinitionId(processInstance.getProcessDefinitionId());
        wmaAssignWorkItemAttributeData.setCurrentProcessInstanceId(procInstId);
        wmaAssignWorkItemAttributeData.setInitialProcessInstanceId(procInstId);
        wmaAssignWorkItemAttributeData.setEventCode(WMAEventCode.ASSIGNED_ACTIVITY_INSTANCE_ATTRIBUTES);
        wmaAssignWorkItemAttributeData.setProcessState(WMProcessInstanceState.OPEN_NOTRUNNING_SUSPENDED_TAG);
        wmaAssignWorkItemAttributeData.setUserId(username);

        DatabaseAuditHelper databaseAuditHelper = new DatabaseAuditHelper();
        databaseAuditHelper.insertAssignWorkItemAttributeAudit(dataSource, wmaAssignWorkItemAttributeData);
    }

    public void reassignWorkItemAudit(String sourceUser, String targetUser, String procInstId, String workItemId, DataSource dataSource, String username, WMProcessInstance processInstance) {
        WMAAssignWorkItemData wmaAssignWorkItemData = new WMAAssignWorkItemData();
        wmaAssignWorkItemData.setTargetDomainId(null);
        wmaAssignWorkItemData.setTargetNodeId(null);
        wmaAssignWorkItemData.setTargetUserId(targetUser);
        wmaAssignWorkItemData.setTargetRoleId(null);

        wmaAssignWorkItemData.setWorkItemState(WMWorkItemState.OPEN_RUNNING_TAG);
        wmaAssignWorkItemData.setPreviousWorkItemState(WMWorkItemState.OPEN_SUSPENDED_TAG);

        wmaAssignWorkItemData.setWorkItemId(workItemId);
        wmaAssignWorkItemData.setProcessDefinitionId(processInstance.getProcessDefinitionId());
        wmaAssignWorkItemData.setCurrentProcessInstanceId(procInstId);
        wmaAssignWorkItemData.setInitialProcessInstanceId(procInstId);
        wmaAssignWorkItemData.setEventCode(WMAEventCode.REASSIGNED_WORK_ITEM);
        wmaAssignWorkItemData.setProcessState(WMProcessInstanceState.OPEN_RUNNING_TAG);
        wmaAssignWorkItemData.setUserId(username);

        DatabaseAuditHelper databaseAuditHelper = new DatabaseAuditHelper();
        databaseAuditHelper.insertReassignWorkItemAudit(dataSource, wmaAssignWorkItemData);
    }

    public void completeWorkItemAudit(String procInstId, String workItemId, DataSource dataSource, String username, WMProcessInstance processInstance) {
        WMAChangeWorkItemStateData wmaChangeWorkItemStateData = new WMAAssignWorkItemData();
        wmaChangeWorkItemStateData.setWorkItemState(WMWorkItemState.OPEN_RUNNING_TAG);
        wmaChangeWorkItemStateData.setPreviousWorkItemState(WMWorkItemState.CLOSED_COMPLETED_TAG);

        wmaChangeWorkItemStateData.setWorkItemId(workItemId);
        wmaChangeWorkItemStateData.setProcessDefinitionId(processInstance.getProcessDefinitionId());
        wmaChangeWorkItemStateData.setCurrentProcessInstanceId(procInstId);
        wmaChangeWorkItemStateData.setInitialProcessInstanceId(procInstId);
        wmaChangeWorkItemStateData.setEventCode(WMAEventCode.COMPLETED_WORK_ITEM);
        wmaChangeWorkItemStateData.setProcessState(WMProcessInstanceState.OPEN_RUNNING_TAG);
        wmaChangeWorkItemStateData.setUserId(username);

        DatabaseAuditHelper databaseAuditHelper = new DatabaseAuditHelper();
        databaseAuditHelper.insertCompleteWorkItemAudit(dataSource, wmaChangeWorkItemStateData);
    }

}
