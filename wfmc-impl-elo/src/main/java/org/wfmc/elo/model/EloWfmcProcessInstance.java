package org.wfmc.elo.model;

import org.wfmc.elo.base.WMProcessInstanceDefault;
import org.wfmc.wapi2.WMEntity;

/**
 * @author adrian.zamfirescu
 * @since 23/02/2015
 */
public class EloWfmcProcessInstance extends WMProcessInstanceDefault{

    private WMEntity eloWfMCProcessInstanceAttributes;

    public WMEntity getEloWfMCProcessInstanceAttributes() {
        return eloWfMCProcessInstanceAttributes;
    }

    public void setEloWfMCProcessInstanceAttributes(WMEntity eloWfMCProcessInstanceAttributes) {
        this.eloWfMCProcessInstanceAttributes = eloWfMCProcessInstanceAttributes;
    }

}
