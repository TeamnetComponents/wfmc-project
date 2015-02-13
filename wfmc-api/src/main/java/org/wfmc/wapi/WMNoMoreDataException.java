package org.wfmc.wapi;

public class WMNoMoreDataException extends WMWorkflowException {
    private static final long serialVersionUID = 1829049389934174084L;

    public WMNoMoreDataException() {
        super(new WMError(WMError.WM_NO_MORE_DATA));
    }
}