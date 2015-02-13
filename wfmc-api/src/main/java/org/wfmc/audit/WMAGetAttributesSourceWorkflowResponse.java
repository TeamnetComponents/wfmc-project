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

/** Section 8.6.3 of the Interface 5 WFMC standards

 @author Antony Lodge
 */

/**
 * @author Antony Lodge
 */
public class WMAGetAttributesSourceWorkflowResponse
    extends WMASourceWorkflowResponse {

    private static final long serialVersionUID = 8684940144526336634L;

    public WMAGetAttributesSourceWorkflowResponse() {
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
     * @param attributeName
     * @param attributeType
     * @param attributeLength
     * @param attributeValue
     */
    public WMAGetAttributesSourceWorkflowResponse(CWADPrefix cwadPrefix,
        String messageId, short extensionNumber, String extensionType,
        String sourceConversationId, String targetConversationId,
        String sourceInitialProcessInstanceId,
        String sourceCurrentProcessInstanceId,
        String sourceActivityInstanceId, Date sourceTimestamp,
        String sourceNodeId, String sourceUserId,
        String sourceRoleId, String sourceProcessDefinitionId,
        String sourceProcessDefinitionBusinessName,
        String sourceActivityDefinitionBusinessName, String attributeName,
        int attributeType, int attributeLength, String attributeValue) {

        super(cwadPrefix, messageId, extensionNumber, extensionType,
            sourceConversationId, targetConversationId,
            sourceInitialProcessInstanceId, sourceCurrentProcessInstanceId,
            sourceActivityInstanceId, sourceTimestamp, sourceNodeId,
            sourceUserId, sourceRoleId, sourceProcessDefinitionId,
            sourceProcessDefinitionBusinessName,
            sourceActivityDefinitionBusinessName, attributeName, attributeType,
            attributeLength, attributeValue);
    }

    public String toString() {
        return "WMAGetAttributesSourceWorkflowResponse@" +
            System.identityHashCode(this) + '[' +
            " cwadPrefix=" + formatCwadPrefix() +
            ", messageId=" + getMessageId() +
            ", sourceInitialProcessInstanceId=" +
            getSourceInitialProcessInstanceId() +
            ", sourceCurrentProcessInstanceId=" +
            getSourceCurrentProcessInstanceId() +
            ", sourceActivityInstanceId=" + getSourceActivityInstanceId() +
            ", sourceTimestamp=" + getSourceTimestamp() +
            ", sourceNodeId=" + getSourceNodeId() +
            ", sourceUserId=" + getSourceUserId() +
            ", sourceRoleId=" + getSourceRoleId() +
            ", sourceProcessDefinitionId=" +
            getSourceProcessDefinitionId() +
            ", sourceProcessDefinitionBusinessName='" +
            getSourceProcessDefinitionBusinessName() + '\'' +
            ", sourceActivityDefinitionBusinessName='" +
            getSourceActivityDefinitionBusinessName() + '\'' +
            ", attributeName='" + getAttributeName() + '\'' +
            ", attributeType=" + getAttributeType() +
            ", attributeLength=" + getAttributeLength() +
            ", attributeValue='" + getAttributeValue() + '\'' +
            ", extensionNumber=" + getExtensionNumber() +
            ", extensionType='" + getExtensionType() + '\'' +
            ", sourceConversationId='" + getSourceConversationId() + '\'' +
            ", targetConversationId='" + getTargetConversationId() + '\'' +
            ']';
    }
}