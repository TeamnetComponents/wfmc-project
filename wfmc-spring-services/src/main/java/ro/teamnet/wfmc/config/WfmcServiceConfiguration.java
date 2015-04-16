package ro.teamnet.wfmc.config;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
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
    private Environment environment;

    @Inject
    private WfmcServiceFactoryContext wfmcServiceFactoryContext;

    @Inject
    private WfmcServiceCacheFactoryContext wfmcServiceCacheFactoryContext;

    @Inject
    private WMConnectInfoBean wmConnectInfoBean;

    @Bean
    public WfmcService wfmcService() {
        try {
            WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(wfmcServiceFactoryContext.getProperties());
            return wfmcServiceFactory.getService(wfmcServiceCacheFactoryContext.getProperties());
        } catch (Exception e) {
            throw new BeanCreationException("Could not create WfmcService", e);
        }
    }

    @Bean
    public WMConnectInfo wmConnectInfo() {
        return wmConnectInfoBean.getWMConnectInfo();
    }
}
