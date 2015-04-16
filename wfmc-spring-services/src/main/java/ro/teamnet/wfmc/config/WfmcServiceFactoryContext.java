package ro.teamnet.wfmc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.wfmc.service.ServiceFactory;

import java.util.Properties;

/**
 * Configuration properties for the WfmcServiceFactory.
 */
@Component
@ConfigurationProperties(prefix = "wfmc.service")
public class WfmcServiceFactoryContext {

    private String instanceName;

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    private String instanceClass;

    public void setInstanceClass(String instanceClass) {
        this.instanceClass = instanceClass;
    }

    public Properties getProperties() {
        Properties context = new Properties();
        if (instanceName != null) {
            context.put(ServiceFactory.INSTANCE_NAME, instanceName);
        }
        if (instanceClass != null) {
            context.put(ServiceFactory.INSTANCE_CLASS, instanceClass);
        }
        return context;
    }


}
