package ro.teamnet.wfmc.service.mock;

import org.wfmc.service.WfmcServiceAbstract;
import org.wfmc.wapi.WMConnectInfo;
import org.wfmc.wapi.WMWorkflowException;

import java.io.IOException;
import java.util.Properties;

/**
 * Mock implementation of WfmcService.
 */
public class WfmcServiceMockImpl extends WfmcServiceAbstract {
    public static final String MOCK_INSTANCE = "mock instance";
    public static final String PROCESS_INSTANCE_ID = "processInstanceId";
    public static final String NEW_PROCESS_INSTANCE_ID = "newProcessInstanceId";
    private Properties context;

    @Override
    public String getServiceInstance() {
        return MOCK_INSTANCE;
    }

    @Override
    public void __initialize(Properties context) throws IOException {
        this.context = context;
    }

    @Override
    public Properties getContext() {
        return context;
    }

    @Override
    public String getName() {
        return null;
    }


    @Override
    public String createProcessInstance(String procDefId, String procInstName) throws WMWorkflowException {
        return PROCESS_INSTANCE_ID;
    }


    @Override
    public void assignProcessInstanceAttribute(String procInstId, String attrName, Object attrValue) throws WMWorkflowException {

    }


    @Override
    public String startProcess(String procInstId) throws WMWorkflowException {
        return NEW_PROCESS_INSTANCE_ID;
    }

    @Override
    public void abortProcessInstance(String procInstId) throws WMWorkflowException {

    }

    @Override
    public void completeWorkItem(String procInstId, String workItemId) throws WMWorkflowException {

    }

    @Override
    public void reassignWorkItem(String sourceUser, String targetUser, String procInstId, String workItemId) throws WMWorkflowException {

    }

    @Override
    public void assignWorkItemAttribute(String procInstId, String workItemId, String attrName, Object attrValue) throws WMWorkflowException {

    }

    @Override
    public void connect(WMConnectInfo connectInfo) throws WMWorkflowException {

    }

    @Override
    public void disconnect() throws WMWorkflowException {

    }
}
