package ro.teamnet.wfmc.audit.service;

import org.wfmc.audit.WMACreateProcessInstanceData;
import org.wfmc.audit.WMAEventCode;
import org.wfmc.wapi.WMProcessInstanceState;

/**
 * Created by Ioan.Ivan on 3/26/2015.
 */
public class WfmcAuditServiceImpl implements WfmcAuditService{

    public WMACreateProcessInstanceData convertCreateProcessToWMACreateProcessInstanceData(String processDefinitionId, String processInstanceName, String tempProcessInstanceId, String username) {

        processDefinitionId = "processDefinition";
        processInstanceName = "processInstanceName";
        tempProcessInstanceId = "tempProcessInstance";
        username = "user";

        WMACreateProcessInstanceData wmaCreateProcessInstanceData = new WMACreateProcessInstanceData();
        wmaCreateProcessInstanceData.setProcessDefinitionBusinessName(processInstanceName);

        wmaCreateProcessInstanceData.setProcessDefinitionId(processDefinitionId);
        wmaCreateProcessInstanceData.setInitialProcessInstanceId(tempProcessInstanceId);
        wmaCreateProcessInstanceData.setCurrentProcessInstanceId(tempProcessInstanceId);
        wmaCreateProcessInstanceData.setEventCode(WMAEventCode.CREATED_PROCESS_INSTANCE);
        wmaCreateProcessInstanceData.setProcessState(WMProcessInstanceState.OPEN_NOTRUNNING_NOTSTARTED_TAG);
        wmaCreateProcessInstanceData.setUserId(username);
        return wmaCreateProcessInstanceData;
    }

    public WMACreateProcessInstanceData convertStartProcessToWMACreateProcessInstanceData(String tempProcessInstanceId, String activeProcessInstanceId, String processInstanceName, String processDefinitionId) {

        tempProcessInstanceId = "tempProcessInstance";
        activeProcessInstanceId = "activeProcessInstance";

        //Nu avem de unde sa le luam
        processDefinitionId = "processDefinitionId";
        processInstanceName = "processInstanceName";

        WMACreateProcessInstanceData wmaCreateProcessInstanceData = new WMACreateProcessInstanceData();
        wmaCreateProcessInstanceData.setProcessDefinitionBusinessName(processInstanceName);

        wmaCreateProcessInstanceData.setProcessDefinitionId(processDefinitionId);
        wmaCreateProcessInstanceData.setInitialProcessInstanceId(tempProcessInstanceId);
        wmaCreateProcessInstanceData.setCurrentProcessInstanceId(activeProcessInstanceId);
        wmaCreateProcessInstanceData.setEventCode(WMAEventCode.STARTED_PROCESS_INSTANCE);
        wmaCreateProcessInstanceData.setProcessState(WMProcessInstanceState.OPEN_RUNNING_TAG);

        return wmaCreateProcessInstanceData;
    }
}
