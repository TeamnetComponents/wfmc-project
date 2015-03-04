package org.wfmc.xpdl.model.ext;

import org.wfmc.util.AbstractBean;

/**
 * Defines the strategy for work item assignment.
 *
 * @author Adrian Price
 */
public final class AssignmentStrategyDef extends AbstractBean {
    private static final long serialVersionUID = -6818952189605162164L;
    /**
     * The default work item assignment strategy.  The default expands groups
     * and assigns work items to all users.
     */
    public static final AssignmentStrategyDef DEFAULT
        = new AssignmentStrategyDef("ALL", true);
    private String _id;
    private boolean _expandGroups = true;

    public AssignmentStrategyDef() {
    }

    public AssignmentStrategyDef(String id,
        boolean expandGroups) {

        _id = id;
        _expandGroups = expandGroups;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        _id = id;
    }

    public boolean getExpandGroups() {
        return _expandGroups;
    }

    public void setExpandGroups(boolean expandGroups) {
        _expandGroups = expandGroups;
    }

    public String toString() {
        return "AssignmentStrategyDef[id=" + _id +
            ", expandGroups=" + _expandGroups +
            ']';
    }
}