package ro.teamnet.wfmc.service;

import org.wfmc.wapi.WMWorkflowException;
import org.wfmc.wapi2.WAPI2;
import ro.teamnet.audit.annotation.AuditedParameter;

import static ro.teamnet.wfmc.audit.constants.WfmcAuditedParameter.*;

/**
 * A facade providing the audited methods of the WfmcService.
 */
public interface WfmcAuditedService extends WAPI2 {

    String createProcessInstance(
            @AuditedParameter(description = PROCESS_DEFINITION_ID) String procDefId,
            @AuditedParameter(description = PROCESS_INSTANCE_NAME) String procInstName) throws WMWorkflowException;

    void assignProcessInstanceAttribute(
            @AuditedParameter(description = PROCESS_INSTANCE_ID) String procInstId,
            @AuditedParameter(description = ATTRIBUTE_NAME) String attrName,
            @AuditedParameter(description = ATTRIBUTE_VALUE) Object attrValue) throws WMWorkflowException;

    String startProcess(@AuditedParameter(description = PROCESS_INSTANCE_ID) String procInstId)
            throws WMWorkflowException;

    void abortProcessInstance(@AuditedParameter(description = PROCESS_INSTANCE_ID) String procInstId)
            throws WMWorkflowException;

    void reassignWorkItem(
            @AuditedParameter(description = SOURCE_USER) String sourceUser,
            @AuditedParameter(description = TARGET_USER) String targetUser,
            @AuditedParameter(description = PROCESS_INSTANCE_ID) String procInstId,
            @AuditedParameter(description = WORK_ITEM_ID) String workItemId) throws WMWorkflowException;

    void assignWorkItemAttribute(
            @AuditedParameter(description = PROCESS_INSTANCE_ID) String procInstId,
            @AuditedParameter(description = WORK_ITEM_ID) String workItemId,
            @AuditedParameter(description = ATTRIBUTE_NAME) String attrName,
            @AuditedParameter(description = ATTRIBUTE_VALUE) Object attrValue) throws WMWorkflowException;

    void completeWorkItem(
            @AuditedParameter(description = PROCESS_INSTANCE_ID) String procInstId,
            @AuditedParameter(description = WORK_ITEM_ID) String workItemId) throws WMWorkflowException;
}
