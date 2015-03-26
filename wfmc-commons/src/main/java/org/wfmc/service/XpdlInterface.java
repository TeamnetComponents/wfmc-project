package org.wfmc.service;

import org.wfmc.wapi.*;
import org.wfmc.xpdl.model.workflow.WorkflowProcess;

import java.util.List;

/**
 * Created by Mihai.Niculae on 3/12/2015.
 */
public interface XpdlInterface {

    public WorkflowProcess getWorkFlowProcess(String processDefinitionId) throws WMWorkflowException;


    public List<WMWorkItem> getNextSteps(String processInstanceId, String workItemId) throws WMWorkflowException;


    WMConnectInfo getWmConnectInfo();
}
