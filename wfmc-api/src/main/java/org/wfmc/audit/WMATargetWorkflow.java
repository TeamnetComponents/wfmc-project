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
 * @author Antony Lodge
 */
public abstract class WMATargetWorkflow extends WMARemoteAuditBase {
    private static final long serialVersionUID = -1252315675931687942L;

    private String sourceInitialProcessInstanceId;
    private String sourceCurrentProcessInstanceId;
    private String sourceActivityInstanceId;
    private Date sourceTimestamp;
    private String sourceNodeId;
    private String sourceUserId;
    private String sourceRoleId;
    private String sourceProcessDefinitionId;
    private String sourceProcessDefinitionBusinessName;
    private String sourceActivityDefinitionBusinessName;

    protected WMATargetWorkflow() {
    }

    /**
     * @param cwadPrefix
     * @param messageId
     * @param extensionNumber
     * @param extensionType
     * @param sourceConversationId
     * @param targetConversationId
     * @param sourceInitialProcessInstanceId
     * @param sourceCurrentProcessInstanceId
     * @param sourceActivityInstanceId
     * @param sourceTimestamp
     * @param sourceNodeId
     * @param sourceUserId
     * @param sourceRoleId
     * @param sourceProcessDefinitionId
     * @param sourceProcessDefinitionBusinessName
     *
     * @param sourceActivityDefinitionBusinessName
     *
     */
    protected WMATargetWorkflow(CWADPrefix cwadPrefix, String messageId,
        short extensionNumber, String extensionType,
        String sourceConversationId, String targetConversationId,
        String sourceInitialProcessInstanceId,
        String sourceCurrentProcessInstanceId,
        String sourceActivityInstanceId, Date sourceTimestamp,
        String sourceNodeId, String sourceUserId,
        String sourceRoleId, String sourceProcessDefinitionId,
        String sourceProcessDefinitionBusinessName,
        String sourceActivityDefinitionBusinessName) {

        super(cwadPrefix, messageId, extensionNumber, extensionType,
            sourceConversationId, targetConversationId);

        this.sourceInitialProcessInstanceId = sourceInitialProcessInstanceId;
        this.sourceCurrentProcessInstanceId = sourceCurrentProcessInstanceId;
        this.sourceActivityInstanceId = sourceActivityInstanceId;
        this.sourceTimestamp = sourceTimestamp;
        this.sourceNodeId = sourceNodeId;
        this.sourceUserId = sourceUserId;
        this.sourceRoleId = sourceRoleId;
        this.sourceProcessDefinitionId = sourceProcessDefinitionId;
        this.sourceProcessDefinitionBusinessName =
            sourceProcessDefinitionBusinessName;
        this.sourceActivityDefinitionBusinessName =
            sourceActivityDefinitionBusinessName;
    }

    /**
     * @return Initial Process Instance Id of source Workflow Engine
     */
    public String getSourceInitialProcessInstanceId() {
        return sourceInitialProcessInstanceId;
    }

    /**
     * @param sourceInitialProcessInstanceId Initial Process Instance Id of
     *                                       source Workflow Engine
     */
    public void setSourceInitialProcessInstanceId(
        String sourceInitialProcessInstanceId) {

        this.sourceInitialProcessInstanceId = sourceInitialProcessInstanceId;
    }

    /**
     * @return Current Process Instance Id of source Workflow Engine
     */
    public String getSourceCurrentProcessInstanceId() {
        return sourceCurrentProcessInstanceId;
    }

    /**
     * @param sourceCurrentProcessInstanceId Current Process Instance Id of
     *                                       source Workflow Engine
     */
    public void setSourceCurrentProcessInstanceId(
        String sourceCurrentProcessInstanceId) {

        this.sourceCurrentProcessInstanceId = sourceCurrentProcessInstanceId;
    }

    /**
     * @return Activity Instance Id of the source Workflow Engine
     */
    public String getSourceActivityInstanceId() {
        return sourceActivityInstanceId;
    }

    /**
     * @param sourceActivityInstanceId Activity Instance Id of the source
     *                                 Workflow Engine
     */
    public void setSourceActivityInstanceId(
        String sourceActivityInstanceId) {

        this.sourceActivityInstanceId = sourceActivityInstanceId;
    }

    /**
     * @return Timestamp of source Workflow Engine event
     */
    public Date getSourceTimestamp() {
        return sourceTimestamp;
    }

    /**
     * @param sourceTimestamp Timestamp of source Workflow Engine event
     */
    public void setSourceTimestamp(Date sourceTimestamp) {
        this.sourceTimestamp = sourceTimestamp;
    }

    /**
     * @return Node Id of sourceWorkflow Engine
     */
    public String getSourceNodeId() {
        return sourceNodeId;
    }

    /**
     * @param sourceNodeId Node Id of sourceWorkflow Engine
     */
    public void setSourceNodeId(String sourceNodeId) {
        this.sourceNodeId = sourceNodeId;
    }

    /**
     * @return User Id associated with the remote Workflow Engine request
     */
    public String getSourceUserId() {
        return sourceUserId;
    }

    /**
     * @param sourceUserId User Id associated with the remote Workflow Engine
     *                     request
     */
    public void setSourceUserId(String sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    /**
     * @return Role Id associated with the remote Workflow Engine request
     */
    public String getSourceRoleId() {
        return sourceRoleId;
    }

    /**
     * @param sourceRoleId Role Id associated with the remote Workflow Engine
     *                     request
     */
    public void setSourceRoleId(String sourceRoleId) {
        this.sourceRoleId = sourceRoleId;
    }

    /**
     * @return as supplied by the source Workflow Engine
     */
    public String getSourceProcessDefinitionId() {
        return sourceProcessDefinitionId;
    }

    /**
     * @param sourceProcessDefinitionId as supplied by the source Workflow
     *                                  Engine
     */
    public void setSourceProcessDefinitionId(
        String sourceProcessDefinitionId) {

        this.sourceProcessDefinitionId = sourceProcessDefinitionId;
    }

    /**
     * @return Business name of the remote Workflow Engine process that
     *         generated the start request
     */
    public String getSourceProcessDefinitionBusinessName() {
        return sourceProcessDefinitionBusinessName;
    }

    /**
     * @param sourceProcessDefinitionBusinessName
     *         Business name of the remote
     *         Workflow Engine process that generated the start request
     */
    public void setSourceProcessDefinitionBusinessName(
        String sourceProcessDefinitionBusinessName) {

        this.sourceProcessDefinitionBusinessName =
            sourceProcessDefinitionBusinessName;
    }

    /**
     * @return Business name of the remote Workflow Engine activity that spawned
     *         the request
     */
    public String getSourceActivityDefinitionBusinessName() {
        return sourceActivityDefinitionBusinessName;
    }

    /**
     * @param sourceActivityDefinitionBusinessName
     *         Business name of the remote
     *         Workflow Engine activity that spawned the request
     */
    public void setSourceActivityDefinitionBusinessName(
        String sourceActivityDefinitionBusinessName) {

        this.sourceActivityDefinitionBusinessName =
            sourceActivityDefinitionBusinessName;
    }

    public String toString() {
        return "WMATargetWorkflow@" + System.identityHashCode(this) + '[' +
            " cwadPrefix=" + formatCwadPrefix() +
            ", messageId=" + getMessageId() +
            ", sourceInitialProcessInstanceId=" +
            sourceInitialProcessInstanceId +
            ", sourceCurrentProcessInstanceId=" +
            sourceCurrentProcessInstanceId +
            ", sourceActivityInstanceId=" + sourceActivityInstanceId +
            ", sourceTimestamp=" + sourceTimestamp +
            ", sourceNodeId=" + sourceNodeId +
            ", sourceUserId=" + sourceUserId +
            ", sourceRoleId=" + sourceRoleId +
            ", sourceProcessDefinitionId=" + sourceProcessDefinitionId +
            ", sourceProcessDefinitionBusinessName='" +
            sourceProcessDefinitionBusinessName + '\'' +
            ", sourceActivityDefinitionBusinessName='" +
            sourceActivityDefinitionBusinessName + '\'' +
            ", extensionNumber=" + getExtensionNumber() +
            ", extensionType='" + getExtensionType() + '\'' +
            ", sourceConversationId='" + getSourceConversationId() + '\'' +
            ", targetConversationId='" + getTargetConversationId() + '\'' +
            ']';
    }
}