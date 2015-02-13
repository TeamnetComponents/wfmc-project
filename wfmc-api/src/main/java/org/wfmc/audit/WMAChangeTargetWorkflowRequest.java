package org.wfmc.audit;

import java.util.Date;

/**
 * Change WorkItem State Audit Data as stated in Interface 5 section
 * 8.5.3 of the WfMC standard.
 *
 * @author Antony Lodge
 */
public class WMAChangeTargetWorkflowRequest extends WMATargetWorkflow {
    private static final long serialVersionUID = 5909489844768004913L;

    private String sourceRequestedState;

    public WMAChangeTargetWorkflowRequest() {
    }

    /**
     * @param cwadPrefix
     * @param messageId
     * @param extensionNumber
     * @param extensionType
     * @param sourceConversationId
     * @param targetConversationId
     * @param sourceInitialProcessInstanceId
     * @param sourceCurrentProcessInstanceId
     * @param sourceActivityInstanceId
     * @param sourceTimestamp
     * @param sourceNodeId
     * @param sourceUserId
     * @param sourceRoleId
     * @param sourceProcessDefinitionId
     * @param sourceProcessDefinitionBusinessName
     *
     * @param sourceActivityDefinitionBusinessName
     *
     */
    public WMAChangeTargetWorkflowRequest(CWADPrefix cwadPrefix,
        String messageId, short extensionNumber, String extensionType,
        String sourceConversationId, String targetConversationId,
        String sourceInitialProcessInstanceId,
        String sourceCurrentProcessInstanceId,
        String sourceActivityInstanceId, Date sourceTimestamp,
        String sourceNodeId, String sourceUserId,
        String sourceRoleId, String sourceProcessDefinitionId,
        String sourceProcessDefinitionBusinessName,
        String sourceActivityDefinitionBusinessName) {

        super(cwadPrefix, messageId, extensionNumber, extensionType,
            sourceConversationId, targetConversationId,
            sourceInitialProcessInstanceId, sourceCurrentProcessInstanceId,
            sourceActivityInstanceId, sourceTimestamp, sourceNodeId,
            sourceUserId, sourceRoleId, sourceProcessDefinitionId,
            sourceProcessDefinitionBusinessName,
            sourceActivityDefinitionBusinessName);
    }

    /**
     * @param cwadPrefix
     * @param messageId
     * @param extensionNumber
     * @param extensionType
     * @param sourceConversationId
     * @param targetConversationId
     * @param sourceInitialProcessInstanceId
     * @param sourceCurrentProcessInstanceId
     * @param sourceActivityInstanceId
     * @param sourceTimestamp
     * @param sourceNodeId
     * @param sourceUserId
     * @param sourceRoleId
     * @param sourceProcessDefinitionId
     * @param sourceProcessDefinitionBusinessName
     *
     * @param sourceActivityDefinitionBusinessName
     *
     * @param sourceRequestedState
     */
    public WMAChangeTargetWorkflowRequest(CWADPrefix cwadPrefix,
        String messageId, short extensionNumber, String extensionType,
        String sourceConversationId, String targetConversationId,
        String sourceInitialProcessInstanceId,
        String sourceCurrentProcessInstanceId,
        String sourceActivityInstanceId, Date sourceTimestamp,
        String sourceNodeId, String sourceUserId,
        String sourceRoleId, String sourceProcessDefinitionId,
        String sourceProcessDefinitionBusinessName,
        String sourceActivityDefinitionBusinessName,
        String sourceRequestedState) {

        super(cwadPrefix, messageId, extensionNumber, extensionType,
            sourceConversationId, targetConversationId,
            sourceInitialProcessInstanceId, sourceCurrentProcessInstanceId,
            sourceActivityInstanceId, sourceTimestamp, sourceNodeId,
            sourceUserId, sourceRoleId, sourceProcessDefinitionId,
            sourceProcessDefinitionBusinessName,
            sourceActivityDefinitionBusinessName);

        this.sourceRequestedState = sourceRequestedState;
    }

    /**
     * @return State process instance requested to change to
     */
    public String getSourceRequestedState() {
        return sourceRequestedState;
    }

    /**
     * @param sourceRequestedState State process instance requested to change to
     */
    public void setSourceRequestedState(String sourceRequestedState) {
        this.sourceRequestedState = sourceRequestedState;
    }

    public String toString() {
        return "WMAChangeTargetWorkflowRequest@" +
            System.identityHashCode(this) + '[' +
            " cwadPrefix=" + formatCwadPrefix() +
            ", messageId=" + getMessageId() +
            ", sourceInitialProcessInstanceId=" +
            getSourceInitialProcessInstanceId() +
            ", sourceCurrentProcessInstanceId=" +
            getSourceCurrentProcessInstanceId() +
            ", sourceActivityInstanceId=" + getSourceActivityInstanceId() +
            ", sourceTimestamp=" + getSourceTimestamp() +
            ", sourceNodeId=" + getSourceNodeId() +
            ", sourceUserId=" + getSourceUserId() +
            ", sourceRoleId=" + getSourceRoleId() +
            ", sourceProcessDefinitionId=" +
            getSourceProcessDefinitionId() +
            ", sourceProcessDefinitionBusinessName='" +
            getSourceProcessDefinitionBusinessName() + '\'' +
            ", sourceActivityDefinitionBusinessName='" +
            getSourceActivityDefinitionBusinessName() + '\'' +
            ", sourceRequestedState=" + sourceRequestedState +
            ", extensionNumber=" + getExtensionNumber() +
            ", extensionType='" + getExtensionType() + '\'' +
            ", sourceConversationId='" + getSourceConversationId() + '\'' +
            ", targetConversationId='" + getTargetConversationId() + '\'' +
            ']';
    }
}