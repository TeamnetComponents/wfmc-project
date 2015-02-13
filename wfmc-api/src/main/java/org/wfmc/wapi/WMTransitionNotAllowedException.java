package org.wfmc.wapi;

public class WMTransitionNotAllowedException extends WMWorkflowException {
    private static final long serialVersionUID = -9001778228100390200L;
    private static final String[] ACTIONS = {
        "(illegal)",
        "(none)",
        "start",
        "stop",
        "suspend",
        "abort",
        "terminate",
        "complete"
    };
    private final String _oldState;
    private final String _newState;
    private final int _action;

    public WMTransitionNotAllowedException(String oldState, String newState,
        String message) {

        super(new WMError(WMError.WM_TRANSITION_NOT_ALLOWED), message);
        _oldState = oldState;
        _newState = newState;
        _action = WMObjectState.ILLEGAL_ACTION;
    }

    public WMTransitionNotAllowedException(String oldState, int action,
        String message) {

        super(new WMError(WMError.WM_TRANSITION_NOT_ALLOWED), message);
        _oldState = oldState;
        _newState = "(invalid)";
        _action = action;
    }

    public String toString() {
        return super.toString() +
            "[from='" + _oldState +
            "', to='" + _newState +
            "', action='" + ACTIONS[_action + 2] +
            "']";
    }
}