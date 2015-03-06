package org.wfmc;

import org.wfmc.elo.WfmcServiceImpl_Elo;
import org.wfmc.impl.base.filter.WMFilterBuilder;
import org.wfmc.service.WfmcService;
import org.wfmc.service.WfmcServiceFactory;
import org.wfmc.wapi.WMConnectInfo;
import org.wfmc.wapi.WMFilter;
import org.wfmc.wapi.WMWorkItem;
import org.wfmc.wapi.WMWorkItemIterator;

import java.io.IOException;
import java.util.List;

/**
 * Created by andras on 3/5/2015.
 */
public class BasicDemoWfMCToElo {

    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {

        //String serviceCacheProperties = "C:\\__JAVA\\jmeter\\test\\dmsutils\\wapi-cache.properties";
        //WfmcServiceCacheFactory wfmcServiceCacheFactory = new WfmcServiceCacheFactory(serviceCacheProperties);
        //WfmcServiceCache wfmcServiceCache = wfmcServiceCacheFactory.getInstance();
        //wfmcServiceCache = ((WfmcServiceImpl_Abstract)wfmcService).getWfmcServiceCache();


        String serviceProperties = "D:\\projects\\wfmc-project\\wfmc-test\\src\\main\\resources\\wapi-elo-renns.properties";


        WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(serviceProperties);
        WfmcService wfmcService = wfmcServiceFactory.getInstance();


        WfmcServiceImpl_Elo wfmcServiceImpl_Elo = new WfmcServiceImpl_Elo();
        
        
        wfmcService.connect(new WMConnectInfo("Administrator", "elo@RENNS2015", "Wfmc Test", "http://10.6.38.90:8080/ix-elo/ix"));

        String procInstId = wfmcService.createProcessInstance("3", "instance 3");
        wfmcService.assignProcessInstanceAttribute(procInstId, "UAT", 1);
        //wfmcService.assignProcessInstanceAttribute(procInstId, "String", "test");
        //wfmcService.assignProcessInstanceAttribute(procInstId, "Double", 1.0);
        //Pas 1. start process
        String activeId = wfmcService.startProcess(procInstId);
        //Pas 2. get available tasks - listWorkItems
        WMFilter wmFilter = WMFilterBuilder.createWMFilterWorkItem().addWorkItemName("Redactare raspuns");
        WMWorkItemIterator wmWorkItemIterator = wfmcService.listWorkItems(wmFilter, false);
        WMWorkItem wmWorkItem = null;
        while (wmWorkItemIterator.hasNext()){
            wmWorkItem  = wmWorkItemIterator.tsNext();
            System.out.println("nume task = " + wmWorkItem.getName());
            System.out.println("nume user pe task = " + wmWorkItem.getParticipant().getName());

        }


        //Pas 3. claimTask - reassign
        wfmcService.reassignWorkItem(wmWorkItem.getParticipant().getName(), "Andra", wmWorkItem.getProcessInstanceId(), wmWorkItem.getId());
        //Pas 4. get next nodes
        System.out.println("...");
        wmWorkItemIterator = wfmcService.listWorkItems(WMFilterBuilder.createWMFilterWorkItem().addWorkItemParticipant("Andra"), false);
        while (wmWorkItemIterator.hasNext()){
            wmWorkItem  = wmWorkItemIterator.tsNext();
            System.out.println("nume task=" + wmWorkItem.getName());
            System.out.println("nume user pe task=" + wmWorkItem.getParticipant().getName());

        }

        List<WMWorkItem> nextSteps = wfmcService.getNextSteps(Integer.parseInt(activeId), Integer.parseInt(wmWorkItem.getId()));

        System.out.println("Next steps: " + nextSteps.size());
        for(WMWorkItem v : nextSteps){
            System.out.println("nume task=" + v.getName());

        }
                //Pas 5. forward task

        wfmcService.disconnect();

        System.out.println("");

        System.out.println("");



    }
}
