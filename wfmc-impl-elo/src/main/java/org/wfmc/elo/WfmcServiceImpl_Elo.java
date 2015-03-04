package org.wfmc.elo;

import de.elo.ix.client.IXConnection;
import org.wfmc.service.WfmcServiceImpl_Abstract;
import org.wfmc.wapi.WMProcessInstance;
import org.wfmc.wapi.WMWorkflowException;

/**
 * Created by Lucian.Dragomir on 2/28/2015.
 */
public class WfmcServiceImpl_Elo extends WfmcServiceImpl_Abstract {

    private IXConnection ixConnection;


    private void borrowIxConnection() {

    }

    private void releaseIXConnection() {
        ixConnection = null;

    }

    private IXConnection getIxConnection() {
        return ixConnection;
    }


    //ELO utility methods
    int getProcessMaskId(String procDefId) {
        return 0;
    }

    @Override
    public void assignProcessInstanceAttribute(String procInstId, String attrName, Object attrValue) throws WMWorkflowException {

        //detect process templateId of this instance
        WMProcessInstance wmProcessInstance=getWfmcServiceCache().getProcessInstance(procInstId);

        //detect mask associated to process template
        //String maskId = getEloMask(wmProcessInstance.getProcessDefinitionId());

        //test if attribute name exists in ELO mask associated to the process and if attrValue is according to the definition

        //if everything is ok then
        super.assignProcessInstanceAttribute(procInstId, attrName, attrValue);
    }

    @Override
    public String startProcess(String procInstId) throws WMWorkflowException {
        String processInstanceId = null;

        //detect process templateId of this instance

        //detect mask associated to process template


        //create process sord in elo and start a process on it
        processInstanceId = "sdfsdfsf"; //eloworkflowstart

        //cleanup process temporary data
        super.startProcess(procInstId);

        return processInstanceId;
    }
}
