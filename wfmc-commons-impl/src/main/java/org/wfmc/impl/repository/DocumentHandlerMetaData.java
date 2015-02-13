package org.wfmc.impl.repository;

import org.wfmc.server.core.xpdl.model.data.ExternalReference;
import org.xml.sax.EntityResolver;

/**
 * @author Adrian Price
 */
public class DocumentHandlerMetaData extends ToolAgentMetaData {
    private static final long serialVersionUID = -3003775733188776748L;
    private static final String IMPL_CLASS =
        "org.obe.runtime.tool.DocumentHandler";
    private static final String[] IMPL_CTOR_SIG = {
        DocumentHandlerMetaData.class.getName()
    };
    // N.B. Only FileDataSources can use file extension mapping.
    // URLDataSources call URLConnection.getContentType().
    private String _url;
    private int _height;
    private int _width;
    private boolean _status;
    private boolean _toolbar;
    private boolean _menubar;
    private boolean _location;
    private boolean _scrollbars;
    private String _title;
    private String _contentType;
    private String _commandName;

    public DocumentHandlerMetaData() {
    }

    public DocumentHandlerMetaData(String id, String displayName,
        String description, String docUrl, String author, boolean threadsafe) {

        super(id, displayName, description, docUrl, author, threadsafe);
    }

    public ToolAgentMetaData introspect(ExternalReference extRef,
        EntityResolver entityResolver) throws RepositoryException {

        // TODO: query JAF CommandMap to see whether content type is supported.
        return null;//this;
    }

    public String getUrl() {
        return _url != null ? _url
            : _type == null || !allowInheritance ? null :
            ((DocumentHandlerMetaData)_type).getUrl();
    }

    public void setUrl(String url) {
        _url = url;
    }

    public String getTitle() {
        return _title != null ? _title
            : _type == null || !allowInheritance ? null :
            ((DocumentHandlerMetaData)_type).getTitle();
    }

    public void setTitle(String title) {
        _title = title;
    }

    public int getHeight() {
        return _height == 0 ? _type == null || !allowInheritance ? 0 :
            ((DocumentHandlerMetaData)_type).getHeight() : _height;
    }

    public void setHeight(int height) {
        _height = height;
    }

    public int getWidth() {
        return _width == 0 ? _type == null || !allowInheritance ? 0 :
            ((DocumentHandlerMetaData)_type).getWidth() : _width;
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

    public String getContentType() {
        return _contentType != null ? _contentType
            : _type == null || !allowInheritance ? null :
            ((DocumentHandlerMetaData)_type).getContentType();
    }

    public void setContentType(String contentType) {
        _contentType = contentType;
    }

    public String getCommandName() {
        return _commandName != null ? _commandName
            : _type == null || !allowInheritance ? null :
            ((DocumentHandlerMetaData)_type).getCommandName();
    }

    public void setCommandName(String commandName) {
        _commandName = commandName;
    }

    protected String getImplClass() {
        return IMPL_CLASS;
    }

    protected String[] getImplCtorSig() {
        return IMPL_CTOR_SIG;
    }
}