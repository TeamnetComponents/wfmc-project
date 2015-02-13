package org.wfmc.impl.repository;

import org.wfmc.impl.tool.Parameter;
import org.wfmc.server.core.xpdl.model.data.ExternalReference;
import org.xml.sax.EntityResolver;

/**
 * Describes a W3C XForms user interface.
 *
 * @author Adrian Price
 */
public final class XFormMetaData extends ToolAgentMetaData {
    private static final long serialVersionUID = -4411143403128681197L;
    private static final String IMPL_CLASS = "org.obe.runtime.tool.XForm";
    private static final String[] IMPL_CTOR_SIG = {
        XFormMetaData.class.getName(),
        Parameter.class.getName() + "[]"
    };
    private String _template;   // The XForm template (or its name).
    private boolean _file;      // true if _template is a file name.

    public XFormMetaData() {
    }

    public XFormMetaData(String id, String displayName, String description,
        String docUrl, String author, String template, boolean file) {

        super(id, displayName, description, docUrl, author, false);
        _template = template;
        _file = file;
    }

    public String getTemplate() {
        return _template != null ? _template
            : _type == null || !allowInheritance ? null :
            ((XFormMetaData)_type).getTemplate();
    }

    public void setTemplate(String template) {
        _template = template;
    }

    public boolean isFile() {
        return _file;
    }

    public boolean getFile() {
        return _file;
    }

    public void setFile(boolean file) {
        _file = file;
    }

    protected String getImplClass() {
        return IMPL_CLASS;
    }

    protected String[] getImplCtorSig() {
        return IMPL_CTOR_SIG;
    }

    public ToolAgentMetaData introspect(ExternalReference extRef,
        EntityResolver entityResolver) {

        // There's no easy, reliable way to recognize an XForm reference.
        return null;
    }
}