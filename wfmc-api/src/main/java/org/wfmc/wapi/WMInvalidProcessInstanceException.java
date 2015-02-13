package org.wfmc.wapi;

public class WMInvalidProcessInstanceException
    extends WMInvalidObjectException {

    private static final long serialVersionUID = -8894973360615671750L;

    public WMInvalidProcessInstanceException(String processInstanceId) {
        super(WMError.WM_INVALID_PROCESS_INSTANCE, processInstanceId);
    }
}
