package org.wfmc.wapi;

public class WMInvalidSourceUserException extends WMInvalidObjectException {
    private static final long serialVersionUID = 513114213906971762L;

    public WMInvalidSourceUserException(String sourceUser) {
        super(WMError.WM_INVALID_SOURCE_USER, sourceUser);
    }
}
