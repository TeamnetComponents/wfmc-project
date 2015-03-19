package org.wfmc.service;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Lucian.Dragomir on 2/28/2015.
 */
public interface Service {
    /**
     * Metoda __init este utilizata intern de clasa de tip factory folosita pentru instantierea serviciului de persistenta
     *
     * @param context - reprezinta tipul repository-ului si parametri specifici de initializare pentru persistenta
     */
    public void __initialize(Properties context) throws IOException;

    public void __terminate();

    public Properties getContext();

    public String getName();

    public String getSessionId();

    public String getSessionUsername();
}
