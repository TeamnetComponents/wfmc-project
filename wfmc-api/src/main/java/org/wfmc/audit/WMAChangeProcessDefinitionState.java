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

import org.wfmc.wapi.WMProcessDefinitionState;

import java.util.Date;

/**
 * Section 9.1 of the Interface 5 WFMC standards
 * When the state of the process definition is changed, the information is
 * written to the audit data. A state change may occur as the result of a
 * State Change API command or as the result of internal WFM Engine operations.
 * This would correspond to the WMChangeProcessDefinitionState, which allows
 * process definitions to be changed temporarily to a specific states, such as
 * disabled or enabled.
 *
 * @author Antony Lodge
 */
public class WMAChangeProcessDefinitionState extends WMAAuditBase {
    private static final long serialVersionUID = -1690595508698119387L;

    private int _newProcessDefinitionState;
    private int _previousProcessDefinitionState;

    protected static String valueOf(int state) {
        return state == -1 ? null :
            WMProcessDefinitionState.valueOf(state).toString();
    }

    protected static int valueOf(String state) {
        return state == null ? -1 :
            WMProcessDefinitionState.valueOf(state).value();
    }

    public WMAChangeProcessDefinitionState() {
    }

    public WMAChangeProcessDefinitionState(String processDefinitionId,
        int processState, WMAEventCode eventCode, String domainId,
        String nodeId, String userId, String roleId, Date timestamp,
        int newProcessDefinitionState, int previousProcessDefinitionState) {

        super(processDefinitionId, null, null, null, null, null, processState,
            eventCode, domainId, nodeId, userId, roleId, timestamp);
        _newProcessDefinitionState = newProcessDefinitionState;
        _previousProcessDefinitionState = previousProcessDefinitionState;
    }

    public WMAChangeProcessDefinitionState(String processDefinitionId,
        int processState, WMAEventCode eventCode, String domainId,
        String nodeId, String userId, String roleId, Date timestamp,
        byte accountCode, short extensionNumber,
        byte extensionType, short extensionLength, short extensionCodePage,
        Object extensionContent, int newProcessDefinitionState,
        int previousProcessDefinitionState) {

        super(processDefinitionId, null, null, null, null, null, processState,
            eventCode, domainId, nodeId, userId, roleId, timestamp,
            accountCode, extensionNumber, extensionType,
            extensionLength, extensionCodePage, extensionContent);
        _newProcessDefinitionState = newProcessDefinitionState;
        _previousProcessDefinitionState = previousProcessDefinitionState;
    }

    /**
     * @return New state for the process definition
     */
    public String getNewProcessDefinitionState() {
        return valueOf(_newProcessDefinitionState);
    }

    /**
     * @param newProcessDefinitionState New state for the process definition
     */
    public void setNewProcessDefinitionState(String newProcessDefinitionState) {
        _newProcessDefinitionState = valueOf(newProcessDefinitionState);
    }

    /**
     * @return Previous state for process definition
     */
    public String getPreviousProcessDefinitionState() {
        return valueOf(_previousProcessDefinitionState);
    }

    /**
     * @param previousProcessDefinitionState Previous state for process definition
     */
    public void setPreviousProcessDefinitionState(
        String previousProcessDefinitionState) {

        _previousProcessDefinitionState =
            valueOf(previousProcessDefinitionState);
    }

    public String toString() {
        return "WMAChangeProcessDefinitionState[cwadPrefix=" +
            formatCwadPrefix() +
            ", newProcessDefinitionState=" +
            getNewProcessDefinitionState() +
            ", previousProcessDefinitionState=" +
            getPreviousProcessDefinitionState() +
            ", cwadSuffix=" + formatCwadSuffix() +
            ']';
    }
}