
package org.wfmc.impl.base;

import org.wfmc.wapi.WMParticipant;

import java.io.Serializable;

public class WMParticipantImpl implements WMParticipant, Serializable {
    private static final long serialVersionUID = -6771345519371167086L;
    private String _name;

    public WMParticipantImpl(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }

    public String toString() {
        return _name;
    }
}