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
 * Describes the supported states of a process definition.  The states and their
 * descriptions are taken from WfMC Interface 2/3.
 *
 * @author Adrian Price
 */
public final class WMProcessDefinitionState extends WMObjectState {
    private static final long serialVersionUID = 1612969421701227549L;

    /**
     * @see #DISABLED
     */
    public static final int DISABLED_INT = 0;
    /**
     * @see #ENABLED
     */
    public static final int ENABLED_INT = 1;
    /**
     * @see #DISABLED
     */
    public static final String DISABLED_TAG = "disabled";
    /**
     * @see #ENABLED
     */
    public static final String ENABLED_TAG = "enabled";
    private static final String[] TAGS = {
        DISABLED_TAG,
        ENABLED_TAG
    };

    /**
     * The process definition is disabled, and new instances cannot be created.
     */
    public static final WMProcessDefinitionState DISABLED
        = new WMProcessDefinitionState(DISABLED_INT);
    /**
     * The process definition is enabled, and new instances can be created.
     */
    public static final WMProcessDefinitionState ENABLED
        = new WMProcessDefinitionState(ENABLED_INT);
    private static final WMProcessDefinitionState[] VALUES = {
        DISABLED,
        ENABLED
    };

    /**
     * Create the process definition.
     */
    public static final int CREATE_ACTION = 0;
    /**
     * Delete the process definition.
     */
    public static final int DELETE_ACTION = 1;
    /**
     * Disable the process definition.
     */
    public static final int DISABLE_ACTION = 2;
    /**
     * Enable the process definition.
     */
    public static final int ENABLE_ACTION = 3;
    /**
     * Update the process definition.
     */
    public static final int UPDATE_ACTION = 4;

    // new state = STATES[state][action]
    private static final int[][] STATES = {
        //CREATE,         DELETE,       DISABLE,      ENABLE,      UPDATE
        {ILLEGAL_ACTION, DISABLED_INT, DISABLED_INT, ENABLED_INT, DISABLED_INT},
        // DISABLED
        {ILLEGAL_ACTION, ENABLED_INT, DISABLED_INT, ENABLED_INT, ENABLED_INT}
        // ENABLED
    };
    // action = ACTIONS[state][newState]
    private static final int[][] ACTIONS = {
        // DISABLED,      ENABLED
        {NO_ACTION, ENABLE_ACTION}, // DISABLED
        {DISABLE_ACTION, NO_ACTION}      // ENABLED
    };

    public static WMProcessDefinitionState valueOf(String state) {
        return (WMProcessDefinitionState)valueOf(TAGS, VALUES, state);
    }

    public static WMProcessDefinitionState valueOf(int state) {
        return VALUES[state];
    }

    public static WMProcessDefinitionState[] states() {
        return (WMProcessDefinitionState[])VALUES.clone();
    }

    private WMProcessDefinitionState(int state) {
        super(state);
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