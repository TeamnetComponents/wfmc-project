package org.wfmc;

import org.wfmc.impl.base.filter.WMFilterBuilder;
import org.wfmc.service.WfmcService;
import org.wfmc.service.WfmcServiceFactory;
import org.wfmc.wapi.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by Ioan.Ivan on 3/6/2015.
 */
public class DemoFluxAprobareOperatiuniAprobat {

    public static void main(String[] arg) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        String serviceProperties = "C:\\Users\\ioan.ivan\\Desktop\\wapi-elo-renns.properties";
        String processInstanceName =  "Instanta flux aprobare operatiuni";


        WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(serviceProperties);
        WfmcService wfmcService = wfmcServiceFactory.getInstance();

        wfmcService.connect(new WMConnectInfo("Administrator", "elo@RENNS2015", "Wfmc Test", "http://10.6.38.90:8080/ix-elo/ix"));
        // Pas 1. Create process instance
        String procInstIdTemp = wfmcService.createProcessInstance("3", processInstanceName);
        //Pas 2. Asign process attribute
        //Pas 3. Start Proces
        String procInstId = wfmcService.startProcess(procInstIdTemp);

        //Pas 4. Get avaible tasks for REDACTARE_RASPUNS taskType
        WMFilter wmFilter = WMFilterBuilder.createWMFilterWorkItem().addWorkItemParticipant("Administrator").
                addWorkItemName(FluxAprobareOperatiuniNodes.REDACTARE_RASPUNS);
        WMWorkItemIterator wmWorkItemIterator = wfmcService.listWorkItems(wmFilter, true);

        //Pas 5. Claim task REDACTARE_RASPUNS
        String curentWorkItemId = null ;
        while (wmWorkItemIterator.hasNext()) {
            WMWorkItem wmWorkItem = wmWorkItemIterator.tsNext();
            if (wmWorkItem.getProcessInstanceId().equals(procInstId)) {
                curentWorkItemId = wmWorkItem.getId();
                wfmcService.reassignWorkItem("Administrator","Andra",procInstId, curentWorkItemId);

            }
        }

        //Pas 6. List next steps for REDACTARE_RASPUNS
        List<WMWorkItem> nextSteps = wfmcService.getNextSteps(procInstId, curentWorkItemId);

        //Pas 7. Forward task to TRIMITE
        for (WMWorkItem wmWorkItem : nextSteps) {
            if (wmWorkItem.getName().equals(FluxAprobareOperatiuniNodes.TRIMITE)) {
                wfmcService.setTransition(procInstId, curentWorkItemId, new String[]{wmWorkItem.getId()});
            }
        }

        //Pas 8. Get avaible tasks for APROBARE_RASPUNS taskType
        WMFilter wmFilter1 = WMFilterBuilder.createWMFilterWorkItem().addWorkItemName(FluxAprobareOperatiuniNodes.APROBARE_RASPUNS).addWorkItemParticipant("Administrator");
        WMWorkItemIterator wmWorkItemIterator1 = wfmcService.listWorkItems(wmFilter1, true);

        //Pas 9. Claim task APROBARE_RASPUNS
        while (wmWorkItemIterator1.hasNext()) {
            WMWorkItem wmWorkItem = wmWorkItemIterator1.tsNext();
            if (wmWorkItem.getProcessInstanceId().equals(procInstId)) {
                curentWorkItemId = wmWorkItem.getId();
                wfmcService.reassignWorkItem("Administrator", "Andra", procInstId, curentWorkItemId);
            }
        }

        //Pas 10. List next steps for APROBARE_RASPUNS
        List<WMWorkItem> nextSteps1 = wfmcService.getNextSteps(procInstId, curentWorkItemId);

        //Pas 11. Forward task to APROBAT
        for (WMWorkItem wmWorkItem : nextSteps1) {
            if(wmWorkItem.getName().equals(FluxAprobareOperatiuniNodes.APROBAT)) {
                wfmcService.setTransition(procInstId, curentWorkItemId, new String[]{wmWorkItem.getId()});
            }
        }
        System.out.println("Process instance id = " + procInstId);
        //Pas 12. Check if workflow was finished
        WMFilter wmFilter2 = WMFilterBuilder.createWMFilterWorkItem().addWorkItemName(processInstanceName);
        WMProcessInstanceIterator wmProcessInstanceIterator = wfmcService.listProcessInstances(wmFilter2, true);
        while (wmProcessInstanceIterator.hasNext()) {
            WMProcessInstance wmProcessInstance = wmProcessInstanceIterator.tsNext();
            System.out.println("Process instance name = " + wmProcessInstance.getName());
            System.out.println("Process instance state = " + wmProcessInstance.getState().stringValue());
        }
    }


}
