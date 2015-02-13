/*--

 Copyright (C) 2002 Anthony Eden.
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

 THIS SOFTWARE IS PROVIdED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR(S) BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIdENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 POSSIBILITY OF SUCH DAMAGE.

 For more information on OBE, please see <http://obe.sourceforge.net/>.

 */

package org.wfmc.audit;

import org.wfmc.wapi.WMProcessInstanceState;

import java.util.Date;

/**
 * @author Antony Lodge
 * @author Adrian Price
 */
public abstract class WMAAuditEntry implements CWADPrefix {
    private static final long serialVersionUID = 4644455394179703302L;
    public static final String WFMC = "WfMC";

    // CWADPrefix fields.
    private String _processDefinitionId;
    private String _activityDefinitionId;
    private String _initialProcessInstanceId;
    private String _currentProcessInstanceId;
    private String _activityInstanceId;
    private String _workItemId;
    private int _processState;
    private WMAEventCode _eventCode;
    private String _domainId;
    private String _nodeId;
    private String _userId;
    private String _roleId;
    private Date _timestamp;
    private String _informationId = WFMC;

    protected static String valueOf(int state) {
        return state == -1 ? null :
            WMProcessInstanceState.valueOf(state).toString();
    }

    protected static int valueOf(String state) {
        return state == null ? -1 :
            WMProcessInstanceState.valueOf(state).value();
    }

    protected WMAAuditEntry() {
    }

    /**
     * Constructor that takes a CWADPrefix object.
     *
     * @param p The prefix.
     */
    protected WMAAuditEntry(CWADPrefix p) {
        setCwadPrefix(p);
    }

    /**
     * Constructor that takes all the CWADPrefix fields.
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
     */
    protected WMAAuditEntry(String processDefinitionId,
        String activityDefinitionId, String initialProcessInstanceId,
        String currentProcessInstanceId, String activityInstanceId,
        String workItemId, int processState, WMAEventCode eventCode,
        String domainId, String nodeId, String userId, String roleId,
        Date timestamp) {

        _processDefinitionId = processDefinitionId;
        _activityDefinitionId = activityDefinitionId;
        _initialProcessInstanceId = initialProcessInstanceId;
        _currentProcessInstanceId = currentProcessInstanceId;
        _activityInstanceId = activityInstanceId;
        _workItemId = workItemId;
        _processState = processState;
        _eventCode = eventCode;
        _domainId = domainId;
        _nodeId = nodeId;
        _userId = userId;
        _roleId = roleId;
        _timestamp = timestamp;
    }

    public final CWADPrefix getCwadPrefix() {
        return this;
    }

    public final void setCwadPrefix(CWADPrefix p) {
        _processDefinitionId = p.getProcessDefinitionId();
        _activityDefinitionId = p.getActivityDefinitionId();
        _initialProcessInstanceId = p.getInitialProcessInstanceId();
        _currentProcessInstanceId = p.getCurrentProcessInstanceId();
        _activityInstanceId = p.getActivityInstanceId();
        _workItemId = p.getWorkItemId();
        _processState = valueOf(p.getProcessState());
        _eventCode = p.getEventCode();
        _domainId = p.getDomainId();
        _nodeId = p.getNodeId();
        _userId = p.getUserId();
        _roleId = p.getRoleId();
        _timestamp = p.getTimestamp();
        _informationId = p.getInformationId();
    }

    public String getProcessDefinitionId() {
        return _processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        _processDefinitionId = processDefinitionId;
    }

    public String getActivityDefinitionId() {
        return _activityDefinitionId;
    }

    public void setActivityDefinitionId(String activityDefinitionId) {
        _activityDefinitionId = activityDefinitionId;
    }

    /**
     * Returns the initial (root) process instance Id.
     *
     * @return The initial process instance Id
     */
    public String getInitialProcessInstanceId() {
        return _initialProcessInstanceId;
    }

    /**
     * Set the initial (root) process instance Id.
     *
     * @param initialProcessInstanceId The initial process instance Id
     */
    public void setInitialProcessInstanceId(
        String initialProcessInstanceId) {

        _initialProcessInstanceId = initialProcessInstanceId;
    }

    /**
     * Returns the current process instance Id.
     *
     * @return The current process instance Id
     */
    public String getCurrentProcessInstanceId() {
        return _currentProcessInstanceId;
    }

    /**
     * Set the current process instance Id.  The string is limited to 64
     * characters according to the WfMC Interface 5 specification.
     *
     * @param currentProcessInstanceId The current process instance Id
     */
    public void setCurrentProcessInstanceId(
        String currentProcessInstanceId) {

//        checkLength(currentProcessInstanceId);
        _currentProcessInstanceId = currentProcessInstanceId;
    }

    /**
     * Returns the activity instance Id.  The activity instance Id is optional so
     * this method may return <code>null</code>.
     *
     * @return The activity instance Id
     */
    public String getActivityInstanceId() {
        return _activityInstanceId;
    }

    /**
     * Set the activity instance Id.  The value can be null if the activity
     * instance Id is not specified.
     *
     * @param activityInstanceId The activity instance Id
     */
    public void setActivityInstanceId(String activityInstanceId) {
        _activityInstanceId = activityInstanceId;
    }

    /**
     * Returns the work item Id.  The work item Id is optional so this method
     * may return <code>null</code>.
     *
     * @return The work item Id.
     */
    public String getWorkItemId() {
        return _workItemId;
    }

    /**
     * Set the work item Id.
     *
     * @param workItemId Work item Id.
     */
    public void setWorkItemId(String workItemId) {
        _workItemId = workItemId;
    }

    /**
     * Returns the process state.
     *
     * @return The process state
     */
    public String getProcessState() {
        return valueOf(_processState);
    }

    /**
     * Set the process state.
     *
     * @param processState The process state
     */
    public void setProcessState(String processState) {
        _processState = valueOf(processState);
    }

    /**
     * Returns the event code.
     *
     * @return The event code
     */
    public WMAEventCode getEventCode() {
        return _eventCode;
    }

    /**
     * Set the event code.
     *
     * @param eventCode The new event code
     */
    public void setEventCode(WMAEventCode eventCode) {
        _eventCode = eventCode;
    }

    public String getDomainId() {
        return _domainId;
    }

    public void setDomainId(String domainId) {
        _domainId = domainId;
    }

    public String getNodeId() {
        return _nodeId;
    }

    public void setNodeId(String nodeId) {
        _nodeId = nodeId;
    }

    public String getUserId() {
        return _userId;
    }

    public void setUserId(String userId) {
        _userId = userId;
    }

    public String getRoleId() {
        return _roleId;
    }

    public void setRoleId(String roleId) {
        _roleId = roleId;
    }

    public Date getTimestamp() {
        return _timestamp;
    }

    public void setTimestamp(Date timestamp) {
        _timestamp = timestamp;
    }

    public final String getInformationId() {
        return _informationId;
    }

    public final void setInformationId(String informationId) {
        _informationId = informationId;
    }

    public String formatCwadPrefix() {
        return
            "CWADPrefix[processDefinitionId=" + _processDefinitionId +
                ", activityDefinitionId=" + _activityDefinitionId +
                ", initialProcessInstanceId=" + _initialProcessInstanceId +
                ", currentProcessInstanceId=" + _currentProcessInstanceId +
                ", activityInstanceId=" + _activityInstanceId +
                ", workItemId=" + _workItemId +
                ", processState=" + getProcessState() +
                ", eventCode=" + _eventCode +
                ", domainId=" + _domainId +
                ", nodeId=" + _nodeId +
                ", userId=" + _userId +
                ", roleId=" + _roleId +
                ", timestamp=" + _timestamp +
                ", informationId=" + _informationId +
                ']';
    }
}