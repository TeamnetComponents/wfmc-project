package org.wfmc.wapi;

public class WMInvalidTargetUserException extends WMInvalidObjectException {
    private static final long serialVersionUID = -1531645422643039104L;

    public WMInvalidTargetUserException(String targetUser) {
        super(WMError.WM_INVALID_TARGET_USER, targetUser);
    }
}
