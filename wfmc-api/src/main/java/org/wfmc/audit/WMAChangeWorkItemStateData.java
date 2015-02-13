package org.wfmc.audit;

import org.wfmc.wapi.WMWorkItemState;

import java.util.Date;

/**
 * Change Work Item State Audit Data.
 *
 * @author Antony Lodge
 */
public class WMAChangeWorkItemStateData extends WMAAuditBase {
    private static final long serialVersionUID = 7025075993712917031L;

    private int _workItemState;
    private int _previousWorkItemState;

    protected static String valueOf(int state) {
        return state == -1 ? null : WMWorkItemState.valueOf(state).toString();
    }

    protected static int valueOf(String state) {
        return state == null ? -1 : WMWorkItemState.valueOf(state).value();
    }

    public WMAChangeWorkItemStateData() {
    }

    /**
     * Constructor that takes all fields (including the CWADPrefix).
     *
     * @param processDefinitionId
     * @param activityDefinitionId
     * @param initialProcessInstanceId
     * @param currentProcessInstanceId
     * @param activityInstanceId
     * @param workItemId
     * @param processState
     * @param eventCode
     * @param domainId
     * @param nodeId
     * @param userId
     * @param roleId
     * @param timestamp
     * @param workItemState
     * @param previousWorkItemState
     */
    public WMAChangeWorkItemStateData(String processDefinitionId,
        String activityDefinitionId, String initialProcessInstanceId,
        String currentProcessInstanceId, String activityInstanceId,
        String workItemId, int processState, WMAEventCode eventCode,
        String domainId, String nodeId, String userId, String roleId,
        Date timestamp, int workItemState, int previousWorkItemState) {

        super(processDefinitionId, activityDefinitionId,
            initialProcessInstanceId, currentProcessInstanceId,
            activityInstanceId, workItemId, processState, eventCode, domainId,
            nodeId, userId, roleId, timestamp);
        _workItemState = workItemState;
        _previousWorkItemState = previousWorkItemState;
    }

    /**
     * Constructor that takes all fields (including the CWADPrefix and
     * CWADSuffix).
     *
     * @param processDefinitionId
     * @param activityDefinitionId
     * @param initialProcessInstanceId
     * @param currentProcessInstanceId
     * @param activityInstanceId
     * @param workItemId
     * @param processState
     * @param eventCode
     * @param domainId
     * @param nodeId
     * @param userId
     * @param roleId
     * @param timestamp
     * @param accountCode
     * @param extensionNumber
     * @param extensionType
     * @param extensionLength
     * @param extensionCodePage
     * @param extensionContent
     * @param workItemState
     * @param previousWorkItemState
     */
    public WMAChangeWorkItemStateData(String processDefinitionId,
        String activityDefinitionId, String initialProcessInstanceId,
        String currentProcessInstanceId, String activityInstanceId,
        String workItemId, int processState, WMAEventCode eventCode,
        String domainId,
        String nodeId, String userId, String roleId, Date timestamp,
        byte accountCode, short extensionNumber,
        byte extensionType, short extensionLength, short extensionCodePage,
        Object extensionContent, int workItemState,
        int previousWorkItemState) {

        super(processDefinitionId, activityDefinitionId,
            initialProcessInstanceId, currentProcessInstanceId,
            activityInstanceId, workItemId, processState, eventCode, domainId,
            nodeId, userId, roleId, timestamp, accountCode, extensionNumber,
            extensionType, extensionLength, extensionCodePage,
            extensionContent);
        _workItemState = workItemState;
        _previousWorkItemState = previousWorkItemState;
    }

    /**
     * @return State of the work item
     */
    public String getWorkItemState() {
        return valueOf(_workItemState);
    }

    /**
     * @param workItemState State of the work item
     */
    public void setWorkItemState(String workItemState) {
        _workItemState = valueOf(workItemState);
    }

    /**
     * @return State of the work item
     */
    public String getPreviousWorkItemState() {
        return valueOf(_previousWorkItemState);
    }

    /**
     * @param workItemState State of the work item
     */
    public void setPreviousWorkItemState(String workItemState) {
        _previousWorkItemState = valueOf(workItemState);
    }

    public String toString() {
        return "WMAChangeWorkItemStateData[cwadPrefix=" + formatCwadPrefix() +
            ", workItemState=" + getWorkItemState() +
            ", previousWorkItemState=" + getPreviousWorkItemState() +
            ", cwadSuffix=" + formatCwadSuffix() +
            ']';
    }
}