package org.wfmc.wapi;


public class WMAttributeAssignmentException extends WMWorkflowException {
    private static final long serialVersionUID = 3908258638565217467L;
    private final String attributeName;

    public WMAttributeAssignmentException(String attributeName) {
        super(new WMError(WMError.WM_ATTRIBUTE_ASSIGNMENT_FAILED));
        this.attributeName = attributeName;
    }

    public String toString() {
        return getClass().getName() + '@' + System.identityHashCode(this)
            + "[attributeName='" + attributeName + '\''
            + ']';
    }
}
