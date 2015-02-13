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
import java.util.Comparator;

/**
 * @author Adrian Price
 */
public final class BSFScriptMetaData extends ScriptMetaData {
    private static final long serialVersionUID = -1968395429581029273L;
    private static final String IMPL_CLASS = "org.obe.runtime.tool.BSFScript";
    private static final String[] IMPL_CTOR_SIG = {
        BSFScriptMetaData.class.getName()
    };
    // TODO: Generate MIME-type mappings dynamically (somehow?).
    private static final String[][] _fileExtToMimeType = {
        // fileExt, contentType, language
        {"bbas", "text/x-beanbasic", "beanbasic"},
        {"bml", "text/x-bml", "bml"},
        {"java", "text/x-java", "java"},
        {"jc", "text/x-javaclass", "javaclass"},
        {"jcl", "text/x-jacl", "jacl"},
        {"js", "application/javascript", "javascript"},
        {"jscr", "text/x-jscript", "jscript"},
        {"ls", "text/x-lotusscript", "lotusscript"},
        {"pl", "text/x-perl", "perl"},
        {"pls", "text/x-perlscript", "perlscript"},
        {"pn", "text/x-pnuts", "pnuts"},
        {"py", "text/x-python", "jpython"},
        {"rex", "text/x-netrexx", "netrexx"},
        {"vbs", "text/x-vbscript", "vbscript"},
        {"xpath", "text/x-xpath", "xpath"},
        {"xsl", "text/xml/x-xslt", "xslt"},
        {"xslt", "text/xml/x-xslt", "xslt"},
    };
    private static final Comparator _fileExtComparator = new Comparator() {
        public int compare(Object o1, Object o2) {
            return o1 instanceof String && o2 instanceof String ?
                ((String[])o1)[0].compareTo(((String[])o2)[0]) : -1;
        }
    };
    private String _language;

    public BSFScriptMetaData() {
    }

    public BSFScriptMetaData(String id, String displayName,
        String description, String docUrl, String author, String language,
        String script, boolean file) {

        super(id, displayName, description, docUrl, author, true, script, file);
        _language = language;
    }

    public ToolAgentMetaData introspect(ExternalReference extRef,
        EntityResolver entityResolver) {

        BSFScriptMetaData metaData = null;
        String location = extRef.getLocation();
        int index = location.lastIndexOf('.');
        if (index != -1) {
            String ext = location.substring(index + 1);
            index = Arrays.binarySearch(_fileExtToMimeType, ext,
                _fileExtComparator);
            if (index > -1) {
                String[] data = _fileExtToMimeType[index];
                metaData = new BSFScriptMetaData(data[1], null, null, null,
                    null, data[2], location, true);
            }
        }
        return metaData;
    }

    protected String getImplClass() {
        return IMPL_CLASS;
    }

    protected String[] getImplCtorSig() {
        return IMPL_CTOR_SIG;
    }

    public String getLanguage() {
        return _language != null ? _language
            : _type == null || !allowInheritance ? null :
            ((BSFScriptMetaData)_type).getLanguage();
    }

    public void setLanguage(String language) {
        _language = language;
    }
}