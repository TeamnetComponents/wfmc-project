package org.wfmc.wapi;


public class WMInvalidSessionException extends WMInvalidObjectException {
    private static final long serialVersionUID = -8332610590181426039L;

    public WMInvalidSessionException() {
        super(WMError.WM_INVALID_SESSION_HANDLE, null);
    }
}
