package org.wfmc.audit;

import java.util.Date;

/**
 * Create/Start Process/Subprocess Instance Audit Data.
 * <p><b>Note:</b> All ids are limited to 64 characters
 *
 * @author Antony Lodge
 */
public class WMACreateProcessInstanceData extends WMAAuditBase {
    private static final long serialVersionUID = 3625765091841098523L;

    private String _processDefinitionBusinessName;

    public WMACreateProcessInstanceData() {
    }

    public WMACreateProcessInstanceData(String processDefinitionId,
        String initialProcessInstanceId, String currentProcessInstanceId,
        int processState, WMAEventCode eventCode, String domainId,
        String nodeId, String userId, String roleId, Date timestamp,
        String processDefinitionBusinessName) {

        super(processDefinitionId, null, initialProcessInstanceId,
            currentProcessInstanceId, null, null, processState, eventCode,
            domainId,
            nodeId, userId, roleId, timestamp);
        _processDefinitionBusinessName = processDefinitionBusinessName;
    }

    public WMACreateProcessInstanceData(String processDefinitionId,
        String initialProcessInstanceId, String currentProcessInstanceId,
        int processState, WMAEventCode eventCode, String domainId,
        String nodeId, String userId, String roleId, Date timestamp,
        byte accountCode, short extensionNumber, byte extensionType,
        short extensionLength, short extensionCodePage, Object extensionContent,
        String processDefinitionBusinessName) {

        super(processDefinitionId, null, initialProcessInstanceId,
            currentProcessInstanceId, null, null, processState, eventCode,
            domainId, nodeId, userId, roleId, timestamp, accountCode,
            extensionNumber, extensionType, extensionLength, extensionCodePage,
            extensionContent);
        _processDefinitionBusinessName = processDefinitionBusinessName;
    }

    /**
     * @return Business name of the process definition relevant to the business
     */
    public String getProcessDefinitionBusinessName() {
        return _processDefinitionBusinessName;
    }

    /**
     * @param processDefinitionBusinessName Business name of the process
     *                                      definition relevant to the business
     */
    public void setProcessDefinitionBusinessName(
        String processDefinitionBusinessName) {
        _processDefinitionBusinessName = processDefinitionBusinessName;
    }

    public String toString() {
        return "WMACreateProcessInstanceData@" +
            System.identityHashCode(this) + '[' +
            " cwadPrefix=" + formatCwadPrefix() +
            ", processDefinitionBusinessName='" +
            _processDefinitionBusinessName + '\'' +
            ", cwadSuffix=" + formatCwadSuffix() +
            ']';
    }
}