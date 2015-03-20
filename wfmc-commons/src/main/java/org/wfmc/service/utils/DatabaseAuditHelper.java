package org.wfmc.service.utils;

import org.wfmc.audit.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import javax.activation.DataSource;

/**
 * Created by andras on 3/17/2015.
 */
public class DatabaseAuditHelper {

    public void insertCreateProcessInstanceAudit(DataSource dataSource, WMACreateProcessInstanceData wmaCreateProcessInstanceData){
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();


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
            if(connection!=null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }


    private Integer generatePrimaryKey(Connection con) throws SQLException {
        Integer i = null;
        PreparedStatement ps = null;
        try {



            ps = con.prepareStatement("SELECT WMAAuditEntry_Sequence.nextval FROM DUAL");
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                i = resultSet.getInt(1);


            }
        }finally {
            if(ps!=null)
                ps.close();
        }

        return i;

    }





    public void updateIdCreateProcessInstanceAudit(DataSource ds,String cacheId,String eloId){
    PreparedStatement preparedStatement = null;
        Connection c = null;
        try{
            c = ds.getConnection();
            preparedStatement = c.prepareStatement("UPDATE WM_AUDIT_ENTRY " +
                    "SET CURRENT_PROCESS_INSTANCE_ID = ? ," +
                    "    INITIAL_PROCESS_INSTANCE_ID = ? " +
                    "WHERE CURRENT_PROCESS_INSTANCE_ID  = ? ");
            preparedStatement.setString(1,eloId);
            preparedStatement.setString(2,cacheId);
            preparedStatement.setString(3,cacheId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {

         if(preparedStatement!=null)
             try {
                 preparedStatement.close();
             } catch (SQLException e1) {
                 e1.printStackTrace();
             }
            if(c!=null)
                try {
                    c.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            e.printStackTrace();
        }


    }


    public void insertAssignProcessInstanceAttributeAudit(DataSource dataSource, WMAAssignProcessInstanceAttributeData wmaAssignProcessInstanceAttributeData)
    {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
             connection = dataSource.getConnection();


            preparedStatement = connection.prepareStatement("INSERT INTO WM_AUDIT_ENTRY  (ID,PROCESS_DEFINITION_ID, " +
                    " ACTIVITY_DEFINITION_ID,  INITIAL_PROCESS_INSTANCE_ID,  CURRENT_PROCESS_INSTANCE_ID,  ACTIVITY_INSTANCE_ID,  \n" +
                    "                          WORK_ITEM_ID,  PROCESS_STATE,  EVENT_CODE,  DOMAIN_ID,  NODE_ID,  USER_ID, ROLE_ID,  time,  INFORMATION_ID,ATTRIBUTE_NAME,ATTRIBUTE_TYPE,PREVIOUS_ATTRIBUTE_VALUE,PREVIOUS_ATTRIBUTE_LENGTH,NEW_ATTRIBUTE_VALUE,NEW_ATTRIBUTE_LENGTH)  VALUES\n" +
                    "  (WMAAuditEntry_Sequence.nextval,?,?," +
                    "?,?,?,?,?,?,?,?,?,?,SYSDATE,?,?,?,?,?,?,?)");

            basicDataPreparedStatement(preparedStatement, wmaAssignProcessInstanceAttributeData);

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
            if(connection!=null)
                try {
                    connection.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            e.printStackTrace();
        }
    }

    public void insertAbortProcessInstanceAudit(DataSource dataSource, WMAChangeProcessInstanceStateData wmaChangeProcessInstanceStateData) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();

            preparedStatement = connection.prepareStatement("INSERT INTO WM_AUDIT_ENTRY  (ID,PROCESS_DEFINITION_ID, " +
                    "ACTIVITY_DEFINITION_ID, INITIAL_PROCESS_INSTANCE_ID, CURRENT_PROCESS_INSTANCE_ID, " +
                    "ACTIVITY_INSTANCE_ID, WORK_ITEM_ID, PROCESS_STATE, EVENT_CODE, DOMAIN_ID, NODE_ID, " +
                    "USER_ID, ROLE_ID, time, INFORMATION_ID, PREVIOUS_PROCESS_INST_STATE, " +
                    "NEW_PROCESS_INSTANCE_STATE)  " +
                    "VALUES (WMAAuditEntry_Sequence.nextval,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,?,?,?)");

            basicDataPreparedStatement(preparedStatement, wmaChangeProcessInstanceStateData);
            preparedStatement.setString(14, wmaChangeProcessInstanceStateData.getPreviousProcessState());
            preparedStatement.setString(15, wmaChangeProcessInstanceStateData.getNewProcessState());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void insertAssignWorkItemAttributeAudit(DataSource dataSource, WMAAssignWorkItemAttributeData wmaAssignWorkItemAttributeData) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();

            preparedStatement = connection.prepareStatement("INSERT INTO WM_AUDIT_ENTRY  (ID,PROCESS_DEFINITION_ID, " +
                    "ACTIVITY_DEFINITION_ID, INITIAL_PROCESS_INSTANCE_ID, CURRENT_PROCESS_INSTANCE_ID, " +
                    "ACTIVITY_INSTANCE_ID, WORK_ITEM_ID, PROCESS_STATE, EVENT_CODE, DOMAIN_ID, NODE_ID, " +
                    "USER_ID, ROLE_ID, time, INFORMATION_ID, ATTRIBUTE_NAME, ATTRIBUTE_TYPE, " +
                    "PREVIOUS_ATTRIBUTE_LENGTH, PREVIOUS_ATTRIBUTE_VALUE, NEW_ATTRIBUTE_LENGTH, NEW_ATTRIBUTE_VALUE, ACTIVITY_STATE" +
                    ") VALUES (WMAAuditEntry_Sequence.nextval,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,?,?,?,?,?,?,?,?)");

            basicDataPreparedStatement(preparedStatement, wmaAssignWorkItemAttributeData);
            preparedStatement.setString(14, wmaAssignWorkItemAttributeData.getAttributeName());
            preparedStatement.setInt(15, wmaAssignWorkItemAttributeData.getAttributeType());
            preparedStatement.setInt(16, wmaAssignWorkItemAttributeData.getPreviousAttributeLength());
            preparedStatement.setString(17, wmaAssignWorkItemAttributeData.getPreviousAttributeValue());
            preparedStatement.setInt(18, wmaAssignWorkItemAttributeData.getNewAttributeLength());
            preparedStatement.setString(19, wmaAssignWorkItemAttributeData.getNewAttributeValue());
            preparedStatement.setString(20, wmaAssignWorkItemAttributeData.getActivityState());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void insertReassignWorkItemAudit(DataSource dataSource, WMAAssignWorkItemData wmaAssignWorkItemData) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();

            preparedStatement = connection.prepareStatement("INSERT INTO WM_AUDIT_ENTRY (ID,PROCESS_DEFINITION_ID, " +
                    "ACTIVITY_DEFINITION_ID, INITIAL_PROCESS_INSTANCE_ID, CURRENT_PROCESS_INSTANCE_ID, " +
                    "ACTIVITY_INSTANCE_ID, WORK_ITEM_ID, PROCESS_STATE, EVENT_CODE, DOMAIN_ID, NODE_ID, " +
                    "USER_ID, ROLE_ID, time, INFORMATION_ID, TARGET_DOMAIN_ID, TARGET_NODE_ID, TARGET_USER_ID, " +
                    "TARGET_ROLE_ID, WORK_ITEM_STATE, PREVIOUS_WORK_ITEM_STATE) " +
                    "VALUES (WMAAuditEntry_Sequence.nextval,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,?,?,?,?,?,?,?)");

            basicDataPreparedStatement(preparedStatement, wmaAssignWorkItemData);
            preparedStatement.setString(14, wmaAssignWorkItemData.getTargetDomainId());
            preparedStatement.setString(15, wmaAssignWorkItemData.getTargetNodeId());
            preparedStatement.setString(16, wmaAssignWorkItemData.getTargetUserId());
            preparedStatement.setString(17, wmaAssignWorkItemData.getTargetRoleId());
            preparedStatement.setString(18, wmaAssignWorkItemData.getWorkItemState());
            preparedStatement.setString(19, wmaAssignWorkItemData.getPreviousWorkItemState());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void insertCompleteWorkItemAudit(DataSource dataSource, WMAChangeWorkItemStateData wmaChangeWorkItemStateData) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();

            preparedStatement = connection.prepareStatement("INSERT INTO WM_AUDIT_ENTRY (ID,PROCESS_DEFINITION_ID, " +
                    "ACTIVITY_DEFINITION_ID, INITIAL_PROCESS_INSTANCE_ID, CURRENT_PROCESS_INSTANCE_ID, " +
                    "ACTIVITY_INSTANCE_ID, WORK_ITEM_ID, PROCESS_STATE, EVENT_CODE, DOMAIN_ID, NODE_ID, " +
                    "USER_ID, ROLE_ID, time, INFORMATION_ID, WORK_ITEM_STATE, PREVIOUS_WORK_ITEM_STATE) " +
                    "VALUES (WMAAuditEntry_Sequence.nextval,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,?,?,?)");

            basicDataPreparedStatement(preparedStatement, wmaChangeWorkItemStateData);

            preparedStatement.setString(14, wmaChangeWorkItemStateData.getWorkItemState());
            preparedStatement.setString(15, wmaChangeWorkItemStateData.getPreviousWorkItemState());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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
