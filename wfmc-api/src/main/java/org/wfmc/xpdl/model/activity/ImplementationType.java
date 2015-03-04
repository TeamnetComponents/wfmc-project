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
 * The ImplementationType class is used to represent how an activity
 * is implemented.
 *
 * @author Adrian Price
 */
public final class ImplementationType extends AbstractEnum {
    private static final long serialVersionUID = 8184059437925889311L;

    public static final int NO_INT = 0;
    public static final int TOOLS_INT = 1;
    public static final int SUBFLOW_INT = 2;
    /**
     * ImplementationType which represents an empty implementation.
     */
    public static final ImplementationType NO =
        new ImplementationType(XPDLNames.NO, NO_INT);
    /**
     * ImplementationType which represents a tool implementation.
     */
    public static final ImplementationType TOOLS =
        new ImplementationType(XPDLNames.TOOL, TOOLS_INT);
    /**
     * ImplementationType which represents a subflow implementation.
     */
    public static final ImplementationType SUBFLOW =
        new ImplementationType(XPDLNames.SUBFLOW, SUBFLOW_INT);

    private static final ImplementationType[] _values = {
        NO,
        TOOLS,
        SUBFLOW
    };
    private static final Map _tagMap = new HashMap();
    public static final List VALUES = clinit(_values, _tagMap);

    /**
     * Convert the specified String to an ImplementationType object.  If there
     * no matching ImplementationType for the given String then this method
     * returns null.
     *
     * @param tag The String
     * @return The ImplementationType object
     */
    public static ImplementationType valueOf(String tag) {
        ImplementationType implementationType = (ImplementationType)
            _tagMap.get(tag);
        if (implementationType == null && tag != null)
            throw new IllegalArgumentException(tag);
        return implementationType;
    }

    /**
     * Construct a new ImplementationType instance.
     *
     * @param name
     * @param ordinal The value
     */
    private ImplementationType(String name, int ordinal) {
        super(name, ordinal);
    }

    public List family() {
        return VALUES;
    }
}