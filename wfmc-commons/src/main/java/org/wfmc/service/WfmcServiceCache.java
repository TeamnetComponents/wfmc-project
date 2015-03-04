package org.wfmc.service;

import org.wfmc.wapi.WMAttribute;
import org.wfmc.wapi.WMAttributeIterator;
import org.wfmc.wapi.WMProcessInstance;

/**
 * Created by Lucian.Dragomir on 2/28/2015.
 */
public interface WfmcServiceCache extends Service {

    public void setWfmcService(WfmcService wfmcService);

    //WMProcessInstance cache

    public WMProcessInstance getProcessInstance(String procInstId);

    public void addProcessInstance(WMProcessInstance wmProcessInstance);

    public void removeProcessInstance(String procInstId);

    //WMAttribute for WMProcessInstance cache

    public WMAttributeIterator getProcessInstanceAttributes(String procInstId);

    public void addProcessInstanceAttribute(String procInstId, WMAttribute wmAttribute);

    public void removeProcessInstanceAttribute(String procInstId, String attrName);

    public void removeProcessInstanceAttributes(String procInstId);

}
