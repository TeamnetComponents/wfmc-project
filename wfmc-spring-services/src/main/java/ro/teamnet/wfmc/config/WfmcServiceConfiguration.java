package ro.teamnet.wfmc.config;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wfmc.service.WfmcService;
import org.wfmc.service.WfmcServiceFactory;
import org.wfmc.wapi.WMConnectInfo;

import javax.inject.Inject;

/**
 * Configuration file for instantiating WFMC beans.
 */
@Configuration
public class WfmcServiceConfiguration {

    @Inject
    private WfmcServiceFactoryContext wfmcServiceFactoryContext;

    @Bean
    public WfmcService wfmcService() {
        try {
            WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(wfmcServiceFactoryContext.getProperties());
            WfmcService wfmcService = wfmcServiceFactory.getInstance();
            return wfmcService;
        } catch (Exception e) {
            throw new BeanCreationException("Could not create WfmcService", e);
        }
    }

    @Bean
    @ConfigurationProperties(prefix = "wmConnectInfo")
    public WMConnectInfo wmConnectInfo(String userIdentification,
                                       String password, String engineName, String scope) {
        return new WMConnectInfo(userIdentification, password, engineName, scope);
    }
}
