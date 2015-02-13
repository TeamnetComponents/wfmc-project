package org.wfmc.service;

import org.apache.commons.lang.StringUtils;
import org.wfmc.commons.FileUtils;
import org.wfmc.wapi.WMWorkflowException;

import java.io.IOException;
import java.util.*;

/**
 * Created by Lucian.Dragomir on 2/10/2015.
 */
public class WfmcServiceFactory {

    //a map with factoryClass, instanceName, instanceObject
    //private static final Map<String, Map<String, InitableService>> factoryMap = new HashMap<String, Map<String, InitableService>>();

    public static final String INSTANCE_NAME = "instance.name";
    public static final String INSTANCE_CLASS = "instance.class";
    public static final String INSTANCE_SOURCE = "instance.source";
    public static final String INSTANCE_SOURCE_TYPE = "instance.source.type";

    private static final String PROPERTIES_FILE_SUFFIX = ".properties";

    private Properties context;

    public static enum ContextType {
        ANY,
        RESOURCE,
        ABSOLUTE
    }

    public WfmcServiceFactory(Properties context) {
        this.context = context;
        setDefaultProperties();
        validateContext();
    }

    public WfmcServiceFactory(String contextName, String... alternatePaths) throws IOException {
        this(contextName, ContextType.ANY, alternatePaths);
    }

    public WfmcServiceFactory(String contextName, ContextType contextType, String... alternatePaths) throws IOException {
        if (StringUtils.isEmpty(contextName)) {
            throw new WMWorkflowException("Configuration is not specified for this factory.");
        }

        Properties contextToLoad = null;
        //extract filename and path from contextName (in case it is a file name containing the context configuration)
        FileUtils fileUtils = (contextName.contains(FileUtils.getFileUtilsOS().getPathDelimiter())) ? FileUtils.getFileUtilsOS() : FileUtils.getFileUtilsDMS();
        String pathName = fileUtils.getParentFolderPathName(contextName);
        String contextShortName = fileUtils.getFileName(contextName);

        if (contextShortName.endsWith(PROPERTIES_FILE_SUFFIX)) {
            contextShortName = contextShortName.substring(0, contextShortName.length() - PROPERTIES_FILE_SUFFIX.length());
        }

        List<String> pathList = new ArrayList<String>();

        if (!pathName.equals(fileUtils.getRootPath())) {
            pathList.add(pathName);
        } else {
            pathList.add(null);
        }
        if (alternatePaths != null && alternatePaths.length > 0) {
            pathList.addAll(Arrays.asList(alternatePaths));
        }
        //add META-INF folder as the last searched folder
        pathList.add("META-INF");

        if (contextName.endsWith(PROPERTIES_FILE_SUFFIX)) {
            //in case the configurationName ends with .properties it is assumed
            //the configuration string is a filename and the factory will try to configuration from different locations
            contextToLoad = loadContextFromFileSystem(contextShortName, contextType, pathList);
        }
//        else {
//            //the configuration is an instance name and it will try to get configuration from the cache (factoryMap)
//            //contextToLoad = loadContextFromCache(contextShortName);
//            WMWorkflowException
//        }

        if (contextToLoad == null) {
            throw new WMWorkflowException("Unable to find configuration for context <" + contextName + "> .");
        }

        setContext(contextToLoad);
        setDefaultProperties();
        validateContext();
    }

    private Properties loadContextFromFileSystem(String contextName, ContextType contextType, List<String> contextPaths) {
        Properties properties = null;
        if (contextPaths == null) {
            contextPaths = new ArrayList<String>();
        }
        if (contextPaths.size() == 0) {
            contextPaths.add(null);
        }

        //try first option, load from profiles path
        if (ContextType.ANY.equals(contextType) || ContextType.RESOURCE.equals(contextType)) {
            for (String path : contextPaths) {
                properties = null;
                try {
                    String configurationFilePathName = contextName;
                    if (path != null) {
                        FileUtils fileUtils = (path.contains(FileUtils.getFileUtilsOS().getPathDelimiter())) ? FileUtils.getFileUtilsOS() : FileUtils.getFileUtilsDMS();
                        configurationFilePathName = fileUtils.concatenate(path, contextName);
                    }
                    properties = convertResourceBundleToProperties(ResourceBundle.getBundle("profiles/" + configurationFilePathName));
                    properties.put(INSTANCE_SOURCE, configurationFilePathName);
                    properties.put(INSTANCE_SOURCE_TYPE, ContextType.RESOURCE.name());
                } catch (Exception e) {
                    //unable to load resource from that path
                }
                if (properties != null) {
                    return properties;
                }
            }
        }

        //try to load the configuration from provided paths
        if (ContextType.ANY.equals(contextType) || ContextType.ABSOLUTE.equals(contextType)) {
            for (String path : contextPaths) {
                properties = null;
                try {
                    String configurationFilePathName = contextName;
                    if (path != null) {
                        FileUtils fileUtils = (path.contains(FileUtils.getFileUtilsOS().getPathDelimiter())) ? FileUtils.getFileUtilsOS() : FileUtils.getFileUtilsDMS();
                        configurationFilePathName = fileUtils.concatenate(path, contextName + PROPERTIES_FILE_SUFFIX);
                    }
                    properties = FileUtils.openOsResource(configurationFilePathName);
                    properties.put(INSTANCE_SOURCE, configurationFilePathName);
                    properties.put(INSTANCE_SOURCE_TYPE, ContextType.ABSOLUTE.name());
                } catch (IOException e) {
                    //unable to load resource from that path
                }
                if (properties != null) {
                    return properties;
                }
            }
        }

        return null;
    }

    private void setDefaultProperties() {
    }

    private void validateContext() {
        if (!this.context.containsKey(INSTANCE_NAME)) {
            throw new WMWorkflowException("The instance name is not defined.");
        }
        if (!this.context.containsKey(INSTANCE_CLASS)) {
            throw new WMWorkflowException("The instance class is not defined.");
        }
    }

    public WfmcService getInstance() throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {
        WfmcService instance = null;

        //ensure the wfmcServiceClassName is provided.
        String wfmcServiceClassName = context.getProperty(getPropertyInstanceClass());
        if (wfmcServiceClassName == null)
            throw new IllegalArgumentException(getPropertyInstanceClass());
        Class wfmcServiceClazz = Class.forName(wfmcServiceClassName);

        //the provided class must implement the WfmcService interface.
        if (!WfmcService.class.isAssignableFrom(wfmcServiceClazz)) {
            throw new IllegalArgumentException("The class " + wfmcServiceClassName +
                    " must implement the interface " +
                    WfmcService.class.getName());
        }

        //return a new instance of WfmcService class
        instance = (WfmcService) wfmcServiceClazz.newInstance();

        //send the factory parameters to the instance
        instance.__init(context);

        return instance;
    }

    public String getPropertyInstanceClass() {
        return INSTANCE_CLASS;
    }

    public String getPropertyInstanceName() {
        return INSTANCE_NAME;
    }

    public String getPropertyInstanceSource() {
        return INSTANCE_SOURCE;
    }

    public String getPropertyInstanceSourceType() {
        return INSTANCE_SOURCE_TYPE;
    }

    public Properties getContext() {
        return context;
    }

    protected void setContext(Properties context) {
        this.context = context;
    }

    private static Properties convertResourceBundleToProperties(ResourceBundle resource) {
        Properties properties = new Properties();
        Enumeration<String> keys = resource.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            properties.put(key, resource.getString(key));
        }
        return properties;
    }

    public static Map<String, String> toMap(Properties properties) {
        Map context = new HashMap();
        for (Object propertyKey : properties.keySet()) {
            context.put(propertyKey, properties.get(propertyKey));
        }
        return context;
    }

    public static Map<String, String> toMap(Map<String, String> properties) {
        Map context = new HashMap();
        for (Object propertyKey : properties.keySet()) {
            context.put(propertyKey, properties.get(propertyKey));
        }
        return context;
    }

    //------------------------------------------------------------------------------------------------------------------
    /*public void registerInstance(WfmcService instance) {
        //!!!!! merge doar daca modelul in care instantiem obiecte din clasa StoreService este 1/nume store service !!!
        String factoryClassName = this.getClass().getCanonicalName();
        String instanceName = instance.getName();
        if (!factoryMap.containsKey(factoryClassName)) {
            synchronized (factoryMap) {
                if (!factoryMap.containsKey(factoryClassName)) {
                    factoryMap.put(factoryClassName, new HashMap<String, WfmcService>());
                }
            }
        }
        if (!factoryMap.get(factoryClassName).containsKey(instanceName)) {
            factoryMap.get(factoryClassName).put(instanceName, instance);
        }
    }

    public boolean containsInstance(String instanceName) {
        String factoryClassName = this.getClass().getCanonicalName();
        if (!factoryMap.containsKey(factoryClassName)) {
            return false;
        }
        return factoryMap.get(factoryClassName).containsKey(instanceName);
    }

    public WfmcService retrieveInstance(String instanceName) {
        if (!containsInstance(instanceName)) {
            return null;
        }
        String factoryClassName = this.getClass().getCanonicalName();
        //return (T) factoryMap.get(factoryClassName).get(instanceName);
        return null;
    }

    protected static WfmcService retrieveInstance(String instanceInterfaceClass, String instaceName) {
        //if (!factoryMap.containsKey(instanceInterfaceClass)) {
        //    return null;
        //}
        //return factoryMap.get(instanceInterfaceClass).get(instaceName);

        return null;
    }*/
}
