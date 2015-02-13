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
 * Assign Activity Instance Attributes Audit Data.
 *
 * @author Antony Lodge
 */
public class WMAAssignActivityInstanceAttributesData
    extends WMAAssignProcessInstanceAttributeData {

    private static final long serialVersionUID = -8136551872480997981L;

    private String _activityState;

    public WMAAssignActivityInstanceAttributesData() {
    }

    public WMAAssignActivityInstanceAttributesData(String processDefinitionId,
        String activityDefinitionId, String initialProcessInstanceId,
        String currentProcessInstanceId, String activityInstanceId,
        int processState, WMAEventCode eventCode, String domainId,
        String nodeId, String userId, String roleId, Date timestamp,
        String attributeName, int attributeType,
        int newAttributeLength, String newAttributeValue,
        int previousAttributeLength, String previousAttributeValue,
        String activityState) {

        super(processDefinitionId, activityDefinitionId,
            initialProcessInstanceId, currentProcessInstanceId,
            activityInstanceId, processState, eventCode, domainId, nodeId,
            userId, roleId, timestamp, attributeName,
            attributeType, newAttributeLength, newAttributeValue,
            previousAttributeLength, previousAttributeValue);
        _activityState = activityState;
    }

    public WMAAssignActivityInstanceAttributesData(String processDefinitionId,
        String activityDefinitionId, String initialProcessInstanceId,
        String currentProcessInstanceId, String activityInstanceId,
        int processState, WMAEventCode eventCode, String domainId,
        String nodeId, String userId, String roleId, Date timestamp,
        byte accountCode, short extensionNumber,
        byte extensionType, short extensionLength, short extensionCodePage,
        Object extensionContent, String attributeName, int attributeType,
        int newAttributeLength, String newAttributeValue,
        int previousAttributeLength, String previousAttributeValue,
        String activityState) {

        super(processDefinitionId, activityDefinitionId,
            initialProcessInstanceId, currentProcessInstanceId,
            activityInstanceId, processState, eventCode, domainId, nodeId,
            userId, roleId, timestamp, accountCode,
            extensionNumber, extensionType, extensionLength, extensionCodePage,
            extensionContent, attributeName, attributeType, newAttributeLength,
            newAttributeValue, previousAttributeLength, previousAttributeValue);
        _activityState = activityState;
    }

    /**
     * @return The activity state.
     */
    public String getActivityState() {
        return _activityState;
    }

    /**
     * @param activityState
     */
    public void setActivityState(String activityState) {
        _activityState = activityState;
    }

    public String toString() {
        return "WMAAssignActivityInstanceAttributesData@" +
            System.identityHashCode(this) + '[' +
            " cwadPrefix=" + formatCwadPrefix() +
            ", activityInstanceId=" + getActivityInstanceId() +
            ", activityState='" + getActivityState() + '\'' +
            ", attributeName='" + getAttributeName() + '\'' +
            ", attributeType=" + getAttributeType() +
            ", newAttributeLength=" + getNewAttributeLength() +
            ", newAttributeValue='" + getNewAttributeValue() + '\'' +
            ", previousAttributeLength=" + getPreviousAttributeLength() +
            ", previousAttributeValue='" + getPreviousAttributeValue() + '\'' +
            ", cwadSuffix=" + formatCwadSuffix() +
            ']';
    }
}