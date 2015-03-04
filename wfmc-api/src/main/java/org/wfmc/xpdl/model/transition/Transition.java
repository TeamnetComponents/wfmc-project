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

package org.wfmc.xpdl.model.transition;


import org.wfmc.xpdl.PackageVisitor;
import org.wfmc.xpdl.model.activity.Activity;
import org.wfmc.xpdl.model.condition.Condition;
import org.wfmc.xpdl.model.ext.Event;
import org.wfmc.xpdl.model.misc.AbstractWFElement;
import org.wfmc.xpdl.model.misc.ExecutionType;

/**
 * Implementation of a transition.
 *
 * @author Adrian Price
 */
public final class Transition extends AbstractWFElement {
    private static final long serialVersionUID = -6489182908053659185L;

    private Activity _toActivity;
    private Activity _fromActivity;
    private Event _event;
    private ExecutionType _executionType;
    private Condition _condition;
    private String _from;
    private String _to;

    public Transition() {
    }

    /**
     * Construct a new Transition.
     *
     * @param id   The transition ID
     * @param name The transition name
     * @param from The activity ID where the transition starts
     * @param to   The activity ID where the transition ends
     */
    public Transition(String id, String name, String from, String to) {
        super(id, name);

        setFrom(from);
        setTo(to);
    }

    public void accept(PackageVisitor visitor) {
        visitor.visit(this);
        if (_event != null)
            _event.accept(visitor);
    }

    /**
     * Get the condition which is used to determine whether or not the
     * transition should be executed.
     *
     * @return The condition
     */
    public Condition getCondition() {
        return _condition;
    }

    /**
     * Set the condition which is used to determine whether or not the
     * transition should be executed.
     *
     * @param condition The condition
     */
    public void setCondition(Condition condition) {
        _condition = condition;
    }

    public Event getEvent() {
        return _event;
    }

    public void setEvent(Event event) {
        _event = event;
    }

    /**
     * Get the start activity ID
     *
     * @return The start activity ID
     */
    public String getFrom() {
        return _from;
    }

    /**
     * Set the start activity ID.
     *
     * @param from The new start activity ID
     */
    public void setFrom(String from) {
        if (from == null)
            throw new IllegalArgumentException("'from' attribute required");
        if (!from.equals(_from)) {
            _from = from;
            _fromActivity = null;
        }
    }

    /**
     * Get the end activity ID.
     *
     * @return The end activity ID
     */
    public String getTo() {
        return _to;
    }

    /**
     * Set the end activity ID.
     *
     * @param to The end activty ID
     */
    public void setTo(String to) {
        if (to == null)
            throw new IllegalArgumentException("'to' attribute required");
        if (!to.equals(_to)) {
            _to = to;
            _toActivity = null;
        }
    }

    public Activity getToActivity() {
        return _toActivity;
    }

    public void setToActivity(Activity toActivity) {
        _toActivity = toActivity;
        _to = toActivity.getId();
    }

    public Activity getFromActivity() {
        return _fromActivity;
    }

    public void setFromActivity(Activity fromActivity) {
        _fromActivity = fromActivity;
        _from = fromActivity.getId();
    }

    public ExecutionType getExecution() {
        return _executionType;
    }

    public void setExecutionType(ExecutionType executionType) {
        _executionType = executionType;
    }

    public String toString() {
        return "Transition[id='" + getId() +
            "', executionType=" + _executionType +
            ", condition=" + _condition +
            ", from='" + _from + '\'' +
            ", to='" + _to + '\'' +
            ']';
    }
}