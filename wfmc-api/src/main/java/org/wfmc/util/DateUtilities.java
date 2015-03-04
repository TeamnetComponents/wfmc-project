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

package org.wfmc.util;

import org.wfmc.util.CommonConfig;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date formatting and parsing utilities.  This is not a threadsafe class.  Each
 * thread must call the {@link #getInstance} method to obtain its own dedicated
 * instance.
 *
 * @author Anthony Eden
 * @author Adrian Price
 */
public final class DateUtilities {
    public static final String DEFAULT_DATE_FORMAT =
        CommonConfig.getDefaultDateFormat();
    private static final String[] _jdk13DefaultFormats = {
        DEFAULT_DATE_FORMAT,
        "yyyy-MM-dd'T'HH:mm:ss",
        // XPDL examples format (yuk).
        "MM/dd/yyyy hh:mm:ss a",
        // Alternative format.
        "yyyy-MM-dd HH:mm:ss z",
        "yyyy-MM-dd HH:mm:ss",
        "yyyy-MM-ddZ",
    };
    private static final String[] _jdk14DefaultFormats = {
        // xsd:dateTime / ISO 8601 extended date/time formats as per XML Schema.
        DEFAULT_DATE_FORMAT,
        "yyyy-MM-dd'T'HH:mm:ss",
        // XPDL examples format (yuk).
        "MM/dd/yyyy hh:mm:ss a",
        // Alternative format.
        "yyyy-MM-dd HH:mm:ss z",
        "yyyy-MM-dd HH:mm:ss",
        "yyyy-MM-ddZ",
        "yyyy-MM-dd",
    };
    private static final String[] formatStrings;
    private static final ThreadLocal _tlsInstance = new ThreadLocal();
    private final DateFormat[] _formats;

    static {
        // We don't support pre-JDK1.3 versions.
        boolean jdk13 = System.getProperty("java.version").startsWith("1.3");
        formatStrings = jdk13 ? _jdk13DefaultFormats : _jdk14DefaultFormats;
    }

    /**
     * Get an instance of the DateUtilities class for the current thread.
     *
     * @return A DateUtilities instance
     */
    public static DateUtilities getInstance() {
        DateUtilities inst = (DateUtilities)_tlsInstance.get();
        if (inst == null) {
            inst = new DateUtilities();
            _tlsInstance.set(inst);
        }
        return inst;
    }

    /**
     * Construct a new DateUtilities class.
     */
    private DateUtilities() {
        _formats = new DateFormat[formatStrings.length];
        for (int i = 0; i < _formats.length; i++)
            _formats[i] = new SimpleDateFormat(formatStrings[i]);
    }

    public void setFormats(DateFormat[] formats) {
        System.arraycopy(formats, 0, _formats, 0, _formats.length);
    }

    public DateFormat[] getFormats() {
        return (DateFormat[])_formats.clone();
    }

    /**
     * Parse the specified date string.
     *
     * @param dateString The date string
     * @return The Date object.
     * @throws java.text.ParseException if the date format is not supported.
     */
    public Date parse(String dateString) throws ParseException {
        for (int i = 0; i < _formats.length; i++) {
            try {
                return _formats[i].parse(dateString);
            } catch (ParseException e) {
                // do nothing
            }
        }
        throw new ParseException("Unsupported date format: " + dateString, -1);
    }

    public String format(Date date) {
        return date == null ? null : _formats[0].format(date);
    }
}