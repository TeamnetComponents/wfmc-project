package org.wfmc.util;

/**
 * Provides string constants for W3C names and URIs.
 *
 * @author Adrian Price
 */
public interface W3CNames {
    /** XML SOAP binding namespace prefix. */
    String SOAP_NS_PREFIX = "soap";

    /** XML SOAP binding namespace URI. */
    String SOAP_NS_URI = "http://schemas.xmlsoap.org/soap/envelope/";

    /** XML SOAP binding namespace prefix. */
    String SOAPBIND_NS_PREFIX = "soapbind";

    /** XML SOAP binding namespace URI. */
    String SOAPBIND_NS_URI = "http://schemas.xmlsoap.org/wsdl/soap/";

    /** XML SOAP encoding namespace prefix. */
    String SOAPENC_NS_PREFIX = "soapenc";

    /** XML SOAP encoding namespace URI. */
    String SOAPENC_NS_URI = "http://schemas.xmlsoap.org/soap/encoding/";

    /** UDDI namespace prefix. */
    String UDDI_NS_PREFIX = "uddi";

    /** UDDI namespace URI. */
    String UDDI_NS_URI = "urn:uddi-org:api_v2";

    /** WSDL namespace prefix. */
    String WSDL_NS_PREFIX = "wsdl";

    /** WSDL namespace URI. */
    String WSDL_NS_URI = "http://schemas.xmlsoap.org/wsdl/";

    /** WS-I namespace prefix. */
    String WSI_NS_PREFIX = "wsi";

    /** WSDL namespace URI. */
    String WSI_NS_URI = "http://ws-i.org/schemas/conformanceClaim/";

    /** XML namespace prefix for XML declarations. */
    String XML_NS_PREFIX = "xml";

    /** XML namespace prefix for XML declarations. */
    String XML_NS_URI = "http://www.w3.org/XML/1998/namespace";

    /** XML namespace prefix for XML Namespace declarations. */
    String XMLNS_NS_PREFIX = "xmlns";

    /** XML namespace URI for XML Namespace declarations. */
    String XMLNS_NS_URI = "http://www.w3.org/2000/xmlns/";

    /** XML namespace prefix to use for XSD elements. */
    String XSD_NS_PREFIX = "xsd";

    /** XML Schema namespace URI. */
    String XSD_NS_URI = "http://www.w3.org/2001/XMLSchema";

    /** XML Schema document element tag name. */
    String XSD_SCHEMA = "schema";

    /** XML namespace prefix to use for XSI elements. */
    String XSI_NS_PREFIX = "xsi";

    /** XML Schema-instance namespace URI. */
    String XSI_URI = "http://www.w3.org/2001/XMLSchema-instance";

    /** XSI schema location attribute tag name. */
    String XSI_SCHEMA_LOCATION = "schemaLocation";

    /** XSI 'no-namespace' schema location attribute tag name. */
    String XSI_NO_NS_SCHEMA_LOCATION = "noNamespaceSchemaLocation";

    /** XML namespace prefix to use for XSL elements. */
    String XSL_NS_PREFIX = "xsl";

    /** XML Stylesheet namespace URI. */
    String XSL_URI = "http://www.w3.org/1999/XSL/Transform";
}