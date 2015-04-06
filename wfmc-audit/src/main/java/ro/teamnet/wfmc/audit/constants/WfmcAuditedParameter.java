package ro.teamnet.wfmc.audit.constants;

/**
 * Audited parameter types for auditable WfMC methods.
 */
public class WfmcAuditedParameter {
    private WfmcAuditedParameter() {
    }

    public static final String PROCESS_DEFINITION_ID = "procDefId";
    public static final String PROCESS_INSTANCE_NAME = "procInstName";
    public static final String PROCESS_INSTANCE_ID = "procInstId";
    public static final String WORK_ITEM_ID = "workItemId";
    public static final String ATTRIBUTE_NAME = "attrName";
    public static final String ATTRIBUTE_VALUE = "attrValue";
    public static final String SOURCE_USER = "sourceUser";
    public static final String TARGET_USER = "targetUser";
}
