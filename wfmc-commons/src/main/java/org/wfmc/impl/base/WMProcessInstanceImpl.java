/*--

 Copyright (C) 2002-2005 Adrian Price.
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
 	adrianprice@sourceforge.net.

 4. Products derived from this software may not be called "OBE" or
 	"Open Business Engine", nor may "OBE" or "Open Business Engine"
 	appear in their name, without prior written permission from
 	Adrian Price (adrianprice@users.sourceforge.net).

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

 For more information on OBE, please see
 <http://obe.sourceforge.net/>.

 */

package org.wfmc.impl.base;


import org.wfmc.wapi.WMParticipant;
import org.wfmc.wapi.WMProcessInstance;
import org.wfmc.wapi.WMProcessInstanceState;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

public class WMProcessInstanceImpl implements WMProcessInstance, Serializable {

    private static final long serialVersionUID = -2212490072805582089L;
    private Date _activityDueDate;
    private Date _activityTargetDate;
    private Date _completedDate;
    private Date _createdDate;
    private Date _dueDate;
    private String _id;
    private String _name;
    private String _parentActivityInstanceId;
    private String _parentProcessInstanceId;
    private WMParticipant[] _participants;
    private int _priority;
    private String _processDefinitionId;
    private Date _startedDate;
    private WMProcessInstanceState _state;
    private Date _targetDate;

    public WMProcessInstanceImpl() {
    }

    public WMProcessInstanceImpl(String name, String id,
                                 String processDefinitionId) {
        this._name = name;
        this._id = String.valueOf(UUID.randomUUID());
        this._processDefinitionId = processDefinitionId;
    }

    public WMProcessInstanceImpl(String name, String id,
                                 String processDefinitionId, String parentActivityInstanceId,
                                 String parentProcessInstanceId, WMProcessInstanceState state,
                                 int priority,
                                 WMParticipant[] participants, Date createdDate, Date startedDate,
                                 Date targetDate, Date dueDate, Date completedDate,
                                 Date activityTargetDate, Date activityDueDate) {

        _name = name;
        _id = id;
        _parentActivityInstanceId = parentActivityInstanceId;
        _parentProcessInstanceId = parentProcessInstanceId;
        _processDefinitionId = processDefinitionId;
        _state = state;
        _priority = priority;
        _participants = participants;
        _createdDate = createdDate;
        _startedDate = startedDate;
        _dueDate = dueDate;
        _targetDate = targetDate;
        _completedDate = completedDate;
        _activityDueDate = activityDueDate;
        _activityTargetDate = activityTargetDate;
    }

    public Date getActivityDueDate() {
        return _activityDueDate;
    }

    public Date getActivityTargetDate() {
        return _activityTargetDate;
    }

    public Date getCompletedDate() {
        return _completedDate;
    }

    public Date getCreatedDate() {
        return _createdDate;
    }

    public Date getDueDate() {
        return _dueDate;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        _id = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public String getParentActivityInstanceId() {
        return _parentActivityInstanceId;
    }

    public void setParentActivityInstanceId(String parentActivityInstanceId) {
        _parentActivityInstanceId = parentActivityInstanceId;
    }

    public String getParentProcessInstanceId() {
        return _parentProcessInstanceId;
    }

    public void setParentProcessInstanceId(String parentProcessInstanceId) {
        _parentProcessInstanceId = parentProcessInstanceId;
    }

    public WMParticipant[] getParticipants() {
        return _participants;
    }

    public void setParticipants(WMParticipant[] participants) {
        _participants = participants;
    }

    public int getPriority() {
        return _priority;
    }

    public String getProcessDefinitionId() {
        return _processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        _processDefinitionId = processDefinitionId;
    }

    public String getProcessInstanceId() {
        return _id;
    }

    public Date getStartedDate() {
        return _startedDate;
    }

    public WMProcessInstanceState getState() {
        return _state;
    }

    public void setState(WMProcessInstanceState state) {
        _state = state;
    }

    public Date getTargetDate() {
        return _targetDate;
    }

    public void setPriority(Integer priority) {
        _priority = priority.intValue();
    }

    public void setState(String state) {
        _state = WMProcessInstanceState.valueOf(state);
    }

    public TemporalStatus getActivityTemporalStatus() {
        return TemporalStatus.computeStatus(null, _activityTargetDate,
                _activityDueDate);
    }

    public final TemporalStatus getTemporalStatus() {
        return TemporalStatus.computeStatus(_completedDate, _targetDate,
                _dueDate);
    }

    public String toString() {
        return "WMProcessInstance[_completedDate=" + _completedDate
                + ", _name='" + _name + '\''
                + ", _id='" + _id + '\''
                + ", _parentActivityInstanceId='" + _parentActivityInstanceId + '\''
                + ", _parentProcessInstanceId='" + _parentProcessInstanceId + '\''
                + ", _processDefinitionId='" + _processDefinitionId + '\''
                + ", _state=" + _state
                + ", _priority=" + _priority
                + ", _participants=" + (_participants == null ? null :
                "length:" + _participants.length + Arrays.asList(_participants))
                + ", _createdDate=" + _createdDate
                + ", _activityDueDate=" + _activityDueDate
                + ", _activityTargetDate=" + _activityTargetDate
                + ", _dueDate=" + _dueDate
                + ", _targetDate=" + _targetDate
                + ", _startedDate=" + _startedDate
                + ']';
    }
}