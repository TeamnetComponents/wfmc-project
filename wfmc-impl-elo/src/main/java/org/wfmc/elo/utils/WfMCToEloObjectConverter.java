package org.wfmc.elo.utils;

import de.elo.ix.client.FindTasksInfo;
import de.elo.ix.client.FindWorkflowInfo;
import de.elo.ix.client.WFTypeC;
import org.wfmc.impl.base.filter.WMFilterProcessInstance;
import org.wfmc.impl.base.filter.WMFilterWorkItem;
import org.wfmc.wapi.WMParticipant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andras on 3/4/2015.
 */
public class WfMCToEloObjectConverter {

    public WfMCToEloObjectConverter(){

    }

    public  FindTasksInfo convertWMFilterWorkItemToFindTasksInfo(WMFilterWorkItem wmFilterWorkItem){
        FindTasksInfo findTasksInfo = new FindTasksInfo();

        if (wmFilterWorkItem.getWorkItemName() != null){
            findTasksInfo.setTaskName(wmFilterWorkItem.getWorkItemName());
        }
        if (wmFilterWorkItem.getWmParticipantList() != null && wmFilterWorkItem.getWmParticipantList().size() > 0){
            List<String> eloUserIds = new ArrayList<>();
            for (WMParticipant wmParticipant: wmFilterWorkItem.getWmParticipantList()){
                eloUserIds.add(wmParticipant.getName());
            }
            String[] eloUserIdsArray = new String[eloUserIds.size()];
            eloUserIds.toArray(eloUserIdsArray);
            findTasksInfo.setUserIds(eloUserIdsArray);
        } else {
            findTasksInfo.setAllUsers(true);
        }
        findTasksInfo.setInclWorkflows(true);
        if (wmFilterWorkItem.getProcessInstanceId() != null) {
            //TODO:
        }

        return  findTasksInfo;
    }

    public FindWorkflowInfo convertWMFilterProcessInstanceToFindWorkflowInfo(WMFilterProcessInstance wmFilterProcessInstance){
        FindWorkflowInfo findWorkflowInfo = new FindWorkflowInfo();

        if (wmFilterProcessInstance.getProcessInstanceName() != null){
            findWorkflowInfo.setName(wmFilterProcessInstance.getProcessInstanceName());
        }
        if (wmFilterProcessInstance.getWmParticipantList() != null && wmFilterProcessInstance.getWmParticipantList().size() > 0){
            List<String> eloUserIds = new ArrayList<>();
            for (WMParticipant wmParticipant: wmFilterProcessInstance.getWmParticipantList()){
                eloUserIds.add(wmParticipant.getName());
            }
            String[] eloUserIdsArray = new String[eloUserIds.size()];
            eloUserIds.toArray(eloUserIdsArray);
            findWorkflowInfo.setUserIds(eloUserIdsArray);
        }

        if (wmFilterProcessInstance.getProcessInstanceId() != null) {
           findWorkflowInfo.setTemplateId(wmFilterProcessInstance.getProcessInstanceId());
        }

        //todo: cum facem cu tipul de proces? cum il trimitem in filtru?  - ma refer la findWorkflowInfo.setType(WFTypeC.ACTIVE);etc
         findWorkflowInfo.setType(WFTypeC.FINISHED);
        return  findWorkflowInfo;
    }
}
