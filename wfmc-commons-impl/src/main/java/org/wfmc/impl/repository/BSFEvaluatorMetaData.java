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

import org.xml.sax.EntityResolver;

/**
 * @author Adrian Price
 */
public class BSFEvaluatorMetaData extends EvaluatorMetaData {
    private static final long serialVersionUID = -5220262874819597930L;
    private static final String IMPL_CLASS =
        "org.obe.runtime.evaluator.BSFEvaluator";
    private static final String[] IMPL_CTOR_SIG = {
        "java.lang.String"
    };

    private String _language;

    public BSFEvaluatorMetaData() {
    }

    public BSFEvaluatorMetaData(String contentType, String displayName,
        String description, String docUrl, String author, String language,
        String grammar) {

        super(contentType, displayName, description, docUrl, author, true,
            grammar);
        _language = language;
    }

    public Object createInstance(EntityResolver entityResolver)
        throws RepositoryException {

        return createInstance(new Object[]{getLanguage()});
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
            ((BSFEvaluatorMetaData)_type).getLanguage();
    }

    public void setLanguage(String language) {
        _language = language;
    }

    public String toString() {
        return "BSFEvalautor[_id='" + _id + '\'' +
            ",_displayName='" + _displayName + '\'' +
            ",_description='" + _description + '\'' +
            ",_docUrl='" + _docUrl + '\'' +
            ",_author='" + _author + '\'' +
            ",_language='" + _language + '\'' +
            ",_type=" + _type +
            ']';
    }
}