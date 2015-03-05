package org.wfmc;

import org.wfmc.service.*;
import org.wfmc.wapi.WMConnectInfo;
import org.wfmc.wapi.WMProcessInstance;

import java.io.IOException;

/**
 * Hello world!
 */
public class MainTest {
    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        System.out.println("Hello World!");

        //String serviceCacheProperties = "C:\\__JAVA\\jmeter\\test\\dmsutils\\wapi-cache.properties";
        //WfmcServiceCacheFactory wfmcServiceCacheFactory = new WfmcServiceCacheFactory(serviceCacheProperties);
        //WfmcServiceCache wfmcServiceCache = wfmcServiceCacheFactory.getInstance();
        //wfmcServiceCache = ((WfmcServiceImpl_Abstract)wfmcService).getWfmcServiceCache();


        String serviceProperties = "C:\\__JAVA\\jmeter\\test\\dmsutils\\wapi-elo-renns.properties";



        WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(serviceProperties);
        WfmcService wfmcService = wfmcServiceFactory.getInstance();

        wfmcService.connect(new WMConnectInfo("Administrator", "elo@RENNS2015", "Wfmc Test", "http://10.6.38.90:8080/ix-elo/ix"));

        String procInstId = wfmcService.createProcessInstance("3", "instance 3");
        wfmcService.assignProcessInstanceAttribute(procInstId, "UAT", 1);
        //wfmcService.assignProcessInstanceAttribute(procInstId, "String", "test");
        //wfmcService.assignProcessInstanceAttribute(procInstId, "Double", 1.0);
        wfmcService.startProcess(procInstId);



        wfmcService.disconnect();

        System.out.println("");

        System.out.println("");



    }
}
