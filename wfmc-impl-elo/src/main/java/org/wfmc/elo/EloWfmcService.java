package org.wfmc.elo;

import de.elo.ix.client.IXConnFactory;
import de.elo.ix.client.IXConnection;
import de.elo.utils.net.RemoteException;
import org.wfmc.service.WfmcServiceImpl_Abstract;
import org.wfmc.wapi.*;
import org.wfmc.wapi2.WMEntity;
import org.wfmc.wapi2.WMEntityIterator;

import java.util.Properties;

/**
 * Created by Lucian.Dragomir on 2/10/2015.
 */
public class EloWfmcService extends WfmcServiceImpl_Abstract {

    private IXConnection eloConnection;

    @Override
    public boolean isWorkListHandlerProfileSupported() {
        return false;
    }

    @Override
    public boolean isProcessControlStatusProfileSupported() {
        return false;
    }

    @Override
    public boolean isProcessDefinitionProfileSupported() {
        return false;
    }

    @Override
    public boolean isProcessAdminProfileSupported() {
        return false;
    }

    @Override
    public boolean isActivityControlStatusProfileSupported() {
        return false;
    }

    @Override
    public boolean isActivityAdminProfileSupported() {
        return false;
    }

    @Override
    public boolean isEntityHandlerProfileSupported() {
        return false;
    }

    @Override
    public boolean isAuditRecordProfileSupported() {
        return false;
    }

    @Override
    public boolean isToolAgentProfileSupported() {
        return false;
    }

    @Override
    public WMEntity createEntity(WMEntity scopingEntity, String entityClass, String entityName) throws WMWorkflowException {
        return null;
    }

    @Override
    public WMEntityIterator listEntities(WMEntity scopingEntity, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return null;
    }

    @Override
    public void deleteEntity(WMEntity scopingEntity, String entityId) throws WMWorkflowException {

    }

    @Override
    public WMAttributeIterator listEntityAttributes(WMEntity scopingEntity, String entityId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return null;
    }

    @Override
    public WMAttribute getEntityAttributeValue(WMEntity scopingEntity, WMEntity entityHandle, String attributeName) throws WMWorkflowException {
        return null;
    }

    @Override
    public WMAttributeIterator listEntityAttributeValues(WMEntity scopingEntity, String entityHandle, String attributeName) throws WMWorkflowException {
        return null;
    }

    @Override
    public void assignEntityAttributeValue(WMEntity entityHandle, String attributeName, int attributeType, String attributeValue) throws WMWorkflowException {

    }

    @Override
    public void clearEntityAttributeList(WMEntity entityHandle, String attributeName) throws WMWorkflowException {

    }

    @Override
    public void addEntityAttributeValue(WMEntity entityHandle, String attributeName, int attributeType, String attributeValue) throws WMWorkflowException {

    }

    @Override
    public WMEntity openWorkflowDefinition(String name, String scope) throws WMWorkflowException {
        return null;
    }

    @Override
    public void closeWorkflowDefinition(WMEntity workflowDefinitionHandle) throws WMWorkflowException {

    }

    @Override
    public String createPackage() throws WMWorkflowException {
        return null;
    }

    @Override
    public void deleteProcessDefinition(String processDefinitionId) throws WMWorkflowException {

    }

    @Override
    public WMEntity openProcessDefinition(String procDefId) throws WMWorkflowException {
        return null;
    }

    @Override
    public void closeProcessDefinition(WMEntity procModelHandle) throws WMWorkflowException {

    }

    @Override
    public WMEntity addTransition(String procModelId, String sourceActDefId, String targetActDefId) throws WMWorkflowException {
        return null;
    }

    @Override
    public void addProcessDataAttribute(String procModelId, String procDataId, String attributeName, int attributeType, int attributeLength, String attributeValue) throws WMWorkflowException {

    }

    @Override
    public void removeProcessDataAttribute(String procModelId, String procDataId, String attributeName) throws WMWorkflowException {

    }

    @Override
    public void connect(WMConnectInfo connectInfo) throws WMWorkflowException {

        Properties connProps = IXConnFactory.createConnProps(connectInfo.getScope());
        Properties sessOpts = IXConnFactory.createSessionOptions("IX-Example", "1.0");
        IXConnFactory connFact = null;
        try {
            connFact = new IXConnFactory(connProps, sessOpts);
            eloConnection = connFact.create(connectInfo.getUserIdentification(),
                                            connectInfo.getPassword(),
                                            connectInfo.getEngineName(),
                                            connectInfo.getUserIdentification());
        } catch (RemoteException remoteException) {
            throw new WMWorkflowException(remoteException);
        }

    }

    @Override
    public void disconnect() throws WMWorkflowException {
        //EloConnectionManager.returnCoonection(this.eloConnection)
        if (eloConnection != null) {
            eloConnection.logout();
            eloConnection = null;
        }
    }

    @Override
    public WMProcessDefinitionIterator listProcessDefinitions(WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return null;
    }

    @Override
    public WMProcessDefinitionStateIterator listProcessDefinitionStates(String procDefId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return null;
    }

    @Override
    public void changeProcessDefinitionState(String procDefId, WMProcessDefinitionState newState) throws WMWorkflowException {

    }

    @Override
    public String createProcessInstance(String procDefId, String procInstName) throws WMWorkflowException {
        return null;
    }

    @Override
    public String startProcess(String procInstId) throws WMWorkflowException {
        return null;
    }

    @Override
    public void terminateProcessInstance(String procInstId) throws WMWorkflowException {

    }

    @Override
    public WMProcessInstanceStateIterator listProcessInstanceStates(String procInstId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return null;
    }

    @Override
    public void changeProcessInstanceState(String procInstId, WMProcessInstanceState newState) throws WMWorkflowException {

    }

    @Override
    public WMAttributeIterator listProcessInstanceAttributes(String procInstId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return null;
    }

    @Override
    public WMAttribute getProcessInstanceAttributeValue(String procInstId, String attrName) throws WMWorkflowException {
        return null;
    }

    @Override
    public void assignProcessInstanceAttribute(String procInstId, String attrName, Object attrValue) throws WMWorkflowException {

    }

    @Override
    public WMActivityInstanceStateIterator listActivityInstanceStates(String procInstId, String actInstId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return null;
    }

    @Override
    public void changeActivityInstanceState(String procInstId, String actInstId, WMActivityInstanceState newState) throws WMWorkflowException {

    }

    @Override
    public WMAttributeIterator listActivityInstanceAttributes(String procInstId, String actInstId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return null;
    }

    @Override
    public WMAttribute getActivityInstanceAttributeValue(String procInstId, String actInstId, String attrName) throws WMWorkflowException {
        return null;
    }

    @Override
    public void assignActivityInstanceAttribute(String procInstId, String actInstId, String attrName, Object attrValue) throws WMWorkflowException {

    }

    @Override
    public WMProcessInstanceIterator listProcessInstances(WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return null;
    }

    @Override
    public WMProcessInstance getProcessInstance(String procInstId) throws WMWorkflowException {
        return null;
    }

    @Override
    public WMActivityInstanceIterator listActivityInstances(WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return null;
    }

    @Override
    public WMActivityInstance getActivityInstance(String procInstId, String actInstId) throws WMWorkflowException {
        return null;
    }

    @Override
    public WMWorkItemIterator listWorkItems(WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return null;
    }

    @Override
    public WMWorkItem getWorkItem(String procInstId, String workItemId) throws WMWorkflowException {
        return null;
    }

    @Override
    public void completeWorkItem(String procInstId, String workItemId) throws WMWorkflowException {

    }

    @Override
    public WMWorkItemStateIterator listWorkItemStates(String procInstId, String workItemId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return null;
    }

    @Override
    public void changeWorkItemState(String procInstId, String workItemId, WMWorkItemState newState) throws WMWorkflowException {

    }

    @Override
    public void reassignWorkItem(String sourceUser, String targetUser, String procInstId, String workItemId) throws WMWorkflowException {

    }

    @Override
    public WMAttributeIterator listWorkItemAttributes(String procInstId, String workItemId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return null;
    }

    @Override
    public WMAttribute getWorkItemAttributeValue(String procInstId, String workItemId, String attrName) throws WMWorkflowException {
        return null;
    }

    @Override
    public void assignWorkItemAttribute(String procInstId, String workItemId, String attrName, Object attrValue) throws WMWorkflowException {

    }

    @Override
    public void changeProcessInstancesState(String procDefId, WMFilter filter, WMProcessInstanceState newState) throws WMWorkflowException {

    }

    @Override
    public void changeActivityInstancesState(String procDefId, String actDefId, WMFilter filter, WMActivityInstanceState newState) throws WMWorkflowException {

    }

    @Override
    public void terminateProcessInstances(String procDefId, WMFilter filter) throws WMWorkflowException {

    }

    @Override
    public void assignProcessInstancesAttribute(String procDefId, WMFilter filter, String attrName, Object attrValue) throws WMWorkflowException {

    }

    @Override
    public void assignActivityInstancesAttribute(String procDefId, String actDefId, WMFilter filter, String attrName, Object attrValue) throws WMWorkflowException {

    }

    @Override
    public void abortProcessInstances(String procDefId, WMFilter filter) throws WMWorkflowException {

    }

    @Override
    public void abortProcessInstance(String procInstId) throws WMWorkflowException {

    }

    @Override
    public void invokeApplication(int toolAgentHandle, String appName, String procInstId, String workItemId, Object[] parameters, int appMode) throws WMWorkflowException {

    }

    @Override
    public WMAttribute[] requestAppStatus(int toolAgentHandle, String procInstId, String workItemId, int[] status) throws WMWorkflowException {
        return new WMAttribute[0];
    }

    @Override
    public void terminateApp(int toolAgentHandle, String procInstId, String workItemId) throws WMWorkflowException {

    }

    public IXConnection getEloConnection() {
        return eloConnection;
    }

    public void setEloConnection(IXConnection eloConnection) {
        this.eloConnection = eloConnection;
    }

}
