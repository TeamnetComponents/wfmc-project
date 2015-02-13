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
 * @author Adrian Price
 */
public final class NativeExecutableMetaData extends ToolAgentMetaData {
    private static final long serialVersionUID = 7650752795061157140L;
    private static final String IMPL_CLASS =
        "org.obe.runtime.tool.NativeExecutable";
    private static final String[] IMPL_CTOR_SIG = {
        NativeExecutableMetaData.class.getName()
    };
    private String[] _arg;
    private String[] _env;
    private String _dir;

    public NativeExecutableMetaData() {
    }

    public NativeExecutableMetaData(String id, String displayName,
        String description, String docUrl, String author, String[] args,
        String[] env, String dir) {

        super(id, displayName, description, docUrl, author, true);
        _arg = args;
        _env = env;
        _dir = dir;
    }

    public ToolAgentMetaData introspect(ExternalReference extRef,
        EntityResolver entityResolver) {

        // TODO: Introspect file name for executability.
        return null;
    }

    protected String getImplClass() {
        return IMPL_CLASS;
    }

    protected String[] getImplCtorSig() {
        return IMPL_CTOR_SIG;
    }

    public String[] getArg() {
        return _arg != null ? _arg
            : _type == null || !allowInheritance ? null :
            ((NativeExecutableMetaData)_type).getArg();
    }

    public void setArg(String[] arg) {
        _arg = arg;
    }

    public String[] getEnv() {
        return _env != null ? _env
            : _type == null || !allowInheritance ? null :
            ((NativeExecutableMetaData)_type).getEnv();
    }

    public void setEnv(String[] env) {
        _env = env;
    }

    public String getDir() {
        return _dir != null ? _dir
            : _type == null || !allowInheritance ? null :
            ((NativeExecutableMetaData)_type).getDir();
    }

    public void setDir(String dir) {
        _dir = dir;
    }

    public String toString() {
        return "NativeExecutableMetaData[id='" + _id +
            "', implClass='" + IMPL_CLASS +
            "', implCtorSig=" +
            (IMPL_CTOR_SIG == null ? null
                :
                "length:" + IMPL_CTOR_SIG.length +
                    Arrays.asList(IMPL_CTOR_SIG)) +
            ", displayName='" + _displayName +
            "', description='" + _description +
            "', docUrl='" + _docUrl +
            "', author='" + _author +
            "', threadsafe=" + _threadsafe +
            "', args=" +
            (_arg == null ? null
                : "length:" + _arg.length + Arrays.asList(_arg)) +
            "', env=" +
            (_env == null ? null
                : "length:" + _env.length + Arrays.asList(_env)) +
            ",dir=" + _dir +
            ",type=" + _type +
            ']';
    }
}