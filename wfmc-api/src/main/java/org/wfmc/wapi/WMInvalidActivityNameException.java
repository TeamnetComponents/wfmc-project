package org.wfmc.wapi;

public class WMInvalidActivityNameException extends WMInvalidObjectException {
    private static final long serialVersionUID = -3050394542272848943L;

    public WMInvalidActivityNameException(String activityName) {
        super(WMError.WM_INVALID_ACTIVITY_NAME, activityName);
    }
}
