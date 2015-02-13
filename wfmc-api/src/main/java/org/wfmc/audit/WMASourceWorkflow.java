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

/**
 * Section 8 of the WfMC Interface 5 standard. This is simply a base class
 * that other classes extend.
 *
 * @author Antony Lodge
 */
public abstract class WMASourceWorkflow extends WMARemoteAuditBase {
    private static final long serialVersionUID = 6083932758362287160L;

    private String sourceActivityDefinitionBusinessName;
    private String targetProcessInstanceId;
    private String targetProcessDefinitionBusinessName;
    private String targetNodeId;
    private String targetUserId;
    private String targetRoleId;

    protected WMASourceWorkflow() {
    }

    /**
     * @param cwadPrefix
     * @param messageId
     * @param extensionNumber
     * @param extensionType
     * @param sourceConversationId
     * @param targetConversationId
     * @param sourceActivityDefinitionBusinessName
     *
     * @param targetProcessInstanceId
     * @param targetProcessDefinitionBusinessName
     *
     * @param targetNodeId
     * @param targetUserId
     * @param targetRoleId
     */
    protected WMASourceWorkflow(CWADPrefix cwadPrefix, String messageId,
        short extensionNumber, String extensionType,
        String sourceConversationId, String targetConversationId,
        String sourceActivityDefinitionBusinessName,
        String targetProcessInstanceId,
        String targetProcessDefinitionBusinessName, String targetNodeId,
        String targetUserId, String targetRoleId) {

        super(cwadPrefix, messageId, extensionNumber, extensionType,
            sourceConversationId, targetConversationId);

        this.sourceActivityDefinitionBusinessName =
            sourceActivityDefinitionBusinessName;
        this.targetProcessInstanceId = targetProcessInstanceId;
        this.targetProcessDefinitionBusinessName =
            targetProcessDefinitionBusinessName;
        this.targetNodeId = targetNodeId;
        this.targetUserId = targetUserId;
        this.targetRoleId = targetRoleId;
    }

    /**
     * @return WMAObjectName Business name of current activity on the source
     *         engine originating the request to create a new process instance
     */
    public String getSourceActivityDefinitionBusinessName() {
        return sourceActivityDefinitionBusinessName;
    }

    /**
     * @param sourceActivityDefinitionBusinessName
     *         WMAObjectName Business name
     *         of current activity on the source engine originating the request
     *         to create a new process instance
     */
    public void setSourceActivityDefinitionBusinessName(
        String sourceActivityDefinitionBusinessName) {

        this.sourceActivityDefinitionBusinessName =
            sourceActivityDefinitionBusinessName;
    }

    /**
     * @return process instance created on the target Workflow Engine
     */
    public String getTargetProcessInstanceId() {
        return targetProcessInstanceId;
    }

    /**
     * @param targetProcessInstanceId process instance created on the target
     *                                Workflow Engine
     */
    public void setTargetProcessInstanceId(
        String targetProcessInstanceId) {
        this.targetProcessInstanceId = targetProcessInstanceId;
    }

    /**
     * @return null | as supplied by target Workflow Engine
     */
    public String getTargetProcessDefinitionBusinessName() {
        return targetProcessDefinitionBusinessName;
    }

    /**
     * @param targetProcessDefinitionBusinessName
     *         null | as supplied by target
     *         Workflow Engine
     */
    public void setTargetProcessDefinitionBusinessName(
        String targetProcessDefinitionBusinessName) {

        this.targetProcessDefinitionBusinessName =
            targetProcessDefinitionBusinessName;
    }

    /**
     * @return Node Id of Workflow Engine accepting the conversation
     */
    public String getTargetNodeId() {
        return targetNodeId;
    }

    /**
     * @param targetNodeId Node Id of Workflow Engine accepting the conversation
     */
    public void setTargetNodeId(String targetNodeId) {
        this.targetNodeId = targetNodeId;
    }

    /**
     * @return null | Id of remote user requested // O perform the process
     */
    public String getTargetUserId() {
        return targetUserId;
    }

    /**
     * @param targetUserId null | Id of remote user requested // O perform the
     *                     process
     */
    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    /**
     * @return null | IS of remote role requested to perform the process
     */
    public String getTargetRoleId() {
        return targetRoleId;
    }

    /**
     * @param targetRoleId null | IS of remote role requested to perform the
     *                     process
     */
    public void setTargetRoleId(String targetRoleId) {
        this.targetRoleId = targetRoleId;
    }

    public String toString() {
        return "WMASourceWorkflow@" + System.identityHashCode(this) + '[' +
            " cwadPrefix=" + formatCwadPrefix() +
            ", messageId=" + getMessageId() +
            ", sourceActivityDefinitionBusinessName='" +
            sourceActivityDefinitionBusinessName + '\'' +
            ", targetProcessInstanceId=" + targetProcessInstanceId +
            ", targetProcessDefinitionBusinessName='" +
            targetProcessDefinitionBusinessName + '\'' +
            ", targetNodeId=" + targetNodeId +
            ", targetUserId=" + targetUserId +
            ", targetRoleId=" + targetRoleId +
            ", extensionNumber=" + getExtensionNumber() +
            ", extensionType='" + getExtensionType() + '\'' +
            ", sourceConversationId='" + getSourceConversationId() + '\'' +
            ", targetConversationId='" + getTargetConversationId() + '\'' +
            ']';
    }
}