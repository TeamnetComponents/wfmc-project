package org.wfmc.xpdl.model.data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.wfmc.xpdl.XMLException;
import org.wfmc.util.AbstractBean;
import org.wfmc.util.W3CNames;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * An abstract class that represents a fragment of an XML document.
 *
 * @author Adrian Price
 */
public abstract class XMLFragment extends AbstractBean {
    private static final long serialVersionUID = 3754638228659834894L;
    public static final int MAX_TOSTRING_TEXT_LEN = 50;
    private static final ThreadLocal _builderFactory = new ThreadLocal();

    private String _text;
    private transient Document _document;

    private static DocumentBuilder getDocumentBuilder()
        throws ParserConfigurationException {

        DocumentBuilderFactory dbf = (DocumentBuilderFactory)
            _builderFactory.get();
        if (dbf == null) {
            dbf = DocumentBuilderFactory.newInstance();
            _builderFactory.set(dbf);
        }
        return dbf.newDocumentBuilder();
    }

    private static Transformer getTransformer()
        throws TransformerConfigurationException {

        return TransformerFactory.newInstance().newTransformer();
    }

    protected XMLFragment() {
    }

    protected XMLFragment(String text) throws XMLException {
        setText(text);
    }

    protected XMLFragment(Document document) throws XMLException {
        setDocument(document);
    }

    /**
     * Returns the unqualified name of the required document element.
     *
     * @return Unqualified element name.
     */
    protected abstract String getDocumentElementName();

    /**
     * Returns the required document element namespace URI.
     *
     * @return Required namespace URI.
     */
    protected abstract String getDocumentElementNamespaceURI();

    public final String getText() {
        return _text;
    }

    public final void setText(String text) throws XMLException {
        _text = text;
        _document = null;
        try {
            getDocument();
            checkDocument(_document);
        } catch (XMLException e) {
            _text = null;
            _document = null;
            throw e;
        }
    }

    public final Document getDocument() throws XMLException {
        if (_document == null && _text != null) {
            try {
                _document = getDocumentBuilder().parse(new InputSource(
                    new StringReader(_text)));
            } catch (ParserConfigurationException e) {
                throw new XMLException(e);
            } catch (SAXException e) {
                throw new XMLException(e);
            } catch (IOException e) {
                throw new XMLException(e);
            }
        }
        return _document;
    }

    public final void setDocument(Document document) throws XMLException {
        try {
            checkDocument(document);
            if (document == null) {
                _text = null;
            } else {
                StringWriter out = new StringWriter();
                getTransformer().transform(new DOMSource(document),
                    new StreamResult(out));
                _text = out.toString();
            }
            _document = document;
        } catch (TransformerConfigurationException e) {
            throw new XMLException(e);
        } catch (TransformerException e) {
            throw new XMLException(e);
        }
    }

    // Basic check that document element is correctly named.
    private void checkDocument(Document document) throws XMLException {
        if (document == null)
            return;

        // Get actual document element name.
        Element docElem = document.getDocumentElement();
        String actName = docElem.getNodeName();

        // Get required document element name and namespace URI.
        String reqNSURI = getDocumentElementNamespaceURI();
        String reqName = getDocumentElementName();

        // Search namespace attributes for the required URI.
        NamedNodeMap attrs = docElem.getAttributes();
        for (int i = 0, n = attrs.getLength(); i < n; i++) {
            Node attr = attrs.item(i);
            // If this is the required URI...
            if (attr.getNodeValue().equals(reqNSURI)) {
                String attrName = attr.getNodeName();
                // and it really is a namespace attribute...
                if (attrName.startsWith(W3CNames.XMLNS_NS_PREFIX)) {
                    // extract the NS prefix (if any) and check doc. elem. name.
                    // (Use region matching to avoid object allocations.)
                    int index = attrName.length() - 6;
                    if (index == -1 && actName.equals(reqName) ||
                        actName.regionMatches(0, attrName, 6, index) &&
                            actName.charAt(index) == ':' &&
                            actName.regionMatches(index + 1, reqName, 0,
                                reqName.length())) {

                        return;
                    }
                }
            }
        }
        throw new XMLException(new IllegalArgumentException(
            "Document element must be '" + reqName +
                "', in the namespace URI: " + reqNSURI));
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof XMLFragment))
            return false;

        XMLFragment xmlFragment = (XMLFragment)obj;
        return !(_text != null ?
            !_text.equals(xmlFragment._text) : xmlFragment._text != null);
    }

    public int hashCode() {
        return _text != null ? _text.hashCode() : 0;
    }

    public final String toString() {
        String className = getClass().getName();
        return className.substring(className.lastIndexOf('.') + 1);
    }
}