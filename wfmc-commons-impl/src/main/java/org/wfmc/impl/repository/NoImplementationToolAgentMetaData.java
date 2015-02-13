package org.wfmc.impl.repository;

import org.wfmc.server.core.xpdl.model.data.ExternalReference;
import org.xml.sax.EntityResolver;

public class NoImplementationToolAgentMetaData extends ToolAgentMetaData {
    private static final String IMPL_CLASS =
        "org.obe.runtime.tool.NoImplementationToolAgent";
    private static final String[] EMPTY_STRINGS = new String[0];

    public ToolAgentMetaData introspect(ExternalReference extRef,
        EntityResolver entityResolver) {

        return null;
    }

    protected String getImplClass() {
        return IMPL_CLASS;
    }

    protected String[] getImplCtorSig() {
        return EMPTY_STRINGS;
    }
}