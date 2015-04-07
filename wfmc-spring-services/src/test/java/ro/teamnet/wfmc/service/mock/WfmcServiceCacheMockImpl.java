package ro.teamnet.wfmc.service.mock;

import org.wfmc.service.WfmcService;
import org.wfmc.service.WfmcServiceCache;
import org.wfmc.wapi.WMAttribute;
import org.wfmc.wapi.WMAttributeIterator;
import org.wfmc.wapi.WMProcessInstance;

import java.io.IOException;
import java.util.Properties;

/**
 * Mock implementation of WfmcServiceCache.
 */
public class WfmcServiceCacheMockImpl implements WfmcServiceCache {
    @Override
    public void setWfmcService(WfmcService wfmcService) {

    }

    @Override
    public WMProcessInstance getProcessInstance(String procInstId) {
        return null;
    }

    @Override
    public void addProcessInstance(WMProcessInstance wmProcessInstance) {

    }

    @Override
    public void removeProcessInstance(String procInstId) {

    }

    @Override
    public WMAttributeIterator getProcessInstanceAttributes(String procInstId) {
        return null;
    }

    @Override
    public void addProcessInstanceAttribute(String procInstId, WMAttribute wmAttribute) {

    }

    @Override
    public void removeProcessInstanceAttribute(String procInstId, String attrName) {

    }

    @Override
    public void removeProcessInstanceAttributes(String procInstId) {

    }

    @Override
    public WMAttributeIterator getWorkItemAttribute(String procInstId, String workItemId) {
        return null;
    }

    @Override
    public void addWorkItemAttribute(String procInstId, String workItemId, WMAttribute wmAttribute) {

    }

    @Override
    public void removeWorkItemAttribute(String procInstId, String workItemId, String attrName) {

    }

    @Override
    public void removeWorkItemAttributes(String procInstId, String workItemId) {

    }

    @Override
    public void __initialize(Properties context) throws IOException {

    }

    @Override
    public void __terminate() {

    }

    @Override
    public Properties getContext() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
