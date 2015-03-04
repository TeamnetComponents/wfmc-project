package org.wfmc.service;

import org.wfmc.wapi2.WAPI2;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Lucian.Dragomir on 2/10/2015.
 */
public interface WfmcService extends WAPI2, Service {

    public String getServiceInstance();
}
