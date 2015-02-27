package org.wfmc.elo.base;

import org.wfmc.wapi.WMParticipant;

/**
 * Created by Ioan.Ivan on 2/26/2015.
 */
public class WMParticipantImpl implements WMParticipant {

    String name;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
