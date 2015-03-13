package org.wfmc.service;

import org.wfmc.wapi.WMWorkflowException;
import org.wfmc.wapi2.WAPI2;

/**
 * @author adrian.zamfirescu
 * @since 03/05/2015
 */
public interface WAPIExtended extends WAPI2 {


    void setTransition(String processInstanceId, String currentWorkItemId, String[] nextWorkItemIds) throws WMWorkflowException;

}
