package org.wfmc.service;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Lucian.Dragomir on 2/10/2015.
 */
public abstract class WfmcServiceImpl_Abstract implements WfmcService {
    Properties context;

    @Override
    public void __init(Properties context) throws IOException {
        this.context = context;
    }

    @Override
    public Properties getContext() {
        return this.context;
    }
}
