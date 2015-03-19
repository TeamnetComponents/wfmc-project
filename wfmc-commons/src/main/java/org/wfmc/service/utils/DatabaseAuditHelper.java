package org.wfmc.service.utils;

import org.wfmc.audit.WMAAssignProcessInstanceAttributeData;
import org.wfmc.audit.WMAAuditEntry;
import org.wfmc.audit.WMACreateProcessInstanceData;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//import javax.activation.DataSource;

/**
 * Created by andras on 3/17/2015.
 */
public class DatabaseAuditHelper {

    public void insertCreateProcessInstanceAudit(DataSource dataSource, WMACreateProcessInstanceData wmaCreateProcessInstanceData){
        PreparedStatement preparedStatement = null;
        try {
            Connection connection = dataSource.getConnection();


            preparedStatement = connection.prepareStatement("INSERT INTO WM_AUDIT_ENTRY  (ID,PROCESS_DEFINITION_ID, " +
                    " ACTIVITY_DEFINITION_ID,  INITIAL_PROCESS_INSTANCE_ID,  CURRENT_PROCESS_INSTANCE_ID,  ACTIVITY_INSTANCE_ID,  \n" +
                    "                          WORK_ITEM_ID,  PROCESS_STATE,  EVENT_CODE,  DOMAIN_ID,  NODE_ID,  USER_ID, ROLE_ID,  time,  INFORMATION_ID,PROCESS_DEF_BUSINESS_NAME)  VALUES\n" +
                    "  (WMAAuditEntry_Sequence.nextval,?,?," +
                    "?,?,?,?,?,?,?,?,?,?,SYSDATE,?,?)");
            preparedStatement.setString(1,wmaCreateProcessInstanceData.getProcessDefinitionId());
            preparedStatement.setString(2,wmaCreateProcessInstanceData.getActivityDefinitionId());
            preparedStatement.setString(3,wmaCreateProcessInstanceData.getInitialProcessInstanceId());
            preparedStatement.setString(4,wmaCreateProcessInstanceData.getCurrentProcessInstanceId());
            preparedStatement.setString(5,wmaCreateProcessInstanceData.getActivityInstanceId());
            preparedStatement.setString(6,wmaCreateProcessInstanceData.getWorkItemId());
            preparedStatement.setString(7,wmaCreateProcessInstanceData.getProcessState());
            preparedStatement.setInt(8,wmaCreateProcessInstanceData.getEventCode().value());
            preparedStatement.setString(9,wmaCreateProcessInstanceData.getDomainId());
            preparedStatement.setString(10,wmaCreateProcessInstanceData.getNodeId());
            preparedStatement.setString(11,wmaCreateProcessInstanceData.getUserId());
            preparedStatement.setString(12,wmaCreateProcessInstanceData.getRoleId());
            preparedStatement.setString(13,wmaCreateProcessInstanceData.getInformationId());
            preparedStatement.setString(14,wmaCreateProcessInstanceData.getProcessDefinitionBusinessName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(preparedStatement!=null)
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }


    public void insertAssignProcessInstanceAttributeAudit(DataSource dataSource, WMAAssignProcessInstanceAttributeData wmaAssignProcessInstanceAttributeData)
    {
        PreparedStatement preparedStatement = null;
        try {
            Connection connection = dataSource.getConnection();


            preparedStatement = connection.prepareStatement("INSERT INTO WM_AUDIT_ENTRY  (ID,PROCESS_DEFINITION_ID, " +
                    " ACTIVITY_DEFINITION_ID,  INITIAL_PROCESS_INSTANCE_ID,  CURRENT_PROCESS_INSTANCE_ID,  ACTIVITY_INSTANCE_ID,  \n" +
                    "                          WORK_ITEM_ID,  PROCESS_STATE,  EVENT_CODE,  DOMAIN_ID,  NODE_ID,  USER_ID, ROLE_ID,  time,  INFORMATION_ID,ATTRIBUTE_NAME,ATTRIBUTE_TYPE,PREVIOUS_ATTRIBUTE_VALUE,PREVIOUS_ATTRIBUTE_LENGTH,NEW_ATTRIBUTE_VALUE,NEW_ATTRIBUTE_LENGTH)  VALUES\n" +
                    "  (WMAAuditEntry_Sequence.nextval,?,?," +
                    "?,?,?,?,?,?,?,?,?,?,SYSDATE,?,?,?,?,?,?,?)");

            basicDataPreparedStatement(preparedStatement,wmaAssignProcessInstanceAttributeData);

            preparedStatement.setString(14, wmaAssignProcessInstanceAttributeData.getAttributeName());
            preparedStatement.setInt(15, wmaAssignProcessInstanceAttributeData.getAttributeType());
            preparedStatement.setString(16, wmaAssignProcessInstanceAttributeData.getPreviousAttributeValue());
            preparedStatement.setInt(17, wmaAssignProcessInstanceAttributeData.getPreviousAttributeLength());
            preparedStatement.setString(18,wmaAssignProcessInstanceAttributeData.getNewAttributeValue());
            preparedStatement.setInt(19,wmaAssignProcessInstanceAttributeData.getNewAttributeLength());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if(preparedStatement!=null)
                try {
                    preparedStatement.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            e.printStackTrace();
        }
    }



    static public void basicDataPreparedStatement(PreparedStatement preparedStatement,WMAAuditEntry w ) throws SQLException {

        preparedStatement.setString(1,w.getProcessDefinitionId());
        preparedStatement.setString(2,w.getActivityDefinitionId());
        preparedStatement.setString(3,w.getInitialProcessInstanceId());
        preparedStatement.setString(4,w.getCurrentProcessInstanceId());
        preparedStatement.setString(5,w.getActivityInstanceId());
        preparedStatement.setString(6,w.getWorkItemId());
        preparedStatement.setString(7,w.getProcessState());
        preparedStatement.setInt(8,  w.getEventCode().value());
        preparedStatement.setString(9, w.getDomainId());
        preparedStatement.setString(10,w.getNodeId());
        preparedStatement.setString(11,w.getUserId());
        preparedStatement.setString(12,w.getRoleId());
        preparedStatement.setString(13,w.getInformationId());

    }




}






