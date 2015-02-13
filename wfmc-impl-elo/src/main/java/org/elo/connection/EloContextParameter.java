package org.elo.connection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucian.Dragomir on 12/27/2014.
 */
public class EloContextParameter {

    // ---- general parameter ----
    public static final String ELO_CONTEXT_PARAMETER_PREFIX = "elo.server.";
    public static final String COMPUTER_NAME = ELO_CONTEXT_PARAMETER_PREFIX + "authentication.computer.name";

    //other session parameters (locale information)
    public static final String LOCALE_LANGUAGE =  ELO_CONTEXT_PARAMETER_PREFIX + "locale.language";
    public static final String LOCALE_COUNTRY = ELO_CONTEXT_PARAMETER_PREFIX + "locale.country";
    public static final String LOCALE_VARIANT = ELO_CONTEXT_PARAMETER_PREFIX + "locale.variant";

    //AUTHENTICATION TYPE
    public static final String AUTHENTICATION_TYPE = ELO_CONTEXT_PARAMETER_PREFIX + "authentication.type";
    //AS
    public static final String USER_AS = ELO_CONTEXT_PARAMETER_PREFIX + "authentication.userAs".toLowerCase(); //fixed for tomcat that has the headers converted to lowercase
    //KERBEROS
    public static final String KERBEROS_REALM = ELO_CONTEXT_PARAMETER_PREFIX + "authentication.kerberos.realm";
    public static final String KERBEROS_KDC = ELO_CONTEXT_PARAMETER_PREFIX + "authentication.kerberos.kdc";
    public static final String KERBEROS_PRINCIPAL = ELO_CONTEXT_PARAMETER_PREFIX + "authentication.kerberos.principal";
    //TICKET
    public static final String TICKET = ELO_CONTEXT_PARAMETER_PREFIX + "authentication.ticket";
    //BASIC
    public static final String USER = "username";
    public static final String PASSWORD = "password";

    //ALL PARAMETERS FROM THIS CLASS
    private static List<String> contextParameterList;

    // utility class
    private EloContextParameter() {
    }

    public static List<String> getEloContextParameters() {
        return contextParameterList;
    }

    static {
        //init class properties in the list
        contextParameterList = initContextParameterList();
    }


    private static List<String> initContextParameterList() {
        List<String> parameterList = new ArrayList<String>();
        Field[] declaredFields = EloContextParameter.class.getDeclaredFields();
        for (Field field : declaredFields) {
            if (Modifier.isPublic(field.getModifiers()) &&
                    Modifier.isStatic(field.getModifiers()) &&
                    Modifier.isFinal(field.getModifiers())) {
                String fieldValue = null;
                try {
                    fieldValue = String.valueOf(field.get(null));
                } catch (IllegalAccessException e) {

                }
                if (fieldValue != null) {
                    parameterList.add(fieldValue);
                }
            }
        }
        return parameterList;
    }
}