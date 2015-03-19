package org.wfmc;

import org.wfmc.service.WfmcService;
import org.wfmc.service.WfmcServiceFactory;
import org.wfmc.wapi.WMConnectInfo;
import org.wfmc.wapi.WMWorkflowException;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by Ioan.Ivan on 3/18/2015.
 */
public class TestIntegrareAuditCreateProcessInstance {

    public static void main(String[] args)
        throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, WMWorkflowException
    {
        String processInstanceName = "Instanta flux aprobare operatiuni 3";
        ResourceBundle configBundle = ResourceBundle.getBundle("wapi-elo-renns_audit");
        WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(convertResourceBundleToProperties(configBundle));
        WfmcService wfmcService = wfmcServiceFactory.getInstance();

        ResourceBundle configBundleForEloService = ResourceBundle.getBundle("wapi-elo-renns");
        WfmcServiceFactory eloServiceFactory = new WfmcServiceFactory(convertResourceBundleToProperties(configBundleForEloService));
        WfmcService eloService = eloServiceFactory.getInstance();

        eloService.connect(new WMConnectInfo("Andra@Administrator", "elo@RENNS2015", "Wfmc Test", "http://10.6.38.90:8080/ix-elo/ix"));

        String procInstIdTemp = wfmcService.createProcessInstance("3", processInstanceName);

    }

    static Properties convertResourceBundleToProperties(ResourceBundle resource) {
        Properties properties = new Properties();

        Enumeration<String> keys = resource.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            properties.put(key, resource.getString(key));
        }

        return properties;
    }
}
