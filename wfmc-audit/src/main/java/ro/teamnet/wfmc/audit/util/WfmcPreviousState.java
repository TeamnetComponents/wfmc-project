package ro.teamnet.wfmc.audit.util;

public class WfmcPreviousState {

    /**
     * Previous state when process instance is created
     */
    public static final String CREATE_PROCESS_INSTANCE = "null";


    /**
     * Previous state when process instance is started
     */
    public static final String START_PROCESS_INSTANCE = "OPEN_NOTRUNNING_NOTSTARTED";


    /**
     * Previous state when process instance is aborted
     */
    public static final String ABORT_PROCESS_INSTANCE = "OPEN_RUNNING";

}
