package org.wfmc.service;

import org.wfmc.audit.WMACreateProcessInstanceData;
import org.wfmc.audit.WMAEventCode;
import org.wfmc.service.utils.DatabaseAuditHelper;
import org.wfmc.wapi.WMProcessInstance;
import org.wfmc.wapi.WMProcessInstanceState;

import javax.sql.DataSource;

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
}
