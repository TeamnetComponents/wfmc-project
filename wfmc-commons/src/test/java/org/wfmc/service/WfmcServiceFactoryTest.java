package org.wfmc.service;

import org.fest.assertions.Assertions;
import org.junit.Ignore;
import org.junit.Test;
import org.wfmc.audit.*;
import org.wfmc.service.utils.DatabaseAuditHelper;
import org.wfmc.wapi.WMProcessInstanceState;
import org.wfmc.wapi.WMWorkItemState;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;


/**
 * Created by andras on 3/16/2015.
 */
public class WfmcServiceFactoryTest {


    @Ignore
    public void testWfmcServiceAuditWithAuditInternalImpl()
            throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
        ResourceBundle configBundle = ResourceBundle.getBundle("wapi-elo-renns");
        WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(convertResourceBundleToProperties(configBundle));
        WfmcService wfmcService = wfmcServiceFactory.getInstance();
        Assertions.assertThat(wfmcService).isInstanceOf(WfmcServiceAuditImpl.class);
        Assertions.assertThat(((WfmcServiceAuditImpl)wfmcService).getInternalService()).isInstanceOf(
                WfmcServiceAuditImpl.class);

    }


    @Ignore
    public void testWfmcServiceAuditWithAuditInternalImplCacheIsNotNull()
            throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
        ResourceBundle configBundle = ResourceBundle.getBundle("wapi-elo-renns");
        WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(convertResourceBundleToProperties(configBundle));
        WfmcService wfmcService = wfmcServiceFactory.getInstance();

        Assertions.assertThat(wfmcService).isInstanceOf(WfmcServiceAuditImpl.class);
        Assertions.assertThat(((WfmcServiceAuditImpl)((WfmcServiceAuditImpl)wfmcService).getInternalService()).getWfmcServiceCache()).isNotNull();
    }

    @Ignore
    public void testDataSourceConnection()
            throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, SQLException {
        ResourceBundle configBundle = ResourceBundle.getBundle("wapi-elo-renns");
        WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(convertResourceBundleToProperties(configBundle));
        WfmcServiceAuditImpl wfmcService = (WfmcServiceAuditImpl)wfmcServiceFactory.getInstance();
        Assertions.assertThat(wfmcService.getDataSource().getConnection()).isNotNull();
    }


    @Ignore
    public void testInsertReassignWorkItemAudit() throws SQLException, ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {

            ResourceBundle configBundle = ResourceBundle.getBundle("wapi-elo-renns");
            WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(convertResourceBundleToProperties(configBundle));
            WfmcServiceAuditImpl wfmcService = (WfmcServiceAuditImpl) wfmcServiceFactory.getInstance();
            DataSource dS = wfmcService.getDataSource();
            con = dS.getConnection();
            DatabaseAuditHelper dah = new DatabaseAuditHelper();

            WMAAssignWorkItemData w = new WMAAssignWorkItemData();

          //setam valori
            String
                    nodeId = "TnodeId",
                    roleId = "TroleId",
                    domainId = "TdomainId",
                    userId = "TuserId";
            w.setProcessDefinitionId("Reasign");
            w.setInitialProcessInstanceId("TestInsId");
            w.setActivityDefinitionId("Testaid");
            w.setCurrentProcessInstanceId("TestPIid");
            w.setActivityInstanceId("actInsId");
            w.setWorkItemId("TestwiId");
            w.setEventCode(WMAEventCode.WAITING_ON_EVENT);
            w.setNodeId("nodeId");
            w.setUserId("userId");
            w.setRoleId("roleId'");
            w.setDomainId("domainId");
            w.setProcessState(WMProcessInstanceState.CLOSED_COMPLETED_TAG);
            w.setInformationId("TestinfId");
            w.setWorkItemState("closed.completed");
            w.setPreviousWorkItemState("open.running");
            w.setTargetDomainId(domainId);
            w.setTargetNodeId(nodeId);
            w.setTargetRoleId(roleId);
            w.setTargetUserId(userId);
            w.setWorkItemState(WMWorkItemState.CLOSED_ABORTED_TAG);
            w.setPreviousWorkItemState(WMWorkItemState.OPEN_NOTRUNNING_TAG);
            Integer i = dah.insertReassignWorkItemAudit(dS,w);

            //System.out.println(i);
            //verificam
            preparedStatement = con.prepareStatement("SELECT TARGET_DOMAIN_ID,TARGET_NODE_ID,TARGET_USER_ID,TARGET_ROLE_ID,WORK_ITEM_STATE,PREVIOUS_WORK_ITEM_STATE FROM WM_AUDIT_ENTRY WHERE ID =  ?");
            preparedStatement.setInt(1,i);

            ResultSet resultSet = preparedStatement.executeQuery();

            assertEquals(true,resultSet.next());

            assertEquals(domainId, resultSet.getString(1));
            assertEquals(nodeId,resultSet.getString(2));
            assertEquals(userId,resultSet.getString(3));
            assertEquals(roleId,resultSet.getString(4));
            assertEquals(WMWorkItemState.CLOSED_ABORTED_INT,resultSet.getInt(5));
            assertEquals(WMWorkItemState.OPEN_NOTRUNNING_INT,resultSet.getInt(6));


            //stergem

            preparedStatement = con.prepareStatement("DELETE FROM WM_AUDIT_ENTRY WHERe ID = ?");
            preparedStatement.setInt(1,i);
            preparedStatement.execute();


        } finally {
            if(preparedStatement!=null)
                preparedStatement.close();
            if(con!=null)
                con.close();
        }

        }


    @Ignore
    public void testInsertCompleteWorkItemAudit() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, SQLException {
       Connection con = null;
        PreparedStatement preparedStatement = null;
        try {

            ResourceBundle configBundle = ResourceBundle.getBundle("wapi-elo-renns");
            WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(convertResourceBundleToProperties(configBundle));
            WfmcServiceAuditImpl wfmcService = (WfmcServiceAuditImpl) wfmcServiceFactory.getInstance();
            DataSource dS = wfmcService.getDataSource();
            con = dS.getConnection();
            DatabaseAuditHelper dah = new DatabaseAuditHelper();
            WMAChangeWorkItemStateData wma = new WMAChangeWorkItemStateData();

            wma.setProcessDefinitionId("Complete2");
            wma.setInitialProcessInstanceId("TestInsId");
            wma.setActivityDefinitionId("Testaid");
            wma.setCurrentProcessInstanceId("TestPIid");
            wma.setActivityInstanceId("actInsId");
            wma.setWorkItemId("TestwiId");
            wma.setEventCode(WMAEventCode.WAITING_ON_EVENT);
            wma.setNodeId("TestnodeId");
            wma.setUserId("TestuserId");
            wma.setRoleId("TestroleId");
            wma.setDomainId("Testdomid");
            wma.setProcessState(WMProcessInstanceState.CLOSED_COMPLETED_TAG);
            wma.setInformationId("TestinfId");
            wma.setWorkItemState("closed.completed");
            wma.setPreviousWorkItemState("open.running");

            int i = dah.insertCompleteWorkItemAudit(dS, wma);


            //verificam
            preparedStatement = con.prepareStatement("SELECT  WORK_ITEM_STATE, PREVIOUS_WORK_ITEM_STATE FROM WM_AUDIT_ENTRY WHERE ID = ?");

            preparedStatement.setInt(1,i);
            ResultSet resultSet = preparedStatement.executeQuery();

            //verificam ca a returnat ceva
            assertEquals(true, resultSet.next());

            //verficam ca are bine starile (daca de exemplu nu e introdus, testul oricum crapa)

            assertEquals(5,resultSet.getInt(1));
            assertEquals(2,resultSet.getInt(2));



            //stergem
            preparedStatement = con.prepareStatement("DELETE FROM WM_AUDIT_ENTRY WHERe ID = ?");
            preparedStatement.setInt(1,i);
            preparedStatement.execute();


        } finally {
            if(preparedStatement!=null)
                preparedStatement.close();
            if(con!=null)
                con.close();
        }
    }



    @Ignore
    public void testInsertAssignProcessInstanceAttributeAudit() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, SQLException {

        PreparedStatement preparedStatement = null;
        Connection con = null;
        try{
        DatabaseAuditHelper databaseAuditHelper = new DatabaseAuditHelper();
        ResourceBundle configBundle = ResourceBundle.getBundle("wapi-elo-renns");
        WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(convertResourceBundleToProperties(configBundle));
        WfmcServiceAuditImpl wfmcService = (WfmcServiceAuditImpl)wfmcServiceFactory.getInstance();
        DataSource dS = wfmcService.getDataSource();


        WMAAssignProcessInstanceAttributeData w = new WMAAssignProcessInstanceAttributeData();
        w.setProcessDefinitionId("ASSIGN");
        w.setInitialProcessInstanceId("TestInsId");
        w.setActivityDefinitionId("Testaid");
        w.setCurrentProcessInstanceId("TestPIid");
        w.setActivityInstanceId("actInsId");
        w.setWorkItemId("TestwiId");
        w.setEventCode(WMAEventCode.WAITING_ON_EVENT);
        w.setNodeId("TestnodeId");
        w.setUserId("TestuserId");
        w.setRoleId("TestroleId");
        w.setDomainId("Testdomid");
        w.setProcessState(WMProcessInstanceState.CLOSED_COMPLETED_TAG);

        w.setInformationId("TestinfId");

        w.setAttributeName("TestNumeAtr");
        w.setAttributeType(20);
        w.setPreviousAttributeLength(0);
        w.setPreviousAttributeValue("PrevValue");
        w.setNewAttributeLength(0);
        w.setNewAttributeValue("NewVal");


         con = dS.getConnection();
         preparedStatement = con.prepareStatement("SELECT COUNT(*) FROM WMS_AUDIT.WM_AUDIT_ENTRY " +
                        "WHERE " +
                        "PROCESS_DEFINITION_ID = ? AND " +
                        "ACTIVITY_DEFINITION_ID = ?  AND " +
                        "INITIAL_PROCESS_INSTANCE_ID = ? AND " +
                        "CURRENT_PROCESS_INSTANCE_ID = ? AND " +
                        "ACTIVITY_INSTANCE_ID = ? AND " +
                        "WORK_ITEM_ID = ? AND " +
                        "PROCESS_STATE = ?  AND " +
                        "EVENT_CODE = ?     AND " +
                        "DOMAIN_ID = ?      AND " +
                        "NODE_ID = ?        AND " +
                        "USER_ID= ?         AND " +
                        "ROLE_ID = ?        AND " +
                        "INFORMATION_ID = ? AND " +
                        "ATTRIBUTE_NAME = ? AND " +
                        "ATTRIBUTE_TYPE = ? AND " +
                        "PREVIOUS_ATTRIBUTE_VALUE = ? AND " +
                        "PREVIOUS_ATTRIBUTE_LENGTH = ? AND " +
                        "NEW_ATTRIBUTE_VALUE = ? AND " +
                        "NEW_ATTRIBUTE_LENGTH = ?"
        );



        DatabaseAuditHelper.basicDataPreparedStatement(preparedStatement,w);

        preparedStatement.setString(14,w.getAttributeName());
        preparedStatement.setInt(15,w.getAttributeType());
        preparedStatement.setString(16,w.getPreviousAttributeValue());
        preparedStatement.setInt(17,w.getPreviousAttributeLength());
        preparedStatement.setString(18,w.getNewAttributeValue());
        preparedStatement.setInt(19,w.getNewAttributeLength());

        int nrRanduriInitiale = 0;


        ResultSet resultSetInital = preparedStatement.executeQuery();
        if(resultSetInital.next()) {
            nrRanduriInitiale = resultSetInital.getInt(1);
        }
        databaseAuditHelper.insertAssignProcessInstanceAttributeAudit(dS, w);
        ResultSet resultSetFinal = preparedStatement.executeQuery();

        int nrRanduriFinal = nrRanduriInitiale;

        if(resultSetFinal.next())
            nrRanduriFinal = resultSetFinal.getInt(1);



        assertEquals(nrRanduriInitiale,nrRanduriFinal-1);

        //Stergem din bd ce am inserat
        preparedStatement= con.prepareStatement("DELETE FROM WMS_AUDIT.WM_AUDIT_ENTRY " +
                "WHERE " +
                "PROCESS_DEFINITION_ID = ? AND " +
                "ACTIVITY_DEFINITION_ID = ?  AND " +
                "INITIAL_PROCESS_INSTANCE_ID = ? AND " +
                "CURRENT_PROCESS_INSTANCE_ID = ? AND " +
                "ACTIVITY_INSTANCE_ID = ? AND " +
                "WORK_ITEM_ID = ? AND " +
                "PROCESS_STATE = ?  AND " +
                "EVENT_CODE = ?     AND " +
                "DOMAIN_ID = ?      AND " +
                "NODE_ID = ?        AND " +
                "USER_ID= ?         AND " +
                "ROLE_ID = ?        AND " +
                "INFORMATION_ID = ? AND " +
                "ATTRIBUTE_NAME = ? AND " +
                "ATTRIBUTE_TYPE = ? AND " +
                "PREVIOUS_ATTRIBUTE_VALUE = ? AND " +
                "PREVIOUS_ATTRIBUTE_LENGTH = ? AND " +
                "NEW_ATTRIBUTE_VALUE = ? AND " +
                "NEW_ATTRIBUTE_LENGTH = ? " );

        DatabaseAuditHelper.basicDataPreparedStatement(preparedStatement,w);

        preparedStatement.setString(14,w.getAttributeName());
        preparedStatement.setInt(15,w.getAttributeType());
        preparedStatement.setString(16,w.getPreviousAttributeValue());
        preparedStatement.setInt(17,w.getPreviousAttributeLength());
        preparedStatement.setString(18,w.getNewAttributeValue());
        preparedStatement.setInt(19,w.getNewAttributeLength());

        preparedStatement.execute();}finally {
            if(preparedStatement!=null)
                preparedStatement.close();
            if(con!=null)
                con.close();
        }
    }



    @Ignore
    public void testUpdateIdCreateProcessInstanceAudit() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
        ResourceBundle configBundle = ResourceBundle.getBundle("wapi-elo-renns");
        WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(convertResourceBundleToProperties(configBundle));
        WfmcServiceAuditImpl wfmcService = (WfmcServiceAuditImpl)wfmcServiceFactory.getInstance();
        DataSource dS = wfmcService.getDataSource();


        //test simplu sa vedem ca functioneaza metoda
        new DatabaseAuditHelper().updateIdCreateProcessInstanceAudit(dS,"7cdac577-f592-4433-b93a-4e392d513e16","eloId");

    }

    @Ignore
    public void testInsertCreateProcessInstanceAudit()
            throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, SQLException {

        PreparedStatement preparedStatement = null;
        Connection con = null;
        try{
        DatabaseAuditHelper databaseAuditHelper = new DatabaseAuditHelper();


        //pregatesc instanta de test

        WMACreateProcessInstanceData w = new WMACreateProcessInstanceData();
        w.setProcessDefinitionId("pdTest");
        w.setInitialProcessInstanceId("TestInsId");
        w.setActivityDefinitionId("Testaid");
        w.setCurrentProcessInstanceId("TestPIid");
        w.setActivityInstanceId("actInsId");
        w.setWorkItemId("TestwiId");
        w.setEventCode(WMAEventCode.WAITING_ON_EVENT);
        w.setNodeId("TestnodeId");
        w.setUserId("TestuserId");
        w.setRoleId("TestroleId");
        w.setDomainId("Testdomid");
        w.setProcessState(WMProcessInstanceState.CLOSED_COMPLETED_TAG);
        w.setProcessDefinitionBusinessName("TEST");
        w.setInformationId("TestinfId");




        ResourceBundle configBundle = ResourceBundle.getBundle("wapi-elo-renns");
        WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(convertResourceBundleToProperties(configBundle));
        WfmcServiceAuditImpl wfmcService = (WfmcServiceAuditImpl)wfmcServiceFactory.getInstance();
        DataSource dS = wfmcService.getDataSource();
        Assertions.assertThat(wfmcService.getDataSource().getConnection()).isNotNull();





         con = wfmcService.getDataSource().getConnection();


        // Vad cate/daca sunt obiecte in bd cu prop inainte de inserare

         preparedStatement = con.prepareStatement("SELECT COUNT(*) FROM WMS_AUDIT.WM_AUDIT_ENTRY " +
                        "WHERE " +
                        "PROCESS_DEFINITION_ID = ? AND " +
                        "ACTIVITY_DEFINITION_ID = ?  AND " +
                        "INITIAL_PROCESS_INSTANCE_ID = ? AND " +
                        "CURRENT_PROCESS_INSTANCE_ID = ? AND " +
                        "ACTIVITY_INSTANCE_ID = ? AND " +
                        "WORK_ITEM_ID = ? AND " +
                        "PROCESS_STATE = ?  AND " +
                        "EVENT_CODE = ?     AND " +
                        "DOMAIN_ID = ?      AND " +
                        "NODE_ID = ?        AND " +
                        "USER_ID= ?         AND " +
                        "ROLE_ID = ?        AND " +
                        "INFORMATION_ID = ? AND " +
                        "PROCESS_DEF_BUSINESS_NAME = ? "
        );

        preparedStatement.setString(1,w.getProcessDefinitionId());
        preparedStatement.setString(2, w.getActivityDefinitionId());
        preparedStatement.setString(3,w.getInitialProcessInstanceId());
        preparedStatement.setString(4, w.getCurrentProcessInstanceId() );
        preparedStatement.setString(5, w.getActivityInstanceId() );
        preparedStatement.setString(6, w.getWorkItemId() );
        preparedStatement.setString(7, w.getProcessState() );
        preparedStatement.setInt(8,w.getEventCode().value());
        preparedStatement.setString(9, w.getDomainId() );
        preparedStatement.setString(10, w.getNodeId() );
        preparedStatement.setString(11, w.getUserId() );
        preparedStatement.setString(12, w.getRoleId() );
        preparedStatement.setString(13, w.getInformationId() );
        preparedStatement.setString(14, w.getProcessDefinitionBusinessName() );

        ResultSet resultSet = preparedStatement.executeQuery();
        int nrInitial = 0;
        if(resultSet.next())
            nrInitial = resultSet.getInt(1);


        resultSet.close();

        //inserez

        databaseAuditHelper.insertCreateProcessInstanceAudit(wfmcService.getDataSource(),w);

        //citesc din nou

        ResultSet resultSet1 = preparedStatement.executeQuery();
        int nrFin = nrInitial;
        if(resultSet1.next()){
            nrFin = resultSet1.getInt(1);
        }

        assertEquals(nrInitial,nrFin-1); //aici doar verificam ca a fost adaugat randul nostru


        // sterg


        preparedStatement=  con.prepareStatement("DELETE FROM WMS_AUDIT.WM_AUDIT_ENTRY " +
                "WHERE " +
                "PROCESS_DEFINITION_ID = ? AND " +
                "ACTIVITY_DEFINITION_ID = ?  AND " +
                "INITIAL_PROCESS_INSTANCE_ID = ? AND " +
                "CURRENT_PROCESS_INSTANCE_ID = ? AND " +
                "ACTIVITY_INSTANCE_ID = ? AND " +
                "WORK_ITEM_ID = ? AND " +
                "PROCESS_STATE = ?  AND " +
                "EVENT_CODE = ?     AND " +
                "DOMAIN_ID = ?      AND " +
                "NODE_ID = ?        AND " +
                "USER_ID= ?         AND " +
                "ROLE_ID = ?        AND " +
                "INFORMATION_ID = ? AND " +
                "PROCESS_DEF_BUSINESS_NAME  = ? ");

        DatabaseAuditHelper.basicDataPreparedStatement(preparedStatement,w);
        preparedStatement.setString(14, w.getProcessDefinitionBusinessName() );
        preparedStatement.executeUpdate();
        }finally{
            if(preparedStatement!=null)
                preparedStatement.close();
            if(con!=null)
                con.close();}
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
