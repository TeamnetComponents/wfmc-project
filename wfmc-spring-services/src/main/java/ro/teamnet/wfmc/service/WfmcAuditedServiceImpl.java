package ro.teamnet.wfmc.service;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.wfmc.service.WfmcService;
import org.wfmc.wapi.*;
import org.wfmc.wapi2.WMEntity;
import org.wfmc.wapi2.WMEntityIterator;
import org.wfmc.xpdl.model.workflow.WorkflowProcess;
import ro.teamnet.audit.annotation.Auditable;

import javax.inject.Inject;

import java.util.List;

import static ro.teamnet.wfmc.audit.constants.WfmcAuditStrategy.WFMC;
import static ro.teamnet.wfmc.audit.constants.WfmcAuditedMethod.*;

/**
 * An audited wrapper for a WfmcService instance, implemented as a Spring component.
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WfmcAuditedServiceImpl implements WfmcAuditedService {


    @Inject
    private WfmcService wfmcService;

    private WMConnectInfo wmConnectInfo = null;

    public String getUserIdentification() {
        if (wmConnectInfo == null) {
            return null;
        }
        return wmConnectInfo.getUserIdentification();
    }

    @Override
    public void connect(WMConnectInfo connectInfo) throws WMWorkflowException {
        wmConnectInfo = connectInfo;
        wfmcService.connect(wmConnectInfo);
    }

    @Override
    public void disconnect() throws WMWorkflowException {
        wmConnectInfo = null;
        wfmcService.disconnect();
    }

    @Override
    @Auditable(strategy = WFMC, type = CREATE_PROCESS_INSTANCE)
    public String createProcessInstance(String procDefId, String procInstName) throws WMWorkflowException {
        return wfmcService.createProcessInstance(procDefId, procInstName);
    }

    @Override
    @Auditable(strategy = WFMC, type = ASSIGN_PROCESS_INSTANCE_ATTRIBUTE)
    public void assignProcessInstanceAttribute(String procInstId, String attrName, Object attrValue) throws WMWorkflowException {
        wfmcService.assignProcessInstanceAttribute(procInstId, attrName, attrValue);
    }

    @Override
    @Auditable(strategy = WFMC, type = START_PROCESS)
    public String startProcess(String procInstId) throws WMWorkflowException {
        return wfmcService.startProcess(procInstId);
    }

    @Override
    @Auditable(strategy = WFMC, type = ABORT_PROCESS_INSTANCE)
    public void abortProcessInstance(String procInstId) throws WMWorkflowException {
        wfmcService.abortProcessInstance(procInstId);
    }

    @Override
    @Auditable(strategy = WFMC, type = REASSIGN_WORK_ITEM)
    public void reassignWorkItem(String sourceUser, String targetUser, String procInstId, String workItemId) throws WMWorkflowException {
        wfmcService.reassignWorkItem(sourceUser, targetUser, procInstId, workItemId);
    }

    @Override
    @Auditable(strategy = WFMC, type = ASSIGN_WORK_ITEM_ATTRIBUTE)
    public void assignWorkItemAttribute(String procInstId, String workItemId, String attrName, Object attrValue) throws WMWorkflowException {
        wfmcService.assignWorkItemAttribute(procInstId, workItemId, attrName, attrValue);
    }

    @Override
    @Auditable(strategy = WFMC, type = COMPLETE_WORK_ITEM)
    public void completeWorkItem(String procInstId, String workItemId) throws WMWorkflowException {
        wfmcService.completeWorkItem(procInstId, workItemId);
    }

    @Override
    public boolean isWorkListHandlerProfileSupported() {
        return wfmcService.isWorkListHandlerProfileSupported();
    }

    @Override
    public boolean isProcessControlStatusProfileSupported() {
        return wfmcService.isProcessControlStatusProfileSupported();
    }

    @Override
    public boolean isProcessDefinitionProfileSupported() {
        return wfmcService.isProcessDefinitionProfileSupported();
    }

    @Override
    public boolean isProcessAdminProfileSupported() {
        return wfmcService.isProcessAdminProfileSupported();
    }

    @Override
    public boolean isActivityControlStatusProfileSupported() {
        return wfmcService.isActivityControlStatusProfileSupported();
    }

    @Override
    public boolean isActivityAdminProfileSupported() {
        return wfmcService.isActivityAdminProfileSupported();
    }

    @Override
    public boolean isEntityHandlerProfileSupported() {
        return wfmcService.isEntityHandlerProfileSupported();
    }

    @Override
    public boolean isAuditRecordProfileSupported() {
        return wfmcService.isAuditRecordProfileSupported();
    }

    @Override
    public boolean isToolAgentProfileSupported() {
        return wfmcService.isToolAgentProfileSupported();
    }

    @Override
    public WMEntity createEntity(WMEntity scopingEntity, String entityClass, String entityName) throws WMWorkflowException {
        return wfmcService.createEntity(scopingEntity, entityClass, entityName);
    }

    @Override
    public WMEntityIterator listEntities(WMEntity scopingEntity, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return wfmcService.listEntities(scopingEntity, filter, countFlag);
    }

    @Override
    public void deleteEntity(WMEntity scopingEntity, String entityId) throws WMWorkflowException {
        wfmcService.deleteEntity(scopingEntity, entityId);
    }

    @Override
    public WMAttributeIterator listEntityAttributes(WMEntity scopingEntity, String entityId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return wfmcService.listEntityAttributes(scopingEntity, entityId, filter, countFlag);
    }

    @Override
    public WMAttribute getEntityAttributeValue(WMEntity scopingEntity, WMEntity entityHandle, String attributeName) throws WMWorkflowException {
        return wfmcService.getEntityAttributeValue(scopingEntity, entityHandle, attributeName);
    }

    @Override
    public WMAttributeIterator listEntityAttributeValues(WMEntity scopingEntity, String entityHandle, String attributeName) throws WMWorkflowException {
        return wfmcService.listEntityAttributeValues(scopingEntity, entityHandle, attributeName);
    }

    @Override
    public void assignEntityAttributeValue(WMEntity entityHandle, String attributeName, int attributeType, String attributeValue) throws WMWorkflowException {
        wfmcService.assignEntityAttributeValue(entityHandle, attributeName, attributeType, attributeValue);
    }

    @Override
    public void clearEntityAttributeList(WMEntity entityHandle, String attributeName) throws WMWorkflowException {
        wfmcService.clearEntityAttributeList(entityHandle, attributeName);
    }

    @Override
    public void addEntityAttributeValue(WMEntity entityHandle, String attributeName, int attributeType, String attributeValue) throws WMWorkflowException {
        wfmcService.addEntityAttributeValue(entityHandle, attributeName, attributeType, attributeValue);
    }

    @Override
    public WMEntity openWorkflowDefinition(String name, String scope) throws WMWorkflowException {
        return wfmcService.openWorkflowDefinition(name, scope);
    }

    @Override
    public void closeWorkflowDefinition(WMEntity workflowDefinitionHandle) throws WMWorkflowException {
        wfmcService.closeWorkflowDefinition(workflowDefinitionHandle);
    }

    @Override
    public String createPackage() throws WMWorkflowException {
        return wfmcService.createPackage();
    }

    @Override
    public void deleteProcessDefinition(String processDefinitionId) throws WMWorkflowException {
        wfmcService.deleteProcessDefinition(processDefinitionId);
    }

    @Override
    public WMEntity openProcessDefinition(String procDefId) throws WMWorkflowException {
        return wfmcService.openProcessDefinition(procDefId);
    }

    @Override
    public void closeProcessDefinition(WMEntity procModelHandle) throws WMWorkflowException {
        wfmcService.closeProcessDefinition(procModelHandle);
    }

    @Override
    public WMEntity addTransition(String procModelId, String sourceActDefId, String targetActDefId) throws WMWorkflowException {
        return wfmcService.addTransition(procModelId, sourceActDefId, targetActDefId);
    }

    @Override
    public void addProcessDataAttribute(String procModelId, String procDataId, String attributeName, int attributeType, int attributeLength, String attributeValue) throws WMWorkflowException {
        wfmcService.addProcessDataAttribute(procModelId, procDataId, attributeName, attributeType, attributeLength, attributeValue);
    }

    @Override
    public void removeProcessDataAttribute(String procModelId, String procDataId, String attributeName) throws WMWorkflowException {
        wfmcService.removeProcessDataAttribute(procModelId, procDataId, attributeName);
    }

    @Override
    public WMProcessDefinitionIterator listProcessDefinitions(WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return wfmcService.listProcessDefinitions(filter, countFlag);
    }

    @Override
    public WMProcessDefinitionStateIterator listProcessDefinitionStates(String procDefId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return wfmcService.listProcessDefinitionStates(procDefId, filter, countFlag);
    }

    @Override
    public void changeProcessDefinitionState(String procDefId, WMProcessDefinitionState newState) throws WMWorkflowException {
        wfmcService.changeProcessDefinitionState(procDefId, newState);
    }

    @Override
    public void terminateProcessInstance(String procInstId) throws WMWorkflowException {
        wfmcService.terminateProcessInstance(procInstId);
    }

    @Override
    public WMProcessInstanceStateIterator listProcessInstanceStates(String procInstId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return wfmcService.listProcessInstanceStates(procInstId, filter, countFlag);
    }

    @Override
    public void changeProcessInstanceState(String procInstId, WMProcessInstanceState newState) throws WMWorkflowException {
        wfmcService.changeProcessInstanceState(procInstId, newState);
    }

    @Override
    public WMAttributeIterator listProcessInstanceAttributes(String procInstId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return wfmcService.listProcessInstanceAttributes(procInstId, filter, countFlag);
    }

    @Override
    public WMAttribute getProcessInstanceAttributeValue(String procInstId, String attrName) throws WMWorkflowException {
        return wfmcService.getProcessInstanceAttributeValue(procInstId, attrName);
    }

    @Override
    public WMActivityInstanceStateIterator listActivityInstanceStates(String procInstId, String actInstId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return wfmcService.listActivityInstanceStates(procInstId, actInstId, filter, countFlag);
    }

    @Override
    public void changeActivityInstanceState(String procInstId, String actInstId, WMActivityInstanceState newState) throws WMWorkflowException {
        wfmcService.changeActivityInstanceState(procInstId, actInstId, newState);
    }

    @Override
    public WMAttributeIterator listActivityInstanceAttributes(String procInstId, String actInstId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return wfmcService.listActivityInstanceAttributes(procInstId, actInstId, filter, countFlag);
    }

    @Override
    public WMAttribute getActivityInstanceAttributeValue(String procInstId, String actInstId, String attrName) throws WMWorkflowException {
        return wfmcService.getActivityInstanceAttributeValue(procInstId, actInstId, attrName);
    }

    @Override
    public void assignActivityInstanceAttribute(String procInstId, String actInstId, String attrName, Object attrValue) throws WMWorkflowException {
        wfmcService.assignActivityInstanceAttribute(procInstId, actInstId, attrName, attrValue);
    }

    @Override
    public WMProcessInstanceIterator listProcessInstances(WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return wfmcService.listProcessInstances(filter, countFlag);
    }

    @Override
    public WMProcessInstance getProcessInstance(String procInstId) throws WMWorkflowException {
        return wfmcService.getProcessInstance(procInstId);
    }

    @Override
    public WMActivityInstanceIterator listActivityInstances(WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return wfmcService.listActivityInstances(filter, countFlag);
    }

    @Override
    public WMActivityInstance getActivityInstance(String procInstId, String actInstId) throws WMWorkflowException {
        return wfmcService.getActivityInstance(procInstId, actInstId);
    }

    @Override
    public WMWorkItemIterator listWorkItems(WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return wfmcService.listWorkItems(filter, countFlag);
    }

    @Override
    public WMWorkItem getWorkItem(String procInstId, String workItemId) throws WMWorkflowException {
        return wfmcService.getWorkItem(procInstId, workItemId);
    }

    @Override
    public WMWorkItemStateIterator listWorkItemStates(String procInstId, String workItemId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return wfmcService.listWorkItemStates(procInstId, workItemId, filter, countFlag);
    }

    @Override
    public void changeWorkItemState(String procInstId, String workItemId, WMWorkItemState newState) throws WMWorkflowException {
        wfmcService.changeWorkItemState(procInstId, workItemId, newState);
    }

    @Override
    public WMAttributeIterator listWorkItemAttributes(String procInstId, String workItemId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return wfmcService.listWorkItemAttributes(procInstId, workItemId, filter, countFlag);
    }

    @Override
    public WMAttribute getWorkItemAttributeValue(String procInstId, String workItemId, String attrName) throws WMWorkflowException {
        return wfmcService.getWorkItemAttributeValue(procInstId, workItemId, attrName);
    }

    @Override
    public void changeProcessInstancesState(String procDefId, WMFilter filter, WMProcessInstanceState newState) throws WMWorkflowException {
        wfmcService.changeProcessInstancesState(procDefId, filter, newState);
    }

    @Override
    public void changeActivityInstancesState(String procDefId, String actDefId, WMFilter filter, WMActivityInstanceState newState) throws WMWorkflowException {
        wfmcService.changeActivityInstancesState(procDefId, actDefId, filter, newState);
    }

    @Override
    public void terminateProcessInstances(String procDefId, WMFilter filter) throws WMWorkflowException {
        wfmcService.terminateProcessInstances(procDefId, filter);
    }

    @Override
    public void assignProcessInstancesAttribute(String procDefId, WMFilter filter, String attrName, Object attrValue) throws WMWorkflowException {
        wfmcService.assignProcessInstancesAttribute(procDefId, filter, attrName, attrValue);
    }

    @Override
    public void assignActivityInstancesAttribute(String procDefId, String actDefId, WMFilter filter, String attrName, Object attrValue) throws WMWorkflowException {
        wfmcService.assignActivityInstancesAttribute(procDefId, actDefId, filter, attrName, attrValue);
    }

    @Override
    public void abortProcessInstances(String procDefId, WMFilter filter) throws WMWorkflowException {
        wfmcService.abortProcessInstances(procDefId, filter);
    }

    @Override
    public void invokeApplication(int toolAgentHandle, String appName, String procInstId, String workItemId, Object[] parameters, int appMode) throws WMWorkflowException {
        wfmcService.invokeApplication(toolAgentHandle, appName, procInstId, workItemId, parameters, appMode);
    }

    @Override
    public WMAttribute[] requestAppStatus(int toolAgentHandle, String procInstId, String workItemId, int[] status) throws WMWorkflowException {
        return wfmcService.requestAppStatus(toolAgentHandle, procInstId, workItemId, status);
    }

    @Override
    public void terminateApp(int toolAgentHandle, String procInstId, String workItemId) throws WMWorkflowException {
        wfmcService.terminateApp(toolAgentHandle, procInstId, workItemId);
    }

    @Override
    public WorkflowProcess getWorkFlowProcess(String processDefinitionId) throws WMWorkflowException {
        return wfmcService.getWorkFlowProcess(processDefinitionId);
    }

    @Override
    public List<WMWorkItem> getNextSteps(String processInstanceId, String workItemId) throws WMWorkflowException {
        return wfmcService.getNextSteps(processInstanceId, workItemId);
    }

}
