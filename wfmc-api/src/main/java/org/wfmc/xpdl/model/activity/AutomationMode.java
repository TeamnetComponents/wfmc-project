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

package org.wfmc.xpdl.model.activity;



import org.wfmc.util.AbstractEnum;
import org.wfmc.xpdl.XPDLNames;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents how an activity should start or end.  There are two modes: {@link
 * #AUTOMATIC} (the default mode) will result in the automatic start or finish
 * of an activity when the activity is executed; {@link #MANUAL} requires some
 * sort of manual intervention to start the activity.
 *
 * @author Adrian Price
 */
public final class AutomationMode extends AbstractEnum {
    private static final long serialVersionUID = -1780844811985379072L;

    public static final int AUTOMATIC_INT = 0;
    public static final int MANUAL_INT = 1;
    /**
     * AutomationMode representing an automatic start or completion.
     */
    public static final AutomationMode AUTOMATIC =
        new AutomationMode(XPDLNames.AUTOMATIC_KIND, AUTOMATIC_INT);
    /**
     * AutomationMode representing a manual start or completion.
     */
    public static final AutomationMode MANUAL =
        new AutomationMode(XPDLNames.MANUAL_KIND, MANUAL_INT);

    private static final AutomationMode[] _values = {
        AUTOMATIC,
        MANUAL
    };
    private static final Map _tagMap = new HashMap();
    public static final List VALUES = clinit(_values, _tagMap);

    /**
     * Converts the specified String to an AutomationMode object.  If there is
     * no matching AutomationMode for the given string then this method returns
     * null.
     *
     * @param tag The string
     * @return The AutomationMode object
     */
    public static AutomationMode valueOf(String tag) {
        AutomationMode automationMode = (AutomationMode)_tagMap.get(tag);
        if (automationMode == null && tag != null)
            throw new IllegalArgumentException(tag);
        return automationMode;
    }

    /**
     * Constructs a new AutomationMode instance.
     *
     * @param name
     * @param ordinal The value
     */
    private AutomationMode(String name, int ordinal) {
        super(name, ordinal);
    }

    public List family() {
        return VALUES;
    }
}