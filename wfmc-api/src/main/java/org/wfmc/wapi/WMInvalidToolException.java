package org.wfmc.wapi;

public class WMInvalidToolException extends WMInvalidObjectException {
    private static final long serialVersionUID = 5418088184966105826L;

    public WMInvalidToolException(String toolId) {
        super(WMError.WM_EXECUTE_FAILED, toolId);
    }
}
