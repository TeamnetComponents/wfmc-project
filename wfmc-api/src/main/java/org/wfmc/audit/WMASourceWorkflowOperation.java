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
public abstract class WMASourceWorkflowOperation extends WMARemoteAuditBase {
    private static final long serialVersionUID = 8323948523064141874L;

    private String sourceActivityInstanceId;
    private String remoteNodeId;
    private String remoteProcessInstanceId;
    private Date remoteTimestamp;
    private String remoteProcessDefinitionBusinessName;

    protected WMASourceWorkflowOperation() {
    }

    /**
     * @param cwadPrefix
     * @param messageId
     * @param extensionNumber
     * @param extensionType
     * @param sourceConversationId
     * @param targetConversationId
     */
    protected WMASourceWorkflowOperation(CWADPrefix cwadPrefix,
        String messageId,
        short extensionNumber, String extensionType,
        String sourceConversationId, String targetConversationId) {

        super(cwadPrefix, messageId, extensionNumber, extensionType,
            sourceConversationId, targetConversationId);
    }

    /**
     * @param cwadPrefix
     * @param messageId
     * @param extensionNumber
     * @param extensionType
     * @param sourceConversationId
     * @param targetConversationId
     * @param sourceActivityInstanceId
     * @param remoteNodeId
     * @param remoteProcessInstanceId
     * @param remoteTimestamp
     * @param remoteProcessDefinitionBusinessName
     *
     */
    protected WMASourceWorkflowOperation(CWADPrefix cwadPrefix,
        String messageId,
        short extensionNumber, String extensionType,
        String sourceConversationId, String targetConversationId,
        String sourceActivityInstanceId,
        String remoteNodeId, String remoteProcessInstanceId,
        Date remoteTimestamp, String remoteProcessDefinitionBusinessName) {

        super(cwadPrefix, messageId, extensionNumber, extensionType,
            sourceConversationId, targetConversationId);

        this.sourceActivityInstanceId = sourceActivityInstanceId;
        this.remoteNodeId = remoteNodeId;
        this.remoteProcessInstanceId = remoteProcessInstanceId;
        this.remoteTimestamp = remoteTimestamp;
        this.remoteProcessDefinitionBusinessName =
            remoteProcessDefinitionBusinessName;
    }

    /**
     * @return Activity Id on source Workflow Engine
     */
    public String getSourceActivityInstanceId() {
        return sourceActivityInstanceId;
    }

    /**
     * @param sourceActivityInstanceId Activity Id on source Workflow Engine
     */
    public void setSourceActivityInstanceId(
        String sourceActivityInstanceId) {

        this.sourceActivityInstanceId = sourceActivityInstanceId;
    }

    /**
     * @return Node Id of target Workflow Engine
     */
    public String getRemoteNodeId() {
        return remoteNodeId;
    }

    /**
     * @param remoteNodeId Node Id of target Workflow Engine
     */
    public void setRemoteNodeId(String remoteNodeId) {
        this.remoteNodeId = remoteNodeId;
    }

    /**
     * @return process instance created on the target Workflow Engine
     */
    public String getRemoteProcessInstanceId() {
        return remoteProcessInstanceId;
    }

    /**
     * @param remoteProcessInstanceId process instance created on the target
     *                                Workflow Engine
     */
    public void setRemoteProcessInstanceId(
        String remoteProcessInstanceId) {

        this.remoteProcessInstanceId = remoteProcessInstanceId;
    }

    /**
     * @return Timestamp for when process instance created on target Workflow
     *         Engine
     */
    public Date getRemoteTimestamp() {
        return remoteTimestamp;
    }

    /**
     * @param remoteTimestamp Timestamp for when process instance created on
     *                        target Workflow Engine
     */
    public void setRemoteTimestamp(Date remoteTimestamp) {
        this.remoteTimestamp = remoteTimestamp;
    }

    /**
     * @return null | as supplied by target Workflow Engine
     */
    public String getRemoteProcessDefinitionBusinessName() {
        return remoteProcessDefinitionBusinessName;
    }

    /**
     * @param remoteProcessDefinitionBusinessName
     *         null | as supplied by target
     *         Workflow Engine
     */
    public void setRemoteProcessDefinitionBusinessName(
        String remoteProcessDefinitionBusinessName) {
        this.remoteProcessDefinitionBusinessName =
            remoteProcessDefinitionBusinessName;
    }

    public String toString() {
        return "WMASourceWorkflowOperation@" +
            System.identityHashCode(this) + '[' +
            " cwadPrefix=" + formatCwadPrefix() +
            ", messageId=" + getMessageId() +
            ", sourceActivityInstanceId=" + sourceActivityInstanceId +
            ", remoteNodeId=" + remoteNodeId +
            ", remoteProcessInstanceId=" + remoteProcessInstanceId +
            ", remoteTimestamp=" + remoteTimestamp +
            ", remoteProcessDefinitionBusinessName='" +
            remoteProcessDefinitionBusinessName + '\'' +
            ", extensionNumber=" + getExtensionNumber() +
            ", extensionType='" + getExtensionType() + '\'' +
            ", sourceConversationId='" + getSourceConversationId() + '\'' +
            ", targetConversationId='" + getTargetConversationId() + '\'' +
            ']';
    }
}