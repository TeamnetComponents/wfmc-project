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
 * Section 8.8.2 of the WfMC Interface 5 standard.
 *
 * @author Antony Lodge
 */
public class WMAStateChangeSourceNotification extends WMASourceNotification {
    private static final long serialVersionUID = 5564671007253864772L;

    private String newProcessState;

    public WMAStateChangeSourceNotification() {
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
     * @param targetProcessDefinitionId
     * @param targetNodeId
     * @param newProcessState
     */
    public WMAStateChangeSourceNotification(CWADPrefix cwadPrefix,
        String messageId, short extensionNumber, String extensionType,
        String sourceConversationId, String targetConversationId,
        String sourceActivityDefinitionBusinessName,
        String targetProcessInstanceId,
        String targetProcessDefinitionId, String targetNodeId,
        String newProcessState) {

        super(cwadPrefix, messageId, extensionNumber, extensionType,
            sourceConversationId, targetConversationId,
            sourceActivityDefinitionBusinessName, targetProcessInstanceId,
            targetProcessDefinitionId, targetNodeId);

        this.newProcessState = newProcessState;
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
     * @param targetProcessDefinitionId
     * @param targetNodeId
     */
    public WMAStateChangeSourceNotification(CWADPrefix cwadPrefix,
        String messageId, short extensionNumber, String extensionType,
        String sourceConversationId, String targetConversationId,
        String sourceActivityDefinitionBusinessName,
        String targetProcessInstanceId,
        String targetProcessDefinitionId, String targetNodeId) {

        super(cwadPrefix, messageId, extensionNumber, extensionType,
            sourceConversationId, targetConversationId,
            sourceActivityDefinitionBusinessName, targetProcessInstanceId,
            targetProcessDefinitionId, targetNodeId);
    }

    /**
     * @return The new process state.
     */
    public String getNewProcessState() {
        return newProcessState;
    }

    /**
     * @param newProcessState
     */
    public void setNewProcessState(String newProcessState) {
        this.newProcessState = newProcessState;
    }

    public String toString() {
        return "WMAStateChangeSourceNotification@" +
            System.identityHashCode(this) + '[' +
            " cwadPrefix=" + formatCwadPrefix() +
            ", messageId=" + getMessageId() +
            ", sourceActivityDefinitionBusinessName='" +
            getSourceActivityDefinitionBusinessName() + '\'' +
            ", targetProcessInstanceId=" + getTargetProcessInstanceId() +
            ", targetProcessDefinitionId=" +
            getTargetProcessDefinitionId() +
            ", targetNodeId=" + getTargetNodeId() +
            ", newProcessState=" + newProcessState +
            ", extensionNumber=" + getExtensionNumber() +
            ", extensionType='" + getExtensionType() + '\'' +
            ", sourceConversationId='" + getSourceConversationId() + '\'' +
            ", targetConversationId='" + getTargetConversationId() + '\'' +
            ']';
    }
}