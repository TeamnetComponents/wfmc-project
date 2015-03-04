package org.wfmc.util;

import org.apache.xmlbeans.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.wfmc.xpdl.XMLException;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
* Utilities for mapping XML Schema constructs to XPDL constructs.
*
* @author Adrian Price
*/
public final class SchemaUtils {
    // Maps XML Schema types to Java classes.
    // N.B. These mappings preserve precision, and create as exact a Java
    // representation as possible.  However, this is sometimes at the expense of
    // relational query-ability. For example, in order to preserve the precision
    // of xsd:decimal, it is mapped to java.lang.BigDecimal, which is currently
    // stored in the OBEATTRIBUTEINSTANCE.OBJVALUE column, which is a BLOB and
    // thus non-queryable.  Possible solutions to this problem could include:
    // - adding more columns to the OBEATTRIBUTEINSTANCE table.
    // Ultimately we're mapping to JDBC types anyway (discounting non-persistent
    // and serialized Java object instance repository implementations).
    private static final Object[][] _xsdToJava = {
        {XmlAnySimpleType.type, Object.class},
        {XmlDuration.type, String.class},
        {XmlDateTime.type, Date.class}, // JAX-RPC mapping: (Calendar.class)
        {XmlTime.type, Date.class},     // JAX-RPC mapping: (Calendar.class)
        {XmlDate.type, Date.class},     // JAX-RPC mapping: (Calendar.class)
        {XmlGYearMonth.type, String.class},
        {XmlGYear.type, String.class},
        {XmlGMonthDay.type, String.class},
        {XmlGDay.type, String.class},
        {XmlGMonth.type, String.class},
        {XmlBoolean.type, boolean.class},
        {XmlBase64Binary.type, byte[].class},
        {XmlHexBinary.type, byte[].class},
        {XmlFloat.type, Float.class},
        {XmlDouble.type, Double.class},
        {XmlAnyURI.type, String.class},
        {XmlQName.type, QName.class},
        {XmlNOTATION.type, null},
        {XmlString.type, String.class},
        {XmlNormalizedString.type, String.class},
        {XmlToken.type, String.class},
        {XmlLanguage.type, String.class},
        {XmlName.type, String.class},
        {XmlNCName.type, String.class},
        {XmlID.type, String.class},
        {XmlIDREF.type, String.class},
        {XmlIDREFS.type, String.class},
        {XmlENTITY.type, String.class},
        {XmlENTITIES.type, String.class},
        {XmlNMTOKEN.type, String.class},
        {XmlNMTOKENS.type, String.class},
        {XmlDecimal.type, BigDecimal.class},
        {XmlInteger.type, BigInteger.class},
        {XmlNonPositiveInteger.type, BigInteger.class},
        {XmlNegativeInteger.type, BigInteger.class},
        {XmlLong.type, Long.class},
        {XmlInt.type, Integer.class},
        {XmlShort.type, Short.class},
        {XmlByte.type, Byte.class},
        {XmlNonNegativeInteger.type, BigInteger.class},
        {XmlPositiveInteger.type, BigInteger.class},
        {XmlUnsignedLong.type, Long.class},
        {XmlUnsignedInt.type, Long.class},
        {XmlUnsignedShort.type, Short.class},
        {XmlUnsignedByte.type, Short.class},
    };
    private static final Map _xsdToJavaMap = new HashMap();
    private static EntityResolver _entityResolver;
    private static URIResolver _uriResolver;

    static {
        for (int i = 0; i < _xsdToJava.length; i++)
            _xsdToJavaMap.put(_xsdToJava[i][0], _xsdToJava[i][1]);
    }

    public static Class classForSchemaType(Element schemaElem, QName name)
        throws XMLException {

        try {
            if (schemaElem != null) {
                // First, note namespaces prefixes defined on the schema node.
                Set nsPrefixes = new HashSet();
                NamedNodeMap attrs = schemaElem.getAttributes();
                for (int i = 0, n = attrs.getLength(); i < n; i++) {
                    Node attr = attrs.item(i);
                    if (org.wfmc.util.W3CNames.XMLNS_NS_PREFIX.equals(attr.getPrefix()))
                        nsPrefixes.add(attr.getLocalName());
                }

                // Duplicate any extra accessible namespaces declared by
                // parents, because XMLBeans doesn't recurse above the schema
                // node when looking up namespaces.
                Node parent = schemaElem;
                while ((parent = parent.getParentNode()) instanceof Element) {
                    attrs = parent.getAttributes();
                    for (int i = 0, n = attrs.getLength(); i < n; i++) {
                        Node attr = attrs.item(i);
                        String nsPrefix = attr.getPrefix();
                        String localName = attr.getLocalName();

                        // If this is a namespace declaration that isn't already
                        // declared by the schema node, duplicate and add it.
                        if (org.wfmc.util.W3CNames.XMLNS_NS_PREFIX.equals(nsPrefix) &&
                            !nsPrefixes.contains(localName)) {

                            schemaElem.setAttributeNS(org.wfmc.util.W3CNames.XMLNS_NS_URI,
                                W3CNames.XMLNS_NS_PREFIX + ':' + localName,
                                attr.getNodeValue());
                            nsPrefixes.add(localName);
                        }
                    }
                }
            }

            // Parse the schema.
            return classForSchemaType(schemaElem == null
                ? null : XmlObject.Factory.parse(schemaElem), name);
        } catch (XmlException e) {
            throw new XMLException(e);
        }
    }

    public static Class classForSchemaType(InputStream in, QName name)
        throws XMLException {

        try {
            return classForSchemaType(
                in == null ? null : XmlObject.Factory.parse(in), name);
        } catch (IOException e) {
            throw new XMLException(e);
        } catch (XmlException e) {
            throw new XMLException(e);
        }
    }

    public static Class classForSchemaType(Reader in, QName name)
        throws XMLException {

        try {
            return classForSchemaType(
                in == null ? null : XmlObject.Factory.parse(in), name);
        } catch (IOException e) {
            throw new XMLException(e);
        } catch (XmlException e) {
            throw new XMLException(e);
        }
    }

    private static Class classForSchemaType(XmlObject xmlObject, QName name)
        throws XmlException {

        // Schema defined data types will be stored as DOM/XML documents unless
        // the schema type is simple and maps to a Java class.
        // N.B. Wondering about this... if we force XML data to be
        // represented as a DOM document, we won't be able to store or
        // manipulate it using any other type mapping system.  For now, let's
        // see how far we can get by relying on JAX-RPC-style type mappings.
        // Serializable JavaBeans are a much more efficient way to store data
        // from XML documents than DOM Documents or XML strings.
        Class javaClass = /*Object*/Document.class;

        // If supplied, assemble the parsed schema document into an XSD types
        // system.  Otherwise, just use the built-in XML Schema types system.
        SchemaTypeSystem bits = XmlBeans.getBuiltinTypeSystem();
        SchemaTypeSystem schemaTypeSystem = xmlObject == null ? bits
            : XmlBeans.compileXsd(new XmlObject[]{xmlObject}, bits, null);

        // If the schema defines any global types, either accept the first such
        // type or search for the requested type.
        SchemaType[] schemaTypes =
            schemaTypeSystem.globalTypes();
        if (schemaTypes.length > 0) {
            for (int i = 0; i < schemaTypes.length; i++) {
                SchemaType schemaType = schemaTypes[i];
                if (name == null || name.equals(schemaType.getName())) {
                    javaClass = Object.class;

                    // Determine the primitive type that underlies this type.
                    SchemaType baseType = schemaType;
                    while (true) {
                        int variety = baseType.getSimpleVariety();
                        switch (variety) {
                            case SchemaType.ATOMIC:
                                baseType = schemaType.getPrimitiveType();
                                break;
                            case SchemaType.LIST:
                                javaClass = List.class;
                                break;
                            case SchemaType.UNION:
                                baseType = schemaType.getUnionCommonBaseType();
                                continue;
                            default:
                                break;
                        }
                        break;
                    }

                    // Map a primitive schema type to a Java class.
                    if (baseType.isPrimitiveType())
                        javaClass = (Class)_xsdToJavaMap.get(baseType);

                    // We've identified the Java type, break out of the loop.
                    break;
                }
            }
        }

        return javaClass;
    }

    public static synchronized EntityResolver getEntityResolver() {
        if (_entityResolver == null) {
            _entityResolver = new DefaultHandler();
            //_logger.warn("Default EntityResolver created");
        }
        return _entityResolver;
    }

    public static synchronized void setEntityResolver(
        EntityResolver entityResolver) {

        if (_entityResolver != null && entityResolver != null)
            //_logger.warn("EntityResolver already set - ignoring call");
        {}
        else
            _entityResolver = entityResolver;
    }

    public static synchronized URIResolver getURIResolver() {
        if (_uriResolver == null) {
            _uriResolver = TransformerFactory.newInstance().getURIResolver();
            //_logger.warn("Default URIResolver created");
        }
        return _uriResolver;
    }

    public static synchronized void setURIResolver(URIResolver uriResolver) {
        if (_uriResolver != null && uriResolver != null)
            //_logger.warn("URIResolver already set - ignoring call");
        {}
        else
            _uriResolver = uriResolver;
    }

    public static void close(InputSource in) throws IOException {
        if (in != null) {
            InputStream is = in.getByteStream();
            if (is != null)
                is.close();
            Reader rdr = in.getCharacterStream();
            if (rdr != null)
                rdr.close();
        }
    }

    public static void close(Source src) throws IOException {
        if (src instanceof StreamSource) {
            StreamSource ss = (StreamSource)src;
            InputStream in = ss.getInputStream();
            if (in != null)
                in.close();
            Reader rdr = ss.getReader();
            if (rdr != null)
                rdr.close();
        }
    }

    private SchemaUtils() {
    }
}