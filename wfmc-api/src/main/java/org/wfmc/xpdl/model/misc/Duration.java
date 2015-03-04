/*--

 Copyright (C) 2002-2005 Adrian Price.
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:

 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions, and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions, and the disclaimer that follows
    these conditions in the documentation and/or other materials
    provided with the distribution.

 3. The names "OBE" and "Open Business Engine" must not be used to
 	endorse or promote products derived from this software without prior
 	written permission.  For written permission, please contact
 	adrianprice@sourceforge.net.

 4. Products derived from this software may not be called "OBE" or
 	"Open Business Engine", nor may "OBE" or "Open Business Engine"
 	appear in their name, without prior written permission from
 	Adrian Price (adrianprice@users.sourceforge.net).

 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR(S) BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 POSSIBILITY OF SUCH DAMAGE.

 For more information on OBE, please see
 <http://obe.sourceforge.net/>.

 */

package org.wfmc.xpdl.model.misc;

import org.wfmc.util.AbstractBean;

public final class Duration extends AbstractBean {
    private static final long serialVersionUID = -4251576301062064086L;


    private int _value;
    private DurationUnit _unit;

    /**
     * Default public no-args constructor to keep Castor happy.
     */
    public Duration() {
    }

    /**
     * Construct a new Duration.  A value of 0 signals an unlimited duration.
     * The duration unit may be null to specify that the default should be used.
     * The default is determined at runtime.
     *
     * @param value The duration value
     * @param unit  The unit of measurement
     */
    public Duration(int value, DurationUnit unit) {
        _value = value;
        _unit = unit;
    }

    /**
     * Construct a new Duration.
     *
     * @param duration The duration value
     * @throws NumberFormatException
     */
    public Duration(String duration) throws NumberFormatException {
        setDuration(duration);
    }

    /**
     * Returns the duration as a string.
     *
     * @return The duration string.
     */
    public String getDuration() {
        return toString();
    }

    /**
     * Sets the duration as a string.
     *
     * @param duration The duration string.
     */
    public void setDuration(String duration) {
        if (duration == null) {
            throw new IllegalArgumentException(
                "Duration string cannot be null");
        }

        StringBuffer intBuffer = new StringBuffer();
        DurationUnit unit = null;
        for (int i = 0; i < duration.length(); i++) {
            char c = duration.charAt(i);
            if (Character.isDigit(c)) {
                intBuffer.append(c);
            } else if (Character.isLetter(c)) {
                char[] cArray = {c};
                unit = DurationUnit.valueOf(new String(cArray));
            }
        }

        _value = intBuffer.length() == 0 ? 0
            : Integer.parseInt(intBuffer.toString());
        _unit = unit;
    }

    /**
     * The duration value.
     *
     * @return The duration value
     */
    public int getValue() {
        return _value;
    }

    public void setValue(int value) {
        _value = value;
    }

    /**
     * Return this duration's unit.  This method may return null if the unit is
     * not specified.
     *
     * @return The duration unit or null
     */
    public DurationUnit getUnit() {
        return _unit;
    }

    public void setUnit(DurationUnit unit) {
        _unit = unit;
    }

    /**
     * Get the duration's unit.  The specified default duration unit is used if
     * this duration has no specified duration unit.
     *
     * @param defaultUnit The default unit if no unit specified
     * @return The duration unit
     */
    public DurationUnit getUnit(DurationUnit defaultUnit) {
        return _unit == null ? defaultUnit : _unit;
    }

    /**
     * Get the duration represented as millseconds.  The specified default
     * duration unit is used this duration has no specified duration unit.
     *
     * @param defaultUnit The default unit if no unit specified
     * @return The number of milliseconds for this duration
     */
    public long getDurationInMilliseconds(DurationUnit defaultUnit) {
        int value = _value;
        DurationUnit unit = getUnit(defaultUnit);
        return value == 0 ? 0 : value * unit.toMilliseconds();
    }

    /**
     * Return a String representation of the Duration.
     *
     * @return A string
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(_value);
        if (_unit != null) {
            buffer.append(_unit);
        }
        return buffer.toString();
    }

    /**
     * Parse the duration string into a Duration object.
     *
     * @param duration The duration string.
     * @return The Duration object.
     * @throws NumberFormatException
     */
    public static Duration valueOf(String duration)
        throws NumberFormatException {

        return duration == null ? null : new Duration(duration);
    }
}