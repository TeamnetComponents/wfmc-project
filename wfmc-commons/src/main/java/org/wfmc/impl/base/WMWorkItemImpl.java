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
import org.wfmc.wapi.WMWorkItemState;

import java.util.Date;

/**
 * @author Anthony Eden
 * @author Adrian Price
 */
public class WMWorkItemImpl extends WMWorkItem_Abstract {
    private static final long serialVersionUID = 1241879921789764009L;
    private String _activityInstanceId;
    private int _toolIndex;
    private WMWorkItemState _state;
    private WMParticipant _participant;
    private String _performer;

    public WMWorkItemImpl() {
    }

    public WMWorkItemImpl(String id, String name, String activityInstanceId,
        String activityDefinitionId, String processInstanceId,
        String processDefinitionId, int priority, Date startedDate,
        Date targetDate, Date dueDate, Date completedDate,
        int toolIndex, WMWorkItemState state, WMParticipant participant,
        String performer) {

        super(id, name, activityDefinitionId, processDefinitionId,
            processInstanceId, priority, startedDate, targetDate, dueDate,
            completedDate);
        _activityInstanceId = activityInstanceId;
        _toolIndex = toolIndex;
        _state = state;
        _participant = participant;
        _performer = performer;
    }

    public WMWorkItemImpl(String procInstId, String workItemId) {
        this.setId(workItemId);
        this.setProcessInstanceId(procInstId);
    }

    public final WMWorkItemState getState() {
        return _state;
    }

    public final WMParticipant getParticipant() {
        return _participant;
    }

    public final String getActivityInstanceId() {
        return _activityInstanceId;
    }

    public final String getPerformer() {
        return _performer;
    }

    public final int getToolIndex() {
        return _toolIndex;
    }

    public final void setActivityInstanceId(String activityInstanceId) {
        _activityInstanceId = activityInstanceId;
    }

    public final void setParticipant(WMParticipant participant) {
        _participant = participant;
    }

    public final void setPerformer(String performer) {
        _performer = performer;
    }

    public final void setState(String state) {
        _state = WMWorkItemState.valueOf(state);
    }

    public final void setState(WMWorkItemState state) {
        _state = state;
    }

    public final void setToolIndex(int toolIndex) {
        _toolIndex = toolIndex;
    }

    public String toString() {
        return "WMWorkItem[_activityDefinitionId='" +
            getActivityDefinitionId() + '\''
            + ", _name='" + getName() + '\''
            + ", _id='" + getId() + '\''
            + ", _activityInstanceId='" + getActivityInstanceId() + '\''
            + ", _processDefinitionId='" + getProcessDefinitionId() + '\''
            + ", _processInstanceId='" + getProcessInstanceId() + '\''
            + ", _toolIndex='" + _toolIndex + '\''
            + ", _state=" + _state
            + ", _priority=" + getPriority()
            + ", _participant=" + _participant
            + ", _performer='" + _performer + '\''
            + ", _startedDate=" + getStartedDate()
            + ", _targetDate=" + getTargetDate()
            + ", _dueDate=" + getDueDate()
            + ", _completedDate=" + getCompletedDate()
            + ", _temporalStatus=" + getTemporalStatus()
            + ']';
    }

    @Override
    public int hashCode() {
        int result = _activityInstanceId != null ? _activityInstanceId.hashCode() : 0;
        result = 31 * result + _toolIndex;
        result = 31 * result + (_state != null ? _state.hashCode() : 0);
        result = 31 * result + (_participant != null ? _participant.hashCode() : 0);
        result = 31 * result + (_performer != null ? _performer.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WMWorkItemImpl that = (WMWorkItemImpl) o;

        if (_toolIndex != that._toolIndex) return false;
        if (_activityInstanceId != null ? !_activityInstanceId.equals(that._activityInstanceId) : that._activityInstanceId != null)
            return false;
        if (_participant != null ? !_participant.equals(that._participant) : that._participant != null) return false;
        if (_performer != null ? !_performer.equals(that._performer) : that._performer != null) return false;
        if (_state != null ? !_state.equals(that._state) : that._state != null) return false;

        return true;
    }
}