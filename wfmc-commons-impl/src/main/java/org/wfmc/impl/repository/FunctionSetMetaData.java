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
 * Describes a user-defined plug-in evaluator function set.
 *
 * @author Adrian Price
 */
public final class FunctionSetMetaData extends ImplClassMetaData {
    private static final long serialVersionUID = -7900131126653741780L;
    private String _nsPrefix;
    private String _nsURI;

    /**
     * Constructs function set meta-data.
     */
    public FunctionSetMetaData() {
    }

    /**
     * Constructs function meta-data. Function set implementation classes must
     * have a public no-args ctor.
     *
     * @param id          The function set ID.
     * @param displayName The display name.
     * @param description Textual description of this function set.
     * @param docUrl      URL for documentation.
     * @param author      Author's name.
     * @param implClass   The fully qualified name of the function set
     *                    implementation class.
     * @param nsPrefix    The namespace prefix to use.
     * @param nsURI       The namespace URI to use.
     */
    public FunctionSetMetaData(String id, String displayName,
        String description, String docUrl, String author, String implClass,
        String nsPrefix, String nsURI) {

        super(id, displayName, description, docUrl, author, true, implClass,
            NO_ARGS_SIG);
        _nsPrefix = nsPrefix;
        _nsURI = nsURI;
    }

    /**
     * Returns an instance of the class that implements the extension
     * functions.
     *
     * @param entityResolver
     * @return Implementation class.
     * @throws RepositoryException if the class could not be found.
     */
    public Object createInstance(EntityResolver entityResolver)
        throws RepositoryException {

        return createInstance(EMPTY_ARGS);
    }

    public String getNsPrefix() {
        return _nsPrefix != null ? _nsPrefix
            : _type == null || !allowInheritance ? null :
            ((FunctionSetMetaData)_type).getNsPrefix();
    }

    public void setNsPrefix(String nsPrefix) {
        _nsPrefix = nsPrefix;
    }

    public String getNsURI() {
        return _nsURI != null ? _nsURI
            : _type == null || !allowInheritance ? null :
            ((FunctionSetMetaData)_type).getNsURI();
    }

    public void setNsURI(String nsURI) {
        _nsURI = nsURI;
    }

    public String toString() {
        return "FunctionSetMetaData[_id='" + _id + '\'' +
            ",_displayName='" + _displayName + '\'' +
            ",_description='" + _description + '\'' +
            ",_docUrl='" + _docUrl + '\'' +
            ",_author='" + _author + '\'' +
            ",_implClass='" + getImplClass() + '\'' +
            ",_nsPrefix='" + _nsPrefix + '\'' +
            ",_nsURI='" + _nsURI + '\'' +
            ",_type=" + _type +
            ']';
    }
}