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
 * Assign Process Instance Attribute Audit Data.
 *
 * @author Antony Lodge
 */
public class WMAAssignProcessInstanceAttributeData extends WMAAuditBase {
    private static final long serialVersionUID = -6108514019431468721L;

    private String _attributeName;
    private int _attributeType;
    private int _newAttributeLength;
    private String _newAttributeValue;
    private int _previousAttributeLength;
    private String _previousAttributeValue;

    public WMAAssignProcessInstanceAttributeData() {
    }

    public WMAAssignProcessInstanceAttributeData(String processDefinitionId,
        String activityDefinitionId, String initialProcessInstanceId,
        String currentProcessInstanceId, String activityInstanceId,
        int processState, WMAEventCode eventCode, String domainId,
        String nodeId, String userId, String roleId, Date timestamp,
        String attributeName, int attributeType,
        int newAttributeLength, String newAttributeValue,
        int previousAttributeLength, String previousAttributeValue) {

        super(processDefinitionId, activityDefinitionId,
            initialProcessInstanceId, currentProcessInstanceId,
            activityInstanceId, null, processState, eventCode, domainId, nodeId,
            userId, roleId, timestamp);
        _attributeName = attributeName;
        _attributeType = attributeType;
        _newAttributeLength = newAttributeLength;
        _newAttributeValue = newAttributeValue;
        _previousAttributeLength = previousAttributeLength;
        _previousAttributeValue = previousAttributeValue;
    }

    public WMAAssignProcessInstanceAttributeData(String processDefinitionId,
        String activityDefinitionId, String initialProcessInstanceId,
        String currentProcessInstanceId, String activityInstanceId,
        int processState, WMAEventCode eventCode, String domainId,
        String nodeId, String userId, String roleId, Date timestamp,
        byte accountCode, short extensionNumber,
        byte extensionType, short extensionLength, short extensionCodePage,
        Object extensionContent, String attributeName, int attributeType,
        int newAttributeLength, String newAttributeValue,
        int previousAttributeLength, String previousAttributeValue) {

        super(processDefinitionId, activityDefinitionId,
            initialProcessInstanceId, currentProcessInstanceId,
            activityInstanceId, null, processState, eventCode, domainId, nodeId,
            userId, roleId, timestamp, accountCode,
            extensionNumber, extensionType, extensionLength, extensionCodePage,
            extensionContent);
        _attributeName = attributeName;
        _attributeType = attributeType;
        _newAttributeLength = newAttributeLength;
        _newAttributeValue = newAttributeValue;
        _previousAttributeLength = previousAttributeLength;
        _previousAttributeValue = previousAttributeValue;
    }

    /**
     * @return The attribute name.
     */
    public String getAttributeName() {
        return _attributeName;
    }

    /**
     * @param attributeName
     */
    public void setAttributeName(String attributeName) {
        _attributeName = attributeName;
    }

    /**
     * @return The attribute type.
     */
    public int getAttributeType() {
        return _attributeType;
    }

    /**
     * @param attributeType
     */
    public void setAttributeType(int attributeType) {
        _attributeType = attributeType;
    }

    /**
     * @return The new attribute length.
     */
    public int getNewAttributeLength() {
        return _newAttributeLength;
    }

    /**
     * @param newAttributeLength
     */
    public void setNewAttributeLength(int newAttributeLength) {
        _newAttributeLength = newAttributeLength;
    }

    /**
     * @return The new attribute value.
     */
    public String getNewAttributeValue() {
        return _newAttributeValue;
    }

    /**
     * @param newAttributeValue
     */
    public void setNewAttributeValue(String newAttributeValue) {
        _newAttributeValue = newAttributeValue;
    }

    /**
     * @return The previous attribute length.
     */
    public int getPreviousAttributeLength() {
        return _previousAttributeLength;
    }

    /**
     * @param previousAttributeLength
     */
    public void setPreviousAttributeLength(int previousAttributeLength) {
        _previousAttributeLength = previousAttributeLength;
    }

    /**
     * @return The previous attribute value.
     */
    public String getPreviousAttributeValue() {
        return _previousAttributeValue;
    }

    /**
     * @param previousAttributeValue
     */
    public void setPreviousAttributeValue(String previousAttributeValue) {
        _previousAttributeValue = previousAttributeValue;
    }

    public String toString() {
        return "WMAAssignProcessInstanceAttributeData@" +
            System.identityHashCode(this) + '[' +
            " cwadPrefix=" + formatCwadPrefix() +
            ", attributeName='" + _attributeName + '\'' +
            ", attributeType=" + _attributeType +
            ", newAttributeLength=" + _newAttributeLength +
            ", newAttributeValue='" + _newAttributeValue + '\'' +
            ", previousAttributeLength=" + _previousAttributeLength +
            ", previousAttributeValue='" + _previousAttributeValue + '\'' +
            ", cwadSuffix=" + formatCwadSuffix() +
            ']';
    }
}