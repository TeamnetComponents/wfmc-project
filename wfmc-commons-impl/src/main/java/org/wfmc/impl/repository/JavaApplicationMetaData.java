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
import org.xml.sax.EntityResolver;

import java.util.Arrays;

/**
 * Describes a ToolAgent that invokes the main method of a Java class.
 *
 * @author Adrian Price
 */
public final class JavaApplicationMetaData extends JavaMethodMetaData {
    private static final long serialVersionUID = 6746440879038462935L;
    private static final String IMPL_CLASS =
        "org.obe.runtime.tool.JavaApplication";
    private static final String[] IMPL_CTOR_SIG = {
        JavaApplicationMetaData.class.getName()
    };
    private static final String MAIN = "main";
    private static final String[] MAIN_SIG = {
        "java.lang.String[]"
    };
    private boolean _fork;
    private String _href;

    public JavaApplicationMetaData() {
    }

    public JavaApplicationMetaData(String id, String displayName,
        String description,
        String docUrl, String author, String className, boolean fork) {

        super(id, displayName, description, docUrl, author, className, MAIN,
            MAIN_SIG);
        _fork = fork;
    }

    public ToolAgentMetaData introspect(ExternalReference extRef,
        EntityResolver entityResolver) {

        // TODO: Move Class introspection code here.
        return null;
    }

    public String getMethod() {
        return MAIN;
    }

    public void setMethod(String method) {
        throw new UnsupportedOperationException("method property is read-only");
    }

    public String[] getMethodSig() {
        return MAIN_SIG;
    }

    public void setMethodSig(String[] methodSig) {
        throw new UnsupportedOperationException(
            "methodSig property is read-only");
    }

    protected String getImplClass() {
        return IMPL_CLASS;
    }

    protected String[] getImplCtorSig() {
        return IMPL_CTOR_SIG;
    }

    public boolean isFork() {
        return _fork;
    }

    public boolean getFork() {
        return _fork;
    }

    public void setFork(boolean fork) {
        _fork = fork;
    }

    public String getHref() {
        return _href != null ? _href
            : _type == null || !allowInheritance ? null :
            ((JavaApplicationMetaData)_type).getHref();
    }

    public void setHref(String href) {
        _href = href;
    }

    public String toString() {
        return "JavaApplicationMetaData[id='" + _id +
            "',implClass='" + IMPL_CLASS +
            "',implCtorSig=" +
            (IMPL_CTOR_SIG == null ? null : Arrays.asList(IMPL_CTOR_SIG)) +
            ",displayName='" + _displayName +
            "',description='" + _description +
            "',docUrl='" + _docUrl +
            "',author='" + _author +
            "',threadsafe=" + _threadsafe +
            "',className=" + _className +
            "',fork=" + _fork +
            ",jnlpUrl='" + _href +
            "',type=" + _type +
            ']';
    }
}