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
 * Abstract base class for all audit data classes.
 *
 * @author Antony Lodge
 */
public abstract class WMAAuditBase extends WMAAuditEntry implements CWADSuffix {
    private static final long serialVersionUID = -1365880456646128382L;

    // CWADSuffix fields.
    private byte _accountCode;
    private short _extensionNumber;
    private byte _extensionType;
    private short _extensionLength;
    private short _extensionCodePage;
    private Object _extensionContent;

    /**
     * Empty constructor to enable subclass JavaBean compliance.
     */
    protected WMAAuditBase() {
    }

    /**
     * Constructor that takes all the CWADPrefix fields.
     *
     * @param processDefinitionId
     * @param activityDefinitionId
     * @param initialProcessInstanceId
     * @param currentProcessInstanceId
     * @param activityInstanceId
     * @param workItemId
     * @param processState
     * @param eventCode
     * @param domainId
     * @param nodeId
     * @param userId
     * @param roleId
     * @param timestamp
     */
    protected WMAAuditBase(String processDefinitionId,
        String activityDefinitionId, String initialProcessInstanceId,
        String currentProcessInstanceId, String activityInstanceId,
        String workItemId, int processState, WMAEventCode eventCode,
        String domainId,
        String nodeId, String userId, String roleId, Date timestamp) {

        super(processDefinitionId, activityDefinitionId,
            initialProcessInstanceId, currentProcessInstanceId,
            activityInstanceId, workItemId, processState, eventCode, domainId,
            nodeId,
            userId, roleId, timestamp);
    }

    /**
     * Constructor that takes all the CWADPrefix and CWADSuffix fields.
     *
     * @param processDefinitionId
     * @param activityDefinitionId
     * @param initialProcessInstanceId
     * @param currentProcessInstanceId
     * @param activityInstanceId
     * @param workItemId
     * @param processState
     * @param eventCode
     * @param domainId
     * @param nodeId
     * @param userId
     * @param roleId
     * @param timestamp
     * @param accountCode
     * @param extensionNumber
     * @param extensionType
     * @param extensionLength
     * @param extensionCodePage
     * @param extensionContent
     */
    protected WMAAuditBase(String processDefinitionId,
        String activityDefinitionId, String initialProcessInstanceId,
        String currentProcessInstanceId, String activityInstanceId,
        String workItemId, int processState, WMAEventCode eventCode,
        String domainId,
        String nodeId, String userId, String roleId, Date timestamp,
        byte accountCode, short extensionNumber,
        byte extensionType, short extensionLength, short extensionCodePage,
        Object extensionContent) {

        super(processDefinitionId, activityDefinitionId,
            initialProcessInstanceId, currentProcessInstanceId,
            activityInstanceId, workItemId, processState, eventCode, domainId,
            nodeId, userId, roleId, timestamp);
        _accountCode = accountCode;
        _extensionNumber = extensionNumber;
        _extensionType = extensionType;
        _extensionLength = extensionLength;
        _extensionCodePage = extensionCodePage;
        _extensionContent = extensionContent;
    }

    /**
     * @param p Prefix information
     * @param s Suffix information.
     */
    protected WMAAuditBase(CWADPrefix p, CWADSuffix s) {
        super(p.getProcessDefinitionId(), p.getActivityDefinitionId(),
            p.getInitialProcessInstanceId(), p.getCurrentProcessInstanceId(),
            p.getActivityInstanceId(), p.getWorkItemId(),
            valueOf(p.getProcessState()), p.getEventCode(), p.getDomainId(),
            p.getNodeId(), p.getUserId(), p.getRoleId(), p.getTimestamp());
        String informationId = p.getInformationId();
        if (informationId != null)
            setInformationId(informationId);
        setCwadSuffix(s);
    }

    /**
     * @return The audit data suffix information.
     */
    public CWADSuffix getCwadSuffix() {
        return this;
    }

    /**
     * @param suffix
     */
    public final void setCwadSuffix(CWADSuffix suffix) {
        _accountCode = suffix.getAccountCode();
        _extensionNumber = suffix.getExtensionNumber();
        _extensionType = suffix.getExtensionType();
        _extensionLength = suffix.getExtensionLength();
        _extensionCodePage = suffix.getExtensionCodePage();
        _extensionContent = suffix.getExtensionContent();
    }

    public String formatCwadSuffix() {
        return "CWADSuffix[" +
            "accountCode=" + _accountCode +
            ", extensionNumber=" + _extensionNumber +
            ", extensionType=" + _extensionType +
            ", extensionLength=" + _extensionLength +
            ", extensionCodePage=" + _extensionCodePage +
            ", extensionContent=" + _extensionContent +
            ']';
    }

    /**
     * @return Accounting Code used for item of work
     */
    public byte getAccountCode() {
        return _accountCode;
    }

    /**
     * @param accountCode Accounting Code used for item of work
     */
    public void setAccountCode(byte accountCode) {
        _accountCode = accountCode;
    }

    /**
     * @return Number of extensions in suffix information
     */
    public short getExtensionNumber() {
        return _extensionNumber;
    }

    /**
     * @param extensionNumber Number of extensions in suffix information
     */
    public void setExtensionNumber(short extensionNumber) {
        _extensionNumber = extensionNumber;
    }

    /**
     * @return Type of extension
     */
    public byte getExtensionType() {
        return _extensionType;
    }

    /**
     * @param extensionType Type of extension
     */
    public void setExtensionType(byte extensionType) {
        _extensionType = extensionType;
    }

    /**
     * @return Total length of extension values
     */
    public short getExtensionLength() {
        return _extensionLength;
    }

    /**
     * @param extensionLength Total length of extension values
     */
    public void setExtensionLength(short extensionLength) {
        _extensionLength = extensionLength;
    }

    /**
     * @return The code page used by this audit extension.
     */
    public short getExtensionCodePage() {
        return _extensionCodePage;
    }

    /**
     * @param extensionCodePage
     */
    public void setExtensionCodePage(short extensionCodePage) {
        _extensionCodePage = extensionCodePage;
    }

    /**
     * @return Content, defined by Extension Type and Length
     */
    public Object getExtensionContent() {
        return _extensionContent;
    }

    /**
     * @param extensionContent Content, defined by Extension Type and Length
     */
    public void setExtensionContent(Object extensionContent) {
        _extensionContent = extensionContent;
    }
}