package org.wfmc.audit;

/**
 * Source Workflow Engine Audit Data.
 *
 * @author Antony Lodge
 */
public abstract class WMAConversationAuditData extends WMARemoteAuditBase {
    private static final long serialVersionUID = -7249229384286210094L;

    private String correspondentContextId;

    protected WMAConversationAuditData() {
    }

    private String correspondentNodeId;

    /**
     * @param cwadPrefix
     * @param messageId
     * @param extensionNumber
     * @param extensionType
     * @param sourceConversationId
     * @param targetConversationId
     */
    protected WMAConversationAuditData(CWADPrefix cwadPrefix, String messageId,
        short extensionNumber, String extensionType,
        String sourceConversationId, String targetConversationId) {

        super(cwadPrefix, messageId, extensionNumber, extensionType,
            sourceConversationId, targetConversationId);
    }

    /**
     * @param cwadPrefix
     * @param messageId
     * @param extensionNumber
     * @param extensionType
     * @param sourceConversationId
     * @param targetConversationId
     * @param correspondentContextId
     * @param correspondentNodeId
     */
    protected WMAConversationAuditData(CWADPrefix cwadPrefix, String messageId,
        short extensionNumber, String extensionType,
        String sourceConversationId, String targetConversationId,
        String correspondentContextId,
        String correspondentNodeId) {

        super(cwadPrefix, messageId, extensionNumber, extensionType,
            sourceConversationId, targetConversationId);

        this.correspondentContextId = correspondentContextId;
        this.correspondentNodeId = correspondentNodeId;
    }

    /**
     * @return ContractId of Workflow Engine accepting the conversation request
     */
    public String getCorrespondentContextId() {
        return correspondentContextId;
    }

    /**
     * @param correspondentContextId ContractId of Workflow Engine accepting
     *                               the conversation request
     */
    public void setCorrespondentContextId(
        String correspondentContextId) {

        this.correspondentContextId = correspondentContextId;
    }

    /**
     * @return Node Id of the Workflow Engine accepting the coversation request
     */
    public String getCorrespondentNodeId() {
        return correspondentNodeId;
    }

    /**
     * @param correspondentNodeId Node Id of the Workflow Engine accepting the
     *                            conversation request
     */
    public void setCorrespondentNodeId(String correspondentNodeId) {
        this.correspondentNodeId = correspondentNodeId;
    }

    public String toString() {
        return "WMAConversationAuditData@" + System.identityHashCode(this) +
            '[' +
            " cwadPrefix=" + formatCwadPrefix() +
            ", messageId=" + getMessageId() +
            ", correspondentContextId=" + correspondentContextId +
            ", correspondentNodeId=" + correspondentNodeId +
            ", extensionNumber=" + getExtensionNumber() +
            ", extensionType='" + getExtensionType() + '\'' +
            ", sourceConversationId='" + getSourceConversationId() + '\'' +
            ", targetConversationId='" + getTargetConversationId() + '\'' +
            ']';
    }
}