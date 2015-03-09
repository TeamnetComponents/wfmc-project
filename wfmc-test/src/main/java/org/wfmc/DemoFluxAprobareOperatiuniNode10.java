package org.wfmc;

import org.wfmc.impl.base.filter.WMFilterBuilder;
import org.wfmc.service.WfmcService;
import org.wfmc.service.WfmcServiceFactory;
import org.wfmc.wapi.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by Mihai.Niculae on 3/6/2015.
 */
public class DemoFluxAprobareOperatiuniNode10 {

    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        String serviceProperties = "D:\\projects\\wfmc-project\\wfmc-test\\src\\main\\resources\\wapi-elo-renns.properties";
        String processInstanceName =  "Instanta flux aprobare operatiuni";
        String sourceUser = "Administrator";
        WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(serviceProperties);
        WfmcService wfmcService = wfmcServiceFactory.getInstance();
        //naming convention for user: {ApplicationUser}@{ImpersonatedUser}   ; ImpersonatedUser = Userul de login in ELO(licenta); Administrator pt DEV
        wfmcService.connect(new WMConnectInfo("Andra@Administrator","elo@RENNS2015","Wfmc Test","http://10.6.38.90:8080/ix-elo/ix"));

         //Pas 1 : Creare processInstance

        String  processTemplateId = wfmcService.createProcessInstance("3", processInstanceName);

        //Pas 2 : [Optional] Asignare atribute

        //Pas 3 : Startare Process

        String  processInstanceId = wfmcService.startProcess(processTemplateId);


        //Pas 4 : Gasire toate Taskuri de tip REDACTARE_RASPUNS

        WMFilter filtruRedactareRaspuns = WMFilterBuilder.createWMFilterWorkItem().addWorkItemParticipantName("Administrator")
                .addWorkItemName(FluxAprobareOperatiuniNodes.REDACTARE_RASPUNS);
        WMWorkItemIterator wmWorkItemIterator = wfmcService.listWorkItems(filtruRedactareRaspuns, true);



       //Pas 5 : Claim pentru task-ul Redactare Raspuns care este pe procesul nostru
        String currentWmWorkItemId = null;
        while(wmWorkItemIterator.hasNext()){
            WMWorkItem wmWorkItem = wmWorkItemIterator.tsNext();
            if(wmWorkItem.getProcessInstanceId().equals(processInstanceId)){
                currentWmWorkItemId = wmWorkItem.getId();
                wfmcService.reassignWorkItem(sourceUser,"Andra",processInstanceId, currentWmWorkItemId);
                break;
        }
        }

        //Pas 6 :  Gasire next steps pt pasul actual, in acest caz pt currentWmWorkItemId - > id-ul lui Redactare Raspuns

        List<WMWorkItem> nextSteps = wfmcService.getNextSteps(processInstanceId, currentWmWorkItemId);


        // Pas 7 : Forward pe Respinge (mergem pe pasul de Respingere)

        for (WMWorkItem wmWorkItem : nextSteps) {
            if (wmWorkItem.getName().equals(FluxAprobareOperatiuniNodes.RESPINGE)) {
                wfmcService.setTransition(processInstanceId, currentWmWorkItemId, new String[]{currentWmWorkItemId = wmWorkItem.getId()});
            break;
            }
        }

        //Pas 8 : Gasire next steps pt pasul actual, in acest caz pt currentWmWorkItemId - > id-ul lui Respinge

         nextSteps = wfmcService.getNextSteps(processInstanceId, currentWmWorkItemId);



        // Pas 9 : Forward pe Nod0 (memrgem pe Nod0)
       //nu l-am mai adaugat la enumeratie, trebuia?

        for (WMWorkItem wmWorkItem : nextSteps) {
        //    System.out.println("Node" + wmWorkItem.getName());
            if (wmWorkItem.getName().equals("node 10")) {


                wfmcService.setTransition(processInstanceId, currentWmWorkItemId, new String[]{currentWmWorkItemId = wmWorkItem.getId()});
                break;
            }
        }

        //Ultimul pas : Verificam ca workflow-ul s-a terminat

        WMFilter filtru = WMFilterBuilder.createWMFilterProcessInstance().addProcessInstanceName(processInstanceName);
        WMProcessInstanceIterator wmWorkItemsIterator = wfmcService.listProcessInstances(filtru, true);
        System.out.println("Numar instante proces :" +  wmWorkItemsIterator.getCount());
        while(wmWorkItemsIterator.hasNext()){
            WMProcessInstance wmWorkItem = wmWorkItemsIterator.tsNext();
            System.out.println("Nume instanta proces : " + wmWorkItem.getName() + "\nStare : " + wmWorkItem.getState().stringValue());
        }

    }


}



