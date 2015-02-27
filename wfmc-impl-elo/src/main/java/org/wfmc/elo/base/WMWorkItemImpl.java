package org.wfmc.elo.base;

import org.wfmc.wapi.WMParticipant;
import org.wfmc.wapi.WMWorkItemState;
import org.wfmc.wapi.WMWorkItem;

/**
 * Created by Ioan.Ivan on 2/25/2015.
 */
public class WMWorkItemImpl implements WMWorkItem {

    String name;
    String id;
    String activityDefinitionId;
    String activityInstanceId;
    String processInstanceId;
    int priority;
    WMParticipant participant;
    WMWorkItemState state;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getActivityDefinitionId() {
        return activityDefinitionId;
    }

    public void setActivityDefinitionId(String activityDefinitionId) {
        this.activityDefinitionId = activityDefinitionId;
    }

    @Override
    public String getActivityInstanceId() {
        return activityInstanceId;
    }

    public void setActivityInstanceId(String activityInstanceId) {
        this.activityInstanceId = activityInstanceId;
    }

    @Override
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public WMParticipant getParticipant() {
        return participant;
    }

    public void setParticipant(WMParticipant participant) {
        this.participant = participant;
    }

    @Override
    public WMWorkItemState getState() {
        return state;
    }

    public void setState(WMWorkItemState state) {
        this.state = state;
    }
}
