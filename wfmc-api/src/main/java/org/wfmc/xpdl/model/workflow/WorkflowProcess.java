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

package org.wfmc.xpdl.model.workflow;

import org.wfmc.xpdl.PackageVisitor;
import org.wfmc.xpdl.model.XPDLProperties;
import org.wfmc.xpdl.model.activity.Activity;
import org.wfmc.xpdl.model.activity.ActivitySet;
import org.wfmc.xpdl.model.activity.BlockActivity;
import org.wfmc.xpdl.model.data.FormalParameter;
import org.wfmc.xpdl.model.ext.Trigger;
import org.wfmc.xpdl.model.misc.AccessLevel;
import org.wfmc.xpdl.model.misc.Graph;
import org.wfmc.xpdl.model.misc.Invokable;
import org.wfmc.xpdl.model.misc.ResourceContainer;
import org.wfmc.xpdl.model.pkg.XPDLPackage;
import org.wfmc.xpdl.model.transition.Transition;

import java.beans.PropertyVetoException;
import java.util.Map;

public final class WorkflowProcess extends ResourceContainer
        implements Graph, Invokable {

    private static final long serialVersionUID = -2304468508983567539L;
    public static final String ACCESS_LEVEL = XPDLProperties.ACCESS_LEVEL;
    public static final String ACTIVITY = XPDLProperties.ACTIVITY;
    public static final String ACTIVITY_SET = XPDLProperties.ACTIVITY_SET;
    public static final String COMPLETION_STRATEGY = XPDLProperties.COMPLETION_STRATEGY;
    public static final String FORMAL_PARAMETER = XPDLProperties.FORMAL_PARAMETER;
    public static final String PACKAGE = XPDLProperties.PACKAGE;
    public static final String PACKAGE_ID = XPDLProperties.PACKAGE_ID;
    public static final String PROCESS_DEFINITION_ID = XPDLProperties.PROCESS_DEFINITION_ID;
    public static final String PROCESS_HEADER = XPDLProperties.PROCESS_HEADER;
    public static final String STATE = XPDLProperties.STATE;
    public static final String TRANSITION = XPDLProperties.TRANSITION;
    public static final String TRIGGER = XPDLProperties.TRIGGER;
    private static final Activity[] EMPTY_ACTIVITY = {};
    private static final ActivitySet[] EMPTY_ACTIVITY_SET = {};
    private static final FormalParameter[] EMPTY_FORMAL_PARM = {};
    private static final Transition[] EMPTY_TRANSITION = {};
    private static final String[] _indexedPropertyNames = {
            APPLICATION, DATA_FIELD, PARTICIPANT, ACTIVITY, ACTIVITY_SET,
            FORMAL_PARAMETER, TRANSITION
    };
    private static final Object[] _indexedPropertyValues = {
            EMPTY_APPLICATION, EMPTY_DATA_FIELD, EMPTY_PARTICIPANT, EMPTY_ACTIVITY,
            EMPTY_ACTIVITY_SET, EMPTY_FORMAL_PARM, EMPTY_TRANSITION
    };
    private static final int ACTIVITY_IDX = 3;
    private static final int ACTIVITY_SET_IDX = 4;
    private static final int FORMAL_PARAMETER_IDX = 5;
    private static final int TRANSITION_IDX = 6;

    private XPDLPackage _pkg;
    private ProcessHeader _processHeader;
    private FormalParameter[] _formalParameter = EMPTY_FORMAL_PARM;
    private ActivitySet[] _activitySet = EMPTY_ACTIVITY_SET;
    private Activity[] _activity = EMPTY_ACTIVITY;
    private Transition[] _transition = EMPTY_TRANSITION;
    private AccessLevel _accessLevel;
    private String _completionStrategy;
    private Trigger _trigger;
    private int _state = 1; // (WMProcessDefinitionState.ENABLED_INT)

    // 'Connects up' each BlockActivity to its ActivitySet.
    private static void resolveActivitySets(ActivitySet[] activitySets,
                                            Activity[] activities, boolean recursive) {

        // Resolve ActivitySet references from the activities collection.
        for (int i = 0, m = activities.length; i < m; i++) {
            Activity activity = activities[i];
            BlockActivity ba = activity.getBlockActivity();
            if (ba != null) {
                for (int j = 0, n = activitySets.length; j < n; j++) {
                    ActivitySet activitySet = activitySets[j];
                    if (activitySet.getId().equals(ba.getBlockId())) {
                        ba.setActivitySet(activitySet);
                        break;
                    }
                }
                if (ba.getActivitySet() == null) {
                    throw new IllegalStateException("ActivitySet not found: " +
                            ba.getBlockId());
                }
            }
        }

        if (recursive) {
            // Resolve ActivitySet references from each of the ActivitySets.
            for (int i = 0, n = activitySets.length; i < n; i++) {
                ActivitySet activitySet = activitySets[i];
                resolveActivitySets(activitySets, activitySet.getActivity(),
                        false);
            }
        }
    }

    // 'Connects up' each activity to its transitions.
    private static void resolveTransitions(ActivitySet[] activitySets,
                                           Activity[] activities, Transition[] transitions) {

        for (int i = 0, m = activities.length; i < m; i++) {
            Activity activity = activities[i];
            String activityId = activity.getId();
            Map afferentTransitions = activity.getAfferentTransitions();
            Map efferentTransitions = activity.getEfferentTransitions();
            afferentTransitions.clear();
            efferentTransitions.clear();
            for (int j = 0, n = transitions.length; j < n; j++) {
                Transition transition = transitions[j];
                if (transition.getFrom().equals(activityId)) {
                    transition.setFromActivity(activity);
                    efferentTransitions.put(transition.getId(), transition);
                }
                if (transition.getTo().equals(activityId)) {
                    transition.setToActivity(activity);
                    afferentTransitions.put(transition.getId(), transition);
                }
            }
        }

        if (activitySets != null) {
            // Resolve ActivitySet references from each of the ActivitySets.
            for (int i = 0, n = activitySets.length; i < n; i++) {
                ActivitySet activitySet = activitySets[i];
                resolveTransitions(null, activitySet.getActivity(),
                        activitySet.getTransition());
            }
        }
    }

    public WorkflowProcess() {
        super(_indexedPropertyNames, _indexedPropertyValues);
        _processHeader = new ProcessHeader();
    }

    /**
     * Construct a new WorkflowProcess.
     *
     * @param id            The id
     * @param name          The name
     * @param processHeader The Workflow process header
     */
    public WorkflowProcess(String id, String name, XPDLPackage pkg,
                           ProcessHeader processHeader) {

        super(_indexedPropertyNames, _indexedPropertyValues, id, name);
        setProcessHeader(processHeader);
        _pkg = pkg;
    }

    public void accept(PackageVisitor visitor) {
        visitor.visit(this);
        super.accept(visitor);
        for (int i = 0; i < _formalParameter.length; i++)
            _formalParameter[i].accept(visitor);
        for (int i = 0; i < _activitySet.length; i++)
            _activitySet[i].accept(visitor);
        for (int i = 0; i < _activity.length; i++)
            _activity[i].accept(visitor);
        for (int i = 0; i < _transition.length; i++)
            _transition[i].accept(visitor);
    }

    public Trigger getTrigger() {
        return _trigger;
    }

    public void setTrigger(Trigger trigger) {
        _trigger = trigger;
    }

    /**
     * Get the workflow process access level.
     *
     * @return The access level
     */

    public AccessLevel getAccessLevel() {
        return _accessLevel;
    }

    /**
     * Set the workflow process access level.
     *
     * @param accessLevel The access level
     */
    public void setAccessLevel(AccessLevel accessLevel) {
        _accessLevel = accessLevel;
    }

    public void add(Activity activity) throws PropertyVetoException {
        _activity = (Activity[]) add(ACTIVITY_IDX, activity);
    }

    public void remove(Activity activity) throws PropertyVetoException {
        _activity = (Activity[]) remove(ACTIVITY_IDX, activity);
    }

    public Activity[] getActivity() {
        return (Activity[]) get(ACTIVITY_IDX);
    }

    public Activity getActivity(int i) {
        return _activity[i];
    }

    public Activity getActivity(String id) {
        if (_activity != null) {
            for (int i = 0; i < _activity.length; i++) {
                Activity activity = _activity[i];
                if (activity.getId().equals(id))
                    return activity;
            }
        }
        return null;
    }

    public void setActivity(Activity[] activities)
            throws PropertyVetoException {

        set(ACTIVITY_IDX, _activity = activities == null
                ? EMPTY_ACTIVITY : activities);
    }

    public void setActivity(int i, Activity activity)
            throws PropertyVetoException {

        set(ACTIVITY_IDX, i, activity);
    }

    public void add(ActivitySet activitySet) throws PropertyVetoException {
        _activitySet = (ActivitySet[]) add(ACTIVITY_SET_IDX, activitySet);
    }

    public void remove(ActivitySet activitySet) throws PropertyVetoException {
        _activitySet = (ActivitySet[]) remove(ACTIVITY_SET_IDX, activitySet);
    }

    public ActivitySet[] getActivitySet() {
        return (ActivitySet[]) get(ACTIVITY_SET_IDX);
    }

    public ActivitySet getActivitySet(int i) {
        return _activitySet[i];
    }

    public ActivitySet getActivitySet(String id) {
        if (_activitySet != null) {
            for (int i = 0; i < _activitySet.length; i++) {
                ActivitySet as = _activitySet[i];
                if (as.getId().equals(id))
                    return as;
            }
        }
        return null;
    }

    public void setActivitySet(ActivitySet[] activitySet)
            throws PropertyVetoException {

        set(ACTIVITY_SET_IDX, _activitySet = activitySet == null
                ? EMPTY_ACTIVITY_SET : activitySet);
    }

    public void setActivitySet(int i, ActivitySet activitySet)
            throws PropertyVetoException {

        set(ACTIVITY_SET_IDX, i, activitySet);
    }

    public String getCompletionStrategy() {
        return _completionStrategy;
    }

    public void setCompletionStrategy(String completionStrategy) {
        _completionStrategy = completionStrategy;
    }

    public void add(FormalParameter formalParameter)
            throws PropertyVetoException {

        _formalParameter = (FormalParameter[]) add(FORMAL_PARAMETER_IDX,
                formalParameter);
    }

    public void remove(FormalParameter formalParameter)
            throws PropertyVetoException {

        _formalParameter = (FormalParameter[]) remove(FORMAL_PARAMETER_IDX,
                formalParameter);
    }

    public FormalParameter[] getFormalParameter() {
        return (FormalParameter[]) get(FORMAL_PARAMETER_IDX);
    }

    public FormalParameter getFormalParameter(int i) {
        return _formalParameter[i];
    }

    public FormalParameter getFormalParameter(String id) {
        if (_formalParameter != null) {
            for (int i = 0; i < _formalParameter.length; i++) {
                FormalParameter fp = _formalParameter[i];
                if (fp.getId().equals(id))
                    return fp;
            }
        }
        return null;
    }

    public void setFormalParameter(FormalParameter[] formalParameters)
            throws PropertyVetoException {

        set(FORMAL_PARAMETER_IDX, _formalParameter = formalParameters == null
                ? EMPTY_FORMAL_PARM : formalParameters);
    }

    public void setFormalParameter(int i, FormalParameter formalParameter)
            throws PropertyVetoException {

        set(FORMAL_PARAMETER_IDX, i, formalParameter);
    }

    public XPDLPackage getPackage() {
        return _pkg;
    }

    public void setPackage(XPDLPackage pkg) {
        _pkg = pkg;
    }

    public String getPackageId() {
        return _pkg == null ? null : _pkg.getId();
    }

    public String getProcessDefinitionId() {
        return getId();
    }

    public ProcessHeader getProcessHeader() {
        return _processHeader;
    }

    public void setProcessHeader(ProcessHeader processHeader) {
        if (processHeader == null) {
            throw new IllegalArgumentException(
                    "ProcessHeader must not be null");
        }

        _processHeader = processHeader;
    }

    public int getState() {
        return _state;
    }

    public void setState(int state) {
        _state = state;
    }

    public void add(Transition transition) throws PropertyVetoException {
        _transition = (Transition[]) add(TRANSITION_IDX, transition);
    }

    public void remove(Transition transition) throws PropertyVetoException {
        _transition = (Transition[]) remove(TRANSITION_IDX, transition);
    }

    public Transition[] getTransition() {
        return (Transition[]) get(TRANSITION_IDX);
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

    public void setTransition(Transition[] transitions)
            throws PropertyVetoException {

        set(TRANSITION_IDX, _transition = transitions == null
                ? EMPTY_TRANSITION : transitions);
    }

    public void setTransition(int i, Transition transition)
            throws PropertyVetoException {

        set(TRANSITION_IDX, i, transition);
    }

    public void setPkg(XPDLPackage pkg) {
        _pkg = pkg;
    }

    public void resolveReferences() {
        resolveActivitySets(getActivitySet(), getActivity(), true);
        resolveTransitions(getActivitySet(), getActivity(), getTransition());
    }

    public String toString() {
        return "WorkflowProcess[id=" + getId() + ", name=" + getName() + ']';
    }
}