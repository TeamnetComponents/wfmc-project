package ro.teamnet.wfmc.service;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.wfmc.service.WfmcService;
import org.wfmc.wapi.WMConnectInfo;
import org.wfmc.wapi.WMWorkflowException;

import javax.inject.Inject;

/**
 * A facade for providing audited methods of the WfmcService.
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WfmcAuditedServiceImpl implements WfmcAuditedService {
    @Inject
    private WfmcService wfmcService;

    private WMConnectInfo wmConnectInfo;

    @Override
    public void connect(WMConnectInfo connectInfo) throws WMWorkflowException {
        wmConnectInfo = connectInfo;
        wfmcService.connect(wmConnectInfo);
    }

    @Override
    public void disconnect() throws WMWorkflowException {
        wfmcService.disconnect();
    }

    @Override
    public String createProcessInstance(String procDefId, String procInstName) throws WMWorkflowException {
        return wfmcService.createProcessInstance(procDefId, procInstName);
    }

    @Override
    public void assignProcessInstanceAttribute(String procInstId, String attrName, Object attrValue) throws WMWorkflowException {
        wfmcService.assignProcessInstanceAttribute(procInstId, attrName, attrValue);
    }

    @Override
    public String startProcess(String procInstId) throws WMWorkflowException {
        return wfmcService.startProcess(procInstId);
    }

    @Override
    public void abortProcessInstance(String procInstId) throws WMWorkflowException {
        wfmcService.abortProcessInstance(procInstId);
    }

    @Override
    public void reassignWorkItem(String sourceUser, String targetUser, String procInstId, String workItemId) throws WMWorkflowException {
        wfmcService.reassignWorkItem(sourceUser, targetUser, procInstId, workItemId);
    }

    @Override
    public void assignWorkItemAttribute(String procInstId, String workItemId, String attrName, Object attrValue) throws WMWorkflowException {
        wfmcService.assignWorkItemAttribute(procInstId, workItemId, attrName, attrValue);
    }

    @Override
    public void completeWorkItem(String procInstId, String workItemId) throws WMWorkflowException {
        wfmcService.completeWorkItem(procInstId, workItemId);
    }
}
