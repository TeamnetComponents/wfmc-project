package org.wfmc.service;

import org.wfmc.audit.WMAAssignProcessInstanceAttributeData;
import org.wfmc.audit.WMACreateProcessInstanceData;
import org.wfmc.audit.WMAEventCode;
import org.wfmc.service.utils.DatabaseAuditHelper;
import org.wfmc.wapi.WMAttribute;
import org.wfmc.wapi.WMProcessInstance;
import org.wfmc.wapi.WMProcessInstanceState;

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
}
