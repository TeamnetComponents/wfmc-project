package org.wfmc.impl.base.filter;

/**
 * Created by Lucian.Dragomir on 3/4/2015.
 */
public class WMFilterBuilder {
    public static WMFilterWorkItem createWMFilterWorkItem()
    {
        return new WMFilterWorkItem();
    }

    public static WMFilterProcessInstance createWMFilterProcessInstance()
    {
        return new WMFilterProcessInstance();
    }

}
