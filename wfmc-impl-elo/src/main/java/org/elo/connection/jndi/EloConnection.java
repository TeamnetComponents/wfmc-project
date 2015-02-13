package org.elo.connection.jndi;

import de.elo.ix.client.IXConnection;
import org.elo.connection.EloConnectionDetails;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Lucian.Dragomir on 2/11/2015.
 */


public class EloConnection implements Closeable {
    private EloConnectionDetails eloConnectionDetails;
    private IXConnection ixConnection;

    @Override
    public final void close() throws IOException {
        //this.eloConnectionDetails.
    }

    public EloConnection(EloConnectionDetails eloConnectionDetails, IXConnection ixConnection) {
        this.eloConnectionDetails = eloConnectionDetails;
        this.ixConnection = ixConnection;
    }

    protected EloConnectionDetails getEloConnectionDetails() {
        return eloConnectionDetails;
    }



    protected void setEloConnectionDetails(EloConnectionDetails eloConnectionDetails) {
        this.eloConnectionDetails = eloConnectionDetails;
    }



    public IXConnection getIxConnection() {
        return ixConnection;
    }

    protected void setIxConnection(IXConnection ixConnection) {
        this.ixConnection = ixConnection;
    }
}
