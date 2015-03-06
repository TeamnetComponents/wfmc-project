package org.wfmc;

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


        String serviceProperties = "C:\\Users\\ioan.ivan\\Desktop\\wapi-elo-renns.properties";



        WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(serviceProperties);
        WfmcService wfmcService = wfmcServiceFactory.getInstance();

        wfmcService.connect(new WMConnectInfo("Administrator", "elo@RENNS2015", "Wfmc Test", "http://10.6.38.90:8080/ix-elo/ix"));

        String procInstId = wfmcService.createProcessInstance("3", "instance 3");
        wfmcService.assignProcessInstanceAttribute(procInstId, "UAT", 1);
        //wfmcService.assignProcessInstanceAttribute(procInstId, "String", "test");
        //wfmcService.assignProcessInstanceAttribute(procInstId, "Double", 1.0);
        //Pas 1. start process
        wfmcService.startProcess(procInstId);
        //Pas 2. get available tasks - listWorkItems
        WMFilter wmFilter = WMFilterBuilder.createWMFilterWorkItem().addWorkItemName("Redactare raspuns");
        WMWorkItemIterator wmWorkItemIterator = wfmcService.listWorkItems(wmFilter, false);
        WMWorkItem wmWorkItem = null;
        while (wmWorkItemIterator.hasNext()){
            wmWorkItem  = wmWorkItemIterator.tsNext();
            System.out.println("nume task=" + wmWorkItem.getName());
            System.out.println("nume user pe task=" + wmWorkItem.getParticipant().getName());
        }
        //Pas 3. claimTask - reassign
        wfmcService.reassignWorkItem(wmWorkItem.getParticipant().getName(), "Andra", wmWorkItem.getProcessInstanceId(), wmWorkItem.getId());
        //Pas 4. get next nodes
        System.out.println("...");
        wmWorkItemIterator = wfmcService.listWorkItems(WMFilterBuilder.createWMFilterWorkItem().addWorkItemParticipant("Administrator"), false);

        while (wmWorkItemIterator.hasNext()){
            wmWorkItem  = wmWorkItemIterator.tsNext();
            System.out.println("nume task=" + wmWorkItem.getName());
            System.out.println("nume user pe task=" + wmWorkItem.getParticipant().getName());
            List<WMWorkItem> nextSteps = wfmcService.getNextSteps(Integer.parseInt(wmWorkItem.getProcessInstanceId()), Integer.parseInt(wmWorkItem.getId()));
            int[] forwardIds = new int[nextSteps.size()];
            int i = 0;
            for ( WMWorkItem workItem : nextSteps) {
                forwardIds[i++] = Integer.parseInt(workItem.getId());
                System.out.println("nume next node = " + workItem.getName() + " si nume utilizator = " + workItem.getParticipant().getName());
                //Pas 5. forward task
                wfmcService.setTransition(Integer.parseInt(wmWorkItem.getProcessInstanceId()), Integer.parseInt(wmWorkItem.getId()),forwardIds);
                System.out.println("Workflow forwarded to " + forwardIds);
            }
        }

        wfmcService.disconnect();

        System.out.println("");

        System.out.println("");



    }
}
