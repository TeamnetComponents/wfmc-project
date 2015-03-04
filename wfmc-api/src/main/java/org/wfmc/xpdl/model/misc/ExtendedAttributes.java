package org.wfmc.xpdl.model.misc;

import org.w3c.dom.*;
import org.wfmc.xpdl.XMLException;
import org.wfmc.xpdl.XPDLNames;
import org.wfmc.xpdl.model.activity.Activity;
import org.wfmc.xpdl.model.activity.BlockActivity;
import org.wfmc.xpdl.model.data.XMLFragment;
import org.wfmc.xpdl.model.ext.AssignmentStrategyDef;
import org.wfmc.xpdl.model.ext.Event;
import org.wfmc.xpdl.model.ext.Loop;
import org.wfmc.xpdl.model.pkg.XPDLPackage;
import org.wfmc.xpdl.model.transition.Transition;
import org.wfmc.xpdl.model.workflow.WorkflowProcess;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Holds 3rd party extended attributes as XML text.  The text must include the
 * &lt;ExtendedAttributes&gt; parent element, correctly declared in the XPDL
 * namespace.
 * <p/>
 * <em>N.B.  DO NOT USE THIS CLASS FOR STORING OBE EXTENDED ATTRIBUTES!</em>
 *
 * @author Adrian Price
 * @see XPDLNames#XPDL_NS_PREFIX
 * @see XPDLNames#XPDL_NS_URI
 * @see XPDLPackage#setAssignmentStrategy(AssignmentStrategyDef)
 * @see WorkflowProcess#setAssignmentStrategy(AssignmentStrategyDef)
 * @see Activity#setAssignmentStrategy(AssignmentStrategyDef)
 * @see XPDLPackage#setCompletionStrategy(String)
 * @see WorkflowProcess#setCompletionStrategy(String)
 * @see Activity#setCompletionStrategy(String)
 * @see XPDLPackage#setCalendar(String)
 * @see WorkflowProcess#setCalendar(String)
 * @see Activity#setCalendar(String)
 * @see Transition#setExecutionType(ExecutionType)
 * @see Transition#setEvent(Event)
 * @see BlockActivity#setLoop(Loop)
 */
public final class ExtendedAttributes extends XMLFragment {
    private static final long serialVersionUID = 6285607010686717240L;
    private transient Map _map;

    public ExtendedAttributes() {
    }

    public ExtendedAttributes(String text) throws XMLException {
        super(text);
    }

    public ExtendedAttributes(Document document) throws XMLException {
        super(document);
    }

    public ExtendedAttributes(Map map) throws XMLException {
        setMap(map);
    }

    protected String getDocumentElementName() {
        return XPDLNames.EXTENDED_ATTRIBUTES;
    }

    protected String getDocumentElementNamespaceURI() {
        return XPDLNames.XPDL_NS_URI;
    }

    public void clear() throws XMLException {
        setMap(null);
    }

    public String get(String key) throws XMLException {
        return (String)getMap().get(key);
    }

    public void put(String key, Object value) throws XMLException {
        getMap();
        _map.put(key, value == null ? null : value.toString());
        setMap(_map);
    }

    public void remove(String key) throws XMLException {
        getMap();
        _map.remove(key);
        setMap(_map);
    }

    /**
     * Returns an unmodifiable map of name:value pairs.  The map representation
     * is only suitable for simple extended attributes with values that can be
     * represented as strings.
     *
     * @return A map of simple name:value pairs.
     * @throws XMLException
     */
    public Map getMap() throws XMLException {
        Map map = _map;
        if (map == null) {
            _map = map = new HashMap();
            Document document = getDocument();
            if (document != null) {
                Element docElem = document.getDocumentElement();
                NodeList nodes = docElem.getChildNodes();
                for (int i = 0, m = nodes.getLength(); i < m; i++) {
                    Node node = nodes.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE &&
                        node.getNodeName()
                            .endsWith(XPDLNames.EXTENDED_ATTRIBUTE)) {

                        Element elem = (Element)node;
                        NamedNodeMap attrs = elem.getAttributes();
                        Node nameAttr = attrs.getNamedItem(XPDLNames.NAME);
                        Node valueAttr = attrs.getNamedItem(XPDLNames.VALUE);
                        String value;
                        if (valueAttr == null) {
                            if (elem.hasChildNodes()) {
                                StringBuffer sb = new StringBuffer();
                                elem.normalize();
                                NodeList children = elem.getChildNodes();
                                for (int j = 0, n = children.getLength(); j < n;
                                    j++)
                                    sb.append(children.item(j).getNodeValue());
                                value = sb.toString();
                            } else {
                                value = null;
                            }
                        } else {
                            value = valueAttr.getNodeValue();
                        }
                        map.put(nameAttr.getNodeValue(),
                            value == null ? null : value.trim());
                    }
                }
            }
        }
        return Collections.unmodifiableMap(map);
    }

    /**
     * Sets extended attributes as map of name:value pairs.  The map
     * representation is only suitable for simple extended attributes with
     * values that can be represented as strings.  Complex extended attributes
     * must be set either as XML text or as a DOM document; in either case the
     * document element must be <code>ExtendedAttributes</code>, in the XPDL
     * namspace URI <code>http://www.wfmc.org/2002/XPDL1.0</code>.
     *
     * @param map Map of name:value pairs.
     * @throws XMLException
     */
    public void setMap(Map map) throws XMLException {
        if (map == null) {
            setText(null);
        } else {
            StringBuffer buf = new StringBuffer();
            buf.append(
                "<ExtendedAttributes xmlns=\"http://www.wfmc.org/2002/XPDL1.0\">\n");
            for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
                Map.Entry entry = (Map.Entry)iter.next();
                Object key = entry.getKey();
                if (key == null) {
                    throw new XMLException(new IllegalArgumentException(
                        "Map key cannot be null"));
                }
                buf.append("  <ExtendedAttribute Name=\"").append(key)
                    .append('\"');
                Object value = entry.getValue();
                if (value != null) {
                    // If the string representation of the extended attribute's
                    // value contains any XML markup or symbols, we must store
                    // it in a CDATA section.
                    String str = value.toString().trim();
                    if (str.indexOf('<') != -1 || str.indexOf('>') != -1 ||
                        str.indexOf('&') != -1) {

                        buf.append(">\n    <![CDATA[\n").append(str)
                            .append("\n    ]]>\n");
                        buf.append("  </ExtendedAttribute>\n");
                    } else {
                        // Otherwise, a simple attribute will suffice.
                        buf.append(" Value=\"").append(value).append("\"/>\n");
                    }
                } else {
                    buf.append("/>\n");
                }
            }
            buf.append("</ExtendedAttributes>\n");
            setText(buf.toString());
        }
        _map = new HashMap(map);
    }
}