package ro.teamnet.wfmc.service;

import org.wfmc.wapi.WMWorkflowException;
import org.wfmc.wapi2.WAPI2;
import ro.teamnet.audit.annotation.AuditedParameter;

import static ro.teamnet.wfmc.audit.constants.WfmcAuditedParameter.*;

/**
 * A facade for the auditable implementations of WfmcService.
 * Implementations annotated as Spring components will be handled by the available audit aspects.
 * The {@link ro.teamnet.audit.annotation.Auditable} annotation must be provided in the implementing classes for each
 * auditable method.
 */
public interface WfmcAuditedService extends WAPI2 {
    /**
     * Retrieves the current user identification.
     * If no user is connected to the WM, method returns {@code null}.
     *
     * @return current user identification, {@code null} if not connected.
     */
    String getUserIdentification();

    /**
     * @inheritDoc
     */
    String createProcessInstance(
            @AuditedParameter(description = PROCESS_DEFINITION_ID) String procDefId,
            @AuditedParameter(description = PROCESS_INSTANCE_NAME) String procInstName) throws WMWorkflowException;

    /**
     * @inheritDoc
     */
    void assignProcessInstanceAttribute(
            @AuditedParameter(description = PROCESS_INSTANCE_ID) String procInstId,
            @AuditedParameter(description = ATTRIBUTE_NAME) String attrName,
            @AuditedParameter(description = ATTRIBUTE_VALUE) Object attrValue) throws WMWorkflowException;

    /**
     * @inheritDoc
     */
    String startProcess(@AuditedParameter(description = PROCESS_INSTANCE_ID) String procInstId)
            throws WMWorkflowException;

    /**
     * @inheritDoc
     */
    void abortProcessInstance(@AuditedParameter(description = PROCESS_INSTANCE_ID) String procInstId)
            throws WMWorkflowException;

    /**
     * @inheritDoc
     */
    void reassignWorkItem(
            @AuditedParameter(description = SOURCE_USER) String sourceUser,
            @AuditedParameter(description = TARGET_USER) String targetUser,
            @AuditedParameter(description = PROCESS_INSTANCE_ID) String procInstId,
            @AuditedParameter(description = WORK_ITEM_ID) String workItemId) throws WMWorkflowException;

    /**
     * @inheritDoc
     */
    void assignWorkItemAttribute(
            @AuditedParameter(description = PROCESS_INSTANCE_ID) String procInstId,
            @AuditedParameter(description = WORK_ITEM_ID) String workItemId,
            @AuditedParameter(description = ATTRIBUTE_NAME) String attrName,
            @AuditedParameter(description = ATTRIBUTE_VALUE) Object attrValue) throws WMWorkflowException;

    /**
     * @inheritDoc
     */
    void completeWorkItem(
            @AuditedParameter(description = PROCESS_INSTANCE_ID) String procInstId,
            @AuditedParameter(description = WORK_ITEM_ID) String workItemId) throws WMWorkflowException;
}
