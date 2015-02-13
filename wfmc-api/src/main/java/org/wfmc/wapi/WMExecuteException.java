package org.wfmc.wapi;

public class WMExecuteException extends WMWorkflowException {
    private static final long serialVersionUID = -6582786780183780238L;

    public WMExecuteException(String exceptionName) {
        super(new WMError(WMError.WM_EXECUTE_FAILED), exceptionName);
    }

    public String getExceptionName() {
        return getMessage();
    }
}