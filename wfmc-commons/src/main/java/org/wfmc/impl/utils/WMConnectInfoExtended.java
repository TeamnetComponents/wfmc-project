package org.wfmc.impl.utils;

import de.elo.extension.connection.IXPoolableConnectionManager;
import org.wfmc.wapi.WMConnectInfo;

import java.io.Serializable;

/**
 * Created by Ioan.Ivan on 7/21/2015.
 */
public class WMConnectInfoExtended extends WMConnectInfo implements Serializable {

    private IXPoolableConnectionManager ixPoolableConnectionManager;

    public WMConnectInfoExtended(String userIdentification, String password, String engineName, String scope) {
        super(userIdentification, password, engineName, scope);
    }

    public IXPoolableConnectionManager getIxPoolableConnectionManager() {
        return ixPoolableConnectionManager;
    }

    public void setIxPoolableConnectionManager(IXPoolableConnectionManager ixPoolableConnectionManager) {
        this.ixPoolableConnectionManager = ixPoolableConnectionManager;
    }
}
