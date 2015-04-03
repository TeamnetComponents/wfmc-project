package ro.teamnet.audit.constants;

/**
 * Lists supported auditing strategies.
 */
public class AuditStrategy {
    /**
     * Ignore the {@link ro.teamnet.audit.annotation.Auditable} annotation and perform no auditing.
     */
    public final static String IGNORE = "ignore";
    /**
     * Perform audit using the WfMC auditing strategy.
     */
    public final static String WFMC = "WfMC";
}
