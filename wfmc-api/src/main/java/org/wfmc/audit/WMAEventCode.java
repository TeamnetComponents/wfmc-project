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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Anthony Eden
 * @author Adrian Price
 */
public final class WMAEventCode implements Serializable {
    private static final long serialVersionUID = 6387120959809446340L;

    // Type integers
    // Create/ Start Process/ Subprocess Instance Audit Data

    /**
     * Unknown/non-standard event.
     */
    public static final int UNKNOWN_INT = 0;

    /**
     * (2,4) Process instance is created.
     */
    public static final int CREATED_PROCESS_INSTANCE_INT = 1;

    /**
     * (2,4) Process instance is started.
     */
    public static final int STARTED_PROCESS_INSTANCE_INT = 2;

    // Change Process/ Subprocess Instance State Audit Data event.
    /**
     * (2,4) Process state has changed by API or internal
     */
    public static final int CHANGED_PROCESS_INSTANCE_STATE_INT = 3;

    /**
     * (2,4) Process instance has completed.
     */
    public static final int COMPLETED_PROCESS_INSTANCE_INT = 4;

    /**
     * (2,4) Process instance has been terminated.
     */
    public static final int TERMINATED_PROCESS_INSTANCE_INT = 5;

    /**
     * (2,4) Process instance has been aborted.
     */
    public static final int ABORTED_PROCESS_INSTANCE_INT = 6;

    /**
     * ( ) Process is waiting on event( s) to occur.
     */
    public static final int WAITING_ON_EVENT_INT = 7;

    /**
     * ( ) Event(s) on which a process was waiting has occurred.
     */
    public static final int EVENT_OCCURRED_INT = 8;

    /**
     * ( )
     */
    public static final int STARTED_SUBPROCESS_INT = 9;

    /**
     * ( )
     */
    public static final int COMPLETED_SUBPROCESS_INT = 10;

    // Assign Process Instance Attributes Audit Data
    /**
     * (2,4) Process Instance Attributes have been changed.
     */
    public static final int ASSIGNED_PROCESS_INSTANCE_ATTRIBUTES_INT = 11;

    // Change Activity Instance State Audit Data
    /**
     * (2) Activity state has changed by API or internal event.
     */
    public static final int CHANGED_ACTIVITY_INSTANCE_STATE_INT = 12;

    // This is also used to record the initial state of the activity instance.
    /**
     * (2) Activity instance has completed.
     */
    public static final int COMPLETED_ACTIVITY_INSTANCE_INT = 13;

    /**
     * (2) Activity instance has been terminated.
     */
    public static final int TERMINATED_ACTIVITY_INSTANCE_INT = 14;

    /**
     * (2) Activity instance has been aborted.
     */
    public static final int ABORTED_ACTIVITY_INSTANCE_INT = 15;

    // Assign Activity Instance Attributes Audit Data
    /**
     * (2) Activity Instance Attributes have been changed.
     */
    public static final int ASSIGNED_ACTIVITY_INSTANCE_ATTRIBUTES_INT = 16;

    // Change Workitem State Audit Data
    /**
     * (2) Work item has been started.
     */
    public static final int STARTED_WORK_ITEM_INT = 17;

    /**
     * ( ) Work item has been completed.
     */
    public static final int COMPLETED_WORK_ITEM_INT = 18;

    /**
     * (2) Work item has been rejected.
     */
    public static final int REJECTED_WORK_ITEM_INT = 19;

    /**
     * (2) User has selected work item off worklist. This includes get, select,
     * reserve, checkout.
     */
    public static final int SELECTED_WORK_ITEM_INT = 20;

    /**
     * (2) Work item state has been changed by API or internal event. This is
     * also used to record the initial state of the work item.
     */
    public static final int CHANGED_WORK_ITEM_STATE_INT = 21;

    // Assign/ Reassign Workitem/ Worklist Audit Data
    /**
     * (2) Work item is placed on user's worklist
     */
    public static final int ASSIGNED_WORK_ITEM_INT = 22;

    /**
     * (2) Work item has been reassigned to one or more users.
     */
    public static final int REASSIGNED_WORK_ITEM_INT = 23;

    /**
     * (2) Entire worklist of a user has been reassigned to one or more users.
     */
    public static final int REASSIGNED_WORK_LIST_INT = 24;

    // Remote Process Operation Audit Data
    /**
     * (4) Process Instance Attributes values have been assigned/ changed.
     */
    public static final int ASSIGNED_PROCESS_INSTANCE_ATTRIBUTE_INT = 25;

    // Link to Remote Subprocess Audit Data
    /**
     * (4) Request sent to start a process instance on a remote WFM Engine
     */
    public static final int SENT_REQUEST_START_PROCESS_INSTANCE_INT = 26;

    /**
     * (4) Request sent to abort a process instance on remote WFM Engine.
     */
    public static final int SENT_REQUEST_ABORT_PROCESS_INSTANCE_INT = 27;

    /**
     * (4) Request sent to terminate a process instance on remote WFM Engine.
     */
    public static final int SENT_REQUEST_TERMINATE_PROCESS_INSTANCE_INT = 28;

    /**
     * (4) Request sent to change process instance on remote WFM Engine.
     */
    public static final int SENT_REQUEST_CHANGE_PROCESS_INSTANCE_ATTRIBUTE_INT =
        29;

    /**
     * (4) Request sent to get process instance attribute on remote WFM Engine.
     */
    public static final int SENT_REQUEST_GET_PROCESS_INSTANCE_ATTRIBUTE_INT =
        30;

    /**
     * (4) Request sent to change process instance state on remote WFM engine.
     */
    public static final int SENT_REQUEST_CHANGE_PROCESS_INSTANCE_STATE_INT = 31;

    /**
     * (4) Response sent to remote WFM Engine when process instance has started.
     */
    public static final int SENT_STARTED_PROCESS_INSTANCE_INT = 32;

    /**
     * (4) Response sent to remote WFM Engine when process instance attribute
     * has been changed.
     */
    public static final int SENT_CHANGED_PROCESS_INSTANCE_ATTRIBUTE_INT = 33;

    /**
     * (4) Response sent to remote WFM Engine when process instance attribute
     * has been retrieved.
     */
    public static final int SENT_RETRIEVED_PROCESS_INSTANCE_ATTRIBUTE_INT = 34;

    /**
     * (4) Response sent to remote WFM Engine when process instance has been
     * aborted.
     */
    public static final int SENT_ABORTED_PROCESS_INSTANCE_INT = 35;

    /**
     * (4) Response sent to remote WFM Engine when process instance has been
     * terminated.
     */
    public static final int SENT_TERMINATED_PROCESS_INSTANCE_INT = 36;

    /**
     * (4) Response sent to remote WFM Engine when process instance state has
     * changed.
     */
    public static final int SENT_CHANGED_PROCESS_INSTANCE_STATE_INT = 37;

    /**
     * (4) Response sent to remote WFM Engine when process instance has
     * completed.
     */
    public static final int SENT_COMPLETED_PROCESS_INSTANCE_INT = 38;

    /**
     * (4) Received request from remote WFM Engine to start process instance.
     */
    public static final int RECEIVED_REQUEST_START_PROCESS_INSTANCE_INT = 39;

    /**
     * (4) Request received from remote WFM Engine to abort process instance.
     */
    public static final int RECEIVED_REQUEST_ABORT_PROCESS_INSTANCE_INT = 40;

    /**
     * (4) Request received from remote WFM Engine to terminate process
     * instance.
     */
    public static final int RECEIVED_REQUEST_TERMINATE_PROCESS_INSTANCE_INT =
        41;

    /**
     * (4) Request received from remote WFM Engine to change process instance
     * state.
     */
    public static final int RECEIVED_REQUEST_CHANGE_PROCESS_INSTANCE_STATE_INT =
        42;

    /**
     * (4) Request received from remote WFM Engine to change process instance
     * attribute.
     */
    public static final int RECEIVED_REQUEST_CHANGE_PROCESS_INSTANCE_ATTRIBUTE_INT =
        43;

    /**
     * (4) Request received from remote WFM to get process instance attribute.
     */
    public static final int RECEIVED_REQUEST_GET_PROCESS_INSTANCE_ATTRIBUTE_INT =
        44;

    /**
     * (4) Response received from remote WFM Engine when process instance has
     * started.
     */
    public static final int RECEIVED_STARTED_PROCESS_INSTANCE_INT = 45;

    /**
     * (4) Response received from remote WFM Engine when process instance
     * attribute has been changed.
     */
    public static final int RECEIVED_CHANGED_PROCESS_INSTANCE_ATTRIBUTE_INT =
        46;

    /**
     * (4) Response received from remote WFM Engine when process instance
     * attribute has been retrieved.
     */
    public static final int RECEIVED_RETRIEVED_PROCESS_INSTANCE_ATTRIBUTE_INT =
        47;

    /**
     * (4) Response received from remote WFM Engine when process instance has
     * been aborted.
     */
    public static final int RECEIVED_ABORTED_PROCESS_INSTANCE_INT = 48;

    /**
     * (4) Response received from remote WFM Engine when process instance has
     * been terminated.
     */
    public static final int RECEIVED_TERMINATED_PROCESS_INSTANCE_INT = 49;

    /**
     * (4) Response received from remote WFM Engine when process instance state
     * has changed.
     */
    public static final int RECEIVED_CHANGED_PROCESS_INSTANCE_STATE_INT = 50;

    /**
     * (4) Response received from remote WFM Engine when process instance has
     * completed.
     */
    public static final int RECEIVED_COMPLETED_PROCESS_INSTANCE_INT = 51;

    // Session Management Audit Data
    /**
     * (4M*) Start a Session with a remote WFM Engine.
     */
    public static final int STARTED_SESSION_INT = 52;

    /**
     * (4M*) Stop a session with a remote WFM Engine.
     */
    public static final int STOPPED_SESSION_INT = 53;

    //Process Definition State Change Audit Data
    /**
     * (2) State of Process definition has been
     */
    public static final int CHANGED_PROCESS_DEFINITION_STATE_INT = 54;

    // Type objects
    public static final WMAEventCode UNKNOWN =
        new WMAEventCode(UNKNOWN_INT);
    public static final WMAEventCode CREATED_PROCESS_INSTANCE =
        new WMAEventCode(CREATED_PROCESS_INSTANCE_INT);
    public static final WMAEventCode STARTED_PROCESS_INSTANCE =
        new WMAEventCode(STARTED_PROCESS_INSTANCE_INT);

    public static final WMAEventCode CHANGED_PROCESS_INSTANCE_STATE =
        new WMAEventCode(CHANGED_PROCESS_INSTANCE_STATE_INT);
    public static final WMAEventCode COMPLETED_PROCESS_INSTANCE =
        new WMAEventCode(COMPLETED_PROCESS_INSTANCE_INT);
    public static final WMAEventCode TERMINATED_PROCESS_INSTANCE =
        new WMAEventCode(TERMINATED_PROCESS_INSTANCE_INT);
    public static final WMAEventCode ABORTED_PROCESS_INSTANCE =
        new WMAEventCode(ABORTED_PROCESS_INSTANCE_INT);
    public static final WMAEventCode WAITING_ON_EVENT =
        new WMAEventCode(WAITING_ON_EVENT_INT);
    public static final WMAEventCode EVENT_OCCURRED =
        new WMAEventCode(EVENT_OCCURRED_INT);
    public static final WMAEventCode STARTED_SUBPROCESS =
        new WMAEventCode(STARTED_SUBPROCESS_INT);
    public static final WMAEventCode COMPLETED_SUBPROCESS =
        new WMAEventCode(COMPLETED_SUBPROCESS_INT);

    public static final WMAEventCode ASSIGNED_PROCESS_INSTANCE_ATTRIBUTES =
        new WMAEventCode(ASSIGNED_PROCESS_INSTANCE_ATTRIBUTES_INT);

    public static final WMAEventCode CHANGED_ACTIVITY_INSTANCE_STATE =
        new WMAEventCode(CHANGED_ACTIVITY_INSTANCE_STATE_INT);
    public static final WMAEventCode COMPLETED_ACTIVITY_INSTANCE =
        new WMAEventCode(COMPLETED_ACTIVITY_INSTANCE_INT);
    public static final WMAEventCode TERMINATED_ACTIVITY_INSTANCE =
        new WMAEventCode(TERMINATED_ACTIVITY_INSTANCE_INT);
    public static final WMAEventCode ABORTED_ACTIVITY_INSTANCE =
        new WMAEventCode(ABORTED_ACTIVITY_INSTANCE_INT);

    public static final WMAEventCode ASSIGNED_ACTIVITY_INSTANCE_ATTRIBUTES =
        new WMAEventCode(ASSIGNED_ACTIVITY_INSTANCE_ATTRIBUTES_INT);

    public static final WMAEventCode STARTED_WORK_ITEM =
        new WMAEventCode(STARTED_WORK_ITEM_INT);
    public static final WMAEventCode COMPLETED_WORK_ITEM =
        new WMAEventCode(COMPLETED_WORK_ITEM_INT);
    public static final WMAEventCode REJECTED_WORK_ITEM =
        new WMAEventCode(REJECTED_WORK_ITEM_INT);
    public static final WMAEventCode SELECTED_WORK_ITEM =
        new WMAEventCode(SELECTED_WORK_ITEM_INT);
    public static final WMAEventCode CHANGED_WORK_ITEM_STATE =
        new WMAEventCode(CHANGED_WORK_ITEM_STATE_INT);

    public static final WMAEventCode ASSIGNED_WORK_ITEM =
        new WMAEventCode(ASSIGNED_WORK_ITEM_INT);
    public static final WMAEventCode REASSIGNED_WORK_ITEM =
        new WMAEventCode(REASSIGNED_WORK_ITEM_INT);
    public static final WMAEventCode REASSIGNED_WORK_LIST =
        new WMAEventCode(REASSIGNED_WORK_LIST_INT);

    public static final WMAEventCode ASSIGNED_PROCESS_INSTANCE_ATTRIBUTE =
        new WMAEventCode(ASSIGNED_PROCESS_INSTANCE_ATTRIBUTE_INT);

    public static final WMAEventCode SENT_REQUEST_START_PROCESS_INSTANCE =
        new WMAEventCode(SENT_REQUEST_START_PROCESS_INSTANCE_INT);
    public static final WMAEventCode SENT_REQUEST_ABORT_PROCESS_INSTANCE =
        new WMAEventCode(SENT_REQUEST_ABORT_PROCESS_INSTANCE_INT);
    public static final WMAEventCode SENT_REQUEST_TERMINATE_PROCESS_INSTANCE =
        new WMAEventCode(SENT_REQUEST_TERMINATE_PROCESS_INSTANCE_INT);
    public static final WMAEventCode SENT_REQUEST_CHANGE_PROCESS_INSTANCE_ATTRIBUTE =
        new WMAEventCode(SENT_REQUEST_CHANGE_PROCESS_INSTANCE_ATTRIBUTE_INT);
    public static final WMAEventCode SENT_REQUEST_GET_PROCESS_INSTANCE_ATTRIBUTE =
        new WMAEventCode(SENT_REQUEST_GET_PROCESS_INSTANCE_ATTRIBUTE_INT);
    public static final WMAEventCode SENT_REQUEST_CHANGE_PROCESS_INSTANCE_STATE =
        new WMAEventCode(SENT_REQUEST_CHANGE_PROCESS_INSTANCE_STATE_INT);
    public static final WMAEventCode SENT_STARTED_PROCESS_INSTANCE =
        new WMAEventCode(SENT_STARTED_PROCESS_INSTANCE_INT);
    public static final WMAEventCode SENT_CHANGED_PROCESS_INSTANCE_ATTRIBUTE =
        new WMAEventCode(SENT_CHANGED_PROCESS_INSTANCE_ATTRIBUTE_INT);
    public static final WMAEventCode SENT_RETRIEVED_PROCESS_INSTANCE_ATTRIBUTE =
        new WMAEventCode(SENT_RETRIEVED_PROCESS_INSTANCE_ATTRIBUTE_INT);
    public static final WMAEventCode SENT_ABORTED_PROCESS_INSTANCE =
        new WMAEventCode(SENT_ABORTED_PROCESS_INSTANCE_INT);
    public static final WMAEventCode SENT_TERMINATED_PROCESS_INSTANCE =
        new WMAEventCode(SENT_TERMINATED_PROCESS_INSTANCE_INT);
    public static final WMAEventCode SENT_CHANGED_PROCESS_INSTANCE_STATE =
        new WMAEventCode(SENT_CHANGED_PROCESS_INSTANCE_STATE_INT);
    public static final WMAEventCode SENT_COMPLETED_PROCESS_INSTANCE =
        new WMAEventCode(SENT_COMPLETED_PROCESS_INSTANCE_INT);
    public static final WMAEventCode RECEIVED_REQUEST_START_PROCESS_INSTANCE =
        new WMAEventCode(RECEIVED_REQUEST_START_PROCESS_INSTANCE_INT);
    public static final WMAEventCode RECEIVED_REQUEST_ABORT_PROCESS_INSTANCE =
        new WMAEventCode(RECEIVED_REQUEST_ABORT_PROCESS_INSTANCE_INT);
    public static final WMAEventCode RECEIVED_REQUEST_TERMINATE_PROCESS_INSTANCE =
        new WMAEventCode(RECEIVED_REQUEST_TERMINATE_PROCESS_INSTANCE_INT);
    public static final WMAEventCode RECEIVED_REQUEST_CHANGE_PROCESS_INSTANCE_STATE =
        new WMAEventCode(RECEIVED_REQUEST_CHANGE_PROCESS_INSTANCE_STATE_INT);
    public static final WMAEventCode RECEIVED_REQUEST_CHANGE_PROCESS_INSTANCE_ATTRIBUTE =
        new WMAEventCode(
            RECEIVED_REQUEST_CHANGE_PROCESS_INSTANCE_ATTRIBUTE_INT);
    public static final WMAEventCode RECEIVED_REQUEST_GET_PROCESS_INSTANCE_ATTRIBUTE =
        new WMAEventCode(RECEIVED_REQUEST_GET_PROCESS_INSTANCE_ATTRIBUTE_INT);
    public static final WMAEventCode RECEIVED_STARTED_PROCESS_INSTANCE =
        new WMAEventCode(RECEIVED_STARTED_PROCESS_INSTANCE_INT);
    public static final WMAEventCode RECEIVED_CHANGED_PROCESS_INSTANCE_ATTRIBUTE =
        new WMAEventCode(RECEIVED_CHANGED_PROCESS_INSTANCE_ATTRIBUTE_INT);
    public static final WMAEventCode RECEIVED_RETRIEVED_PROCESS_INSTANCE_ATTRIBUTE =
        new WMAEventCode(RECEIVED_RETRIEVED_PROCESS_INSTANCE_ATTRIBUTE_INT);
    public static final WMAEventCode RECEIVED_ABORTED_PROCESS_INSTANCE =
        new WMAEventCode(RECEIVED_ABORTED_PROCESS_INSTANCE_INT);
    public static final WMAEventCode RECEIVED_TERMINATED_PROCESS_INSTANCE =
        new WMAEventCode(RECEIVED_TERMINATED_PROCESS_INSTANCE_INT);
    public static final WMAEventCode RECEIVED_CHANGED_PROCESS_INSTANCE_STATE =
        new WMAEventCode(RECEIVED_CHANGED_PROCESS_INSTANCE_STATE_INT);
    public static final WMAEventCode RECEIVED_COMPLETED_PROCESS_INSTANCE =
        new WMAEventCode(RECEIVED_COMPLETED_PROCESS_INSTANCE_INT);
    public static final WMAEventCode STARTED_SESSION =
        new WMAEventCode(STARTED_SESSION_INT);
    public static final WMAEventCode STOPPED_SESSION =
        new WMAEventCode(STOPPED_SESSION_INT);
    public static final WMAEventCode CHANGED_PROCESS_DEFINITION_STATE =
        new WMAEventCode(CHANGED_PROCESS_DEFINITION_STATE_INT);

    private static final String[] TAGS = {
        "(UNKNOWN)",
        "CREATED_PROCESS_INSTANCE",
        "STARTED_PROCESS_INSTANCE",
        "CHANGED_PROCESS_INSTANCE_STATE",
        "COMPLETED_PROCESS_INSTANCE",
        "TERMINATED_PROCESS_INSTANCE",
        "ABORTED_PROCESS_INSTANCE",
        "WAITING_ON_EVENT",
        "EVENT_OCCURRED",
        "STARTED_SUBPROCESS",
        "COMPLETED_SUBPROCESS",
        "ASSIGNED_PROCESS_INSTANCE_ATTRIBUTES",
        "CHANGED_ACTIVITY_INSTANCE_STATE",
        "COMPLETED_ACTIVITY_INSTANCE",
        "TERMINATED_ACTIVITY_INSTANCE",
        "ABORTED_ACTIVITY_INSTANCE",
        "ASSIGNED_ACTIVITY_INSTANCE_ATTRIBUTES",
        "STARTED_WORK_ITEM",
        "COMPLETED_WORK_ITEM",
        "REJECTED_WORK_ITEM",
        "SELECTED_WORK_ITEM",
        "CHANGED_WORK_ITEM_STATE",
        "ASSIGNED_WORK_ITEM",
        "REASSIGNED_WORK_ITEM",
        "REASSIGNED_WORK_LIST",
        "ASSIGNED_PROCESS_INSTANCE_ATTRIBUTE",
        "SENT_REQUEST_START_PROCESS_INSTANCE",
        "SENT_REQUEST_ABORT_PROCESS_INSTANCE",
        "SENT_REQUEST_TERMINATE_PROCESS_INSTANCE",
        "SENT_REQUEST_CHANGE_PROCESS_INSTANCE_ATTRIBUTE",
        "SENT_REQUEST_GET_PROCESS_INSTANCE_ATTRIBUTE",
        "SENT_REQUEST_CHANGE_PROCESS_INSTANCE_STATE",
        "SENT_STARTED_PROCESS_INSTANCE",
        "SENT_CHANGED_PROCESS_INSTANCE_STATE",
        "SENT_RETRIEVED_PROCESS_INSTANCE_ATTRIBUTE",
        "SENT_ABORTED_PROCESS_INSTANCE",
        "SENT_TERMINATED_PROCESS_INSTANCE",
        "SENT_CHANGED_PROCESS_INSTANCE_STATE",
        "SENT_COMPLETED_PROCESS_INSTANCE",
        "RECEIVED_REQUEST_START_PROCESS_INSTANCE",
        "RECEIVED_REQUEST_ABORT_PROCESS_INSTANCE",
        "RECEIVED_REQUEST_TERMINATE_PROCESS_INSTANCE",
        "RECEIVED_REQUEST_CHANGE_PROCESS_INSTANCE_STATE",
        "RECEIVED_REQUEST_CHANGE_PROCESS_INSTANCE_ATTRIBUTE",
        "RECEIVED_REQUEST_GET_PROCESS_INSTANCE_ATTRIBUTE",
        "RECEIVED_STARTED_PROCESS_INSTANCE",
        "RECEIVED_CHANGED_PROCESS_INSTANCE_ATTRIBUTE",
        "RECEIVED_RETRIEVED_PROCESS_INSTANCE_ATTRIBUTE",
        "RECEIVED_ABORTED_PROCESS_INSTANCE",
        "RECEIVED_TERMINATED_PROCESS_INSTANCE",
        "RECEIVED_CHANGED_PROCESS_INSTANCE_STATE",
        "RECEIVED_COMPLETED_PROCESS_INSTANCE",
        "STARTED_SESSION",
        "STOPPED_SESSION",
        "CHANGED_PROCESS_DEFINITION_STATE"
    };
    private static final WMAEventCode[] VALUES = {
        UNKNOWN,
        CREATED_PROCESS_INSTANCE,
        STARTED_PROCESS_INSTANCE,
        CHANGED_PROCESS_INSTANCE_STATE,
        COMPLETED_PROCESS_INSTANCE,
        TERMINATED_PROCESS_INSTANCE,
        ABORTED_PROCESS_INSTANCE,
        WAITING_ON_EVENT,
        EVENT_OCCURRED,
        STARTED_SUBPROCESS,
        COMPLETED_SUBPROCESS,
        ASSIGNED_PROCESS_INSTANCE_ATTRIBUTES,
        CHANGED_ACTIVITY_INSTANCE_STATE,
        COMPLETED_ACTIVITY_INSTANCE,
        TERMINATED_ACTIVITY_INSTANCE,
        ABORTED_ACTIVITY_INSTANCE,
        ASSIGNED_ACTIVITY_INSTANCE_ATTRIBUTES,
        STARTED_WORK_ITEM,
        COMPLETED_WORK_ITEM,
        REJECTED_WORK_ITEM,
        SELECTED_WORK_ITEM,
        CHANGED_WORK_ITEM_STATE,
        ASSIGNED_WORK_ITEM,
        REASSIGNED_WORK_ITEM,
        REASSIGNED_WORK_LIST,
        ASSIGNED_PROCESS_INSTANCE_ATTRIBUTE,
        SENT_REQUEST_START_PROCESS_INSTANCE,
        SENT_REQUEST_ABORT_PROCESS_INSTANCE,
        SENT_REQUEST_TERMINATE_PROCESS_INSTANCE,
        SENT_REQUEST_CHANGE_PROCESS_INSTANCE_ATTRIBUTE,
        SENT_REQUEST_GET_PROCESS_INSTANCE_ATTRIBUTE,
        SENT_REQUEST_CHANGE_PROCESS_INSTANCE_STATE,
        SENT_STARTED_PROCESS_INSTANCE,
        SENT_CHANGED_PROCESS_INSTANCE_STATE,
        SENT_RETRIEVED_PROCESS_INSTANCE_ATTRIBUTE,
        SENT_ABORTED_PROCESS_INSTANCE,
        SENT_TERMINATED_PROCESS_INSTANCE,
        SENT_CHANGED_PROCESS_INSTANCE_STATE,
        SENT_COMPLETED_PROCESS_INSTANCE,
        RECEIVED_REQUEST_START_PROCESS_INSTANCE,
        RECEIVED_REQUEST_ABORT_PROCESS_INSTANCE,
        RECEIVED_REQUEST_TERMINATE_PROCESS_INSTANCE,
        RECEIVED_REQUEST_CHANGE_PROCESS_INSTANCE_STATE,
        RECEIVED_REQUEST_CHANGE_PROCESS_INSTANCE_ATTRIBUTE,
        RECEIVED_REQUEST_GET_PROCESS_INSTANCE_ATTRIBUTE,
        RECEIVED_STARTED_PROCESS_INSTANCE,
        RECEIVED_CHANGED_PROCESS_INSTANCE_ATTRIBUTE,
        RECEIVED_RETRIEVED_PROCESS_INSTANCE_ATTRIBUTE,
        RECEIVED_ABORTED_PROCESS_INSTANCE,
        RECEIVED_TERMINATED_PROCESS_INSTANCE,
        RECEIVED_CHANGED_PROCESS_INSTANCE_STATE,
        RECEIVED_COMPLETED_PROCESS_INSTANCE,
        STARTED_SESSION,
        STOPPED_SESSION,
        CHANGED_PROCESS_DEFINITION_STATE
    };
    private static final Map tagMap = new HashMap(67);

    private final int _value;

    static {
        for (int i = 0; i < TAGS.length; i++) {
            tagMap.put(TAGS[i], VALUES[i]);
        }
    }

    public static WMAEventCode valueOf(int code) {
        return VALUES[code];
    }

    public static WMAEventCode valueOf(String tag) {
        WMAEventCode wmaEventCode = (WMAEventCode)tagMap.get(tag);
        if (wmaEventCode == null && tag != null)
            throw new IllegalArgumentException(tag);
        return wmaEventCode;
    }

    private WMAEventCode(int value) {
        _value = value;
    }

    public int value() {
        return _value;
    }

    public String toString() {
        return TAGS[_value];
    }

    private Object readResolve() {
        return VALUES[_value];
    }
}