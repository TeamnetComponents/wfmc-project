package org.wfmc.wapi;

/**
 * @author Adrian Price
 */
public class WMConnectException extends WMWorkflowException {
    private static final long serialVersionUID = 7800700798975973052L;

    public WMConnectException(Exception e) {
        super(new WMError(WMError.WM_CONNECT_FAILED), e);
    }
}
