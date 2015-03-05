package org.wfmc.impl.base.filter;

import org.wfmc.impl.base.WMParticipantImpl;
import org.wfmc.wapi.WMFilter;
import org.wfmc.wapi.WMParticipant;
import org.wfmc.wapi.WMWorkItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucian.Dragomir on 3/4/2015.
 */
public class WMFilterWorkItem extends WMFilter{

    WMWorkItem wmWorkItem;
    List<WMParticipant> wmParticipantList;
    String workItemName;


    public WMFilterWorkItem() {
        super("");
        wmParticipantList = new ArrayList<>();
    }

    public void addWorkItemParticipant(WMParticipant participant){
        wmParticipantList.add(participant);
    }

    public WMFilterWorkItem addWorkItemParticipant(String participantName){
        wmParticipantList.add(new WMParticipantImpl(participantName));
        return this;
    }

    public WMFilterWorkItem addWorkItemName(String workItemName){
        this.workItemName = workItemName;
        return this;
    }

    public WMFilterWorkItem addWorkItem(WMWorkItem wmWorkItem){
        this.wmWorkItem = wmWorkItem;
        return this;
    }

    public WMWorkItem getWmWorkItem() {
        return wmWorkItem;
    }

    public void setWmWorkItem(WMWorkItem wmWorkItem) {
        this.wmWorkItem = wmWorkItem;
    }

    public List<WMParticipant> getWmParticipantList() {
        return wmParticipantList;
    }

    public void setWmParticipantList(List<WMParticipant> wmParticipantList) {
        this.wmParticipantList = wmParticipantList;
    }

    public String getWorkItemName() {
        return workItemName;
    }

    public void setWorkItemName(String workItemName) {
        this.workItemName = workItemName;
    }
}
