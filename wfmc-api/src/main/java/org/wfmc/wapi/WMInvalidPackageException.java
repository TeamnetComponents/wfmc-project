package org.wfmc.wapi;

/**
 * @author Adrian Price
 */
public class WMInvalidPackageException extends WMInvalidObjectException {
    private static final long serialVersionUID = 9126684042783604376L;

    public WMInvalidPackageException(String packageId) {
        super(WMError.WM_GENERAL_ERROR, packageId);
    }
}