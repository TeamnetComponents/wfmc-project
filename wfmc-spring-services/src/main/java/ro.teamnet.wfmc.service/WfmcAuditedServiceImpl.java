package ro.teamnet.wfmc.service;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.wfmc.service.WfmcService;
import org.wfmc.wapi.*;
import org.wfmc.wapi2.WMEntity;
import org.wfmc.wapi2.WMEntityIterator;
import ro.teamnet.audit.annotation.Auditable;
import ro.teamnet.audit.constants.AuditStrategy;

import javax.inject.Inject;

import static ro.teamnet.wfmc.audit.constants.WfmcAuditedMethod.*;

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
    @Auditable(strategy = AuditStrategy.WFMC, type = CREATE_PROCESS_INSTANCE)
    public String createProcessInstance(String procDefId, String procInstName) throws WMWorkflowException {
        return wfmcService.createProcessInstance(procDefId, procInstName);
    }

    @Override
    @Auditable(strategy = AuditStrategy.WFMC, type = ASSIGN_PROCESS_INSTANCE_ATTRIBUTE)
    public void assignProcessInstanceAttribute(String procInstId, String attrName, Object attrValue) throws WMWorkflowException {
        wfmcService.assignProcessInstanceAttribute(procInstId, attrName, attrValue);
    }

    @Override
    @Auditable(strategy = AuditStrategy.WFMC, type = START_PROCESS)
    public String startProcess(String procInstId) throws WMWorkflowException {
        return wfmcService.startProcess(procInstId);
    }

    @Override
    @Auditable(strategy = AuditStrategy.WFMC, type = ABORT_PROCESS_INSTANCE)
    public void abortProcessInstance(String procInstId) throws WMWorkflowException {
        wfmcService.abortProcessInstance(procInstId);
    }

    @Override
    @Auditable(strategy = AuditStrategy.WFMC, type = REASSIGN_WORK_ITEM)
    public void reassignWorkItem(String sourceUser, String targetUser, String procInstId, String workItemId) throws WMWorkflowException {
        wfmcService.reassignWorkItem(sourceUser, targetUser, procInstId, workItemId);
    }

    @Override
    @Auditable(strategy = AuditStrategy.WFMC, type = ASSIGN_WORK_ITEM_ATTRIBUTE)
    public void assignWorkItemAttribute(String procInstId, String workItemId, String attrName, Object attrValue) throws WMWorkflowException {
        wfmcService.assignWorkItemAttribute(procInstId, workItemId, attrName, attrValue);
    }

    @Override
    @Auditable(strategy = AuditStrategy.WFMC, type = COMPLETE_WORK_ITEM)
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

    //TODO : implement all WAPI2 methods below by calling the method with the same name from wfmcService
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
    public WMWorkItemStateIterator listWorkItemStates(String procInstId, String workItemId, WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return null;
    }

    @Override
    public void changeWorkItemState(String procInstId, String workItemId, WMWorkItemState newState) throws WMWorkflowException {

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
    public void invokeApplication(int toolAgentHandle, String appName, String procInstId, String workItemId, Object[] parameters, int appMode) throws WMWorkflowException {

    }

    @Override
    public WMAttribute[] requestAppStatus(int toolAgentHandle, String procInstId, String workItemId, int[] status) throws WMWorkflowException {
        return new WMAttribute[0];
    }

    @Override
    public void terminateApp(int toolAgentHandle, String procInstId, String workItemId) throws WMWorkflowException {

    }
}
