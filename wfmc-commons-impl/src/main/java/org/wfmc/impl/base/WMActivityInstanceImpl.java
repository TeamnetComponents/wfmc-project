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

import org.wfmc.impl.model.OBEActivityInstance;
import org.wfmc.wapi.WMActivityInstanceState;
import org.wfmc.wapi.WMParticipant;

import java.util.Arrays;
import java.util.Date;

/**
 * @author Anthony Eden
 * @author Adrian Price
 */
public class WMActivityInstanceImpl extends AbstractTask
    implements OBEActivityInstance {

    private static final long serialVersionUID = 4115655353613381281L;

    private WMActivityInstanceState _state;
    protected WMParticipant[] _participants;

    public WMActivityInstanceImpl() {
    }

    public WMActivityInstanceImpl(String id, String name,
        String activityDefinitionId, String processInstanceId,
        String processDefinitionId, int priority, Date startedDate,
        Date targetDate, Date dueDate, Date completedDate,
        WMActivityInstanceState state, WMParticipant[] participants) {

        super(id, name, activityDefinitionId, processDefinitionId,
            processInstanceId, priority, startedDate, targetDate, dueDate,
            completedDate);
        _state = state;
        _participants = participants;
    }

    public final WMActivityInstanceState getState() {
        return _state;
    }

    public void setState(WMActivityInstanceState state) {
        _state = state;
    }

    public final void setState(String state) {
        _state = WMActivityInstanceState.valueOf(state);
    }

    public final WMParticipant[] getParticipants() {
        return _participants;
    }

    public void setParticipants(WMParticipant[] participants) {
        _participants = participants;
    }

    public String toString() {
        return "WMActivityInstance[_activityDefinitionId='" +
            getActivityDefinitionId() + '\''
            + ", _name='" + getName() + '\''
            + ", _id='" + getId() + '\''
            + ", _processInstanceId='" + getProcessInstanceId() + '\''
            + ", _processDefinitionId='" + getProcessDefinitionId() + '\''
            + ", _state=" + _state
            + ", _priority=" + getPriority()
            + ", _participants=" +
            (_participants == null ? null :
                "length:" + _participants.length + Arrays.asList(_participants))
            + ", _startedDate=" + getStartedDate()
            + ", _targetDate=" + getTargetDate()
            + ", _dueDate=" + getDueDate()
            + ", _completedDate=" + getCompletedDate()
            + ", temporalStatus=" + getTemporalStatus()
            + ']';
    }
}