package org.wfmc;

import org.wfmc.impl.base.WMWorkItemAttributeNames;
import org.wfmc.impl.base.filter.WMFilterBuilder;
import org.wfmc.service.WfmcService;
import org.wfmc.service.WfmcServiceFactory;
import org.wfmc.wapi.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ioan.Ivan on 3/6/2015.
 */
public class DemoFluxHotarareConsiliuLocalAprobat {

    public static void main(String[] args)
        throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException, WMWorkflowException
    {
        String serviceProperties = "D:\\projects\\wfmc-project\\wfmc-test\\src\\main\\resources\\wapi-elo-renns.properties";
        String processInstanceName = "Instanta flux hotarare consiliu local 2";

        WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(serviceProperties);
        WfmcService wfmcService = wfmcServiceFactory.getInstance();
        //naming convention for user: {ApplicationUser}@{ImpersonatedUser}   ; ImpersonatedUser = Userul de login in ELO(licenta); Administrator pt DEV
        wfmcService.connect(new WMConnectInfo("Andra@Administrator", "elo@RENNS2015", "Wfmc Test", "http://10.6.38.90:8080/ix-elo/ix"));
        // Pas 1. Create process instance
        String procInstIdTemp = wfmcService.createProcessInstance("5", processInstanceName);

        //Pas 2. Assign process instance attributes
        wfmcService.assignProcessInstanceAttribute(procInstIdTemp, "UAT", 1);
        wfmcService.assignProcessInstanceAttribute(procInstIdTemp, "String", "test");
        wfmcService.assignProcessInstanceAttribute(procInstIdTemp, "Double", 1.0);

        //Pas 3. Start process instance
        String processInstanceId = wfmcService.startProcess(procInstIdTemp);

        //Pas 4. Get avaible task for Automatizare Asteptare and user = ELO Service.
        WMFilter wmFilter = WMFilterBuilder.createWMFilterWorkItem().addWorkItemParticipantName("ELO Service").
                addWorkItemName(FluxHotarareConsiliuLocalNodes.AUTOMATIZARE_ASTEPTARE);

        WMWorkItemIterator wmWorkItemIterator = wfmcService.listWorkItems(wmFilter, true);
        List<WMWorkItem> wmWorkItemListForEloService = new ArrayList<>();
        while (wmWorkItemIterator.hasNext()){
            WMWorkItem wmWorkItem = wmWorkItemIterator.tsNext();
            wmWorkItemListForEloService.add(wmWorkItem);
            System.out.println("Task for user " + wmWorkItem.getParticipant().getName() + " are : " + wmWorkItem.getName());
        }

        //Pas 5. Claim task Automatizare Asteptare
        String currentWorkItemId = null;
        for (WMWorkItem wmWorkItem : wmWorkItemListForEloService) {
            if (wmWorkItem.getProcessInstanceId().equals(processInstanceId)){
                currentWorkItemId = wmWorkItem.getId();
                wfmcService.reassignWorkItem("ELO Service", "Andra", processInstanceId, currentWorkItemId);
            }
        }

        //Pas 6. Check if the work item was assigned to Andra by getting avaible task for Automatizare Asteptare and user = Andra.
        WMFilter wmFilterForAndra = WMFilterBuilder.createWMFilterWorkItem().addWorkItemParticipantName("Andra").
                addWorkItemName(FluxHotarareConsiliuLocalNodes.AUTOMATIZARE_ASTEPTARE);
        WMWorkItemIterator wmWorkItemIteratorForAndra = wfmcService.listWorkItems(wmFilterForAndra, true);
        while (wmWorkItemIteratorForAndra.hasNext()){
            WMWorkItem wmWorkItem = wmWorkItemIteratorForAndra.tsNext();
            currentWorkItemId = wmWorkItem.getId();
            System.out.println("Task for user " + wmWorkItem.getParticipant().getName() + " are : " + wmWorkItem.getName() );
        }

        // 6.1 print workflow
        WMProcessInstance wmProcessInstance = wfmcService.getProcessInstance(processInstanceId);
        System.out.println(wmProcessInstance == null ? "null process instance" : "Process instance id = " + wmProcessInstance.getId());
        System.out.println(wmProcessInstance == null ? "null process instance" : "Process instance name = " + wmProcessInstance.getName());
        System.out.println(wmProcessInstance == null || wmProcessInstance.getState() == null ? "Process instance state = " + "null": "Process instance state = " + wmProcessInstance.getState().stringValue() );
        //Pas 7. List next steps for Automatizare Asteptare.
        List<WMWorkItem> nextSteps = wfmcService.getNextSteps(processInstanceId, currentWorkItemId);

        //Pas 8. Forward task to Aprobat
        for (WMWorkItem wmWorkItem : nextSteps) {
            if(wmWorkItem.getName().equals(FluxHotarareConsiliuLocalNodes.APROBAT)) {
                wfmcService.assignWorkItemAttribute(processInstanceId, currentWorkItemId, WMWorkItemAttributeNames.TRANSITION_NEXT_WORK_ITEM_ID.toString(), wmWorkItem.getId());
            }
        }
        wfmcService.completeWorkItem(processInstanceId, currentWorkItemId);

        //Pas 9. Check if workflow was finished
        WMFilter wmFilter2 = WMFilterBuilder.createWMFilterProcessInstance().isActive(false).addProcessInstanceName(processInstanceName);
        WMProcessInstanceIterator wmProcessInstanceIterator = wfmcService.listProcessInstances(wmFilter2, true);
        while (wmProcessInstanceIterator.hasNext()) {
            WMProcessInstance wmProcessInstanceTemp = wmProcessInstanceIterator.tsNext();
            System.out.println(wmProcessInstanceTemp == null ? "null process instance" : "Process instance id = " + wmProcessInstanceTemp.getId());
            System.out.println(wmProcessInstanceTemp == null ? "null process instance" : "Process instance name = " + wmProcessInstanceTemp.getName());
            System.out.println(wmProcessInstanceTemp == null || wmProcessInstance.getState() == null ? "Process instance state = " + "null": "Process instance state = " + wmProcessInstanceTemp.getState().stringValue() );
        }
    }
}
