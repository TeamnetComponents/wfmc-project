package org.wfmc.wapi;

/**
 * @author Adrian Price
 */
public abstract class WMInvalidObjectException extends WMWorkflowException {
    private static final long serialVersionUID = -5184952411867999690L;
    private final Object object;

    protected WMInvalidObjectException(int errorCode, Object object) {
        super(new WMError(errorCode));
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public String toString() {
        return getClass().getName() + '@' + System.identityHashCode(this)
            + "[object='" + object + '\''
            + ']';
    }
}