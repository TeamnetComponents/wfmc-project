package org.wfmc.wapi;

public class WMInvalidActivityInstanceException
    extends WMInvalidObjectException {

    private static final long serialVersionUID = 1738151149117001141L;

    /**
     * Constructs a new WMInvalidActivityInstanceException.  This exception is
     * thrown when an activity instance cannot be located by its definition ID
     * or its instance ID, depending on which was passed to the finder method.
     *
     * @param activityId The ID of the activity definition or the activity
     *                   instance, depending on the context.
     */
    public WMInvalidActivityInstanceException(String activityId) {
        super(WMError.WM_INVALID_ACTIVITY_INSTANCE, activityId);
    }
}
