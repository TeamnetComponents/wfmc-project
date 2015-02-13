package org.wfmc.wapi;

import java.io.IOException;

public class WMIOException extends WMWorkflowException {
    private static final long serialVersionUID = -1019791291722421305L;

    public WMIOException(IOException e) {
        super(new WMError(WMError.WM_IOERROR), e);
    }
}
