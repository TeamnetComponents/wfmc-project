/*--

 Copyright (C) 2002-2005 Adrian Price.
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
 	adrianprice@sourceforge.net.

 4. Products derived from this software may not be called "OBE" or
 	"Open Business Engine", nor may "OBE" or "Open Business Engine"
 	appear in their name, without prior written permission from
 	Adrian Price (adrianprice@users.sourceforge.net).

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

 For more information on OBE, please see
 <http://obe.sourceforge.net/>.

 */

package org.wfmc.impl.spi.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Stores the persistent state of an activity instance's join.
 *
 * @author Adrian Price
 */
public abstract class JoinInstance implements Serializable {
    private static final long serialVersionUID = -6225903231253140734L;
    protected final String[] _transitions;
    protected final boolean[] _states;
    private boolean _fired;
    private int _firedCount;
    private transient boolean _modified;

    private static String asList(boolean[] states) {
        StringBuffer sb = new StringBuffer();
        sb.append('[');
        for (int i = 0, n = states.length; i < n; i++) {
            sb.append(states[i]);
            if (i < n - 1)
                sb.append(',');
        }
        sb.append(']');
        return sb.toString();
    }

    protected JoinInstance(String[] transitions) {
        _transitions = (String[])transitions.clone();
        _states = new boolean[transitions.length];
    }

    /**
     * Handles the activation of an afferent transition.
     *
     * @param transitionId The ID of the transition that fired.
     * @return <code>true</code> if the join fired.
     */
    public abstract boolean shouldFire(String transitionId);

    /**
     * Informs the join that it has fired. If it has not already fired it is
     * marked as both fired and modified, which states persist until it is
     * reset and persisted respectively. Firing a join also increments its
     * {@link #getFiredCount() fired count}.
     */
    protected final synchronized void fire() {
        if (!_fired) {
            _fired = _modified = true;
            _firedCount++;
        }
    }

    /**
     * Returns whether the join has fired.
     *
     * @return <code>true</code> if the join has fired.
     */
    public final synchronized boolean hasFired() {
        return _fired;
    }

    protected final synchronized boolean hasFired(int index) {
        return _states[index];
    }

    /**
     * Sets the fired state for the specified transition. A change of state
     * marks the join as modified.
     *
     * @param index Index to set.
     * @return Whether the transition fired.
     */
    protected final synchronized boolean didFire(int index) {
        boolean previousState = _states[index];
        if (!previousState)
            _modified = _states[index] = true;
        return !previousState;
    }

    /**
     * Returns the number of times the join has fired since the workflow
     * started.
     *
     * @return Fire count;
     */
    public final synchronized int getFiredCount() {
        return _firedCount;
    }

    /**
     * Returns whether the join has been modified since it was last persisted.
     *
     * @return <code>true</code> if the join has been modified.
     */
    public final synchronized boolean isModified() {
        return _modified;
    }

    /**
     * Resets the join to its initial state.  The {@link #getFiredCount 'fired
     * count'} is unaffected by the reset operation.
     */
    public final synchronized void reset() {
        for (int i = 0; i < _states.length; i++) {
            _modified |= _states[i];
            _states[i] = false;
        }
        _modified |= _fired;
        _fired = false;
    }

    public final synchronized boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || obj.getClass() != getClass())
            return false;

        JoinInstance that = (JoinInstance)obj;
        return _fired == that._fired && Arrays.equals(_states, that._states) &&
            Arrays.equals(_transitions, that._transitions);
    }

    public final synchronized int hashCode() {
        int hashCode = 0;
        for (int i = 0; i < _transitions.length; i++) {
            hashCode ^= _transitions[i].hashCode();
            if (_states[i])
                hashCode++;
        }
        if (_fired)
            hashCode++;
        hashCode ^= _firedCount;
        return hashCode;
    }

    public final synchronized String toString() {
        String type = getClass().getName();
        type = type.substring(type.lastIndexOf('.') + 1);
        return type + "[transitions=" +
            (_transitions == null ? null : "length:" +
                _transitions.length + Arrays.asList(_transitions))
            + ", states=" + (_states == null ? null : "length:" +
            _states.length + asList(_states))
            + ", fired=" + _fired
            + ", firedCount=" + _firedCount
            + ']';
    }
}