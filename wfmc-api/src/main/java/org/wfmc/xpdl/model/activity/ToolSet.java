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

import org.wfmc.util.AbstractBean;
import org.wfmc.xpdl.PackageVisitor;
import org.wfmc.xpdl.model.XPDLProperties;

import java.beans.PropertyVetoException;
import java.util.Arrays;

/**
 * A set of tools.
 *
 * @author Adrian Price
 */
public final class ToolSet extends AbstractBean implements Implementation {
    private static final long serialVersionUID = -3120702180107909751L;
    public static final String IMPLEMENTATION_TYPE =
        XPDLProperties.IMPLEMENTATION_TYPE;
    public static final String TOOL = XPDLProperties.TOOL;
    private static final Tool[] EMPTY_TOOL = {};
    private static final String[] _indexedPropertyNames = {TOOL};
    private static final Object[] _indexedPropertyValues = {EMPTY_TOOL};
    private static final int TOOL_IDX = 0;

    private Tool[] _tool = EMPTY_TOOL;

    /**
     * Construct a new ToolSet.
     */
    public ToolSet() {
        super(_indexedPropertyNames, _indexedPropertyValues);
    }

    public void accept(PackageVisitor visitor) {
        visitor.visit(this);
        for (int i = 0; i < _tool.length; i++)
            _tool[i].accept(visitor);
    }

    public ImplementationType getType() {
        return ImplementationType.TOOLS;
    }

    public void add(Tool tool) throws PropertyVetoException {
        _tool = (Tool[])add(TOOL_IDX, tool);
    }

    public void remove(Tool tool) throws PropertyVetoException {
        _tool = (Tool[])remove(TOOL_IDX, tool);
    }

    /**
     * Get a List of all tools in the set.
     *
     * @return a List of tools
     */
    public Tool[] getTool() {
        return (Tool[])get(TOOL_IDX);
    }

    public Tool getTool(int i) {
        return _tool[i];
    }

    public Tool getTool(String id) {
        if (_tool != null) {
            for (int i = 0; i < _tool.length; i++) {
                Tool t = _tool[i];
                if (t.getId().equals(id))
                    return t;
            }
        }
        return null;
    }

    public void setTool(Tool[] tools) throws PropertyVetoException {
        set(TOOL_IDX, _tool = tools == null ? EMPTY_TOOL : tools);
    }

    public void setTool(int i, Tool tool) throws PropertyVetoException {
        set(TOOL_IDX, i, tool);
    }

    public String toString() {
        return "ToolSet[tools=" +
            (_tool == null ? null : Arrays.asList(_tool)) + ']';
    }
}