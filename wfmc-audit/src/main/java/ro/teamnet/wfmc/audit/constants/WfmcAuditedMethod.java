package ro.teamnet.wfmc.audit.constants;

/**
 * Auditable WfMC method types.
 */
public class WfmcAuditedMethod {
    private WfmcAuditedMethod() {
    }

    public static final String CREATE_PROCESS_INSTANCE = "createProcessInstance";
    public static final String ASSIGN_PROCESS_INSTANCE_ATTRIBUTE = "assignProcessInstanceAttribute";
    public static final String START_PROCESS = "startProcess";
    public static final String ABORT_PROCESS_INSTANCE = "abortProcessInstance";
    public static final String REASSIGN_WORK_ITEM = "reassignWorkItem";
    public static final String ASSIGN_WORK_ITEM_ATTRIBUTE = "assignWorkItemAttribute";
    public static final String COMPLETE_WORK_ITEM = "completeWorkItem";
}
