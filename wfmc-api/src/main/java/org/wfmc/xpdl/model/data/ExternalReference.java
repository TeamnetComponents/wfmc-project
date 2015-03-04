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
import org.w3c.dom.Element;
import org.wfmc.xpdl.XMLException;
import org.wfmc.util.ClassUtils;
//import org.wfmc.util.SchemaUtils;
import org.wfmc.xpdl.XPDLRuntimeException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;

/**
* Reference to an external entity as defined in XPDL 1.0b1.
*
* @author Adrian Price
*/
public final class ExternalReference implements Type, Serializable {

    private String _location;

    public ExternalReference() {
    }

    public ExternalReference(String _location) {
        this._location = _location;
    }

    @Override
    public int value() {
        return 0;
    }

    @Override
    public boolean isAssignableFrom(Type fromType) {
        return false;
    }

    @Override
    public Type getImpliedType() {
        return null;
    }

//    private static final long serialVersionUID = -5052512021996360394L;
//    private static final String WSDL_SCHEMA_XPATH =
//            "/wsdl:definitions/wsdl:types/xsd:schema[@targetNamespace=$namespace]";
//    private static final DocumentBuilderFactory _docBuilderFactory =
//            DocumentBuilderFactory.newInstance();
//
//    private String _location;
//    private String _xref;
//    private String _namespace;
//    private transient QName _qname;
//    private transient Type _xpdlType;
//
//    static {
//        _docBuilderFactory.setNamespaceAware(true);
//        _docBuilderFactory.setIgnoringComments(true);
//        _docBuilderFactory.setValidating(false);
//    }
//
//    public ExternalReference() {
//    }
//
//    /**
//     * Construct a new ExternalReference.
//     *
//     * @param location The location
//     */
//    public ExternalReference(String location) {
//        setLocation(location);
//    }
//
//    /**
//     * Construct a new ExternalReference.
//     *
//     * @param location  The location
//     * @param xref
//     * @param namespace
//     */
//    public ExternalReference(String location, String xref, String namespace) {
//        _location = location;
//        _xref = xref;
//        _namespace = namespace;
//    }
//
//    public int value() {
//        return EXTERNAL_REFERENCE_TYPE;
//    }
//
//    /**
//     * Get the location URI.
//     *
//     * @return The location URI
//     */
//    public String getLocation() {
//        return _location;
//    }
//
//    /**
//     * Set the location URI.
//     *
//     * @param location The new location URI
//     */
//    public void setLocation(String location) {
//        if (location == null)
//            throw new IllegalArgumentException("location cannot be null");
//        _location = location;
//    }
//
//    /**
//     * Get the identity of the entity in the external reference.  This value is
//     * optional and this method may return null.
//     *
//     * @return The entity identity
//     */
//    public String getXref() {
//        return _xref;
//    }
//
//    /**
//     * Set the identity of the entity in the external reference.  This value is
//     * optional and can be set to null.
//     *
//     * @param xref The entity identity
//     */
//    public void setXref(String xref) {
//        _xref = xref;
//        _qname = null;
//    }
//
//    /**
//     * Get the namespace of the external reference.  The value is optional and
//     * this method may return null.
//     *
//     * @return The namespace
//     */
//    public String getNamespace() {
//        return _namespace;
//    }
//
//    /**
//     * Set the namespace of the external reference.  This value is optional and
//     * can be set to null.
//     *
//     * @param namespace The namespace
//     */
//    public void setNamespace(String namespace) {
//        _namespace = namespace;
//        _qname = null;
//    }
//
//    public QName getQName() {
//        if (_qname == null && _xref != null)
//            _qname = new QName(_namespace, _xref);
//        return _qname;
//    }
//
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        if (!(obj instanceof ExternalReference))
//            return false;
//
//        ExternalReference externalReference = (ExternalReference) obj;
//
//        if (!_location.equals(externalReference._location))
//            return false;
//        if (_namespace != null
//                ? !_namespace.equals(externalReference._namespace)
//                : externalReference._namespace != null) {
//
//            return false;
//        }
//        return !(_xref != null
//                ? !_xref.equals(externalReference._xref)
//                : externalReference._xref != null);
//    }
//
//    public int hashCode() {
//        int result;
//        result = _location.hashCode();
//        result = 29 * result + (_xref != null ? _xref.hashCode() : 0);
//        result = 29 * result + (_namespace != null ? _namespace.hashCode() : 0);
//        return result;
//    }
//
//    public Type getImpliedType() {
//        if (_xpdlType == null) {
//            try {
//                // TODO: eliminate case sensitivity.
//                if (_location.startsWith("java:")) {
//                    resolveJavaReference();
//                } else if (_location.endsWith("?wsdl") ||
//                        _location.endsWith(".wsdl")) {
//
//                    resolveWSDLReference();
//                } else if (_location.endsWith(".xsd")) {
//                    resolveSchemaReference();
//                } else {
//                    _xpdlType = this;
//                }
//            } catch (ClassNotFoundException e) {
//                throw new XPDLRuntimeException(e);
//            } catch (IOException e) {
//                throw new XPDLRuntimeException(e);
////            } catch (JaxenException e) {
////                throw new XPDLRuntimeException(e);
//            } catch (ParserConfigurationException e) {
//                throw new XPDLRuntimeException(e);
//            } catch (SAXException e) {
//                throw new XPDLRuntimeException(e);
//            } catch (TransformerException e) {
//                throw new XPDLRuntimeException(e);
//            } catch (XMLException e) {
//                throw new XPDLRuntimeException(e);
//            }
//        }
//        return _xpdlType;
//    }
//
//    private void resolveJavaReference() throws ClassNotFoundException {
//        Class javaClass = ClassUtils.classForName(_location.substring(5));
//        Type type = DataTypes.dataTypeForClass(javaClass).getType();
//        _xpdlType = type instanceof ExternalReference ? this : type;
//    }
//
//    private void resolveSchemaReference()
//            throws IOException, XMLException, TransformerException {
//
//        // Map the schema type to a Java class.
//        Source src = null;
//        Class javaType = null;
//        try {
//            QName typeName = getQName();
//            src = SchemaUtils.getURIResolver().resolve(_location, null);
//            if (src == null) {
//                throw new XMLException("Could not resolve reference '" +
//                        _location + '\'');
//            }
//
//            if (src instanceof DOMSource) {
//                DOMSource domSource = (DOMSource) src;
//                javaType = SchemaUtils.classForSchemaType(
//                        (Element) domSource.getNode(), typeName);
//            } else if (src instanceof StreamSource) {
//                StreamSource ss = (StreamSource) src;
//                InputStream in = ss.getInputStream();
//                if (in != null) {
//                    javaType = SchemaUtils.classForSchemaType(in, typeName);
//                } else {
//                    Reader rdr = ss.getReader();
//                    if (rdr != null)
//                        javaType =
//                                SchemaUtils.classForSchemaType(rdr, typeName);
//                }
//            }
//        } finally {
//            SchemaUtils.close(src);
//        }
//
//        // Map a non-XML Java class to an XPDL data type.
//        _xpdlType = DataTypes.dataTypeForClass(javaType).getType();
//    }
//
//    private void resolveWSDLReference() throws SAXException, IOException,
//            ParserConfigurationException, XMLException //, JaxenException
//    {
//
//        // Retrieve the WSDL and parse it into a DOM document.
//        Document wsdlDoc;
//        InputSource in = null;
//        try {
//            in = SchemaUtils.getEntityResolver().resolveEntity(null, _location);
//            wsdlDoc = _docBuilderFactory.newDocumentBuilder().parse(in);
//        } finally {
//            SchemaUtils.close(in);
//        }
//
////        // Use XPath to select the appropriate schema element.
////        XPath xpath = new DOMXPath(WSDL_SCHEMA_XPATH);
////        xpath.addNamespace("wsdl", W3CNames.WSDL_NS_URI);
////        xpath.addNamespace("xsd", W3CNames.XSD_NS_URI);
////        SimpleVariableContext varCtx = new SimpleVariableContext();
////        varCtx.setVariableValue(XPDLNames.NAMESPACE, _namespace);
////        xpath.setVariableContext(varCtx);
////        Element elem = (Element)xpath.selectSingleNode(wsdlDoc);
//        Element elem = null;
//
//
//        // Examine the schema to determine the Java class which matches the type
//        // referenced by this external reference.
//        if (elem == null) {
//            throw new XMLException("WSDL file '" + _location +
//                    "' does not provide a schema for the target namespace '" +
//                    _namespace + '\'');
//        }
//
//        // Map the XML Schema type to a Java class.
//        Class javaType = SchemaUtils.classForSchemaType(elem, getQName());
//
//        // Map a non-XML Java class to an XPDL data type.
//        _xpdlType = DataTypes.dataTypeForClass(javaType).getType();
//    }
//
//    public boolean isAssignableFrom(Type fromType) {
//        return equals(fromType) ||
//                DataTypes.classForType(getImpliedType()).isAssignableFrom(
//                        DataTypes.classForType(fromType));
//    }
//
//    public String toString() {
//        // General form is: <location>[#[{<namespace>}]<xref>]
//        String s;
//        if (_namespace == null && _xref == null) {
//            s = _location;
//        } else {
//            StringBuffer sb = new StringBuffer(_location).append('#');
//            if (_namespace != null)
//                sb.append('{').append(_namespace).append('}');
//            if (_xref != null)
//                sb.append(_xref);
//            s = sb.toString();
//        }
//        return s;
//    }
}