package org.wfmc.impl.repository;

import org.xml.sax.EntityResolver;

/**
 * Describes an XML document, schema, DTD, template, XSL transform, etc.
 *
 * @author Adrian Price
 */
public class ResourceMetaData extends AbstractMetaData {
    private static final long serialVersionUID = 4546822991337176280L;
    private static final String[] EMPTY_STRING = {};
    private String _publicId;
    private String _systemId;
    private byte[] _content;

    public ResourceMetaData() {
    }

    public ResourceMetaData(String publicId, String systemId) {
        setPublicId(publicId);
        setSystemId(systemId);
    }

    public ResourceMetaData(String displayName, String description,
        String docUrl,
        String author, String publicId, String systemId, byte[] content) {

        super(null, displayName, description, docUrl, author, true);
        setPublicId(publicId);
        setSystemId(systemId);
        _content = content;
    }

    public Object createInstance(EntityResolver entityResolver)
        throws RepositoryException {

        return _content;
    }

    protected String getImplClass() {
        return null;
    }

    protected String[] getImplCtorSig() {
        return EMPTY_STRING;
    }

    public final String getPublicId() {
        return _publicId != null ? _publicId
            : _type == null || !allowInheritance ? null :
            ((ResourceMetaData)_type).getPublicId();
    }

    public final void setPublicId(String publicId) {
        if (publicId != null) {
            setId(publicId);
        }
        _publicId = publicId;
    }

    public final String getSystemId() {
        return _systemId != null ? _systemId
            : _type == null || !allowInheritance ? null :
            ((ResourceMetaData)_type).getSystemId();
    }

    public final void setSystemId(String systemId) {
        if (_publicId == null) {
            setId(systemId != null ? systemId : null);
        }
        _systemId = systemId;
    }

    public String getResourceType() {
        return _type == null ? getId() :
            ((ResourceMetaData)_type).getResourceType();
    }

    public byte[] getContent() {
        return _content;
    }

    public void setContent(byte[] content) {
        _content = content;
    }
}