package org.wfmc.wapi;

public class WMInvalidProcessDefinitionException
    extends WMInvalidObjectException {

    private static final long serialVersionUID = -6619236362370390306L;

    public WMInvalidProcessDefinitionException(String msg) {
        super(WMError.WM_INVALID_PROCESS_DEFINITION, msg);
    }
}
