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

import org.wfmc.util.AbstractEnum;
import org.wfmc.xpdl.XPDLNames;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Type map for conformance classes.
 *
 * @author Adrian Price
 */
public final class GraphConformance extends AbstractEnum {
    private static final long serialVersionUID = 7855371018441648810L;

    public static final int NON_BLOCKED_INT = 0;
    public static final int LOOP_BLOCKED_INT = 1;
    public static final int FULL_BLOCKED_INT = 2;
    public static final GraphConformance NON_BLOCKED =
        new GraphConformance(XPDLNames.NON_BLOCKED_KIND, NON_BLOCKED_INT);
    public static final GraphConformance LOOP_BLOCKED =
        new GraphConformance(XPDLNames.LOOP_BLOCKED_KIND, LOOP_BLOCKED_INT);
    public static final GraphConformance FULL_BLOCKED =
        new GraphConformance(XPDLNames.FULL_BLOCKED_KIND, FULL_BLOCKED_INT);
    private static final GraphConformance[] _values = {
        NON_BLOCKED,
        LOOP_BLOCKED,
        FULL_BLOCKED
    };
    private static final Map _tagMap = new HashMap();
    public static final List VALUES = clinit(_values, _tagMap);

    /**
     * Convert the specified String to a ConformanceClass object.
     *
     * @param tag The String representation
     * @return The ConformanceClass
     */
    public static GraphConformance valueOf(String tag) {
        GraphConformance graphConformance = (GraphConformance)_tagMap.get(tag);
        if (graphConformance == null && tag != null) {
            throw new IllegalArgumentException(tag);
        }
        return graphConformance;
    }

    /**
     * Constructs a new ConformanceClass type.
     *
     * @param name
     * @param ordinal The int value
     */
    private GraphConformance(String name, int ordinal) {
        super(name, ordinal);
    }

    public List family() {
        return VALUES;
    }
}