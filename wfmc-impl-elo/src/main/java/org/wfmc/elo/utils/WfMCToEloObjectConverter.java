package org.wfmc.elo.utils;

import de.elo.ix.client.FindTasksInfo;
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
        }
        findTasksInfo.setInclWorkflows(true);

        return  findTasksInfo;
    }
}
