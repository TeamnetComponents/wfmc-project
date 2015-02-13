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
 * Section 8.10 of the Interface 5 WFMC standards
 *
 * @author Antony Lodge
 */
public class WMASessionManagementAuditData extends WMAAuditBase {
    private static final long serialVersionUID = -3124899135217676983L;

    private String _messageId;
    private String _correspondentDomainId;
    private String _correspondentNodeId;

    public WMASessionManagementAuditData() {
    }

    public WMASessionManagementAuditData(WMAEventCode eventCode,
        String domainId, String nodeId, String userId, String roleId,
        Date timestamp, String messageId,
        String correspondentDomainId, String correspondentNodeId) {

        super(null, null, null, null, null, null, -1, eventCode, domainId,
            nodeId,
            userId, roleId, timestamp);
        _messageId = messageId;
        _correspondentDomainId = correspondentDomainId;
        _correspondentNodeId = correspondentNodeId;
    }

    public WMASessionManagementAuditData(WMAEventCode eventCode,
        String domainId, String nodeId, String userId, String roleId,
        Date timestamp, byte accountCode,
        short extensionNumber, byte extensionType, short extensionLength,
        short extensionCodePage, Object extensionContent, String messageId,
        String correspondentDomainId, String correspondentNodeId) {

        super(null, null, null, null, null, null, -1, eventCode, domainId,
            nodeId,
            userId, roleId, timestamp, accountCode,
            extensionNumber, extensionType, extensionLength, extensionCodePage,
            extensionContent);
        _messageId = messageId;
        _correspondentDomainId = correspondentDomainId;
        _correspondentNodeId = correspondentNodeId;
    }

    /**
     * @param cwadPrefix
     * @param cwadSuffix
     */
    public WMASessionManagementAuditData(CWADPrefix cwadPrefix,
        CWADSuffix cwadSuffix) {

        super(cwadPrefix, cwadSuffix);
    }

    /**
     * @param cwadPrefix
     * @param cwadSuffix
     * @param messageId
     * @param correspondentDomainId
     * @param correspondentNodeId
     */
    public WMASessionManagementAuditData(CWADPrefix cwadPrefix,
        CWADSuffix cwadSuffix, String messageId,
        String correspondentDomainId,
        String correspondentNodeId) {

        super(cwadPrefix, cwadSuffix);
        _messageId = messageId;
        _correspondentDomainId = correspondentDomainId;
        _correspondentNodeId = correspondentNodeId;
    }

    /**
     * @return Message Id associated with event
     */
    public String getMessageId() {
        return _messageId;
    }

    /**
     * @param messageId Message Id associated with event
     */
    public void setMessageId(String messageId) {
        _messageId = messageId;
    }

    /**
     * @return DomainId of accepting the session request
     */
    public String getCorrespondentDomainId() {
        return _correspondentDomainId;
    }

    /**
     * @param correspondentDomainId DomainId of accepting the session request
     */
    public void setCorrespondentDomainId(String correspondentDomainId) {
        _correspondentDomainId = correspondentDomainId;
    }

    /**
     * @return Node Id of accepting the session request
     */
    public String getCorrespondentNodeId() {
        return _correspondentNodeId;
    }

    /**
     * @param correspondentNodeId Node Id of accepting the session request
     */
    public void setCorrespondentNodeId(String correspondentNodeId) {
        _correspondentNodeId = correspondentNodeId;
    }

    public String toString() {
        return "WMASessionManagementAuditData@" +
            System.identityHashCode(this) + '[' +
            " cwadPrefix=" + formatCwadPrefix() +
            ", messageId=" + _messageId +
            ", correspondentDomainId=" + _correspondentDomainId +
            ", correspondentNodeId=" + _correspondentNodeId +
            ", cwadSuffix=" + formatCwadSuffix() +
            ']';
    }
}