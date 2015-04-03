package ro.teamnet.wfmc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.wfmc.service.ServiceFactory;

import java.util.Properties;

/**
 * Configuration properties for the WfmcServiceFactory.
 */
@Component
@ConfigurationProperties(prefix = "wfmcService")
public class WfmcServiceFactoryContext {

    private String instanceName;

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    private String instanceClass;

    public void setInstanceClass(String instanceClass) {
        this.instanceClass = instanceClass;
    }
    private WfmcServiceCacheFactoryContext cacheService;

    public void setCacheService(WfmcServiceCacheFactoryContext cacheService) {
        this.cacheService = cacheService;
    }

    public Properties getProperties() {
        Properties context = new Properties();
        context.put(ServiceFactory.INSTANCE_NAME, instanceName);
        context.put(ServiceFactory.INSTANCE_CLASS, instanceClass);
        return context;
    }

    public Properties getCacheProperties() {
        return cacheService.getCacheContext();
    }

    private class WfmcServiceCacheFactoryContext {
        private String instanceName;
        private String instanceClass;
        private Long timeToExpire;
        private Long timeToLive;
        private Long timeToEvict;

        private static final String TIME_TO_EXPIRE = "time.to.expire";
        private static final String TIME_TO_LIVE = "time.to.live";
        private static final String TIME_TO_EVICT = "time.to.evict";

        public WfmcServiceCacheFactoryContext(String instanceName, String instanceClass, Long timeToExpire, Long timeToLive, Long timeToEvict) {
            this.instanceName = instanceName;
            this.instanceClass = instanceClass;
            this.timeToExpire = timeToExpire;
            this.timeToLive = timeToLive;
            this.timeToEvict = timeToEvict;
        }

        public Properties getCacheContext() {
            Properties context = new Properties();
            context.put(ServiceFactory.INSTANCE_NAME, instanceName);
            context.put(ServiceFactory.INSTANCE_CLASS, instanceClass);
            context.put(TIME_TO_EXPIRE, timeToExpire);
            context.put(TIME_TO_LIVE, timeToLive);
            context.put(TIME_TO_EVICT, timeToEvict);
            return context;
        }
    }
}
