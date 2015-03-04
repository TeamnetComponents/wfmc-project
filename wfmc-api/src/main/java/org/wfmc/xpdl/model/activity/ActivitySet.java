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

package org.wfmc.xpdl.model.activity;


import org.wfmc.util.AbstractBean;
import org.wfmc.xpdl.PackageVisitor;
import org.wfmc.xpdl.XPDLMessages;
import org.wfmc.xpdl.model.XPDLProperties;
import org.wfmc.xpdl.model.misc.Graph;
import org.wfmc.xpdl.model.transition.Transition;

import java.beans.PropertyVetoException;

/**
 * A set of activities and related transitions.
 *
 * @author Adrian Price
 */
public final class ActivitySet extends AbstractBean implements Graph {
    private static final long serialVersionUID = 7301415215949413883L;
    public static final String ACTIVITY = XPDLProperties.ACTIVITY;
    public static final String ID = XPDLProperties.ID;
    public static final String TRANSITION = XPDLProperties.TRANSITION;
    private static final Activity[] EMPTY_ACTIVITY = {};
    private static final Transition[] EMPTY_TRANSITION = {};
    private static final String[] _indexedPropertyNames = {
        ACTIVITY, TRANSITION
    };
    private static final Object[] _indexedPropertyValues = {
        EMPTY_ACTIVITY, EMPTY_TRANSITION
    };
    private static final int ACTIVITY_IDX = 0;
    private static final int TRANSITION_IDX = 1;

    private String _id;
    private Activity[] _activity = EMPTY_ACTIVITY;
    private Transition[] _transition = EMPTY_TRANSITION;

    public ActivitySet() {
        super(_indexedPropertyNames, _indexedPropertyValues);
    }

    /**
     * Construct a new ActivitySet.
     *
     * @param id The unique ID
     */
    public ActivitySet(String id) {
        super(_indexedPropertyNames, _indexedPropertyValues);
        setId(id);
    }

    public void accept(PackageVisitor visitor) {
        visitor.visit(this);
        for (int i = 0; i < _activity.length; i++)
            _activity[i].accept(visitor);
        for (int i = 0; i < _transition.length; i++)
            _transition[i].accept(visitor);
    }

    public void add(Activity activity) throws PropertyVetoException {
        _activity = (Activity[])add(ACTIVITY_IDX, activity);
    }

    public void remove(Activity activity) throws PropertyVetoException {
        _activity = (Activity[])remove(ACTIVITY_IDX, activity);
    }

    /**
     * Get a list of activities in the set.
     *
     * @return List of activities in the set
     */
    public Activity[] getActivity() {
        return (Activity[])get(ACTIVITY_IDX);
    }

    public Activity getActivity(int i) {
        return _activity[i];
    }

    public Activity getActivity(String id) {
        if (_activity != null) {
            for (int i = 0; i < _activity.length; i++) {
                Activity a = _activity[i];
                if (a.getId().equals(id))
                    return a;
            }
        }
        return null;
    }

    public void setActivity(Activity[] activities)
        throws PropertyVetoException {

        set(ACTIVITY_IDX,
            _activity = activities == null ? EMPTY_ACTIVITY : activities);
    }

    public void setActivity(int i, Activity activity)
        throws PropertyVetoException {

        set(ACTIVITY_IDX, i, activity);
    }

    /**
     * Get the ID for the activity set.
     *
     * @return The activity set ID
     */
    public String getId() {
        return _id;
    }

    /**
     * Set the activity set ID.  The id must be unique across all activity sets
     * in the process definition and cannot be null.
     *
     * @param id The activity set ID
     */
    public void setId(String id) {
        if (id == null)
            throw new IllegalArgumentException(XPDLMessages.ID_CANNOT_BE_NULL);
        _id = id;
    }

    public void add(Transition transition) throws PropertyVetoException {
        _transition = (Transition[])add(TRANSITION_IDX, transition);
    }

    public void remove(Transition transition) throws PropertyVetoException {
        _transition = (Transition[])remove(TRANSITION_IDX, transition);
    }

    /**
     * Get a list of transitions in the set.
     *
     * @return List of transitions in the set
     */
    public Transition[] getTransition() {
        return (Transition[])get(TRANSITION_IDX);
    }

    public Transition getTransition(int i) {
        return _transition[i];
    }

    public Transition getTransition(String id) {
        if (_transition != null) {
            for (int i = 0; i < _transition.length; i++) {
                Transition t = _transition[i];
                if (t.getId().equals(id))
                    return t;
            }
        }
        return null;
    }

    public void setTransition(Transition[] transitions) {
        _transition = transitions == null ? EMPTY_TRANSITION : transitions;
    }

    public void setTransition(int i, Transition transition) {
        _transition[i] = transition;
    }

    public String toString() {
        return "ActivitySet[id=" + _id + ']';
    }
}