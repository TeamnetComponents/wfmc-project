package org.wfmc.service;

import org.wfmc.cache.ExpirableMemoryCache;
import org.wfmc.impl.base.WMAttributeIteratorImpl;
import org.wfmc.impl.base.WMWorkItemImpl;
import org.wfmc.wapi.WMAttribute;
import org.wfmc.wapi.WMAttributeIterator;
import org.wfmc.wapi.WMProcessInstance;
import org.wfmc.wapi.WMWorkItem;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Lucian.Dragomir on 2/28/2015.
 */
public class WfmcServiceCacheImpl_Memory implements WfmcServiceCache {

    public static final String TIME_TO_EXPIRE = "time.to.expire";
    public static final String TIME_TO_LIVE = "time.to.live";
    public static final String TIME_TO_EVICT = "time.to.evict";

    //the map key is the service name value
    private Map<String, DataCache> cacheMap = new ConcurrentHashMap<String, DataCache>();

    public static class DataCache {
        private Map<String, WMProcessInstance> wmProcessInstanceCache;
        private Map<String, Map<String, WMAttribute>> wmProcessInstanceAttributeCache;
        private Map<WMWorkItem, List<WMAttribute>> wmWorkItemAttributeCache;
        private Map<String, String> userCache;

        public DataCache(long timeToExpire, long timeToLive, long timeToEvict) {
            this.wmProcessInstanceCache = new ExpirableMemoryCache<String, WMProcessInstance>(timeToExpire, timeToLive, timeToEvict);
            this.wmProcessInstanceAttributeCache = new ExpirableMemoryCache<String, Map<String, WMAttribute>>(timeToExpire, timeToLive, timeToEvict);
            this.wmWorkItemAttributeCache = new ExpirableMemoryCache<WMWorkItem, List<WMAttribute>>(timeToExpire, timeToLive, timeToEvict);
            this.userCache = new ExpirableMemoryCache<String, String>(timeToExpire, timeToLive, timeToEvict);
        }
    }

    private String sessionId;
    private Properties context;
    WfmcService wfmcService;

    @Override
    public void setWfmcService(WfmcService wfmcService) {
        this.wfmcService = wfmcService;
    }

    @Override
    public WMProcessInstance getProcessInstance(String procInstId) {
        return this.getCache().wmProcessInstanceCache.get(getProcessInstanceCacheKey(procInstId));
    }

    @Override
    public void addProcessInstance(WMProcessInstance wmProcessInstance) {
        this.getCache().wmProcessInstanceCache.put(getProcessInstanceCacheKey(wmProcessInstance.getId()), wmProcessInstance);
    }

    @Override
    public void removeProcessInstance(String procInstId) {
        this.getCache().wmProcessInstanceCache.remove(getProcessInstanceCacheKey(procInstId));
    }


    @Override
    public WMAttributeIterator getProcessInstanceAttributes(String procInstId) {
        WMAttributeIterator wmAttributeIterator = null;
        String key = getProcessInstanceCacheKey(procInstId);
        Map<String, WMAttribute> wmAttributeMap = this.getCache().wmProcessInstanceAttributeCache.get(key);
        if (wmAttributeMap != null) {
            wmAttributeIterator = new WMAttributeIteratorImpl(wmAttributeMap.values().toArray());
        } else {
            wmAttributeIterator = new WMAttributeIteratorImpl(0);
        }
        return wmAttributeIterator;
    }

    @Override
    public void addProcessInstanceAttribute(String procInstId, WMAttribute wmAttribute) {
        String key = getProcessInstanceCacheKey(procInstId);
        WMProcessInstance wmProcessInstance = this.getCache().wmProcessInstanceCache.get(key);
        if (wmProcessInstance != null) {
            synchronized (wmProcessInstance) {
                Map<String, WMAttribute> wmAttributeMap = this.getCache().wmProcessInstanceAttributeCache.get(key);
                if (wmAttributeMap == null) {
                    wmAttributeMap = new ConcurrentHashMap<String, WMAttribute>();
                    this.getCache().wmProcessInstanceAttributeCache.put(key, wmAttributeMap);
                }
                wmAttributeMap.put(wmAttribute.getName(), wmAttribute);
            }
        }
    }

    @Override
    public void removeProcessInstanceAttribute(String procInstId, String attrName) {
        String key = getProcessInstanceCacheKey(procInstId);
        Map<String, WMAttribute> wmAttributeMap = this.getCache().wmProcessInstanceAttributeCache.get(key);
        if (wmAttributeMap != null) {
            wmAttributeMap.remove(attrName);
        }
    }

    @Override
    public void removeProcessInstanceAttributes(String procInstId) {
        String key = getProcessInstanceCacheKey(procInstId);
        this.getCache().wmProcessInstanceAttributeCache.remove(key);
    }


    @Override
    public WMAttributeIterator getWorkItemAttribute(String procInstId, String workItemId) {
        WMWorkItem workItem = new WMWorkItemImpl(procInstId, workItemId);
        List<WMAttribute> wmAttributeList = this.getCache().wmWorkItemAttributeCache.get(workItem);
        return new WMAttributeIteratorImpl(wmAttributeList.toArray());
    }

    @Override
    public void addWorkItemAttribute(String procInstId, String workItemId, WMAttribute wmAttribute) {
        WMWorkItem wmWorkItem = new WMWorkItemImpl(procInstId, workItemId);
        if (wmWorkItem.getId() != null) {
            synchronized (wmWorkItem) {
                List<WMAttribute> wmAttributeList = getCache().wmWorkItemAttributeCache.get(wmWorkItem);
                if (wmAttributeList == null) {
                    wmAttributeList = new ArrayList<>();
                }
                wmAttributeList.add(wmAttribute);
                getCache().wmWorkItemAttributeCache.put(wmWorkItem, wmAttributeList);
            }
        }
    }

    @Override
    public void removeWorkItemAttribute(String procInstId, String workItemId, String attrName) {
        WMWorkItem wmWorkItem = new WMWorkItemImpl(procInstId, workItemId);
        List<WMAttribute> wmAttributeList = this.getCache().wmWorkItemAttributeCache.get(wmWorkItem);
        if (wmAttributeList != null) {
            wmAttributeList.clear();
        }
    }

    @Override
    public void removeWorkItemAttributes(String procInstId, String workItemId) {
        WMWorkItem workItem = new WMWorkItemImpl(procInstId, workItemId);
        this.getCache().wmWorkItemAttributeCache.remove(workItem);
    }


    @Override
    public String getUserName(String sessionId) {
        return getCache().userCache.get(sessionId);
    }

    @Override
    public void addUserName(String sessionId, String userName) {

        if (!getCache().userCache.containsKey(sessionId)) {
            Map<String, String> userCache = getCache().userCache;
            userCache.put(sessionId, userName);
            System.out.println("");
        }

    }

    @Override
    public void removeUserName(String sessionId) {
        if (getCache().userCache.containsKey(sessionId)) {
            getCache().userCache.remove(sessionId);
        }
    }


    @Override
    public void __initialize(Properties context) throws IOException {
        this.context = context;
        if (!this.cacheMap.containsKey(getName())) {
            synchronized (this.cacheMap) {
                if (!this.cacheMap.containsKey(getName())) {
                    this.cacheMap.put(getName(), new DataCache(getTimeToExpire(), getTimeToLive(), getTimeToEvict()));
                }
            }
        }
    }

    @Override
    public void __terminate() {
        this.wfmcService = null;
        this.cacheMap.remove(getName());
        //context should be last variable set to null (otherwise other methods like getName will stop working correctly)
        this.context = null;
    }

    @Override
    public Properties getContext() {
        return this.context;
    }

    @Override
    public String getName() {
        return (String) context.get(ServiceFactory.INSTANCE_NAME);
    }

    @Override
    public String getSession() {
        return this.sessionId;
    }

    private long getTimeToExpire() {
        return Long.parseLong(getContext().getProperty(TIME_TO_EXPIRE, "0"));
    }

    private long getTimeToLive() {
        return Long.parseLong(getContext().getProperty(TIME_TO_LIVE, "0"));
    }

    private long getTimeToEvict() {
        return Long.parseLong(getContext().getProperty(TIME_TO_EVICT, "0"));
    }

    private DataCache getCache() {
        return this.cacheMap.get(getName());
    }

    private String getProcessInstanceCacheKey(String processInstanceId) {
        return wfmcService.getServiceInstance() + "-" + processInstanceId;
    }

}
