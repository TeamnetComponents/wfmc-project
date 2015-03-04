package org.wfmc.elo.utils;

import de.elo.ix.client.*;
import de.elo.utils.net.RemoteException;
import org.wfmc.elo.base.WMParticipantImpl;
import org.wfmc.elo.base.WMWorkItemImpl;
import org.wfmc.wapi.WMWorkItem;

import java.util.ArrayList;

/**
 * Created by Ioan.Ivan on 2/25/2015.
 */
public class UserTaskUtils {

    public static ArrayList<WMWorkItem> findUserTasksByUserOrGroupName(IXConnection eloConnection, String userOrGroupName) throws RemoteException {
        Integer workItemsNumber = 500;

        FindTasksInfo findTasksInfo = new FindTasksInfo();
        findTasksInfo.setInclActivities(true);
        findTasksInfo.setInclReminders(true);
        findTasksInfo.setInclWorkflows(true);
        findTasksInfo.setAllUsers(true);


        FindResult findResult = eloConnection.ix().findFirstTasks(findTasksInfo, workItemsNumber); //workItemsNumber = numarul de WorkItem-uri aduse de cautare.
        UserTask[] userTasks = findResult.getTasks();
        ArrayList<WMWorkItem> userTaskList = new ArrayList<WMWorkItem>() ;

        for (final UserTask userTask : userTasks) {
            if (userTask.getWfNode().getUserName().equals(userOrGroupName)){

                WMWorkItemImpl wmWorkItem = new WMWorkItemImpl();
                wmWorkItem.setName(userTask.getWfNode().getNodeName());
                wmWorkItem.setPriority(userTask.getWfNode().getPrio());
                wmWorkItem.setId(String.valueOf(userTask.getWfNode().getNodeId()));
                wmWorkItem.setProcessInstanceId(String.valueOf(userTask.getWfNode().getFlowId()));
                WMParticipantImpl user = new WMParticipantImpl();
                user.setName(userTask.getWfNode().getUserName());
                wmWorkItem.setParticipant(user);

                userTaskList.add(wmWorkItem);
            }
        }
        return userTaskList;
    }
}
