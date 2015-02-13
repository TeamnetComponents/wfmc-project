package org.wfmc.impl.repository;

import java.io.Serializable;

/**
 * Describes a subscription to a workflow event.
 *
 * @author Adrian Price
 */
public final class WorkflowEventSubscription implements Serializable {
    private static final long serialVersionUID = 9082940684713832739L;
    public String listenerType;
    public int eventMask = Integer.MAX_VALUE;

    public String toString() {
        return "WorkflowEventSubscription[listenerType=" + listenerType +
            ", eventMask=" + eventMask + ']';
    }
}