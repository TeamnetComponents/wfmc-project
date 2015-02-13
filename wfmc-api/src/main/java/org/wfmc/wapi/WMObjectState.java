package org.wfmc.wapi;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract base that represents the state of an object.
 *
 * @author Adrian Price
 */
public abstract class WMObjectState implements Serializable {
    private static final long serialVersionUID = 3506741109231223508L;

    /**
     * Signifies that the object remains in its current state.
     */
    public static final int DEFAULT_INT = -1;
    /**
     * Action does not cause a state transition.
     */
    public static final int NO_ACTION = -1;
    /**
     * Action is invalid for the current state.
     */
    public static final int ILLEGAL_ACTION = -2;
    /**
     * Action is illegal to API caller, but is being forced by the engine.
     */
    public static final int FORCED_ACTION = -3;

    public static final String MESSAGE =
        "Not a valid transition for an object in this state";

    /**
     * Array of states to which legal transitions from the current state exist.
     */
    private transient WMObjectState[] _allowedStatesList;
    /**
     * The object state code.
     */
    protected int _state;

    protected static WMObjectState valueOf(String[] tags,
        WMObjectState[] values, String state) {

        for (int i = 0; i < tags.length; i++) {
            if (tags[i].equals(state))
                return values[i];
        }
        throw new IllegalArgumentException(state);
    }

    /**
     * Construct a new <code>WMObjectState</code>.  The array type parameters
     * are references to arrays statically defined in the calling subclass.
     *
     * @param state The integer code for this state.
     */
    protected WMObjectState(int state) {
        _state = state;
    }

    /**
     * Returns the list of states to which legal transitions are possible.
     *
     * @return List of legal states.
     */
    public final WMObjectState[] getStates() {
        if (_allowedStatesList == null) {
            int[] thisState = getStatesByAction();
            int n = thisState.length;
            Set states = new HashSet(n);
            for (int i = 0; i < n; i++) {
                int action = thisState[i];
                if (action != ILLEGAL_ACTION)
                    states.add(getValues()[action]);
            }
            _allowedStatesList = (WMObjectState[])states.toArray(
                (Object[])Array.newInstance(getClass(), states.size()));
        }
        return (WMObjectState[])_allowedStatesList.clone();
    }

    /**
     * Returns the state that would result from a specified action.
     *
     * @param action Action code.
     * @return State code.
     * @throws WMTransitionNotAllowedException
     *          if the specified action is
     *          inapplicable to the current state.
     */
    protected final int stateFromAction(int action)
        throws WMTransitionNotAllowedException {

        int newState = getStatesByAction()[action];
        if (newState < 0) {
            throw new WMTransitionNotAllowedException(
                getValues()[_state].stringValue(), action,
                MESSAGE);
        }
        return newState;
    }

    /**
     * Returns the action required to transition to a specified state.
     *
     * @param newState       The new state required.
     * @param throwException Causes an exception to be thrown if the transition
     *                       would be illegal.
     * @return Action code.
     * @throws WMTransitionNotAllowedException
     *          if a transition from the current
     *          state to the new state would be illegal.
     */
    public final int checkTransition(WMObjectState newState,
        boolean throwException) throws WMTransitionNotAllowedException {

        return checkTransition(newState.value(), throwException);
    }

    /**
     * Returns the action required to transition to a specified state.
     *
     * @param newState       The new state required.
     * @param throwException Causes an exception to be thrown if the transition
     *                       would be illegal.
     * @return Action code.
     * @throws WMTransitionNotAllowedException
     *          if a transition from the current
     *          state to the new state would be illegal.
     */
    public final int checkTransition(int newState, boolean throwException)
        throws WMTransitionNotAllowedException {

        int action = getActionsByState()[newState];
        if (action == ILLEGAL_ACTION && throwException) {
            WMObjectState[] states = getValues();
            throw new WMTransitionNotAllowedException(
                states[_state].stringValue(), states[newState].stringValue(),
                MESSAGE);
        }
        return action;
    }

    /**
     * Returns the list of all state tags applicable to this instance's class.
     * The array is indexed by state code.
     * This is be a reference to a final array defined statically in the
     * instance's subclass.
     *
     * @return Array of state tags.
     */
    protected abstract String[] getTags();

    /**
     * Returns the list of all state values applicable to this instance's class.
     * This is a reference to a final array defined statically in the
     * instance's subclass.
     *
     * @return Array of state objects.
     */
    protected abstract WMObjectState[] getValues();

    /**
     * Returns the transitions from the current state, indexed by action.
     * Illegal transitions are marked by the array element value
     * {@link #ILLEGAL_ACTION}.
     *
     * @return Array of state codes. This is a reference to a final array
     *         defined statically in the instance's subclass.
     */
    protected abstract int[] getStatesByAction();

    /**
     * Returns the transitions from the current state, indexed by new state.
     * Illegal transitions are marked by the array element value
     * {@link #ILLEGAL_ACTION}.
     *
     * @return Array of action codes. This is a reference to a final array
     *         defined statically in the instance's subclass.
     */
    protected abstract int[] getActionsByState();

    /**
     * Tests for object identity.  Only one instance of each ordinal value can
     * ever exist.
     *
     * @param obj The with which to compare object this instance.
     * @return <code>true</code> if the two references point to the same object.
     */
    public final boolean equals(Object obj) {
        // This works because we have ensured that only one instance of each
        // value can exist in a VM, even when deserializing instances from an
        // ObjectInputStream.
        return this == obj;
    }

    /**
     * Equal objects must have equal hash codes.
     *
     * @return The hash code.
     */
    public final int hashCode() {
        return _state;
    }

    /**
     * Returns the object state as an integer. This ordinal state number is how
     * the state is represented in persistent storage.
     *
     * @return Ordinal state number, as defined in subclasses.
     */
    public final int value() {
        return _state;
    }

    /**
     * JavaBean-compliant property accessor, synonym for {@link #value}.
     */
    public final int getValue() {
        return _state;
    }

    protected final Object readResolve() throws ObjectStreamException {
        // This code ensures that only one instance of a given class and state
        // value can ever exist per JVM instance.
        WMObjectState obj = getValues()[_state];
        if (obj == null) {
            throw new InvalidObjectException("Invalid code in object stream: "
                + _state);
        }
        return obj;
    }

    /**
     * Returns the object state as a string.
     *
     * @return The object state.
     */
    public final String stringValue() {
        return getTags()[_state];
    }

    public final String toString() {
//        String clazz = getClass().getName();
//        clazz = clazz.substring(clazz.lastIndexOf('.') + 1);
//        return clazz + "[state=" + stringValue() + ']';
        return stringValue();
    }
}