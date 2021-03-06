/*
 * Generated by XDoclet - Do not edit!
 */
package org.wfmc.impl.base;

import org.wfmc.impl.tool.Parameter;
import org.wfmc.impl.tool.ToolInvocation;
import org.wfmc.server.core.xpdl.model.pkg.XPDLPackage;
import org.wfmc.audit.WMAAuditEntry;
import org.wfmc.wapi.*;

/**
 * Generic interface to the workflow engine.  The methods in this interface all
 * throw java.lang.Exception in order to support J2EE local/remote transparency.
 */
public interface WorkflowEngineIntf {
    WMProcessDefinition[] listProcessDefinitions(WMFilter filter,
        boolean countFlag) throws Exception;

    WMProcessDefinitionState[] listProcessDefinitionStates(WMFilter filter,
        boolean countFlag) throws Exception;

    void changeProcessDefinitionState(String processDefinitionId,
        WMProcessDefinitionState newState) throws Exception;

    String createProcessInstance(String processDefinitionId,
        String processInstanceName) throws Exception;

    String createProcessInstanceVersioned(String name,
        String processInstanceName) throws Exception;

    String startProcess(String processInstanceId) throws Exception;

    void terminateProcessInstance(String processInstanceId) throws Exception;

    void changeProcessInstanceState(String processInstanceId,
        WMProcessInstanceState newState) throws Exception;

    WMProcessInstanceState[] listProcessInstanceStates(String processInstanceId,
        WMFilter filter, boolean countFlag) throws Exception;

    WMAttribute[] listProcessInstanceAttributes(String processInstanceId,
        WMFilter filter, boolean countFlag) throws Exception;

    WMAttribute getProcessInstanceAttributeValue(String processInstanceId,
        String attributeName) throws Exception;

    void assignProcessInstanceAttribute(String processInstanceId,
        String attributeName, Object attributeValue) throws Exception;

    WMActivityInstanceState[] listActivityInstanceStates(
        String processInstanceId, String activityInstanceId, WMFilter filter,
        boolean countFlag) throws Exception;

    void changeActivityInstanceState(String processInstanceId,
        String activityInstanceId, WMActivityInstanceState newState)
        throws Exception;

    WMAttribute[] listActivityInstanceAttributes(String processInstanceId,
        String activityInstanceId, WMFilter filter, boolean countFlag)
        throws Exception;

    WMAttribute getActivityInstanceAttributeValue(String processInstanceId,
        String activityInstanceId, String attributeName) throws Exception;

    void assignActivityInstanceAttribute(String processInstanceId,
        String activityInstanceId, String attributeName, Object attributeValue)
        throws Exception;

    WMProcessInstance[] listProcessInstances(WMFilter filter, boolean countFlag)
        throws Exception;

    WMProcessInstance getProcessInstance(String processInstanceId)
        throws Exception;

    WMActivityInstance[] listActivityInstances(WMFilter filter,
        boolean countFlag) throws Exception;

    WMActivityInstance getActivityInstance(String processInstanceId,
        String activityInstanceId) throws Exception;

    WMWorkItem[] listWorkItems(WMFilter filter, boolean countFlag)
        throws Exception;

    WMWorkItem getWorkItem(String processInstanceId, String workItemId)
        throws Exception;

    void completeWorkItem(String processInstanceId, String workItemId)
        throws Exception;

    void reassignWorkItem(String sourceUser, String targetUser,
        String processInstanceId, String workItemId) throws Exception;

    WMWorkItemState[] listWorkItemStates(String processInstanceId,
        String workItemId, WMFilter filter, boolean countFlag) throws Exception;

    void changeWorkItemState(String processInstanceId, String workItemId,
        WMWorkItemState newState) throws Exception;

    WMAttribute[] listWorkItemAttributes(String processInstanceId,
        String workItemId, WMFilter filter, boolean countFlag) throws Exception;

    WMAttribute getWorkItemAttributeValue(String processInstanceId,
        String workItemId, String attributeName) throws Exception;

    void assignWorkItemAttribute(String processInstanceId, String workItemId,
        String attributeName, Object attributeValue) throws Exception;

    void changeProcessInstancesState(String processDefinitionId,
        WMFilter filter, WMProcessInstanceState newState) throws Exception;

    void changeActivityInstancesState(String processDefinitionId,
        String activityDefinitionId, WMFilter filter,
        WMActivityInstanceState newState) throws Exception;

    void terminateProcessInstances(String processDefinitionId, WMFilter filter)
        throws Exception;

    void assignProcessInstancesAttribute(String processDefinitionId,
        WMFilter filter, String attributeName, Object attributeValue)
        throws Exception;

    void assignActivityInstancesAttribute(String processDefinitionId,
        String activityDefinitionId, WMFilter filter, String attributeName,
        Object attributeValue) throws Exception;

    void abortProcessInstances(String processDefinitionId, WMFilter filter)
        throws Exception;

    void abortProcessInstance(String processInstanceId) throws Exception;

    String createPackage(XPDLPackage pkg) throws Exception;

    void updatePackage(XPDLPackage pkg) throws Exception;

    XPDLPackage getPackage(String packageId) throws Exception;

    String createPackage(String content, String contentType) throws Exception;

    String getPackageContent(String packageId, String contentType)
        throws Exception;

    void setPackageContent(String packageId, String content, String contentType)
        throws Exception;

    void deletePackage(String packageId) throws Exception;

    void deleteProcessInstance(String processInstanceId) throws Exception;

    void deleteProcessInstances(String processDefinitionId, WMFilter filter)
        throws Exception;

    WMAAuditEntry[] listAuditEntries(WMFilter filter) throws Exception;

    int deleteAuditEntries(WMFilter filter) throws Exception;

    ToolInvocation[] executeWorkItem(String processInstanceId,
        String workItemId)
        throws Exception;

    void toolStarted(String processInstanceId, String workItemId)
        throws Exception;

    void toolFinished(String processInstanceId, String workItemId,
        int appStatus,
        Parameter[] parms) throws Exception;
}