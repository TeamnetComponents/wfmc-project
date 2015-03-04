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

package org.wfmc.xpdl.parser.dom4j;

import org.dom4j.Element;
import org.dom4j.QName;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.wfmc.util.DateUtilities;
import org.wfmc.xpdl.model.misc.Duration;
import org.wfmc.xpdl.model.misc.DurationUnit;
import org.wfmc.xpdl.parser.XPDLParserException;

import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class Util {
    /**
     * Return the child element with the given name.  The element must be in the
     * same name space as the parent element.
     *
     * @param element The parent element
     * @param name    The child element name
     * @return The child element
     */
    public static Element child(Element element, String name) {
        // Now that we're using a default namespace, none of the XPDL elements
        // have a qualified name.
//        return element.element(new QName(name, element.getNamespace()));
        return element.element(name);
    }

    /**
     * Return the child elements with the given name.  The elements must be in
     * the same name space as the parent element.
     *
     * @param element The parent element
     * @param name    The child element name
     * @return The child elements
     */
    public static List children(Element element, String name) {
        // Now that we're using a default namespace, none of the XPDL elements
        // have a qualified name.
//        return element.elements(new QName(name, element.getNamespace()));
        return element.elements(name);
    }

    /**
     * Detaches an element from its parent and returns a child node.
     *
     * @param element The element to detach.
     * @param qname   The qualified name of the child element to return.
     * @return The child element.
     */
    public static Element detach(Element element, QName qname) {
        element.detach();
        return element.element(qname);
    }

    // Conversion

    /**
     * Return the value of the child element with the given name.  The element
     * must be in the same name space as the parent element.
     *
     * @param element The parent element
     * @param name    The child element name
     * @return The child element value
     */
    public static String elementAsString(Element element, String name) {
        String s = element.elementTextTrim(
            new QName(name, element.getNamespace()));
        return s == null || s.length() == 0 ? null : s;
    }

    public static Date elementAsDate(Element element, String name)
        throws XPDLParserException {

        String text = elementAsString(element, name);
        if (text == null)
            return null;

        try {
            return DateUtilities.getInstance().parse(text);
        } catch (ParseException e) {
            throw new XPDLParserException("Error parsing date: " + text, e);
        }
    }

    public static int elementAsInteger(Element element, String name) {
        String text = elementAsString(element, name);
        if (text == null)
            return 0;

        return Integer.parseInt(text);
    }

    public static boolean elementAsBoolean(Element element, String name) {
        String text = elementAsString(element, name);
        if (text == null)
            return false;

        return Boolean.valueOf(text).booleanValue();
    }

    public static URL elementAsURL(Element element, String name)
        throws XPDLParserException {

        String text = elementAsString(element, name);
        if (text == null)
            return null;

        try {
            return new URL(text);
        } catch (MalformedURLException e) {
            throw new XPDLParserException("Invalid URL: " + text, e);
        }
    }

    public static Duration elementAsDuration(Element element, String name)
        throws XPDLParserException {

        String text = elementAsString(element, name);
        if (text == null)
            return null;

        try {
            return Duration.valueOf(text);
        } catch (NumberFormatException e) {
            throw new XPDLParserException("Error parsing duration", e);
        }
    }

    public static DurationUnit elementAsDurationUnit(Element element,
        String name) throws XPDLParserException {

        String text = elementAsString(element, name);
        if (text == null)
            return null;

        try {
            return DurationUnit.valueOf(text);
        } catch (NumberFormatException e) {
            throw new XPDLParserException("Error parsing duration unit", e);
        }
    }

    public static String exportToText(Element element)
        throws XPDLParserException {

        StringWriter out = new StringWriter();
        OutputFormat format = new OutputFormat("    ", true);
        format.setTrimText(true);
        XMLWriter writer = new XMLWriter(out, format);
        try {
            writer.write(element);
        } catch (IOException e) {
            throw new XPDLParserException(e);
        }
        out.flush();
        return out.toString();
    }

    private Util() {
    }
}