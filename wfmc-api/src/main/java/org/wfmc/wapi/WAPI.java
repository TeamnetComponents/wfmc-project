/*--

 Copyright (C) 2002 Anthony Eden, Adrian Price.
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:

 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions, and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions, and the disclaimer that follows
    these conditions in the documentation and/or other materials
    provided with the distribution.

 3. The names "OBE" and "Open Business Engine" must not be used to
    endorse or promote products derived from this software without prior
    written permission.  For written permission, please contact
    me@anthonyeden.com.

 4. Products derived from this software may not be called "OBE" or
    "Open Business Engine", nor may "OBE" or "Open Business Engine"
    appear in their name, without prior written permission from
    Anthony Eden (me@anthonyeden.com).

 In addition, I request (but do not require) that you include in the
 end-user documentation provided with the redistribution and/or in the
 software itself an acknowledgement equivalent to the following:
     "This product includes software developed by
      Anthony Eden (http://www.anthonyeden.com/)."

 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR(S) BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 POSSIBILITY OF SUCH DAMAGE.

 For more information on OBE, please see <http://obe.sourceforge.net/>.

 */

package org.wfmc.wapi;

/**
 * Enables client applications to connect to and interact with a workflow
 * engine.
 * <p/>
 * This interface is based on the WfMC's Interface 2 Client WAPI
 * specification.  Some of the methods have been modified from the original
 * specification to fit within the normal design of Java applications.  For
 * instance, the WfMC specification functions always return an error object
 * (even for success) and uses out parameters to return values.  This
 * interface returns the value and throws an exception when an error occurs.
 * If no error occurs then an exception is not thrown.  The C WAPI uses
 * query handles and iterator functions to retrieve collections; this Java
 * binding uses {@link WMIterator}.
 *
 * @author Anthony Eden
 * @author Adrian Price
 */
public interface WAPI {
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
     * Opens a list of process definitions.  The items in the list can be
     * retrieved sequentially in a typesafe way by calling the iterator's
     * <code>tsNext()</code> method.
     *
     * @param filter    The filter or null.
     * @param countFlag True to return count value.
     * @return An iterator to access the {@link WMProcessDefinition} objects.
     * @throws WMWorkflowException Workflow client exception.
     */
    WMProcessDefinitionIterator listProcessDefinitions(WMFilter filter,
        boolean countFlag) throws WMWorkflowException;

    /**
     * Opens a list of process definition states.  The items in the state list
     * can be retrieved sequentially in a typesafe way by calling the iterator's
     * <code>tsNext()</code> method.
     *
     * @param procDefId The unique process definition ID.
     * @param filter    The filter or null.
     * @param countFlag True to return count value.
     * @return An iterator to access the {@link WMProcessDefinitionState}
     *         objects.
     * @throws WMWorkflowException Workflow client exception.
     */
    WMProcessDefinitionStateIterator listProcessDefinitionStates(
        String procDefId, WMFilter filter, boolean countFlag)
        throws WMWorkflowException;

    /**
     * Changes the process definition state.
     *
     * @param procDefId The process definition id.
     * @param newState  The new process definition state.
     * @throws WMWorkflowException Workflow client exception.
     */
    void changeProcessDefinitionState(String procDefId,
        WMProcessDefinitionState newState) throws WMWorkflowException;

    /**
     * Creates a new process instance for the given process definition.
     *
     * @param procDefId    The process definition id.
     * @param procInstName The name of the process instance.
     * @return The process instance id.
     * @throws WMWorkflowException Workflow client exception.
     */
    String createProcessInstance(String procDefId, String procInstName)
        throws WMWorkflowException;

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
     * Terminates a process instance.
     *
     * @param procInstId The process instance id.
     * @throws WMWorkflowException Workflow client exception.
     */
    void terminateProcessInstance(String procInstId)
        throws WMWorkflowException;

    /**
     * Opens a list of process instance states.  The items in the state list
     * can be retrieved sequentially in a typesafe way by calling the iterator's
     * <code>tsNext()</code> method.
     *
     * @param procInstId The unique process instance ID.
     * @param filter     The filter or null.
     * @param countFlag  True to return count value.
     * @return An iterator to access the {@link WMProcessInstanceState} objects.
     * @throws WMWorkflowException Workflow client exception.
     */
    WMProcessInstanceStateIterator listProcessInstanceStates(String procInstId,
        WMFilter filter, boolean countFlag) throws WMWorkflowException;

    /**
     * Changes the state of a process instance.
     *
     * @param procInstId The process instance id.
     * @param newState   The new process instance state.
     * @throws WMWorkflowException Workflow client exception.
     */
    void changeProcessInstanceState(String procInstId,
        WMProcessInstanceState newState) throws WMWorkflowException;

    /**
     * Opens a list of process instance attributes.  The items in the
     * attribute list can be retrieved sequentially in a typesafe way by calling
     * the iterator's <code>tsNext()</code> method.
     *
     * @param filter    The filter or null.
     * @param countFlag True to return count value.
     * @return An iterator to access the {@link WMAttribute} objects.
     * @throws WMWorkflowException Workflow client exception.
     */
    WMAttributeIterator listProcessInstanceAttributes(String procInstId,
        WMFilter filter, boolean countFlag) throws WMWorkflowException;

    /**
     * Gets the specified process instance attribute value.
     *
     * @param procInstId The process instance id.
     * @param attrName   The attribute name.
     * @return The attribute.
     * @throws WMWorkflowException Workflow client exception.
     */
    WMAttribute getProcessInstanceAttributeValue(String procInstId,
        String attrName) throws WMWorkflowException;

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
     * Opens a list of process instance states.  The items in the state list
     * can be retrieved sequentially in a typesafe way by calling the iterator's
     * <code>tsNext()</code> method.
     *
     * @param procInstId The process instance id.
     * @param actInstId  The activity instance id.
     * @param filter     The filter or null.
     * @param countFlag  True to return count value.
     * @return An iterator to access the {@link WMActivityInstanceState} objects.
     * @throws WMWorkflowException Workflow client exception.
     */
    WMActivityInstanceStateIterator listActivityInstanceStates(
        String procInstId, String actInstId, WMFilter filter, boolean countFlag)
        throws WMWorkflowException;

    /**
     * Changes the state of an activity instance.
     *
     * @param procInstId The process instance id.
     * @param actInstId  The activity instance id.
     * @param newState   The new activity instance state.
     * @throws WMWorkflowException Workflow client exception.
     */
    void changeActivityInstanceState(String procInstId, String actInstId,
        WMActivityInstanceState newState) throws WMWorkflowException;

    /**
     * Opens a list of activity instance attributes.  The items in the
     * attribute list can be retrieved sequentially in a typesafe way by calling
     * the iterator's <code>tsNext()</code> method.
     *
     * @param procInstId The process instance id.
     * @param actInstId  The activity instance id.
     * @param filter     The filter or null.
     * @param countFlag  True to return count value.
     * @return An iterator to access the {@link WMAttribute} objects.
     * @throws WMWorkflowException Workflow client exception.
     */
    WMAttributeIterator listActivityInstanceAttributes(String procInstId,
        String actInstId, WMFilter filter, boolean countFlag)
        throws WMWorkflowException;

    /**
     * Gets the value of an activity instance attribute.
     *
     * @param procInstId The process instance id.
     * @param actInstId  The activity instance id.
     * @param attrName   The attribute name.
     * @return The attribute.
     * @throws WMWorkflowException Workflow client exception.
     */
    WMAttribute getActivityInstanceAttributeValue(String procInstId,
        String actInstId, String attrName) throws WMWorkflowException;

    /**
     * Sets the value of an activity instance attribute.
     *
     * @param procInstId The process instance id.
     * @param actInstId  The activity instance id.
     * @param attrName   The attribute name.
     * @param attrValue  The attribute value.
     * @throws WMWorkflowException Workflow client exception.
     */
    void assignActivityInstanceAttribute(String procInstId,
        String actInstId, String attrName, Object attrValue)
        throws WMWorkflowException;

    /**
     * Opens a list of process instances.  The items in the list
     * can be retrieved sequentially in a typesafe way by calling the iterator's
     * <code>tsNext()</code> method.
     *
     * @param filter    The filter or null.
     * @param countFlag True to return count value.
     * @return An iterator to access the {@link WMProcessInstance} objects.
     * @throws WMWorkflowException Workflow client exception.
     */
    WMProcessInstanceIterator listProcessInstances(WMFilter filter,
        boolean countFlag) throws WMWorkflowException;

    /**
     * Retrieves a process instance.
     *
     * @param procInstId The process instance id.
     * @return The process instance.
     * @throws WMWorkflowException Workflow client exception.
     */
    WMProcessInstance getProcessInstance(String procInstId)
        throws WMWorkflowException;

    /**
     * Opens a list of activity instances.  The items in the list can be
     * retrieved sequentially in a typesafe way by calling the iterator's
     * <code>tsNext()</code> method.
     *
     * @param filter    The filter or null.
     * @param countFlag True to return count value.
     * @return An iterator to access the {@link WMActivityInstance} objects.
     * @throws WMWorkflowException Workflow client exception.
     */
    WMActivityInstanceIterator listActivityInstances(WMFilter filter,
        boolean countFlag) throws WMWorkflowException;

    /**
     * Retrieves an activity instance.
     *
     * @param procInstId The process instance id.
     * @param actInstId  The activity instance id.
     * @return The activity instance.
     * @throws WMWorkflowException Workflow client exception.
     */
    WMActivityInstance getActivityInstance(String procInstId, String actInstId)
        throws WMWorkflowException;

    /**
     * Opens a worklist.  The items in the list can be retrieved
     * sequentially using the iterator's <code>tsNext()</code> method.
     *
     * @param filter    The filter or null.
     * @param countFlag True to return count value.
     * @return An iterator to access the {@link WMWorkItem} objects.
     * @throws WMWorkflowException Workflow client exception.
     */
    WMWorkItemIterator listWorkItems(WMFilter filter, boolean countFlag)
        throws WMWorkflowException;

    /**
     * Retrieves a work item.
     *
     * @param procInstId The process instance id.
     * @param workItemId The work item id.
     * @return The work item.
     * @throws WMWorkflowException Workflow client exception.
     */
    WMWorkItem getWorkItem(String procInstId, String workItemId)
        throws WMWorkflowException;

    /**
     * Completes the specified work item.
     *
     * @param procInstId The process instance id.
     * @param workItemId The work item id.
     * @throws WMWorkflowException Workflow client exception.
     */
    void completeWorkItem(String procInstId, String workItemId)
        throws WMWorkflowException;

    /**
     * Opens a list of work item states.  The items in the
     * work item states list can be retrieved sequentially in a typesafe way by
     * calling the iterator's <code>tsNext()</code> method.
     * <p/>
     * N.B. This function is poorly documented in the WfMC specification, which
     * contains several 'copy/paste' errors.
     * <p/>
     * N.B. The signature of this method differs from that described in the
     * WAPI2 specification, in that it has a procInstId parameter.  This
     * is because the specification's definition for this function is clearly in
     * error, having been copied badly from that for
     * WMOpenProcessDefinitionStatesList. The other WAPI functions that refer to
     * work items invariably require the procInstId parameter.
     *
     * @param procInstId The process instance id.
     * @param workItemId The process instance id.
     * @param filter     The filter or null.
     * @param countFlag  True to return count value.
     * @return An iterator to access the {@link WMWorkItemState} objects.
     * @throws WMWorkflowException Workflow client exception.
     */
    WMWorkItemStateIterator listWorkItemStates(String procInstId,
        String workItemId, WMFilter filter, boolean countFlag)
        throws WMWorkflowException;

    /**
     * Changes the state of a work item.
     * <em>N.B. The signature of this method differs from that described in the
     * WAPI2 specification, in that it has a <code>procInstId</code> parameter.
     * This is because the specification's definition for this function is
     * clearly in error, having been badly copied from that for
     * <code>WMChangeDefinitionState</code>. The other WAPI functions that refer
     * to work items invariably require the <code>procInstId</code> parameter.
     * </em>
     *
     * @param procInstId The process instance id.
     * @param workItemId The work item id.
     * @param newState   The new work item state.
     * @throws WMWorkflowException Workflow client exception.
     */
    void changeWorkItemState(String procInstId, String workItemId,
        WMWorkItemState newState) throws WMWorkflowException;

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
     * Opens a list of work item attributes.  The items in the
     * attribute list can be retrieved sequentially in a typesafe way by calling
     * the iterator's <code>tsNext()</code> method.
     *
     * @param procInstId The process instance id.
     * @param workItemId The work item id.
     * @param filter     The filter or null.
     * @param countFlag  True to return count value.
     * @return An iterator to access the {@link WMAttribute} objects.
     * @throws WMWorkflowException Workflow client exception.
     */
    WMAttributeIterator listWorkItemAttributes(String procInstId,
        String workItemId, WMFilter filter, boolean countFlag)
        throws WMWorkflowException;

    /**
     * Retrieves the value of a work item attribute.
     *
     * @param procInstId The process instance id.
     * @param workItemId The work item id.
     * @param attrName   The attribute name.
     * @return The attribute.
     * @throws WMWorkflowException Workflow client exception.
     */
    WMAttribute getWorkItemAttributeValue(String procInstId, String workItemId,
        String attrName) throws WMWorkflowException;

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
     * Changes the state of selected process instances.
     *
     * @param procDefId The ID of the process definition for which
     *                  instances are to be changed.
     * @param filter    A filter specification; can be <code>null</code>.
     * @param newState  The new state to apply.
     * @throws WMWorkflowException Workflow client exception.
     */
    void changeProcessInstancesState(String procDefId, WMFilter filter,
        WMProcessInstanceState newState) throws WMWorkflowException;

    /**
     * Changes the state of selected activity instances.
     *
     * @param procDefId The ID of the process definition for which
     *                  activity instances are to be changed.
     * @param actDefId  The ID of the activity definition for which
     *                  instances are to be changed.
     * @param filter    A filter specification; can be <code>null</code>.
     * @param newState  The new state to apply.
     * @throws WMWorkflowException Workflow client exception.
     */
    void changeActivityInstancesState(String procDefId, String actDefId,
        WMFilter filter, WMActivityInstanceState newState)
        throws WMWorkflowException;

    /**
     * Terminates a group of process instances.
     *
     * @param procDefId The ID of the process definition for which
     *                  instances are to be terminated.
     * @param filter    A filter specification; can be <code>null</code>.
     * @throws WMWorkflowException Workflow client exception.
     */
    void terminateProcessInstances(String procDefId, WMFilter filter)
        throws WMWorkflowException;

    /**
     * Assigns an attribute value for a group of process instances.
     *
     * @param procDefId The ID of the process definition for which
     *                  instance attributes are to be assigned.
     * @param filter    A filter specification; can be <code>null</code>.
     * @param attrName  The attribute name.
     * @param attrValue The attribute value.
     * @throws WMWorkflowException Workflow client exception.
     */
    void assignProcessInstancesAttribute(String procDefId, WMFilter filter,
        String attrName, Object attrValue) throws WMWorkflowException;

    /**
     * Assigns an attribute value for a group of process instances.
     *
     * @param procDefId The ID of the process definition for which
     *                  activity instance attributes are to be assigned.
     * @param actDefId  The ID of the activity definition for which
     *                  instance attributes are to be assigned.
     * @param filter    A filter specification; can be <code>null</code>.
     * @param attrName  The attribute name.
     * @param attrValue The attribute value.
     * @throws WMWorkflowException Workflow client exception.
     */
    void assignActivityInstancesAttribute(String procDefId, String actDefId,
        WMFilter filter, String attrName, Object attrValue)
        throws WMWorkflowException;

    /**
     * Aborts a group of process instances.
     *
     * @param procDefId The ID of the process definition for which
     *                  instances are to be aborted.
     * @param filter    A filter specification; can be <code>null</code>.
     * @throws WMWorkflowException Workflow client exception.
     */
    void abortProcessInstances(String procDefId, WMFilter filter)
        throws WMWorkflowException;

    /**
     * Aborts a process instance.
     *
     * @param procInstId The ID of the process instance to abort.
     * @throws WMWorkflowException Workflow client exception.
     */
    void abortProcessInstance(String procInstId) throws WMWorkflowException;

    /**
     * Connects to the tool agent for a specified work item.
     *
     * @return tool agent handle.
     * @throws WMWorkflowException
     */
//    int connectToolAgent(String procInstId, String workItemId)
//        throws WMWorkflowException;

    /**
     *
     * @throws WMWorkflowException
     */
//    void disconnectToolAgent(int toolAgentHandle) throws WMWorkflowException;

    /**
     * Invokes a client-side tool.
     *
     * @param toolAgentHandle
     * @param appName         The tag name of the tool.
     * @param procInstId      The ID of the associated process instance.
     * @param workItemId      The ID of the associated work item.
     * @param parameters      Parameters to pass to the tool.
     * @param appMode         Application mode, one of:
     * @throws WMWorkflowException Workflow client exception.
     */
    void invokeApplication(int toolAgentHandle, String appName,
        String procInstId, String workItemId, Object[] parameters, int appMode)
        throws WMWorkflowException;

    /**
     * Requests the status of an invoked tool.
     *
     * @param toolAgentHandle The tool handle, returned by the prior call
     *                        to {@link #invokeApplication}.
     * @param procInstId      The ID of the associated process instance.
     * @param workItemId      The ID of the associated work item.
     * @param status
     * @return The status of specified tool.
     * @throws WMWorkflowException Workflow client exception.
     */
    WMAttribute[] requestAppStatus(int toolAgentHandle, String procInstId,
        String workItemId, int[] status) throws WMWorkflowException;

    /**
     * Terminates a running tool.
     *
     * @param toolAgentHandle
     * @param procInstId      The ID of the associated process instance.
     * @param workItemId      The ID of the associated work item.
     * @throws WMWorkflowException Workflow client exception.
     */
    void terminateApp(int toolAgentHandle, String procInstId,
        String workItemId) throws WMWorkflowException;
}