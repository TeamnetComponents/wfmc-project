package org.wfmc.service;

import org.wfmc.impl.base.WMAttributeImpl;
import org.wfmc.impl.base.WMProcessInstanceImpl;
import org.wfmc.wapi.*;
import org.wfmc.wapi2.WMEntity;
import org.wfmc.wapi2.WMEntityIterator;
import org.wfmc.xpdl.model.workflow.WorkflowProcess;
import ro.teamnet.wfmc.audit.annotation.Auditable;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by Lucian.Dragomir on 2/10/2015.
 */
public abstract class WfmcServiceAbstract implements WfmcService {

    protected Properties context;
    protected String serviceInstance;
    private WfmcServiceCache wfmcServiceCache;
    public WMConnectInfo wmConnectInfo;

    public WfmcServiceCache getWfmcServiceCache() {
        return wfmcServiceCache;
    }

    protected void setWfmcServiceCache(WfmcServiceCache wfmcServiceCache) {
        this.wfmcServiceCache = wfmcServiceCache;
    }

    @Override
    public void __initialize(Properties context) throws IOException {
        this.context = context;
    }

    @Override
    public final void __terminate() {
        this.serviceInstance = null;
    }

    @Override
    public Properties getContext() {
        return this.context;
    }

    @Override
    public String getName() {
        return (String) context.get(ServiceFactory.INSTANCE_NAME);
    }

    @Override
    public String getServiceInstance() {
        return serviceInstance;
    }

    @Override
    public void connect(WMConnectInfo connectInfo) throws WMWorkflowException {
        this.serviceInstance = UUID.randomUUID().toString();
    }

    @Override
    public void disconnect() throws WMWorkflowException {
        this.serviceInstance = null;
    }


    @Override
    @Auditable(value="createProcessInstance")
    public String createProcessInstance(String procDefId, String procInstName) throws WMWorkflowException {
        WMProcessInstance wmProcessInstance = new WMProcessInstanceImpl(procInstName, String.valueOf(UUID.randomUUID()), procDefId);
        wfmcServiceCache.addProcessInstance(wmProcessInstance);
        return wmProcessInstance.getId();
    }

    @Override
    public void assignProcessInstanceAttribute(String procInstId, String attrName, Object attrValue) throws WMWorkflowException {
        WMAttribute wmAttribute = new WMAttributeImpl(attrName, WMAttribute.DEFAULT_TYPE, attrValue);
        wfmcServiceCache.addProcessInstanceAttribute(procInstId, wmAttribute);
    }

    @Override
    public String startProcess(String procInstId) throws WMWorkflowException {
        wfmcServiceCache.removeProcessInstance(procInstId);
        wfmcServiceCache.removeProcessInstanceAttributes(procInstId);
        return procInstId;
    }

    @Override
    public boolean isWorkListHandlerProfileSupported() {
        throw new WMUnsupportedOperationException("isWorkListHandlerProfileSupported");
    }

    @Override
    public boolean isProcessControlStatusProfileSupported() {
        throw new WMUnsupportedOperationException("isProcessControlStatusProfileSupported");
    }

    @Override
    public boolean isProcessDefinitionProfileSupported() {
        throw new WMUnsupportedOperationException("isProcessDefinitionProfileSupported");
    }

    @Override
    public boolean isProcessAdminProfileSupported() {
        throw new WMUnsupportedOperationException("isProcessAdminProfileSupported");
    }

    @Override
    public boolean isActivityControlStatusProfileSupported() {
        throw new WMUnsupportedOperationException("isActivityControlStatusProfileSupported");
    }

    @Override
    public boolean isActivityAdminProfileSupported() {
        throw new WMUnsupportedOperationException("isActivityAdminProfileSupported");
    }

    @Override
    public boolean isEntityHandlerProfileSupported() {
        throw new WMUnsupportedOperationException("isEntityHandlerProfileSupported");
    }

    @Override
    public boolean isAuditRecordProfileSupported() {
        throw new WMUnsupportedOperationException("isAuditRecordProfileSupported");
    }

    @Override
    public boolean isToolAgentProfileSupported() {
        throw new WMUnsupportedOperationException("isToolAgentProfileSupported");
    }

    @Override
    public WMEntity createEntity(WMEntity scopingEntity, String entityClass, String entityName) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("createEntity");
    }

    @Override
    public WMEntityIterator listEntities(WMEntity scopingEntity, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("listEntities");
    }

    @Override
    public void deleteEntity(WMEntity scopingEntity, String entityId) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("deleteEntity");
    }

    @Override
    public WMAttributeIterator listEntityAttributes(WMEntity scopingEntity, String entityId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("listEntityAttributes");
    }

    @Override
    public WMAttribute getEntityAttributeValue(WMEntity scopingEntity, WMEntity entityHandle, String attributeName) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("getEntityAttributeValue");
    }

    @Override
    public WMAttributeIterator listEntityAttributeValues(WMEntity scopingEntity, String entityHandle, String attributeName) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("listEntityAttributeValues");
    }

    @Override
    public void assignEntityAttributeValue(WMEntity entityHandle, String attributeName, int attributeType, String attributeValue) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("assignEntityAttributeValue");
    }

    @Override
    public void clearEntityAttributeList(WMEntity entityHandle, String attributeName) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("clearEntityAttributeList");
    }

    @Override
    public void addEntityAttributeValue(WMEntity entityHandle, String attributeName, int attributeType, String attributeValue) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("addEntityAttributeValue");
    }

    @Override
    public WMEntity openWorkflowDefinition(String name, String scope) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("openWorkflowDefinition");
    }

    @Override
    public void closeWorkflowDefinition(WMEntity workflowDefinitionHandle) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("closeWorkflowDefinition");
    }

    @Override
    public String createPackage() throws WMWorkflowException {
        throw new WMUnsupportedOperationException("createPackage");
    }

    @Override
    public void deleteProcessDefinition(String processDefinitionId) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("deleteProcessDefinition");
    }

    @Override
    public WMEntity openProcessDefinition(String procDefId) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("openProcessDefinition");
    }

    @Override
    public void closeProcessDefinition(WMEntity procModelHandle) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("closeProcessDefinition");
    }

    @Override
    public WMEntity addTransition(String procModelId, String sourceActDefId, String targetActDefId) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("addTransition");
    }

    @Override
    public void addProcessDataAttribute(String procModelId, String procDataId, String attributeName, int attributeType, int attributeLength, String attributeValue) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("addProcessDataAttribute");
    }

    @Override
    public void removeProcessDataAttribute(String procModelId, String procDataId, String attributeName) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("removeProcessDataAttribute");
    }

    @Override
    public WMProcessDefinitionIterator listProcessDefinitions(WMFilter filter, boolean countFlag) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("listProcessDefinitions");
    }

    @Override
    public WMProcessDefinitionStateIterator listProcessDefinitionStates(String procDefId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("listProcessDefinitionStates");
    }

    @Override
    public void changeProcessDefinitionState(String procDefId, WMProcessDefinitionState newState) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("changeProcessDefinitionState");
    }

    @Override
    public void terminateProcessInstance(String procInstId) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("terminateProcessInstance");
    }

    @Override
    public WMProcessInstanceStateIterator listProcessInstanceStates(String procInstId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("listProcessInstanceStates");
    }

    @Override
    public void changeProcessInstanceState(String procInstId, WMProcessInstanceState newState) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("changeProcessInstanceState");
    }

    @Override
    public WMAttributeIterator listProcessInstanceAttributes(String procInstId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("listProcessInstanceAttributes");
    }

    @Override
    public WMAttribute getProcessInstanceAttributeValue(String procInstId, String attrName) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("getProcessInstanceAttributeValue");
    }

    @Override
    public WMActivityInstanceStateIterator listActivityInstanceStates(String procInstId, String actInstId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("listActivityInstanceStates");
    }

    @Override
    public void changeActivityInstanceState(String procInstId, String actInstId, WMActivityInstanceState newState) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("changeActivityInstanceState");
    }

    @Override
    public WMAttributeIterator listActivityInstanceAttributes(String procInstId, String actInstId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("listActivityInstanceAttributes");
    }

    @Override
    public WMAttribute getActivityInstanceAttributeValue(String procInstId, String actInstId, String attrName) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("getActivityInstanceAttributeValue");
    }

    @Override
    public void assignActivityInstanceAttribute(String procInstId, String actInstId, String attrName, Object attrValue) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("assignActivityInstanceAttribute");
    }

    @Override
    public WMProcessInstanceIterator listProcessInstances(WMFilter filter, boolean countFlag) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("listProcessInstances");
    }

    @Override
    public WMProcessInstance getProcessInstance(String procInstId) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("getProcessInstance");
    }

    @Override
    public WMActivityInstanceIterator listActivityInstances(WMFilter filter, boolean countFlag) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("listActivityInstances");
    }

    @Override
    public WMActivityInstance getActivityInstance(String procInstId, String actInstId) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("getActivityInstance");
    }

    @Override
    public WMWorkItemIterator listWorkItems(WMFilter filter, boolean countFlag) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("listWorkItems");
    }

    @Override
    public WMWorkItem getWorkItem(String procInstId, String workItemId) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("getWorkItem");
    }

    @Override
    public void completeWorkItem(String procInstId, String workItemId) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("completeWorkItem");
    }

    @Override
    public WMWorkItemStateIterator listWorkItemStates(String procInstId, String workItemId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("listWorkItemStates");
    }

    @Override
    public void changeWorkItemState(String procInstId, String workItemId, WMWorkItemState newState) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("changeWorkItemState");
    }

    @Override
    public void reassignWorkItem(String sourceUser, String targetUser, String procInstId, String workItemId) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("reassignWorkItem");
    }

    @Override
    public WMAttributeIterator listWorkItemAttributes(String procInstId, String workItemId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("listWorkItemAttributes");
    }

    @Override
    public WMAttribute getWorkItemAttributeValue(String procInstId, String workItemId, String attrName) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("getWorkItemAttributeValue");
    }

    @Override
    public void assignWorkItemAttribute(String procInstId, String workItemId, String attrName, Object attrValue) throws WMWorkflowException {
        WMAttribute wmAttribute = new WMAttributeImpl(attrName, WMAttribute.DEFAULT_TYPE, attrValue);
        wfmcServiceCache.addWorkItemAttribute(procInstId, workItemId, wmAttribute);
    }

    @Override
    public void changeProcessInstancesState(String procDefId, WMFilter filter, WMProcessInstanceState newState) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("changeProcessInstancesState");
    }

    @Override
    public void changeActivityInstancesState(String procDefId, String actDefId, WMFilter filter, WMActivityInstanceState newState) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("changeActivityInstancesState");
    }

    @Override
    public void terminateProcessInstances(String procDefId, WMFilter filter) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("terminateProcessInstances");
    }

    @Override
    public void assignProcessInstancesAttribute(String procDefId, WMFilter filter, String attrName, Object attrValue) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("assignProcessInstancesAttribute");
    }

    @Override
    public void assignActivityInstancesAttribute(String procDefId, String actDefId, WMFilter filter, String attrName, Object attrValue) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("assignActivityInstancesAttribute");
    }

    @Override
    public void abortProcessInstances(String procDefId, WMFilter filter) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("abortProcessInstances");
    }

    @Override
    public void abortProcessInstance(String procInstId) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("abortProcessInstance");
    }

    @Override
    public void invokeApplication(int toolAgentHandle, String appName, String procInstId, String workItemId, Object[] parameters, int appMode) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("invokeApplication");
    }

    @Override
    public WMAttribute[] requestAppStatus(int toolAgentHandle, String procInstId, String workItemId, int[] status) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("requestAppStatus");
    }

    @Override
    public void terminateApp(int toolAgentHandle, String procInstId, String workItemId) throws WMWorkflowException {
        throw new WMUnsupportedOperationException("terminateApp");
    }


    public List<WMWorkItem> getNextSteps(String processInstanceId, String workItemId) throws WMUnsupportedOperationException {
        throw new WMUnsupportedOperationException("getNextSteps");
    }

    public WorkflowProcess getWorkFlowProcess(String processDefinitionId) throws WMWorkflowException{
        throw new WMUnsupportedOperationException("getWorkFlowProcess");
    }

    public WMConnectInfo getWmConnectInfo() {
        return wmConnectInfo;
    }

    public void setWmConnectInfo(WMConnectInfo wmConnectInfo) {
        this.wmConnectInfo = wmConnectInfo;
    }
}
