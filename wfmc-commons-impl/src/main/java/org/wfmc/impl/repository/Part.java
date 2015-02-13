package org.wfmc.impl.repository;

import java.io.Serializable;

/**
 * @author Adrian Price
 */
public final class Part implements Serializable {
    private static final long serialVersionUID = -5982728605059069710L;
    public String name;
    public String typeNamespaceURI;
    public String typeName;

    public Part() {
    }

    public Part(String name, String typeNamespaceURI, String typeName) {
        this.name = name;
        this.typeNamespaceURI = typeNamespaceURI;
        this.typeName = typeName;
    }
}
