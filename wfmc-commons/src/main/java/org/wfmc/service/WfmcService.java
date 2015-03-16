package org.wfmc.service;

import org.wfmc.wapi2.WAPI2;

/**
 * Created by Lucian.Dragomir on 2/10/2015.
 */
public interface WfmcService extends Service, XpdlInterface, WAPI2 {

    public String getServiceInstance();
}
