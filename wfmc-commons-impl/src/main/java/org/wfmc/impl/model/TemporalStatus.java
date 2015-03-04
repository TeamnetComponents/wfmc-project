package org.wfmc.impl.model;

import org.wfmc.server.core.util.AbstractEnum;
import org.wfmc.server.core.xpdl.OBENames;
import org.wfmc.server.core.xpdl.model.activity.Activity;
import org.wfmc.server.core.xpdl.model.misc.TimeEstimation;
import org.wfmc.server.core.xpdl.model.workflow.ProcessHeader;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The temporal status of a process instance, activity instance, or work item.
 * Temporal status reflects whether the entity in question is ahead of schedule,
 * behind schedule or overdue, according to its target and due dates.  These are
 * computed by the workflow engine from the entities Duration and Limit
 * properties as defined in the XPDL.
 *
 * @author Adrian Price
 * @see Activity #getLimit()
 * @see ProcessHeader#getLimit()
 * @see TimeEstimation#getDuration()
 */
public final class TemporalStatus extends AbstractEnum {
    public static final int UNDEFINED_INT = 0;
    public static final int NORMAL_INT = 1;
    public static final int WARNING_INT = 2;
    public static final int OVERDUE_INT = 3;

    public static final String UNDEFINED_KIND = "UNDEFINED";
    public static final String NORMAL_KIND = "NORMAL";
    public static final String WARNING_KIND = "WARNING";
    public static final String OVERDUE_KIND = "OVERDUE";

    public static final TemporalStatus UNDEFINED =
        new TemporalStatus(UNDEFINED_KIND, UNDEFINED_INT);
    public static final TemporalStatus NORMAL =
        new TemporalStatus(NORMAL_KIND, NORMAL_INT);
    public static final TemporalStatus WARNING =
        new TemporalStatus(WARNING_KIND, WARNING_INT);
    public static final TemporalStatus OVERDUE =
        new TemporalStatus(OVERDUE_KIND, OVERDUE_INT);

    private static final TemporalStatus[] _values = {
        UNDEFINED,
        NORMAL,
        WARNING,
        OVERDUE
    };
    private static final Map _tagMap = new HashMap();
    public static final List VALUES = clinit(_values, _tagMap);

    /**
     * Computes the temporal status corresponding to the set of input values.
     *
     * @param completed The date at which the process or task was completed (can
     *                  be null).
     * @param target    The date by which the process or task is/was expected to be
     *                  complete.
     * @param due       The date by which the process or task is/was required to be
     *                  complete.
     * @return Temporal status, one of: {@link #UNDEFINED}, {@link #NORMAL},
     *         {@link #WARNING}, {@link #OVERDUE}.
     */
    public static TemporalStatus computeStatus(Date completed, Date target,
        Date due) {

        long baseline = completed != null
            ? completed.getTime() : System.currentTimeMillis();
        TemporalStatus status;
        if (due != null && baseline > due.getTime())
            status = OVERDUE;
        else if (target != null && baseline > target.getTime())
            status = WARNING;
        else if (due != null || target != null)
            status = NORMAL;
        else
            status = UNDEFINED;
        return status;
    }

    private TemporalStatus(String name, int ordinal) {
        super(name, ordinal);
    }

    public List family() {
        return VALUES;
    }
}