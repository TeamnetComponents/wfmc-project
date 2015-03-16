package org.wfmc.impl.utils;

import org.apache.commons.dbcp.BasicDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by Lucian.Dragomir on 1/17/2015.
 */
public class DatabaseUtils {
    private static final TemplateEngine templateEngine = TemplateEngine.getInstance();

    public static final String CONNECTION_NAME = "connectionName";
    public static final String SCHEMA_NAME = "schemaName";
    public static final String TABLE_NAME = "tableName";
    private static final String CONNECTION_TYPE = "connection.jdbc." + templateEngine.escapeVariable(CONNECTION_NAME) + ".type";
    private static final String CONNECTION_URL = "connection.jdbc." + templateEngine.escapeVariable(CONNECTION_NAME) + ".url";
    private static final String CONNECTION_DRIVER = "connection.jdbc." + templateEngine.escapeVariable(CONNECTION_NAME) + ".driver";
    private static final String CONNECTION_USER = "connection.jdbc." + templateEngine.escapeVariable(CONNECTION_NAME) + ".user";
    private static final String CONNECTION_PASSWORD = "connection.jdbc." + templateEngine.escapeVariable(CONNECTION_NAME) + ".password";
    private static final String CONNECTION_SCHEMA = "connection.jdbc." + templateEngine.escapeVariable(CONNECTION_NAME) + ".schema";
    private static final String CONNECTION_TYPE_LOCAL = "local";
    private static final String CONNECTION_TYPE_JNDI = "jndi";
    private static final String CONNECTION_CURRENT_SCHEMA = "currentSchema";

    public DatabaseUtils() {
    }

    //connection detail helper methods
    public static String getConnectionType(Properties context, String connectionName) {
        return (String) context.get(templateEngine.getValueFromTemplate(CONNECTION_TYPE, CONNECTION_NAME, connectionName));
    }

    public static String getConnectionUrl(Properties context, String connectionName) {
        return (String) context.get(templateEngine.getValueFromTemplate(CONNECTION_URL, CONNECTION_NAME, connectionName));
    }

    public static String getConnectionDriver(Properties context, String connectionName) {
        return (String) context.get(templateEngine.getValueFromTemplate(CONNECTION_DRIVER, CONNECTION_NAME, connectionName));
    }

    public static String getConnectionUser(Properties context, String connectionName) {
        return (String) context.get(templateEngine.getValueFromTemplate(CONNECTION_USER, CONNECTION_NAME, connectionName));
    }

    public static String getConnectionPassword(Properties context, String connectionName) {
        return (String) context.get(templateEngine.getValueFromTemplate(CONNECTION_PASSWORD, CONNECTION_NAME, connectionName));
    }

    public static String getConnectionSchema(Properties context, String connectionName) {
        return (String) context.get(templateEngine.getValueFromTemplate(CONNECTION_SCHEMA, CONNECTION_NAME, connectionName));
    }

    public static DataSource getDataSource(Properties context, String connectionName) {
        String connectionType = getConnectionType(context, connectionName);
        if (CONNECTION_TYPE_LOCAL.equalsIgnoreCase(connectionType)) {
            BasicDataSource basicDataSource = new BasicDataSource();
            basicDataSource.setDriverClassName(getConnectionDriver(context, connectionName));
            basicDataSource.setUrl(getConnectionUrl(context, connectionName));
            basicDataSource.setUsername(getConnectionUser(context, connectionName));
            basicDataSource.setPassword(getConnectionPassword(context, connectionName));
            String connectionSchema = getConnectionSchema(context, connectionName);
            if (connectionSchema != null && !connectionSchema.isEmpty())
                basicDataSource.addConnectionProperty(CONNECTION_CURRENT_SCHEMA, connectionSchema);

            return basicDataSource;
        } else if (CONNECTION_TYPE_JNDI.equalsIgnoreCase(connectionType)) {
            try {
                Context initContext = new InitialContext();
                return (DataSource) initContext.lookup(getConnectionUrl(context, connectionName));
            } catch (NamingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("The connection name <" + connectionName + "> is not defined correctly.");
        }
    }

    public static Connection getConnection(Properties context, String connectionName) {
        DataSource dataSource = null;
        Connection connection = null;
        try {
            dataSource = getDataSource(context, connectionName);
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            dataSource = null;
        }
        return connection;
    }

    public static long getSequenceNextVal(Connection connection, String sequenceSql) throws SQLException {
        long sequence = -1;
        sequenceSql = sequenceSql.toUpperCase().trim();
        if (!sequenceSql.startsWith("SELECT ")) {
            //if oracle
           // if (true) {
                sequenceSql = "SELECT " + sequenceSql + ".NEXTVAL FROM DUAL";
            //}

            //if db2
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sequenceSql);
        resultSet.next();
        sequence = resultSet.getLong(1);
        resultSet.close();
        resultSet = null;
        statement.close();
        statement = null;
        return sequence;
    }
}
