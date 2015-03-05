package org.wfmc.elo.utils;

import de.elo.ix.client.FindTasksInfo;
import org.wfmc.impl.base.filter.WMFilterWorkItem;
import org.wfmc.wapi.WMFilter;
import org.wfmc.wapi.WMInvalidFilterException;
import org.wfmc.wapi.WMParticipant;
import org.wfmc.wapi.WMProcessInstanceState;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

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
        findTasksInfo.setInclActivities(true);
        findTasksInfo.setInclReminders(true);
        findTasksInfo.setInclWorkflows(true);

        return  findTasksInfo;
    }
}
