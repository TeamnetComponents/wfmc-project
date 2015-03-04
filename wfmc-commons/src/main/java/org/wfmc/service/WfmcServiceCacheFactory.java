package org.wfmc.service;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Lucian.Dragomir on 2/28/2015.
 */
public class WfmcServiceCacheFactory extends ServiceFactory<WfmcServiceCache> {

    public WfmcServiceCacheFactory(Properties context) {
        super(context);
    }

    public WfmcServiceCacheFactory(String contextName, String... alternatePaths) throws IOException {
        super(contextName, alternatePaths);
    }

    public WfmcServiceCacheFactory(String contextName, ContextType contextType, String... alternatePaths) throws IOException {
        super(contextName, contextType, alternatePaths);
    }
}
