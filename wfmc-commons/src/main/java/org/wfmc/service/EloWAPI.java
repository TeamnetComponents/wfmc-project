package org.wfmc.service;

import org.wfmc.wapi.WMWorkItem;
import org.wfmc.wapi.WMWorkflowException;
import org.wfmc.wapi2.WAPI2;

import java.util.List;

/**
 * @author adrian.zamfirescu
 * @since 03/05/2015
 */
public interface EloWAPI extends WAPI2 {

    List<WMWorkItem> getNextSteps(Integer workflowId, Integer nodeId) throws WMWorkflowException;

    void setTransition(Integer workflowId, Integer currentNodeId, Integer transitionNodeId) throws WMWorkflowException;

}
