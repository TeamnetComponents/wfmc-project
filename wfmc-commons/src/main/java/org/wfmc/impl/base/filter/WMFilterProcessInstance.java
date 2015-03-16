package org.wfmc.impl.base.filter;

import org.wfmc.impl.base.WMParticipantImpl;
import org.wfmc.wapi.WMFilter;
import org.wfmc.wapi.WMParticipant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucian.Dragomir on 3/4/2015.
 */
public class WMFilterProcessInstance extends WMFilter {
    String processInstanceName;
    String processInstanceId;
    String processDefinitionId;
    List<WMParticipant> wmParticipantList;
    Boolean isActive;

    public WMFilterProcessInstance() {
        super("");
        wmParticipantList = new ArrayList<>();
    }

    public WMFilterProcessInstance addProcessInstanceParticipant(WMParticipant participant){
        wmParticipantList.add(participant);
        return this;
    }

    public WMFilterProcessInstance addProcessInstanceParticipant(String participantName){
        wmParticipantList.add(new WMParticipantImpl(participantName));
        return this;
    }

    public WMFilterProcessInstance addProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public WMFilterProcessInstance addProcessInstanceName(String processInstanceName) {
        this.processInstanceName = processInstanceName;
        return this;
    }

    public WMFilterProcessInstance addProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
        return this;
    }

    public WMFilterProcessInstance isActive (Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public String getProcessInstanceName() {
        return processInstanceName;
    }

    public void setProcessInstanceName(String processInstanceName) {
        this.processInstanceName = processInstanceName;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public List<WMParticipant> getWmParticipantList() {
        return wmParticipantList;
    }

    public void setWmParticipantList(List<WMParticipant> wmParticipantList) {
        this.wmParticipantList = wmParticipantList;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
