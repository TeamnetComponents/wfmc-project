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
 * Section 8.9.3 of the Interface 5 WFMC standards.
 *
 * @author Antony Lodge
 */
public class WMAAttributeChangedTargetNotification
    extends WMATargetNotification {
    private static final long serialVersionUID = 2953781925294546131L;

    private String attributeName;
    private int attributeType;
    private int attributeLength;
    private int attributeValue;

    /**
     * @return Name of attribute requested
     */
    public String getAttributeName() {
        return attributeName;
    }

    /**
     * @param attributeName Name of attribute requested
     */
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    /**
     * @return Type of attribute requested
     */
    public int getAttributeType() {
        return attributeType;
    }

    /**
     * @param attributeType Type of attribute requested
     */
    public void setAttributeType(int attributeType) {
        this.attributeType = attributeType;
    }

    /**
     * @return Length of requested attribute
     */
    public int getAttributeLength() {
        return attributeLength;
    }

    /**
     * @param attributeLength Length of requested attribute
     */
    public void setAttributeLength(int attributeLength) {
        this.attributeLength = attributeLength;
    }

    /**
     * @return New value Extension Number
     */
    public int getAttributeValue() {
        return attributeValue;
    }

    /**
     * @param attributeValue New value Extension Number
     */
    public void setAttributeValue(int attributeValue) {
        this.attributeValue = attributeValue;
    }

    public String toString() {
        return "WMAAttributeChangedTargetNotification@" +
            System.identityHashCode(this) + '[' +
            " cwadPrefix=" + formatCwadPrefix() +
            ", messageId=" + getMessageId() +
            ", sourceActivityDefinitionBusinessName='" +
            getSourceActivityDefinitionBusinessName() + '\'' +
            ", sourceProcessInstanceId=" + getSourceProcessInstanceId() +
            ", sourceActivityInstanceId=" + getSourceActivityInstanceId() +
            ", sourceNodeId=" + getSourceNodeId() +
            ", attributeName='" + attributeName + '\'' +
            ", attributeType=" + attributeType +
            ", attributeLength=" + attributeLength +
            ", attributeValue=" + attributeValue +
            ", extensionNumber=" + getExtensionNumber() +
            ", extensionType='" + getExtensionType() + '\'' +
            ", sourceConversationId='" + getSourceConversationId() + '\'' +
            ", targetConversationId='" + getTargetConversationId() + '\'' +
            ']';
    }
}