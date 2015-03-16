package org.wfmc.service;

import org.wfmc.audit.WMACreateProcessInstanceData;
import org.wfmc.wapi.*;
import org.wfmc.wapi2.WMEntity;
import org.wfmc.wapi2.WMEntityIterator;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Created by Lucian.Dragomir on 3/16/2015.
 */
public class WfmcServiceAudit_Impl extends WfmcServiceImpl_Abstract {
    private WfmcService internalService;


    @Override
    public WfmcServiceCache getWfmcServiceCache() {
        return super.getWfmcServiceCache();
    }

    @Override
    protected void setWfmcServiceCache(WfmcServiceCache wfmcServiceCache) {
        super.setWfmcServiceCache(wfmcServiceCache);
    }

    @Override
    public void __initialize(Properties context) throws IOException {
        super.__initialize(context);
    }

    @Override
    public Properties getContext() {
        return super.getContext();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getServiceInstance() {
        return super.getServiceInstance();
    }

    @Override
    public void connect(WMConnectInfo connectInfo) throws WMWorkflowException {
        super.connect(connectInfo);
    }

    @Override
    public void disconnect() throws WMWorkflowException {
        super.disconnect();
    }

    @Override
    public String createProcessInstance(String procDefId, String procInstName) throws WMWorkflowException {

        String status= "OK";
        try {
            return internalService.createProcessInstance(procDefId, procInstName);
        }
        catch(Exception ex)
        {status = ex.getMessage();

        }
        finally {
            //log
            WMACreateProcessInstanceData wmaCreateProcessInstanceData = new WMACreateProcessInstanceData();
            wmaCreateProcessInstanceData.

        }
        //log after (including error catching )
    }

    @Override
    public void assignProcessInstanceAttribute(String procInstId, String attrName, Object attrValue) throws WMWorkflowException {
        super.assignProcessInstanceAttribute(procInstId, attrName, attrValue);
    }

    @Override
    public String startProcess(String procInstId) throws WMWorkflowException {
        return super.startProcess(procInstId);
    }

    @Override
    public boolean isWorkListHandlerProfileSupported() {
        return super.isWorkListHandlerProfileSupported();
    }

    @Override
    public boolean isProcessControlStatusProfileSupported() {
        return super.isProcessControlStatusProfileSupported();
    }

    @Override
    public boolean isProcessDefinitionProfileSupported() {
        return super.isProcessDefinitionProfileSupported();
    }

    @Override
    public boolean isProcessAdminProfileSupported() {
        return super.isProcessAdminProfileSupported();
    }

    @Override
    public boolean isActivityControlStatusProfileSupported() {
        return super.isActivityControlStatusProfileSupported();
    }

    @Override
    public boolean isActivityAdminProfileSupported() {
        return super.isActivityAdminProfileSupported();
    }

    @Override
    public boolean isEntityHandlerProfileSupported() {
        return super.isEntityHandlerProfileSupported();
    }

    @Override
    public boolean isAuditRecordProfileSupported() {
        return super.isAuditRecordProfileSupported();
    }

    @Override
    public boolean isToolAgentProfileSupported() {
        return super.isToolAgentProfileSupported();
    }

    @Override
    public WMEntity createEntity(WMEntity scopingEntity, String entityClass, String entityName) throws WMWorkflowException {
        return super.createEntity(scopingEntity, entityClass, entityName);
    }

    @Override
    public WMEntityIterator listEntities(WMEntity scopingEntity, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return super.listEntities(scopingEntity, filter, countFlag);
    }

    @Override
    public void deleteEntity(WMEntity scopingEntity, String entityId) throws WMWorkflowException {
        super.deleteEntity(scopingEntity, entityId);
    }

    @Override
    public WMAttributeIterator listEntityAttributes(WMEntity scopingEntity, String entityId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return super.listEntityAttributes(scopingEntity, entityId, filter, countFlag);
    }

    @Override
    public WMAttribute getEntityAttributeValue(WMEntity scopingEntity, WMEntity entityHandle, String attributeName) throws WMWorkflowException {
        return super.getEntityAttributeValue(scopingEntity, entityHandle, attributeName);
    }

    @Override
    public WMAttributeIterator listEntityAttributeValues(WMEntity scopingEntity, String entityHandle, String attributeName) throws WMWorkflowException {
        return super.listEntityAttributeValues(scopingEntity, entityHandle, attributeName);
    }

    @Override
    public void assignEntityAttributeValue(WMEntity entityHandle, String attributeName, int attributeType, String attributeValue) throws WMWorkflowException {
        super.assignEntityAttributeValue(entityHandle, attributeName, attributeType, attributeValue);
    }

    @Override
    public void clearEntityAttributeList(WMEntity entityHandle, String attributeName) throws WMWorkflowException {
        super.clearEntityAttributeList(entityHandle, attributeName);
    }

    @Override
    public void addEntityAttributeValue(WMEntity entityHandle, String attributeName, int attributeType, String attributeValue) throws WMWorkflowException {
        super.addEntityAttributeValue(entityHandle, attributeName, attributeType, attributeValue);
    }

    @Override
    public WMEntity openWorkflowDefinition(String name, String scope) throws WMWorkflowException {
        return super.openWorkflowDefinition(name, scope);
    }

    @Override
    public void closeWorkflowDefinition(WMEntity workflowDefinitionHandle) throws WMWorkflowException {
        super.closeWorkflowDefinition(workflowDefinitionHandle);
    }

    @Override
    public String createPackage() throws WMWorkflowException {
        return super.createPackage();
    }

    @Override
    public void deleteProcessDefinition(String processDefinitionId) throws WMWorkflowException {
        super.deleteProcessDefinition(processDefinitionId);
    }

    @Override
    public WMEntity openProcessDefinition(String procDefId) throws WMWorkflowException {
        return super.openProcessDefinition(procDefId);
    }

    @Override
    public void closeProcessDefinition(WMEntity procModelHandle) throws WMWorkflowException {
        super.closeProcessDefinition(procModelHandle);
    }

    @Override
    public WMEntity addTransition(String procModelId, String sourceActDefId, String targetActDefId) throws WMWorkflowException {
        return super.addTransition(procModelId, sourceActDefId, targetActDefId);
    }

    @Override
    public void addProcessDataAttribute(String procModelId, String procDataId, String attributeName, int attributeType, int attributeLength, String attributeValue) throws WMWorkflowException {
        super.addProcessDataAttribute(procModelId, procDataId, attributeName, attributeType, attributeLength, attributeValue);
    }

    @Override
    public void removeProcessDataAttribute(String procModelId, String procDataId, String attributeName) throws WMWorkflowException {
        super.removeProcessDataAttribute(procModelId, procDataId, attributeName);
    }

    @Override
    public WMProcessDefinitionIterator listProcessDefinitions(WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return super.listProcessDefinitions(filter, countFlag);
    }

    @Override
    public WMProcessDefinitionStateIterator listProcessDefinitionStates(String procDefId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return super.listProcessDefinitionStates(procDefId, filter, countFlag);
    }

    @Override
    public void changeProcessDefinitionState(String procDefId, WMProcessDefinitionState newState) throws WMWorkflowException {
        super.changeProcessDefinitionState(procDefId, newState);
    }

    @Override
    public void terminateProcessInstance(String procInstId) throws WMWorkflowException {
        super.terminateProcessInstance(procInstId);
    }

    @Override
    public WMProcessInstanceStateIterator listProcessInstanceStates(String procInstId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return super.listProcessInstanceStates(procInstId, filter, countFlag);
    }

    @Override
    public void changeProcessInstanceState(String procInstId, WMProcessInstanceState newState) throws WMWorkflowException {
        super.changeProcessInstanceState(procInstId, newState);
    }

    @Override
    public WMAttributeIterator listProcessInstanceAttributes(String procInstId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return super.listProcessInstanceAttributes(procInstId, filter, countFlag);
    }

    @Override
    public WMAttribute getProcessInstanceAttributeValue(String procInstId, String attrName) throws WMWorkflowException {
        return super.getProcessInstanceAttributeValue(procInstId, attrName);
    }

    @Override
    public WMActivityInstanceStateIterator listActivityInstanceStates(String procInstId, String actInstId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return super.listActivityInstanceStates(procInstId, actInstId, filter, countFlag);
    }

    @Override
    public void changeActivityInstanceState(String procInstId, String actInstId, WMActivityInstanceState newState) throws WMWorkflowException {
        super.changeActivityInstanceState(procInstId, actInstId, newState);
    }

    @Override
    public WMAttributeIterator listActivityInstanceAttributes(String procInstId, String actInstId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return super.listActivityInstanceAttributes(procInstId, actInstId, filter, countFlag);
    }

    @Override
    public WMAttribute getActivityInstanceAttributeValue(String procInstId, String actInstId, String attrName) throws WMWorkflowException {
        return super.getActivityInstanceAttributeValue(procInstId, actInstId, attrName);
    }

    @Override
    public void assignActivityInstanceAttribute(String procInstId, String actInstId, String attrName, Object attrValue) throws WMWorkflowException {
        super.assignActivityInstanceAttribute(procInstId, actInstId, attrName, attrValue);
    }

    @Override
    public WMProcessInstanceIterator listProcessInstances(WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return super.listProcessInstances(filter, countFlag);
    }

    @Override
    public WMProcessInstance getProcessInstance(String procInstId) throws WMWorkflowException {
        return super.getProcessInstance(procInstId);
    }

    @Override
    public WMActivityInstanceIterator listActivityInstances(WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return super.listActivityInstances(filter, countFlag);
    }

    @Override
    public WMActivityInstance getActivityInstance(String procInstId, String actInstId) throws WMWorkflowException {
        return super.getActivityInstance(procInstId, actInstId);
    }

    @Override
    public WMWorkItemIterator listWorkItems(WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return super.listWorkItems(filter, countFlag);
    }

    @Override
    public WMWorkItem getWorkItem(String procInstId, String workItemId) throws WMWorkflowException {
        return super.getWorkItem(procInstId, workItemId);
    }

    @Override
    public void completeWorkItem(String procInstId, String workItemId) throws WMWorkflowException {
        super.completeWorkItem(procInstId, workItemId);
    }

    @Override
    public WMWorkItemStateIterator listWorkItemStates(String procInstId, String workItemId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return super.listWorkItemStates(procInstId, workItemId, filter, countFlag);
    }

    @Override
    public void changeWorkItemState(String procInstId, String workItemId, WMWorkItemState newState) throws WMWorkflowException {
        super.changeWorkItemState(procInstId, workItemId, newState);
    }

    @Override
    public void reassignWorkItem(String sourceUser, String targetUser, String procInstId, String workItemId) throws WMWorkflowException {
        super.reassignWorkItem(sourceUser, targetUser, procInstId, workItemId);
    }

    @Override
    public WMAttributeIterator listWorkItemAttributes(String procInstId, String workItemId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return super.listWorkItemAttributes(procInstId, workItemId, filter, countFlag);
    }

    @Override
    public WMAttribute getWorkItemAttributeValue(String procInstId, String workItemId, String attrName) throws WMWorkflowException {
        return super.getWorkItemAttributeValue(procInstId, workItemId, attrName);
    }

    @Override
    public void assignWorkItemAttribute(String procInstId, String workItemId, String attrName, Object attrValue) throws WMWorkflowException {
        super.assignWorkItemAttribute(procInstId, workItemId, attrName, attrValue);
    }

    @Override
    public void changeProcessInstancesState(String procDefId, WMFilter filter, WMProcessInstanceState newState) throws WMWorkflowException {
        super.changeProcessInstancesState(procDefId, filter, newState);
    }

    @Override
    public void changeActivityInstancesState(String procDefId, String actDefId, WMFilter filter, WMActivityInstanceState newState) throws WMWorkflowException {
        super.changeActivityInstancesState(procDefId, actDefId, filter, newState);
    }

    @Override
    public void terminateProcessInstances(String procDefId, WMFilter filter) throws WMWorkflowException {
        super.terminateProcessInstances(procDefId, filter);
    }

    @Override
    public void assignProcessInstancesAttribute(String procDefId, WMFilter filter, String attrName, Object attrValue) throws WMWorkflowException {
        super.assignProcessInstancesAttribute(procDefId, filter, attrName, attrValue);
    }

    @Override
    public void assignActivityInstancesAttribute(String procDefId, String actDefId, WMFilter filter, String attrName, Object attrValue) throws WMWorkflowException {
        super.assignActivityInstancesAttribute(procDefId, actDefId, filter, attrName, attrValue);
    }

    @Override
    public void abortProcessInstances(String procDefId, WMFilter filter) throws WMWorkflowException {
        super.abortProcessInstances(procDefId, filter);
    }

    @Override
    public void abortProcessInstance(String procInstId) throws WMWorkflowException {
        super.abortProcessInstance(procInstId);
    }

    @Override
    public void invokeApplication(int toolAgentHandle, String appName, String procInstId, String workItemId, Object[] parameters, int appMode) throws WMWorkflowException {
        super.invokeApplication(toolAgentHandle, appName, procInstId, workItemId, parameters, appMode);
    }

    @Override
    public WMAttribute[] requestAppStatus(int toolAgentHandle, String procInstId, String workItemId, int[] status) throws WMWorkflowException {
        return super.requestAppStatus(toolAgentHandle, procInstId, workItemId, status);
    }

    @Override
    public void terminateApp(int toolAgentHandle, String procInstId, String workItemId) throws WMWorkflowException {
        super.terminateApp(toolAgentHandle, procInstId, workItemId);
    }

    @Override
    public List<WMWorkItem> getNextSteps(String processInstanceId, String workItemId) throws WMWorkflowException {
        return super.getNextSteps(processInstanceId, workItemId);
    }

    @Override
    public void setTransition(String processInstanceId, String currentWorkItemId, String[] nextWorkItemIds) throws WMWorkflowException {
        super.setTransition(processInstanceId, currentWorkItemId, nextWorkItemIds);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
