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
 * Base class for all Interface 4-related audit entries.
 *
 * @author Antony Lodge
 */
public abstract class WMARemoteAuditBase extends WMAAuditEntry {
    private static final long serialVersionUID = -3442734110797076316L;

    private String messageId;
    private short extensionNumber;
    private String extensionType;
    private String sourceConversationId;
    private String targetConversationId;

    protected WMARemoteAuditBase() {
    }

    /**
     * @param cwadPrefix
     * @param messageId
     * @param extensionNumber
     * @param extensionType
     * @param sourceConversationId
     * @param targetConversationId
     */
    protected WMARemoteAuditBase(CWADPrefix cwadPrefix, String messageId,
        short extensionNumber, String extensionType,
        String sourceConversationId, String targetConversationId) {

        super(cwadPrefix);
        this.messageId = messageId;
        this.extensionNumber = extensionNumber;
        this.extensionType = extensionType;
        this.sourceConversationId = sourceConversationId;
        this.targetConversationId = targetConversationId;
    }

    /**
     * @return Message Id associated with event
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * @param messageId Message Id associated with event
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * @return The extension number.
     */
    public short getExtensionNumber() {
        return extensionNumber;
    }

    /**
     * @param extensionNumber
     */
    public void setExtensionNumber(short extensionNumber) {
        this.extensionNumber = extensionNumber;
    }

    /**
     * @return The extension type.
     */
    public String getExtensionType() {
        return extensionType;
    }

    /**
     * @param extensionType
     */
    public void setExtensionType(String extensionType) {
        this.extensionType = extensionType;
    }

    /**
     * @return As supplied by the source Workflow Engine or by the transport
     *         mechanism
     */
    public String getSourceConversationId() {
        return sourceConversationId;
    }

    /**
     * @param sourceConversationId As supplied by the source Workflow Engine or
     *                             by the transport mechanism
     */
    public void setSourceConversationId(String sourceConversationId) {
        this.sourceConversationId = sourceConversationId;
    }

    /**
     * @return As supplied by the target Workflow Engine or by the transport
     *         mechanism
     */
    public String getTargetConversationId() {
        return targetConversationId;
    }

    /**
     * @param targetConversationId As supplied by the target Workflow Engine or
     *                             by the transport mechanism
     */
    public void setTargetConversationId(String targetConversationId) {
        this.targetConversationId = targetConversationId;
    }

    public String toString() {
        return "WMARemoteAuditBase@" + System.identityHashCode(this) + '[' +
            ", messageId=" + messageId +
            ", extensionNumber=" + extensionNumber +
            ", extensionType='" + extensionType + '\'' +
            ", sourceConversationId='" + sourceConversationId + '\'' +
            ", targetConversationId='" + targetConversationId + '\'' +
            ']';
    }
}