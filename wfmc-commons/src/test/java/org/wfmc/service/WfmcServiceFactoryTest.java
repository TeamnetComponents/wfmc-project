package org.wfmc.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import org.fest.assertions.Assertions;

import org.junit.Test;



/**
 * Created by andras on 3/16/2015.
 */
public class WfmcServiceFactoryTest {


    @Test
    public void testWfmcServiceAuditWithAuditInternalImpl()
        throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
        ResourceBundle configBundle = ResourceBundle.getBundle("wapi-elo-renns");
        WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(convertResourceBundleToProperties(configBundle));
        WfmcService wfmcService = wfmcServiceFactory.getInstance();
        Assertions.assertThat(wfmcService).isInstanceOf(WfmcServiceAudit_Impl.class);
        Assertions.assertThat(((WfmcServiceAudit_Impl)wfmcService).getInternalService()).isInstanceOf(
            WfmcServiceAudit_Impl.class);

    }


    @Test
    public void testWfmcServiceAuditWithAuditInternalImplCacheIsNotNull()
        throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
        ResourceBundle configBundle = ResourceBundle.getBundle("wapi-elo-renns");
        WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(convertResourceBundleToProperties(configBundle));
        WfmcService wfmcService = wfmcServiceFactory.getInstance();
        Assertions.assertThat(wfmcService).isInstanceOf(WfmcServiceAudit_Impl.class);
        Assertions.assertThat(((WfmcServiceAudit_Impl)((WfmcServiceAudit_Impl)wfmcService).getInternalService()).getWfmcServiceCache()).isNotNull();
    }

    @Test
    public void testDataSourceConnection()
        throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, SQLException {
        ResourceBundle configBundle = ResourceBundle.getBundle("wapi-elo-renns");
        WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(convertResourceBundleToProperties(configBundle));
        WfmcServiceAudit_Impl wfmcService = (WfmcServiceAudit_Impl)wfmcServiceFactory.getInstance();
        Assertions.assertThat(wfmcService.getDataSource().getConnection()).isNotNull();
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
