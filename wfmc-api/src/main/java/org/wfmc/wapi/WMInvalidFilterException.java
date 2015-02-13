package org.wfmc.wapi;

public class WMInvalidFilterException extends WMInvalidObjectException {
    private static final long serialVersionUID = -2878545707324033369L;

    public WMInvalidFilterException(String filterString) {
        super(WMError.WM_INVALID_FILTER, filterString);
    }
}
