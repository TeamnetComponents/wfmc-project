package ro.teamnet.wfmc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.wfmc.service.ServiceFactory;

import java.util.Properties;

/**
 * Configuration properties for the WfmcServiceCacheFactory.
 */
@Component
@ConfigurationProperties(prefix = "wfmcService.cacheService")
public class WfmcServiceCacheFactoryContext {

    private String instanceName;

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    private String instanceClass;

    public void setInstanceClass(String instanceClass) {
        this.instanceClass = instanceClass;
    }

    private Long timeToExpire;

    public void setTimeToExpire(Long timeToExpire) {
        this.timeToExpire = timeToExpire;
    }

    private Long timeToLive;

    public void setTimeToLive(Long timeToLive) {
        this.timeToLive = timeToLive;
    }

    private Long timeToEvict;

    public void setTimeToEvict(Long timeToEvict) {
        this.timeToEvict = timeToEvict;
    }

    private static final String TIME_TO_EXPIRE = "time.to.expire";
    private static final String TIME_TO_LIVE = "time.to.live";
    private static final String TIME_TO_EVICT = "time.to.evict";

    public Properties getProperties() {
        Properties context = new Properties();
        context.put(ServiceFactory.INSTANCE_NAME, instanceName);
        context.put(ServiceFactory.INSTANCE_CLASS, instanceClass);
        context.put(TIME_TO_EXPIRE, timeToExpire);
        context.put(TIME_TO_LIVE, timeToLive);
        context.put(TIME_TO_EVICT, timeToEvict);
        return context;
    }

}
