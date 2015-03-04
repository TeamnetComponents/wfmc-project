package org.wfmc;

import org.wfmc.service.*;
import org.wfmc.wapi.WMProcessInstance;

import java.io.IOException;

/**
 * Hello world!
 */
public class MainTest {
    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        System.out.println("Hello World!");

        String serviceProperties = "C:\\__JAVA\\jmeter\\test\\dmsutils\\wapi-elo-renns.properties";
        String serviceCacheProperties = "C:\\__JAVA\\jmeter\\test\\dmsutils\\wapi-cache.properties";

        WfmcServiceCacheFactory wfmcServiceCacheFactory = new WfmcServiceCacheFactory(serviceCacheProperties);
        WfmcServiceCache wfmcServiceCache = wfmcServiceCacheFactory.getInstance();

        WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(serviceProperties);
        WfmcService wfmcService = wfmcServiceFactory.getInstance();


        wfmcServiceCache = ((WfmcServiceImpl_Abstract)wfmcService).getWfmcServiceCache();

        String procInstId = wfmcService.createProcessInstance("1", "instance 1");;
        wfmcService.assignProcessInstanceAttribute(procInstId, "UAT", 1);
        wfmcService.assignProcessInstanceAttribute(procInstId, "String", "test");
        wfmcService.assignProcessInstanceAttribute(procInstId, "Double", 1.0);
        wfmcService.startProcess(procInstId);





        System.out.println("");

        System.out.println("");



    }
}
