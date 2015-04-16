package org.wfmc.service;

import org.apache.commons.lang.StringUtils;
import org.wfmc.impl.utils.FileUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Lucian.Dragomir on 2/10/2015.
 */
public class WfmcServiceFactory extends ServiceFactory<WfmcService> {
    public static final String SERVICE_CACHE_INSTANCE_CONFIGURATION = "service.cache.instance.configuration";

    private WfmcServiceCacheFactory wfmcServiceCacheFactory;

    public WfmcServiceFactory(Properties context) {
        super(context);
    }

    public WfmcServiceFactory(String contextName, String... alternatePaths) throws IOException {
        super(contextName, alternatePaths);
    }

    public WfmcServiceFactory(String contextName, ContextType contextType, String... alternatePaths) throws IOException {
        super(contextName, contextType, alternatePaths);
    }

    @Override
    public WfmcService getInstance() throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {
        WfmcService wfmcService = null;
        wfmcService = super.getInstance();

        WfmcService wfmcServiceForService = null;
        try {
            wfmcServiceForService = super.getServiceInstance();
        } catch (IllegalArgumentException e) {
            //do nothing
        }

        //prepare service cache

        //get parent paths to optimize configuration file detection
        String instanceSource = wfmcService.getContext().getProperty(ServiceFactory.INSTANCE_SOURCE);
        String instanceSourceType = wfmcService.getContext().getProperty(ServiceFactory.INSTANCE_SOURCE_TYPE);
        if (instanceSourceType == null) {
            instanceSourceType = ServiceFactory.ContextType.ANY.name();
        }
        ServiceFactory.ContextType contextType = ServiceFactory.ContextType.valueOf(instanceSourceType);
        FileUtils fileUtils = FileUtils.proposeFileUtils(instanceSource);


        String[] alternatePaths = null;
        if (StringUtils.isNotEmpty(instanceSource)) {
            String instancePath = fileUtils.getParentFolderPathName(instanceSource);
            if (!instancePath.equals(fileUtils.getRootPath())) {
                alternatePaths = new String[]{instancePath};
            } else {
                alternatePaths = new String[0];
            }
        }
        if (this.getContext().containsKey(SERVICE_CACHE_INSTANCE_CONFIGURATION)) {
            //get service cache factory
            if (wfmcServiceCacheFactory == null) {
                synchronized (this) {
                    if (wfmcServiceCacheFactory == null) {
                        wfmcServiceCacheFactory = new WfmcServiceCacheFactory(this.getContext().getProperty(SERVICE_CACHE_INSTANCE_CONFIGURATION), contextType, alternatePaths);
                    }
                }
            }
            WfmcServiceCache wfmcServiceCache = wfmcServiceCacheFactory.getInstance();
            if (wfmcServiceForService != null) {
                wfmcServiceCache.setWfmcService(wfmcServiceForService);
                ((WfmcServiceAbstract) wfmcServiceForService).setWfmcServiceCache(wfmcServiceCache);
            } else {
                wfmcServiceCache.setWfmcService(wfmcService);
                ((WfmcServiceAbstract) wfmcService).setWfmcServiceCache(wfmcServiceCache);
            }
        }
        if ((wfmcServiceForService != null) && (wfmcService instanceof WfmcServiceAuditImpl)) {
            ((WfmcServiceAuditImpl) wfmcService).setInternalService(wfmcServiceForService);
        }
        return wfmcService;
    }

    public WfmcService getService(Properties wfmcServiceCacheContext) throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {
        WfmcService wfmcService = null;
        wfmcService = super.getInstance();

        WfmcService wfmcServiceForService = null;
        try {
            wfmcServiceForService = super.getServiceInstance();
        } catch (IllegalArgumentException e) {
            //do nothing
        }

        //prepare service cache

        //get parent paths to optimize configuration file detection
        String instanceSource = wfmcService.getContext().getProperty(ServiceFactory.INSTANCE_SOURCE);
        String instanceSourceType = wfmcService.getContext().getProperty(ServiceFactory.INSTANCE_SOURCE_TYPE);
        if (instanceSourceType == null) {
            instanceSourceType = ServiceFactory.ContextType.ANY.name();
        }
        ServiceFactory.ContextType contextType = ServiceFactory.ContextType.valueOf(instanceSourceType);
        FileUtils fileUtils = FileUtils.proposeFileUtils(instanceSource);


        String[] alternatePaths = null;
        if (StringUtils.isNotEmpty(instanceSource)) {
            String instancePath = fileUtils.getParentFolderPathName(instanceSource);
            if (!instancePath.equals(fileUtils.getRootPath())) {
                alternatePaths = new String[]{instancePath};
            } else {
                alternatePaths = new String[0];
            }
        }

        //get service cache factory
        if (wfmcServiceCacheFactory == null) {
            synchronized (this) {
                if (wfmcServiceCacheFactory == null) {
                    wfmcServiceCacheFactory = new WfmcServiceCacheFactory(wfmcServiceCacheContext);
                }
            }
        }
        WfmcServiceCache wfmcServiceCache = wfmcServiceCacheFactory.getInstance();
        if (wfmcServiceForService != null) {
            wfmcServiceCache.setWfmcService(wfmcServiceForService);
            ((WfmcServiceAbstract) wfmcServiceForService).setWfmcServiceCache(wfmcServiceCache);
        } else {
            wfmcServiceCache.setWfmcService(wfmcService);
            ((WfmcServiceAbstract) wfmcService).setWfmcServiceCache(wfmcServiceCache);
        }

        if ((wfmcServiceForService != null) && (wfmcService instanceof WfmcServiceAuditImpl)) {
            ((WfmcServiceAuditImpl) wfmcService).setInternalService(wfmcServiceForService);
        }
        return wfmcService;
    }
}
