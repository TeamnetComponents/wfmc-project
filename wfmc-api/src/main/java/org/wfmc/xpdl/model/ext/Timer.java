package org.wfmc.xpdl.model.ext;


import org.wfmc.xpdl.model.XPDLProperties;
import org.wfmc.xpdl.model.misc.Duration;

/**
 * @author Adrian Price
 */
public final class Timer extends Trigger {
    public static final String CALENDAR = XPDLProperties.CALENDAR;
    public static final String INTERVAL = XPDLProperties.INTERVAL;
    public static final String RECOVERABLE = XPDLProperties.RECOVERABLE;

    private Duration _interval;
    private String _calendar;
    private boolean _recoverable;

    public Timer() {
    }

    public Timer(String id, String count, String interval, String calendar,
        String recoverable) {

        super(id, count);
        _interval = Duration.valueOf(interval);
        _calendar = calendar;
        _recoverable = recoverable != null &&
            Boolean.valueOf(recoverable).booleanValue();
    }

    public Duration getInterval() {
        return _interval;
    }

    public void setInterval(Duration interval) {
        _interval = interval;
    }

    public String getCalendar() {
        return _calendar;
    }

    public void setCalendar(String calendar) {
        _calendar = calendar;
    }

    public boolean isRecoverable() {
        return _recoverable;
    }

    public void setRecoverable(boolean recoverable) {
        _recoverable = recoverable;
    }
}