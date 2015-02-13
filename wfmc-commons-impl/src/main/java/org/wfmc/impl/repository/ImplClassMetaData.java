package org.wfmc.impl.repository;

/**
 * @author Adrian Price
 */
public abstract class ImplClassMetaData extends AbstractMetaData {
    private static final long serialVersionUID = -7818991286704032647L;
    private String _implClass;
    private String[] _implCtorSig;

    protected ImplClassMetaData() {
    }

    protected ImplClassMetaData(String id, String displayName,
        String description, String docUrl, String author, boolean threadsafe,
        String implClass, String[] implCtorSig) {

        super(id, displayName, description, docUrl,
            author, threadsafe);
        _implClass = implClass;
        _implCtorSig = implCtorSig;
    }

    public final String getImplClass() {
        return _implClass != null ? _implClass
            : _type == null || !allowInheritance ? null :
            _type.getImplClass();
    }

    public final void setImplClass(String implClass) {
        _implClass = implClass;
    }

    public final String[] getImplCtorSig() {
        return _implCtorSig != null ? _implCtorSig
            : _type == null || !allowInheritance ? null :
            _type.getImplCtorSig();
    }

    public final void setImplCtorSig(String[] implCtorSig) {
        _implCtorSig = implCtorSig;
    }
}