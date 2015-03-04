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

package org.wfmc.xpdl.serializer.dom4j;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.io.SAXReader;
import org.wfmc.util.DateUtilities;
import org.wfmc.xpdl.model.misc.Duration;
//import org.wfmc.xpdl.parser.dom4j.DOM4JNames;

import java.io.StringReader;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * Dom4J XPDL Serializer utility class.
 *
 * @author Anthony Eden
 * @author Adrian Price
 */
final class Util {
    /**
     * Adds a child element with the specific name to the given parent element
     * and returns the child element.  This method will use the namespace of the
     * parent element for the child element's namespace.
     *
     * @param parent The parent element
     * @param name   The new child element name
     * @return The child element
     */
    static Element addElement(Element parent, String name) {
//        Namespace namespace = parent.getNamespace();
//        return namespace == Namespace.NO_NAMESPACE
//            ? parent.addElement(name)
//            : parent.addElement(new QName(name, namespace));
        return parent.addElement(name);
    }

    /**
     * Adds a child element with the specific name and the given value to the
     * given parent element and returns the child element.  This method will use
     * the namespace of the parent element for the child element's namespace.
     *
     * @param parent The parent element
     * @param name   The new child element name
     * @param value  The value
     * @return The child element
     */
    static Element addElement(Element parent, String name, Date value) {
        return addElement(parent, name, value, null);
    }

    /**
     * Adds a child element with the specific name and the given value to the
     * given parent element and returns the child element.  This method will use
     * the namespace of the parent element for the child element's namespace.
     * If the given value is null then the default value is used.  If the value
     * is null then this method will not add the child element and will return
     * null.
     *
     * @param parent       The parent element
     * @param name         The new child element name
     * @param value        The value
     * @param defaultValue The default value (if the value is null)
     * @return The child element
     */
    static Element addElement(Element parent, String name, Date value,
        Date defaultValue) {

        Element child = null;

        if (value == null)
            value = defaultValue;

        if (value != null) {
            child = addElement(parent, name);
            child.addText(DateUtilities.getInstance().format(value));
        }

        return child;
    }

    /**
     * Adds a child element with the specific name and the given value to the
     * given parent element and returns the child element.  This method will use
     * the namespace of the parent element for the child element's namespace.
     *
     * @param parent The parent element
     * @param name   The new child element name
     * @param value  The value
     * @return The child element
     */
    static Element addElement(Element parent, String name, String value) {
        return addElement(parent, name, value, null);
    }

    /**
     * Adds a child element with the specific name and the given value to the
     * given parent element and returns the child element.  This method will use
     * the namespace of the parent element for the child element's namespace.
     * If the given value is null then the default value is used.  If the value
     * is null then this method will not add the child element and will return
     * null.
     *
     * @param parent       The parent element
     * @param name         The new child element name
     * @param value        The value
     * @param defaultValue The default value (if the value is null)
     * @return The child element
     */
    static Element addElement(Element parent, String name, String value,
        String defaultValue) {

        Element child = null;

        if (value == null)
            value = defaultValue;

        if (value != null) {
            child = addElement(parent, name);
            child.addText(value);
        }

        return child;
    }

    /**
     * Adds a child element with the specific name and the given value to the
     * given parent element and returns the child element.  This method will use
     * the namespace of the parent element for the child element's namespace.
     *
     * @param parent The parent element
     * @param name   The new child element name
     * @param value  The value
     * @return The child element
     */
    static Element addElement(Element parent, String name, URL value) {
        return addElement(parent, name, value, null);
    }

    /**
     * Adds a child element with the specific name and the given value to the
     * given parent element and returns the child element.  This method will use
     * the namespace of the parent element for the child element's namespace.
     * If the given value is null then the default value is used.  If the value
     * is null then this method will not add the child element and will return
     * null.
     *
     * @param parent       The parent element
     * @param name         The new child element name
     * @param value        The value
     * @param defaultValue The default value (if the value is null)
     * @return The child element
     */
    static Element addElement(Element parent, String name, URL value,
        URL defaultValue) {
        Element child = null;

        if (value == null)
            value = defaultValue;

        if (value != null) {
            child = addElement(parent, name);
            child.addText(value.toString());
        }

        return child;
    }

    /**
     * Adds a child element with the specific name and the given value to the
     * given parent element and returns the child element.  This method will use
     * the namespace of the parent element for the child element's namespace.
     *
     * @param parent The parent element
     * @param name   The new child element name
     * @param value  The value
     * @return The child element
     */
    static Element addElement(Element parent, String name, Duration value) {
        return addElement(parent, name, value, null);
    }

    /**
     * Adds a child element with the specific name and the given value to the
     * given parent element and returns the child element.  This method will use
     * the namespace of the parent element for the child element's namespace.
     * If the given value is null then the default value is used.  If the value
     * is null then this method will not add the child element and will return
     * null.
     *
     * @param parent       The parent element
     * @param name         The new child element name
     * @param value        The value
     * @param defaultValue The default value (if the value is null)
     * @return The child element
     */
    static Element addElement(Element parent, String name, Duration value,
        Duration defaultValue) {
        Element child = null;

        if (value == null)
            value = defaultValue;

        if (value != null) {
            child = addElement(parent, name);
            child.addText(value.toString());
        }

        return child;
    }

    static void importFromText(String text, Element parent)
        throws DocumentException {

        if (text == null || text.length() == 0)
            return;

        SAXReader reader = new SAXReader(DocumentFactory.getInstance());
        reader.setStripWhitespaceText(true);
        reader.setMergeAdjacentText(true);
        Document document = reader.read(new StringReader(text));
        Element docElem = document.getRootElement();
        Namespace ns = docElem.getNamespace();
        normalizeNSPrefix(docElem,
            ns == Namespace.NO_NAMESPACE ? Namespace.NO_NAMESPACE : ns);
        parent.add(docElem.detach());
    }

    static void normalizeNSPrefix(Element elem, Namespace ns) {
        Namespace elemNS = elem.getNamespace();
        /*if (elemNS.equals(ns))
            elem.setQName(new QName(elem.getName(), ns));
        else*/
        if (!elemNS.equals(ns) &&
            elem.getNamespaceURI().equals(ns.getURI())) {

            elem.setQName(new QName(elem.getName(), ns));
        }
        List children = elem.elements();
        for (int i = 0; i < children.size(); i++) {
            Element child = (Element)children.get(i);
            normalizeNSPrefix(child, ns);
        }
    }

    private Util() {
    }
}