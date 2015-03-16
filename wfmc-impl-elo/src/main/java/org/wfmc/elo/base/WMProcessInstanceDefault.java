package org.wfmc.elo.base;

import java.util.List;

import org.wfmc.wapi.WMParticipant;
import org.wfmc.wapi.WMProcessInstance;
import org.wfmc.wapi.WMProcessInstanceState;

/**
 * @author adrian.zamfirescu
 * @since 25/02/2015
 */
public class WMProcessInstanceDefault implements WMProcessInstance{

    private String name;
    private String id;
    private String processDefinitionId;
    private WMProcessInstanceState state;
    private int priority;
    private WMParticipant[] participants;

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
    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    @Override
    public WMProcessInstanceState getState() {
        return state;
    }

    public void setState(WMProcessInstanceState state) {
        this.state = state;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public WMParticipant[] getParticipants() {
        return participants;
    }

    public void setParticipants(List<WMParticipant> participants) {
        this.participants = participants.toArray(this.participants);
    }
}
