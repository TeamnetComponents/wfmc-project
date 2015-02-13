package org.elo.connection;

import de.elo.ix.client.IXConnection;
import org.elo.connection.jndi.EloConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Created by Lucian.Dragomir on 6/5/2014.
 */
public class EloConnectionManager {

    //ELO CONNECTION CONTEXT
    public static final String ELO_CONNECTION_CONTEXT = "elo.connection";

    private static final Logger LOG = LoggerFactory.getLogger(EloConnectionManager.class);

    private Properties serviceProperties;

    private EloConnectionPools eloConnectionPools;
    private EloConnectionDetails defaultEloConnectionDetails;

    public EloConnectionManager(Properties serviceProperties) {
        this.serviceProperties = serviceProperties;
        this.eloConnectionPools = new EloConnectionPools(this);
        //this.ixConnFactory = null;
        this.defaultEloConnectionDetails = new EloConnectionDetails(this);
    }

    public Properties getServiceProperties() {
        return this.serviceProperties;
    }

    public EloConnectionDetails createEloConnectionDetails(Properties callContext) {
        return new EloConnectionDetails(this, callContext);
    }

    public EloConnection borrowConnection(EloConnectionDetails eloConnectionDetails) throws Exception {
        EloConnection eloConnection = null;
        IXConnection ixConnection = null;
        ixConnection = this.eloConnectionPools.borrowObject(eloConnectionDetails);
        eloConnection = new EloConnection(eloConnectionDetails, ixConnection);
        return eloConnection;
    }

    public boolean returnConnection(EloConnection eloConnection) {
        return true;
    }


//    public IXConnection getConnection(Properties callContext) {
//        IXConnection ixConnection = null;
//        EloConnectionDetails eloConnectionDetails = new EloConnectionDetails(this, callContext);
//        if (callContext != null) {
//            ixConnection = (IXConnection) callContext.get(ELO_CONNECTION_CONTEXT);
//            if (ixConnection != null) {
//                if (true || eloConnectionDetails.isValidConnection(ixConnection)) {
//                    return ixConnection;
//                } else {
//                    MutableCallContext mutableCallContext = (MutableCallContext) callContext;
//                    mutableCallContext.remove(ELO_CONNECTION_CONTEXT);
//                    try {
//                        this.eloConnectionPools.invalidateObject(eloConnectionDetails, ixConnection);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    ixConnection = null;
//                }
//            }
//        }
//        if (ixConnection == null) {
//            try {
//                ixConnection = this.eloConnectionPools.borrowObject(eloConnectionDetails);
//                if (callContext != null) {
//                    MutableCallContext mutableCallContext = (MutableCallContext) callContext;
//                    mutableCallContext.put(ELO_CONNECTION_CONTEXT, ixConnection);
//                }
//            } catch (Exception e) {
//                throw new CmisPermissionDeniedException("Unable to create connection to ELO Server.", e);
//            }
//        }
//        return ixConnection;
//    }
//
//    public boolean returnConnection(CallContext callContext) {
//        boolean returned = false;
//        IXConnection ixConnection = null;
//        EloConnectionDetails eloConnectionDetails = new EloConnectionDetails(this, callContext);
//        ixConnection = (IXConnection) callContext.get(ELO_CONNECTION_CONTEXT);
//        if (ixConnection != null) {
//            //return connection back to the pool
//            try {
//                this.eloConnectionPools.returnObject(eloConnectionDetails, ixConnection);
//                returned = true;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            //update call context accordingly
//            MutableCallContext mutableCallContext = (MutableCallContext) callContext;
//            mutableCallContext.remove(ELO_CONNECTION_CONTEXT);
//        }
//        return returned;
//    }

    public IXConnection getAdminConnection() {
        //return getConnection(null);
        return null;
    }

    public boolean returnAdminConnection(IXConnection ixConnection) {
        boolean returned = false;
        EloConnectionDetails eloConnectionDetails = new EloConnectionDetails(this, null);
        //return connection back to the pool
        try {
            this.eloConnectionPools.returnObject(eloConnectionDetails, ixConnection);
            returned = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returned;
    }
}
