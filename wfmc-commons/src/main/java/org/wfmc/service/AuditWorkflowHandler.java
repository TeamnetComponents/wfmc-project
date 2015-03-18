package org.wfmc.service;

import org.wfmc.audit.WMACreateProcessInstanceData;
import org.wfmc.audit.WMAEventCode;
import org.wfmc.service.utils.DatabaseAuditHelper;
import org.wfmc.wapi.WMProcessInstanceState;

import javax.sql.DataSource;

/**
 * Created by andras on 3/17/2015.
 */
public class AuditWorkflowHandler {

    public void createProcessInstanceAudit(String procDefId, String procInstName, String tempInstId, DataSource dataSource){
        WMACreateProcessInstanceData wmaCreateProcessInstanceData = new WMACreateProcessInstanceData();
        wmaCreateProcessInstanceData.setProcessDefinitionBusinessName(procInstName);

        wmaCreateProcessInstanceData.setProcessDefinitionId(procDefId);
        wmaCreateProcessInstanceData.setInitialProcessInstanceId(tempInstId);
        wmaCreateProcessInstanceData.setEventCode(WMAEventCode.CREATED_PROCESS_INSTANCE);
        wmaCreateProcessInstanceData.setProcessState(WMProcessInstanceState.OPEN_NOTRUNNING_NOTSTARTED_TAG);

//        WfmcServiceCacheImpl_Memory wfmcServiceCacheImpl_memory = new WfmcServiceCacheImpl_Memory();
//        String userName = wfmcServiceCacheImpl_memory.getUserName(sessionId);
//        wmaCreateProcessInstanceData.setUserId(userName);

        DatabaseAuditHelper databaseAuditHelper = new DatabaseAuditHelper();
        databaseAuditHelper.insertCreateProcessInstanceAudit(dataSource, wmaCreateProcessInstanceData);
    }
}
