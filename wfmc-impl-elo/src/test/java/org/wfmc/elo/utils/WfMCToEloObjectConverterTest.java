package org.wfmc.elo.utils;

import de.elo.ix.client.FindTasksInfo;
import de.elo.ix.client.FindWorkflowInfo;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wfmc.impl.base.filter.WMFilterBuilder;
import org.wfmc.impl.base.filter.WMFilterProcessInstance;
import org.wfmc.impl.base.filter.WMFilterWorkItem;
import org.wfmc.wapi.WMFilter;

/**
 * Created by andras on 3/5/2015.
 */
public class WfMCToEloObjectConverterTest {

    WfMCToEloObjectConverter wfMCToEloObjectConverter;

    @Before
    public void setUp(){
        wfMCToEloObjectConverter = new WfMCToEloObjectConverter();
    }

    @Test
    public void test_convertWMFilterWorkItemToFindTasksInfo_with_participants(){
        WMFilterWorkItem wmFilterWorkItem = WMFilterBuilder.createWMFilterWorkItem().addWorkItemParticipant("firstParticipant")
                .addWorkItemParticipant("secondParticipant");
        FindTasksInfo findTasksInfo =  wfMCToEloObjectConverter.convertWMFilterWorkItemToFindTasksInfo(wmFilterWorkItem);

        Assert.assertEquals(findTasksInfo.getUserIds().length,2);
    }

    @Test
    public void test_convertWMFilterWorkItemToFindTasksInfo_with_name(){
        String taskName = "tasculetz";
        WMFilterWorkItem wmFilterWorkItem = WMFilterBuilder.createWMFilterWorkItem().addWorkItemName(taskName);
        FindTasksInfo findTasksInfo =  wfMCToEloObjectConverter.convertWMFilterWorkItemToFindTasksInfo(wmFilterWorkItem);

        Assert.assertEquals(findTasksInfo.getTaskName(),taskName);
    }

    @Test
    public void test_convertWMFilterProcessInstanceToFindWorkflowInfo_with_participants(){
        WMFilterProcessInstance wmFilterProcessInstance = WMFilterBuilder.createWMFilterProcessInstance().addProcessInstanceParticipant("firstParticipant")
                .addProcessInstanceParticipant("secondParticipant");
        FindWorkflowInfo findWorkflowInfo =  wfMCToEloObjectConverter.convertWMFilterProcessInstanceToFindWorkflowInfo(wmFilterProcessInstance);

        Assert.assertEquals(findWorkflowInfo.getUserIds().length,2);
    }

    @Test
    public void test_convertWMFilterProcessInstanceToFindWorkflowInfo_with_name(){
        String processInstanceName = "procesel";
        WMFilterProcessInstance wmFilterProcessInstance = WMFilterBuilder.createWMFilterProcessInstance().addProcessInstanceName(processInstanceName);
        FindWorkflowInfo findWorkflowInfo =  wfMCToEloObjectConverter.convertWMFilterProcessInstanceToFindWorkflowInfo(wmFilterProcessInstance);
        Assert.assertEquals(findWorkflowInfo.getName(),processInstanceName);
    }

}
