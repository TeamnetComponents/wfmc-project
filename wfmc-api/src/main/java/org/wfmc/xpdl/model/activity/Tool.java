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

import org.wfmc.xpdl.PackageVisitor;
import org.wfmc.xpdl.model.misc.ExtendedAttributes;
import org.wfmc.xpdl.model.misc.Invocation;

/**
 * Implementation which specifies a particular tool which should be used for the
 * activity.  The tool will either be an application or a procedure.
 *
 * @author Adrian Price
 */
public final class Tool extends Invocation {
    private static final long serialVersionUID = -6478999523224995636L;

    private String _description;
    private ToolType _toolType;
    private ExtendedAttributes _extendedAttributes;

    public Tool() {
    }

    /**
     * Construct a new Tool object with the given id.
     *
     * @param id The tool ID
     */
    public Tool(String id) {
        _id = id;
    }

    public void accept(PackageVisitor visitor) {
        visitor.visit(this);
        super.accept(visitor);
    }

    /**
     * Get a description of the tool.
     *
     * @return A description of the tool
     */
    public String getDescription() {
        return _description;
    }

    /**
     * Set a description of the tool.
     *
     * @param description The new description of the tool
     */
    public void setDescription(String description) {
        _description = description;
    }

    /**
     * Returns the extended attributes.
     *
     * @return Extended attributes
     */
    public ExtendedAttributes getExtendedAttributes() {
        return _extendedAttributes;
    }

    /**
     * Sets the extended attributes.
     *
     * @param extendedAttributes Extended attributes.
     */
    public void setExtendedAttributes(ExtendedAttributes extendedAttributes) {
        _extendedAttributes = extendedAttributes;
    }

    /**
     * Get the tool type, for example an application or function.
     *
     * @return The ToolType
     */
    public ToolType getToolType() {
        return _toolType;
    }

    /**
     * Set the tool type.
     *
     * @param toolType The tool type
     */
    public void setToolType(ToolType toolType) {
        _toolType = toolType;
    }

    public String toString() {
        return "Tool[id=" + _id +
            ", description=" + _description +
            ", toolType=" + _toolType +
            ", extendedAttributes=" + _extendedAttributes + ']';
    }
}