package org.wfmc.service;

import org.wfmc.wapi2.WAPI2;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Lucian.Dragomir on 2/10/2015.
 */
public interface WfmcService extends WAPI2 {
    /**
     * Metoda __init este utilizata intern de clasa de tip factory folosita pentru instantierea serviciului de persistenta
     *
     * @param context - reprezinta tipul repository-ului si parametri specifici de initializare pentru persistenta
     */
    public void __init(Properties context) throws IOException;


    public Properties getContext();

}
