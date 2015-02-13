package org.elo.connection;

/**
 * @author andreeaf
 * @since 9/11/2014 1:26 PM
 */
public enum EloAuthenticationType {
    BASIC,
    BASIC_AS,
    //AS,
    KERBEROS,
    TICKET;

    public static EloAuthenticationType fromValue(String v) {
        if (v == null) {
            v = EloAuthenticationType.BASIC.name();
        }
        for (EloAuthenticationType c : EloAuthenticationType.values()) {
            if (c.name().toLowerCase().equals(v.toLowerCase())) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
