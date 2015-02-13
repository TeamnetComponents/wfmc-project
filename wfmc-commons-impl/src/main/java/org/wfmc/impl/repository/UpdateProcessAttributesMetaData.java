package org.wfmc.impl.repository;


import org.wfmc.server.core.xpdl.model.data.ExternalReference;
import org.xml.sax.EntityResolver;

/**
 * @author Adrian Price
 */
public class UpdateProcessAttributesMetaData extends ToolAgentMetaData {
    private static final long serialVersionUID = -3003775733188776748L;
    private static final String IMPL_CLASS =
        "org.obe.runtime.tool.UpdateProcessAttributes";
    private static final String[] IMPL_CTOR_SIG = {
        UpdateProcessAttributesMetaData.class.getName()
    };
    private int _height;
    private int _width;
    private boolean _status;
    private boolean _toolbar;
    private boolean _menubar;
    private boolean _location;
    private boolean _scrollbars;
    private String _title;

    public UpdateProcessAttributesMetaData() {
    }

    public UpdateProcessAttributesMetaData(String id, String displayName,
        String description, String docUrl, String author, boolean threadsafe) {

        super(id, displayName, description, docUrl, author, threadsafe);
    }

    public ToolAgentMetaData introspect(ExternalReference extRef,
        EntityResolver entityResolver) throws RepositoryException {

        return null;
    }

    public String getTitle() {
        return _title != null ? _title
            : _type == null || !allowInheritance ? null :
            ((UpdateProcessAttributesMetaData)_type).getTitle();
    }

    public void setTitle(String title) {
        _title = title;
    }

    public int getHeight() {
        return _height == 0 ? _type == null || !allowInheritance ? 0 :
            ((UpdateProcessAttributesMetaData)_type).getHeight() : _height;
    }

    public void setHeight(int height) {
        _height = height;
    }

    public int getWidth() {
        return _width == 0 ? _type == null || !allowInheritance ? 0 :
            ((UpdateProcessAttributesMetaData)_type).getWidth() : _width;
    }

    public void setWidth(int width) {
        _width = width;
    }

    public boolean getStatus() {
        return _status;
    }

    public void setStatus(boolean status) {
        _status = status;
    }

    public boolean getToolbar() {
        return _toolbar;
    }

    public void setToolbar(boolean toolbar) {
        _toolbar = toolbar;
    }

    public boolean getMenubar() {
        return _menubar;
    }

    public void setMenubar(boolean menubar) {
        _menubar = menubar;
    }

    public boolean getLocation() {
        return _location;
    }

    public void setLocation(boolean location) {
        _location = location;
    }

    public boolean getScrollbars() {
        return _scrollbars;
    }

    public void setScrollbars(boolean scrollbars) {
        _scrollbars = scrollbars;
    }

    protected String getImplClass() {
        return IMPL_CLASS;
    }

    protected String[] getImplCtorSig() {
        return IMPL_CTOR_SIG;
    }
}