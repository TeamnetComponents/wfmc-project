package org.wfmc.service.utils;

import org.wfmc.audit.WMACreateProcessInstanceData;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by andras on 3/17/2015.
 */
public class DatabaseAuditHelper {

    public void insertCreateProcessInstanceAudit(DataSource dataSource, WMACreateProcessInstanceData wmaCreateProcessInstanceData){
        PreparedStatement preparedStatement = null;
        try {
            Connection connection = dataSource.getConnection();


            preparedStatement = connection.prepareStatement("INSERT INTO WMAAuditEntry  (ID,processDefinitionId, " +
                    " activityDefinitionId,  initialProcessInstanceId,  currentProcessInstanceId,  activityInstanceId,  \n" +
                    "                          workItemId,  processState,  eventCode,  domainId,  nodeId,  userId, roleId,  time,  informationId)  VALUES\n" +
                    "  (WMAAuditEntry_Sequence.nextval,?,?," +
                    "?,?,?,?,?,?,?,?,?,?,SYSDATE,?)");
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
}
