package org.wfmc.elo.model;

import org.wfmc.elo.base.WMProcessInstanceDefault;
import org.wfmc.wapi2.WMEntity;

/**
 * @author adrian.zamfirescu
 * @since 23/02/2015
 */
public class EloWfmcProcessInstance extends WMProcessInstanceDefault{

    private WMEntity eloWfmcSord;

    public WMEntity getEloWfmcSord() {
        return eloWfmcSord;
    }

    public void setEloWfmcSord(WMEntity eloWfmcSord) {
        this.eloWfmcSord = eloWfmcSord;
    }

}
