package org.elo.connection;

import de.elo.ix.client.IXConnection;

/**
 * Created by Lucian.Dragomir on 12/28/2014.
 */
public class EloConnectionPools {
    private EloConnectionManager eloConnectionManager;
    private EloConnectionPoolFactory eloConnectionPoolFactory;
    private EloConnectionPool[] eloConnectionPools;

    public EloConnectionPools(EloConnectionManager eloConnectionManager) {
        this.eloConnectionManager = eloConnectionManager;
        this.eloConnectionPoolFactory = new EloConnectionPoolFactory(this.eloConnectionManager);
        this.eloConnectionPools = null;
    }

    private EloConnectionPool[] getEloConnectionPools() {
        //create connection pools on the first request
        if (eloConnectionPools == null) {
            synchronized (EloConnectionPool.class) {
                if (eloConnectionPools == null) {
                    this.eloConnectionPools = this.eloConnectionPoolFactory.createConnectionPools();
                }
            }
        }
        return this.eloConnectionPools;
    }

    public int getPoolIndex(EloConnectionDetails eloConnectionDetails) {
        for (int index = 0; index < getEloConnectionPools().length; index++) {
            EloConnectionPool eloConnectionPool = getEloConnectionPools()[index];
            if (eloConnectionPool.allow(eloConnectionDetails)) {
                return index;
            }
        }
        throw new RuntimeException("No connection pool is compatible with provided ELO connection details.");
    }

    public IXConnection borrowObject(EloConnectionDetails eloConnectionDetails) throws Exception {
        int index = getPoolIndex(eloConnectionDetails);
        EloConnectionPool eloConnectionPool = getEloConnectionPools()[index];
        IXConnection ixConnection = eloConnectionPool.borrowObject(eloConnectionDetails);
        return ixConnection;
    }

    public void returnObject(EloConnectionDetails eloConnectionDetails, IXConnection ixConnection) throws Exception {
        int index = getPoolIndex(eloConnectionDetails);
        EloConnectionPool eloConnectionPool = getEloConnectionPools()[index];
        eloConnectionPool.returnObject(eloConnectionDetails, ixConnection);
    }

    public void invalidateObject(EloConnectionDetails eloConnectionDetails, IXConnection ixConnection) throws Exception {
        int index = getPoolIndex(eloConnectionDetails);
        EloConnectionPool eloConnectionPool = getEloConnectionPools()[index];
        eloConnectionPool.invalidateObject(eloConnectionDetails, ixConnection);
        //eloConnectionPools[getPoolIndex(eloConnectionDetails)].invalidateObject(eloConnectionDetails, ixConnection);
    }

}
