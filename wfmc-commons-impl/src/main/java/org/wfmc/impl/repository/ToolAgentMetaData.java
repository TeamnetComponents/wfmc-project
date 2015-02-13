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

package org.wfmc.impl.repository;


import org.wfmc.server.core.xpdl.model.data.ExternalReference;
import org.wfmc.server.core.xpdl.model.data.FormalParameter;
import org.xml.sax.EntityResolver;

import java.util.Arrays;

/**
 * Describes a user-defined tool agent.  Different tool types extend this class
 * with the additional meta data required to instantiate the tool agent itself.
 *
 * @author Adrian Price
 */
public abstract class ToolAgentMetaData extends AbstractMetaData {
    private static final long serialVersionUID = -429157659267364196L;
    protected FormalParameter[] _formalParameters;

    protected ToolAgentMetaData() {
    }

    /**
     * Constructs tool meta-data.
     *
     * @param id          The tool ID.
     * @param displayName The display name.
     * @param description Textual description of this tool.
     * @param docUrl      URL for documentation.
     * @param author      Author's name.
     * @param threadsafe  <code>true</code> if instances of
     *                    <code>impClass</code>
     */
    protected ToolAgentMetaData(String id, String displayName,
        String description,
        String docUrl, String author, boolean threadsafe) {

        super(id, displayName, description, docUrl, author, threadsafe);
    }

    public Object createInstance(EntityResolver entityResolver)
        throws RepositoryException {

        // The design pattern for stateless tool agent instantiation requires a
        // public constructor that accepts the appropriate ToolAgentMetaData subclass.
        return createInstance(new Object[]{this});
    }

    public Object createInstance(EntityResolver entityResolver, Object args)
        throws RepositoryException {

        // The design pattern for stateful tool agent instantiation requires a
        // public constructor that accepts the appropriate ToolAgentMetaData subclass
        // and (optionally) the parameters to pass to the tool agent.
        return createInstance(getImplCtorSig().length == 2
            ? new Object[]{this, args} : new Object[]{this});
    }

    public FormalParameter[] getFormalParameter() {
        return _formalParameters != null ? _formalParameters
            : _type == null || !allowInheritance ? null :
            ((ToolAgentMetaData)_type).getFormalParameter();
    }

    public void setFormalParameter(FormalParameter[] formalParameters) {
        _formalParameters = formalParameters;
    }

    /**
     * Provides an opportunity for metadata classes to perform post-load
     * initialization. The <code>ToolAgentFactory</code> calls this method on
     * each <code>ToolAgentMetaData</code> object registered with it as part
     * of the final phase of initialization. <em>Client code should not call
     * this method.</em>
     */
    public void init() {
    }

    /**
     * Introspects a reference to an externally defined tool or service.  If the
     * associated tool agent type can handle the reference, the method returns
     * the meta-data required to instantiate such a tool agent.
     *
     * @param extRef         The external tool reference.
     * @param entityResolver
     * @return Configured tool meta-data, or <code>null</code> if this tool type
     *         cannot handle this reference.
     */
    public abstract ToolAgentMetaData introspect(ExternalReference extRef,
        EntityResolver entityResolver) throws RepositoryException;

    public String toString() {
        String className = getClass().getName();
        className = className.substring(className.lastIndexOf('.') + 1);
        return className +
            "[id='" + _id +
            "',displayName='" + _displayName +
            "',description='" + _description +
            "',docUrl='" + _docUrl +
            "',author='" + _author +
            "',threadsafe=" + _threadsafe +
            ",type=" + _type +
            ",formalParameters=" + (_formalParameters == null ? null :
            Arrays.asList(_formalParameters)) +
            ']';
    }
}