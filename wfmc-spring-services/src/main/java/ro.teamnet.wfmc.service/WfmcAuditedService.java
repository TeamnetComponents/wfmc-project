package ro.teamnet.wfmc.service;

import org.wfmc.wapi.WMConnectInfo;
import org.wfmc.wapi.WMWorkflowException;

/**
 * A facade providing the audited methods of the WfmcService.
 */
public interface WfmcAuditedService {

    /**
     * Connects to a workflow service.
     *
     * @param connectInfo The connection info.  In OBE, pass <code>null</code>
     *                    to skip the JAAS login and retain the current security identity (if any).
     * @throws WMWorkflowException Workflow client exception.
     */
    void connect(WMConnectInfo connectInfo) throws WMWorkflowException;

    /**
     * Disconnects from the workflow service.
     *
     * @throws WMWorkflowException Workflow client exception.
     */
    void disconnect() throws WMWorkflowException;

    /**
     * Creates a new process instance for the given process definition.
     *
     * @param procDefId    The process definition id.
     * @param procInstName The name of the process instance.
     * @return The process instance id.
     * @throws org.wfmc.wapi.WMWorkflowException Workflow client exception.
     */
    String createProcessInstance(String procDefId, String procInstName)
            throws WMWorkflowException;

    /**
     * Sets the specified process instance attribute value.
     *
     * @param procInstId The process instance id.
     * @param attrName   The attribute name.
     * @param attrValue  The attribute value.
     * @throws WMWorkflowException Workflow client exception.
     */
    void assignProcessInstanceAttribute(String procInstId, String attrName,
                                        Object attrValue) throws WMWorkflowException;

    /**
     * Starts a process instance.  The process instance id is retrieved
     * from a prior call to <code>createProcessInstance()</code>
     *
     * @param procInstId The process instance id retrieved in a prior
     *                   call to <code>createProcessInstance()</code>.
     * @return The new process instance id (which may be the same as the old).
     * @throws WMWorkflowException Workflow client exception.
     */
    String startProcess(String procInstId) throws WMWorkflowException;

    /**
     * Aborts a process instance.
     *
     * @param procInstId The ID of the process instance to abort.
     * @throws WMWorkflowException Workflow client exception.
     */
    void abortProcessInstance(String procInstId) throws WMWorkflowException;

    /**
     * Reassigns a work item to another user.
     *
     * @param sourceUser The current user.
     * @param targetUser The new user.
     * @param procInstId The process instance id.
     * @param workItemId The work item id.
     * @throws WMWorkflowException Workflow client exception.
     */
    void reassignWorkItem(String sourceUser, String targetUser,
                          String procInstId, String workItemId) throws WMWorkflowException;

    /**
     * Sets the value of a work item attribute.
     *
     * @param procInstId The process instance id.
     * @param workItemId The work item id.
     * @param attrName   The attribute name.
     * @param attrValue  The attribute value.
     * @throws WMWorkflowException Workflow client exception.
     */
    void assignWorkItemAttribute(String procInstId, String workItemId,
                                 String attrName, Object attrValue) throws WMWorkflowException;

    /**
     * Completes the specified work item.
     *
     * @param procInstId The process instance id.
     * @param workItemId The work item id.
     * @throws WMWorkflowException Workflow client exception.
     */
    void completeWorkItem(String procInstId, String workItemId)
            throws WMWorkflowException;
}
