package org.wfmc.service;

import org.fest.assertions.Assertions;
import org.junit.Test;
import org.wfmc.audit.WMACreateProcessInstanceData;
import org.wfmc.audit.WMAEventCode;
import org.wfmc.service.utils.DatabaseAuditHelper;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;


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
        Assertions.assertThat(wfmcService).isInstanceOf(WfmcServiceAuditImpl.class);
        Assertions.assertThat(((WfmcServiceAuditImpl)wfmcService).getInternalService()).isInstanceOf(
            WfmcServiceAuditImpl.class);

    }


    @Test
    public void testWfmcServiceAuditWithAuditInternalImplCacheIsNotNull()
        throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
        ResourceBundle configBundle = ResourceBundle.getBundle("wapi-elo-renns");
        WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(convertResourceBundleToProperties(configBundle));
        WfmcService wfmcService = wfmcServiceFactory.getInstance();
        Assertions.assertThat(wfmcService).isInstanceOf(WfmcServiceAuditImpl.class);
        Assertions.assertThat(((WfmcServiceAuditImpl)((WfmcServiceAuditImpl)wfmcService).getInternalService()).getWfmcServiceCache()).isNotNull();
    }

    @Test
    public void testDataSourceConnection()
        throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, SQLException {
        ResourceBundle configBundle = ResourceBundle.getBundle("wapi-elo-renns");
        WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(convertResourceBundleToProperties(configBundle));
        WfmcServiceAuditImpl wfmcService = (WfmcServiceAuditImpl)wfmcServiceFactory.getInstance();
        Assertions.assertThat(wfmcService.getDataSource().getConnection()).isNotNull();
    }

    @Test
    public void testInsertCreateProcessInstanceAudit()
            throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, SQLException {


        DatabaseAuditHelper databaseAuditHelper = new DatabaseAuditHelper();



        WMACreateProcessInstanceData w = new WMACreateProcessInstanceData();
        w.setProcessDefinitionId("procDefId4");
        w.setInitialProcessInstanceId("iniprocId");
        w.setActivityDefinitionId("actDefId");
        w.setCurrentProcessInstanceId("procInsId");
        w.setActivityInstanceId("actInsId");
        w.setWorkItemId("wiId");
        w.setProcessState(null);
        w.setEventCode(WMAEventCode.WAITING_ON_EVENT);
        w.setNodeId("nodeId");
        w.setUserId("userId");
        w.setRoleId("roleId");
        w.setDomainId("domid");

        w.setTimestamp(null);
        w.setInformationId("infId");


        //apel insert

        ResourceBundle configBundle = ResourceBundle.getBundle("wapi-elo-renns");
        WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(convertResourceBundleToProperties(configBundle));
        WfmcServiceAuditImpl wfmcService = (WfmcServiceAuditImpl)wfmcServiceFactory.getInstance();
        DataSource dS = wfmcService.getDataSource();
        Assertions.assertThat(wfmcService.getDataSource().getConnection()).isNotNull();
        databaseAuditHelper.insertCreateProcessInstanceAudit(dS,w);

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
