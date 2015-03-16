package org.wfmc;

import org.wfmc.impl.base.WMWorkItemAttributeNames;
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
        String serviceProperties = "D:\\projects\\wfmc-project\\wfmc-test\\src\\main\\resources\\wapi-elo-renns.properties";
        WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(serviceProperties);
        WfmcService wfmcService = wfmcServiceFactory.getInstance();
        wfmcService.connect(new WMConnectInfo("Andra@Administrator", "elo@RENNS2015", "Wfmc Test", "http://10.6.38.90:8080/ix-elo/ix"));
        String tempProcInstId = wfmcService.createProcessInstance("3", "instance 3");
        wfmcService.assignProcessInstanceAttribute(tempProcInstId, "UAT", 1);
        //Pas 1. start process
        String processInstanceId = wfmcService.startProcess(tempProcInstId);
        //Pas 2. get available tasks - listWorkItems
        WMFilter wmFilter = WMFilterBuilder.createWMFilterWorkItem().addWorkItemName("Redactare raspuns");
        WMWorkItemIterator wmWorkItemIterator = wfmcService.listWorkItems(wmFilter, false);
        WMWorkItem wmWorkItem = null;
        String currentItemId = null;
        while (wmWorkItemIterator.hasNext()){
            wmWorkItem  = wmWorkItemIterator.tsNext();
            if (wmWorkItem.getProcessInstanceId().equals(processInstanceId)) {
                currentItemId = wmWorkItem.getId();
                System.out.println("nume task=" + wmWorkItem.getName());
                System.out.println("nume user pe task=" + wmWorkItem.getParticipant().getName());
            }
        }
        //Pas 3. claimTask - reassign
        wfmcService.reassignWorkItem(wmWorkItem.getParticipant().getName(), "Andra", wmWorkItem.getProcessInstanceId(), currentItemId);
        //Pas 4. get next nodes
        System.out.println("...");
        wmWorkItemIterator = wfmcService.listWorkItems(WMFilterBuilder.createWMFilterWorkItem().addWorkItemParticipantName("Andra"), false);

        while (wmWorkItemIterator.hasNext()){
            wmWorkItem  = wmWorkItemIterator.tsNext();
            System.out.println("nume task=" + wmWorkItem.getName());
            if (wmWorkItem.getProcessInstanceId().equals(processInstanceId)) {
                System.out.println("nume user pe task=" + wmWorkItem.getParticipant().getName());
                List<WMWorkItem> nextSteps = wfmcService.getNextSteps((wmWorkItem.getProcessInstanceId()), (wmWorkItem.getId()));

                for (WMWorkItem workItem : nextSteps) {
                    System.out.println("nume next node = " + workItem.getName() + " si nume utilizator = " + workItem.getParticipant().getName());
                    //Pas 5. forward task
                    wfmcService.assignWorkItemAttribute(wmWorkItem.getProcessInstanceId(), wmWorkItem.getId(), WMWorkItemAttributeNames.TRANSITION_NEXT_WORK_ITEM_ID.toString(), workItem.getId());
                    System.out.println("Workflow forwarded to " + workItem.getId());
                }
            }
        }
        wfmcService.completeWorkItem(processInstanceId, currentItemId);
        wfmcService.disconnect();
    }
}
