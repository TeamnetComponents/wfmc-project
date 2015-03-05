package org.wfmc.impl.base;



import org.wfmc.wapi.WMWorkItem;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Adrian Price
 */
public abstract class WMWorkItem_Abstract implements WMWorkItem,Serializable {
    private static final long serialVersionUID = 4138703038866374530L;
    private String _id;
    private String _name;
    private String _activityDefinitionId;
    private String _processDefinitionId;
    private String _processInstanceId;
    private int _priority;
    private Date _startedDate;
    private Date _targetDate;
    private Date _dueDate;
    private Date _completedDate;

    protected WMWorkItem_Abstract() {
    }

    protected WMWorkItem_Abstract(String id, String name, String activityDefinitionId,
                                  String processDefinitionId, String processInstanceId, int priority,
                                  Date startedDate, Date targetDate, Date dueDate, Date completedDate) {

        _id = id;
        _name = name;
        _activityDefinitionId = activityDefinitionId;
        _processDefinitionId = processDefinitionId;
        _processInstanceId = processInstanceId;
        _priority = priority;
        _startedDate = startedDate;
        _targetDate = targetDate;
        _dueDate = dueDate;
        _completedDate = completedDate;
    }

    public String getId() {
        return _id;
    }

    public final String getName() {
        return _name;
    }

    public final String getActivityDefinitionId() {
        return _activityDefinitionId;
    }

    public final int getPriority() {
        return _priority;
    }

    public final Date getCompletedDate() {
        return _completedDate;
    }

    public final Date getTargetDate() {
        return _targetDate;
    }

    public final Date getDueDate() {
        return _dueDate;
    }

    public final String getProcessDefinitionId() {
        return _processDefinitionId;
    }

    public final String getProcessInstanceId() {
        return _processInstanceId;
    }

    public final Date getStartedDate() {
        return _startedDate;
    }

    public final void setId(String id) {
        _id = id;
    }

    public final void setProcessDefinitionId(String processDefinitionId) {
        _processDefinitionId = processDefinitionId;
    }

    public final void setPriority(int priority) {
        _priority = priority;
    }

    public final void setStartedDate(Date startedDate) {
        _startedDate = startedDate;
    }

    public final void setTargetDate(Date targetDate) {
        _targetDate = targetDate;
    }

    public final void setDueDate(Date dueDate) {
        _dueDate = dueDate;
    }

    public final void setCompletedDate(Date completedDate) {
        _completedDate = completedDate;
    }

    public final void setName(String name) {
        _name = name;
    }

    public final void setActivityDefinitionId(String activityDefinitionId) {
        _activityDefinitionId = activityDefinitionId;
    }

    public final void setProcessInstanceId(String processInstanceId) {
        _processInstanceId = processInstanceId;
    }

    public final void setPriority(Integer priority) {
        if (priority != null)
            _priority = priority.intValue();
    }

    public abstract void setState(String state);

    public final TemporalStatus getTemporalStatus() {
        return TemporalStatus.computeStatus(_completedDate, _targetDate,
            _dueDate);
    }
}