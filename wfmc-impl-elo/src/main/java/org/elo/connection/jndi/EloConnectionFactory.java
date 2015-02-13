package org.elo.connection.jndi;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;
import java.util.Hashtable;

/**
 * Created by Lucian.Dragomir on 2/11/2015.
 */
public class EloConnectionFactory implements ObjectFactory {

    @Override
    public EloConnection getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
        //TODO - implement the ELO connection as a JNDI resource

        Reference ref = (Reference) obj;

        return null;
    }

}
