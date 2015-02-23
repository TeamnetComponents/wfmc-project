package org.wfmc.elo;

import org.wfmc.wapi.WMParticipant;
import org.wfmc.wapi.WMProcessInstance;
import org.wfmc.wapi.WMProcessInstanceState;
import org.wfmc.wapi2.WMEntity;

/**
 * @author adrian.zamfirescu
 * @since 23/02/2015
 */
public class EloWfmcProcessInstance implements WMProcessInstance{

    private String processDefinitionId;
    private String name;
    private WMEntity eloWfmcSord;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getId() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
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
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getPriority() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public WMParticipant[] getParticipants() {
        return new WMParticipant[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    public WMEntity getEloWfmcSord() {
        return eloWfmcSord;
    }

    public void setEloWfmcSord(WMEntity eloWfmcSord) {
        this.eloWfmcSord = eloWfmcSord;
    }

}
