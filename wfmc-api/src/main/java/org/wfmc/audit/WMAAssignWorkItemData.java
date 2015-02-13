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

import java.util.Date;

/**
 * Assign/Reassign WorkItem Audit Data
 *
 * @author Antony Lodge
 */
public class WMAAssignWorkItemData extends WMAChangeWorkItemStateData {
    private static final long serialVersionUID = 6630195259692364092L;

    private String _targetDomainId;
    private String _targetNodeId;
    private String _targetUserId;
    private String _targetRoleId;

    public WMAAssignWorkItemData() {
    }

    public WMAAssignWorkItemData(String processDefinitionId,
        String activityDefinitionId, String initialProcessInstanceId,
        String currentProcessInstanceId, String activityInstanceId,
        int processState, WMAEventCode eventCode, String domainId,
        String nodeId, String userId, String roleId, Date timestamp,
        String workItemId, int workItemState, String targetDomainId,
        String targetNodeId, String targetUserId, String targetRoleId) {

        super(processDefinitionId, activityDefinitionId,
            initialProcessInstanceId, currentProcessInstanceId,
            activityInstanceId, workItemId, processState, eventCode, domainId,
            nodeId, userId, roleId, timestamp, workItemState, workItemState);
        _targetDomainId = targetDomainId;
        _targetNodeId = targetNodeId;
        _targetUserId = targetUserId;
        _targetRoleId = targetRoleId;
    }

    public WMAAssignWorkItemData(String processDefinitionId,
        String activityDefinitionId, String initialProcessInstanceId,
        String currentProcessInstanceId, String activityInstanceId,
        String workItemId, int processState, WMAEventCode eventCode,
        String domainId, String nodeId, String userId, String roleId,
        Date timestamp, byte accountCode, short extensionNumber,
        byte extensionType,
        short extensionLength, short extensionCodePage, Object extensionContent,
        int workItemState, String targetDomainId,
        String targetNodeId, String targetUserId, String targetRoleId) {

        super(processDefinitionId, activityDefinitionId,
            initialProcessInstanceId, currentProcessInstanceId,
            activityInstanceId, workItemId, processState, eventCode, domainId,
            nodeId, userId, roleId, timestamp, accountCode, extensionNumber,
            extensionType, extensionLength, extensionCodePage,
            extensionContent, workItemState, workItemState);
        _targetDomainId = targetDomainId;
        _targetNodeId = targetNodeId;
        _targetUserId = targetUserId;
        _targetRoleId = targetRoleId;
    }

    /**
     * @return Domain Id for user being assigned work item
     */
    public String getTargetDomainId() {
        return _targetDomainId;
    }

    /**
     * @param targetDomainId Domain Id for user being assigned work item
     */
    public void setTargetDomainId(String targetDomainId) {
        _targetDomainId = targetDomainId;
    }

    /**
     * @return Node Id for user being assigned work item
     */
    public String getTargetNodeId() {
        return _targetNodeId;
    }

    /**
     * @param targetNodeId Node Id for user being assigned work item
     */
    public void setTargetNodeId(String targetNodeId) {
        _targetNodeId = targetNodeId;
    }

    /**
     * @return User Id for user being assigned work item
     */
    public String getTargetUserId() {
        return _targetUserId;
    }

    /**
     * @param targetUserId User Id for user being assigned work item
     */
    public void setTargetUserId(String targetUserId) {
        _targetUserId = targetUserId;
    }

    /**
     * @return Role Id for user being assigned work item
     */
    public String getTargetRoleId() {
        return _targetRoleId;
    }

    /**
     * @param targetRoleId Role Id for user being assigned work item
     */
    public void setTargetRoleId(String targetRoleId) {
        _targetRoleId = targetRoleId;
    }

    public String toString() {
        return "WMAAssignWorkItemData@" + System.identityHashCode(this) + '[' +
            " cwadPrefix=" + formatCwadPrefix() +
            ", activityInstanceId=" + getActivityInstanceId() +
            ", workItemId=" + getWorkItemId() +
            ", workItemState='" + getWorkItemState() + '\'' +
            ", targetDomainId=" + _targetDomainId +
            ", targetNodeId=" + _targetNodeId +
            ", targetUserId=" + _targetUserId +
            ", targetRoleId=" + _targetRoleId +
            ", cwadSuffix=" + formatCwadSuffix() +
            ']';
    }
}