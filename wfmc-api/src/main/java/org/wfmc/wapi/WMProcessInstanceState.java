/*--

 Copyright (C) 2002 Anthony Eden, Adrian Price.
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

 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR(S) BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 POSSIBILITY OF SUCH DAMAGE.

 For more information on OBE, please see <http://obe.sourceforge.net/>.

 */

package org.wfmc.wapi;


/**
 * Describes the supported states of a process instance.  The states and their
 * descriptions are taken from WfMC Interface 2/3.
 *
 * @author Adrian Price
 */
public final class WMProcessInstanceState extends WMObjectState {
    private static final long serialVersionUID = 8203181961666090141L;

    /**
     * @see #OPEN_NOTRUNNING_NOTSTARTED
     */
    public static final int OPEN_NOTRUNNING_NOTSTARTED_INT = 0;
    /**
     * @see #OPEN_NOTRUNNING_SUSPENDED
     */
    public static final int OPEN_NOTRUNNING_SUSPENDED_INT = 1;
    /**
     * @see #OPEN_RUNNING
     */
    public static final int OPEN_RUNNING_INT = 2;
    /**
     * @see #CLOSED_ABORTED
     */
    public static final int CLOSED_ABORTED_INT = 3;
    /**
     * @see #CLOSED_TERMINATED
     */
    public static final int CLOSED_TERMINATED_INT = 4;
    /**
     * @see #CLOSED_COMPLETED
     */
    public static final int CLOSED_COMPLETED_INT = 5;

    /**
     * @see #OPEN_NOTRUNNING_NOTSTARTED
     */
    public static final String OPEN_NOTRUNNING_NOTSTARTED_TAG =
        "open.notRunning.notStarted";
    /**
     * @see #OPEN_NOTRUNNING_SUSPENDED
     */
    public static final String OPEN_NOTRUNNING_SUSPENDED_TAG =
        "open.notRunning.suspended";
    /**
     * @see #OPEN_RUNNING
     */
    public static final String OPEN_RUNNING_TAG = "open.running";
    /**
     * @see #CLOSED_ABORTED
     */
    public static final String CLOSED_ABORTED_TAG = "closed.aborted";
    /**
     * @see #CLOSED_TERMINATED
     */
    public static final String CLOSED_TERMINATED_TAG = "closed.terminated";
    /**
     * @see #CLOSED_COMPLETED
     */
    public static final String CLOSED_COMPLETED_TAG = "closed.completed";
    private static final String[] TAGS = {
        OPEN_NOTRUNNING_NOTSTARTED_TAG,
        OPEN_NOTRUNNING_SUSPENDED_TAG,
        OPEN_RUNNING_TAG,
        CLOSED_ABORTED_TAG,
        CLOSED_TERMINATED_TAG,
        CLOSED_COMPLETED_TAG
    };

    /**
     * The process instance has been created, but was not started yet.
     */
    public static final WMProcessInstanceState OPEN_NOTRUNNING_NOTSTARTED
        = new WMProcessInstanceState(OPEN_NOTRUNNING_NOTSTARTED_INT);
    /**
     * Execution of the process instance was temporarily suspended.
     */
    public static final WMProcessInstanceState OPEN_NOTRUNNING_SUSPENDED
        = new WMProcessInstanceState(OPEN_NOTRUNNING_SUSPENDED_INT);
    /**
     * The process instance is executing.
     */
    public static final WMProcessInstanceState OPEN_RUNNING
        = new WMProcessInstanceState(OPEN_RUNNING_INT);
    /**
     * Enactment of the process instance has been aborted by a user. (See the
     * specification of WMAbortProcessInstance for a definition of abortion in
     * contrast to termination).
     */
    public static final WMProcessInstanceState CLOSED_ABORTED
        = new WMProcessInstanceState(CLOSED_ABORTED_INT);
    /**
     * Enactment of the process instance has been terminated by a user. (See
     * the specification of WMTerminateProcessInstance for a definition of
     * termination in contrast to abortion).
     */
    public static final WMProcessInstanceState CLOSED_TERMINATED
        = new WMProcessInstanceState(CLOSED_TERMINATED_INT);
    /**
     * Enactment of the process instance has completed normally. (i.e., was not
     * forced by a user).
     */
    public static final WMProcessInstanceState CLOSED_COMPLETED
        = new WMProcessInstanceState(CLOSED_COMPLETED_INT);
    private static final WMProcessInstanceState[] VALUES = {
        OPEN_NOTRUNNING_NOTSTARTED,
        OPEN_NOTRUNNING_SUSPENDED,
        OPEN_RUNNING,
        CLOSED_ABORTED,
        CLOSED_TERMINATED,
        CLOSED_COMPLETED
    };

    /**
     * Abort the instance.
     */
    public static final int ABORT_ACTION = 0;
    /**
     * Complete the instance.
     */
    public static final int COMPLETE_ACTION = 1;
    /**
     * Create the instance.
     */
    public static final int CREATE_ACTION = 2;
    /**
     * Delete the instance.
     */
    public static final int DELETE_ACTION = 3;
    /**
     * Resume the instance.
     */
    public static final int RESUME_ACTION = 4;
    /**
     * Start the instance.
     */
    public static final int START_ACTION = 5;
    /**
     * Suspend the instance.
     */
    public static final int SUSPEND_ACTION = 6;
    /**
     * Terminate the instance.
     */
    public static final int TERMINATE_ACTION = 7;

    // new state = STATES[state][action]
    private static final int[][] STATES = {
        //ABORT,              COMPLETE,             CREATE,         DELETE,                         RESUME,           START,            SUSPEND,                       TERMINATE,
        {CLOSED_ABORTED_INT, ILLEGAL_ACTION, ILLEGAL_ACTION,
            OPEN_NOTRUNNING_NOTSTARTED_INT, ILLEGAL_ACTION, OPEN_RUNNING_INT,
            ILLEGAL_ACTION, CLOSED_TERMINATED_INT}, // NOTSTARTED
        {CLOSED_ABORTED_INT, ILLEGAL_ACTION, ILLEGAL_ACTION,
            OPEN_NOTRUNNING_SUSPENDED_INT, OPEN_RUNNING_INT, OPEN_RUNNING_INT,
            OPEN_NOTRUNNING_SUSPENDED_INT, CLOSED_TERMINATED_INT}, // SUSPENDED
        {CLOSED_ABORTED_INT, CLOSED_COMPLETED_INT, ILLEGAL_ACTION,
            OPEN_RUNNING_INT, OPEN_RUNNING_INT, OPEN_RUNNING_INT,
            OPEN_NOTRUNNING_SUSPENDED_INT, CLOSED_TERMINATED_INT}, // RUNNING
        {CLOSED_ABORTED_INT, ILLEGAL_ACTION, ILLEGAL_ACTION, CLOSED_ABORTED_INT,
            ILLEGAL_ACTION, ILLEGAL_ACTION, ILLEGAL_ACTION, ILLEGAL_ACTION},
        // ABORTED
        {ILLEGAL_ACTION, ILLEGAL_ACTION, ILLEGAL_ACTION, CLOSED_TERMINATED_INT,
            ILLEGAL_ACTION, ILLEGAL_ACTION, ILLEGAL_ACTION,
            CLOSED_TERMINATED_INT}, // TERMINATED
        {ILLEGAL_ACTION, CLOSED_COMPLETED_INT, ILLEGAL_ACTION,
            CLOSED_COMPLETED_INT, ILLEGAL_ACTION, ILLEGAL_ACTION,
            ILLEGAL_ACTION, ILLEGAL_ACTION}  // COMPLETED
    };
    // action = ACTIONS[state][newState]
    private static final int[][] ACTIONS = {
        // NOTSTARTED,    SUSPENDED,      RUNNING,        ABORTED,        TERMINATED,       COMPLETED
        {NO_ACTION, ILLEGAL_ACTION, START_ACTION, ABORT_ACTION,
            TERMINATE_ACTION, ILLEGAL_ACTION},  // NOTSTARTED
        {ILLEGAL_ACTION, NO_ACTION, RESUME_ACTION, ABORT_ACTION,
            TERMINATE_ACTION, ILLEGAL_ACTION},  // SUSPENDED
        {ILLEGAL_ACTION, SUSPEND_ACTION, NO_ACTION, ABORT_ACTION,
            TERMINATE_ACTION, COMPLETE_ACTION}, // RUNNING
        {ILLEGAL_ACTION, ILLEGAL_ACTION, ILLEGAL_ACTION, NO_ACTION,
            ILLEGAL_ACTION, ILLEGAL_ACTION},  // ABORTED
        {ILLEGAL_ACTION, ILLEGAL_ACTION, ILLEGAL_ACTION, ILLEGAL_ACTION,
            NO_ACTION, ILLEGAL_ACTION},  // TERMINATED
        {ILLEGAL_ACTION, ILLEGAL_ACTION, ILLEGAL_ACTION, ILLEGAL_ACTION,
            ILLEGAL_ACTION, NO_ACTION}        // COMPLETED
    };

    public static WMProcessInstanceState valueOf(String state) {
        return (WMProcessInstanceState)valueOf(TAGS, VALUES, state);
    }

    public static WMProcessInstanceState valueOf(int state) {
        return VALUES[state];
    }

    public static WMProcessInstanceState[] states() {
        return (WMProcessInstanceState[])VALUES.clone();
    }

    private WMProcessInstanceState(int state) {
        super(state);
    }

    public boolean isClosed() {
        return _state == CLOSED_ABORTED_INT ||
            _state == CLOSED_TERMINATED_INT ||
            _state == CLOSED_COMPLETED_INT;
    }

    public static boolean isClosed(int state) {
        return valueOf(state).isClosed();
    }

    public boolean isOpen() {
        return _state == OPEN_NOTRUNNING_NOTSTARTED_INT ||
            _state == OPEN_NOTRUNNING_SUSPENDED_INT ||
            _state == OPEN_RUNNING_INT;
    }

    public static boolean isOpen(int state) {
        return valueOf(state).isOpen();
    }

    protected String[] getTags() {
        return TAGS;
    }

    protected WMObjectState[] getValues() {
        return VALUES;
    }

    protected int[] getStatesByAction() {
        return STATES[_state];
    }

    protected int[] getActionsByState() {
        return ACTIONS[_state];
    }
}