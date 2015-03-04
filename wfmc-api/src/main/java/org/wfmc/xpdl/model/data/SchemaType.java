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

package org.wfmc.xpdl.model.data;

import org.w3c.dom.Document;
import org.wfmc.xpdl.XMLException;
import org.wfmc.util.SchemaUtils;
import org.wfmc.util.W3CNames;

import java.io.StringReader;

/**
 * A schema type refers to a data type that is defined by an inline XML schema.
 *
 * @author Adrian Price
 */
public final class SchemaType extends XMLFragment implements Type {
    private static final long serialVersionUID = 6285607010686717240L;

    private transient Type _xpdlType;

    /**
     * Constructs a new SchemaType object.
     */
    public SchemaType() {
    }

    public SchemaType(String text) throws XMLException {
        super(text);
    }

    public SchemaType(Document document) throws XMLException {
        super(document);
    }

    protected String getDocumentElementName() {
        return W3CNames.XSD_SCHEMA;
    }

    protected String getDocumentElementNamespaceURI() {
        return W3CNames.XSD_NS_URI;
    }

    public int value() {
        return SCHEMA_TYPE;
    }

    public Type getImpliedType() {
        if (_xpdlType == null) {
            if (getText() != null) {
                try {
                    // Map the schema type to a Java class.
                    Class javaType = SchemaUtils.classForSchemaType(
                        new StringReader(getText()), null);

                    // Map a non-XML Java class to an XPDL data type.
                    _xpdlType = javaType == /*Document*/Object.class
                        ? this : DataTypes.dataTypeForClass(javaType).getType();
                } catch (XMLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                _xpdlType = this;
            }
        }
        return _xpdlType;
    }

    public boolean isAssignableFrom(Type fromType) {
        // Determining compatibility between two schema types is hard, so for
        // now we'll settle for textual, not semantic, equality.  If this schema
        // type actually implies some other type, determine assignability on
        // the basis of the implied type.
        return equals(fromType) ||
            getImpliedType() != this && _xpdlType.isAssignableFrom(fromType);
    }
}