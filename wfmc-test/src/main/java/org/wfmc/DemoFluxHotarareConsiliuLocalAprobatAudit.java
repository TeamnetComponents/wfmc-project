package org.wfmc;

import org.wfmc.impl.base.WMWorkItemAttributeNames;
import org.wfmc.impl.base.filter.WMFilterBuilder;
import org.wfmc.service.WfmcService;
import org.wfmc.service.WfmcServiceFactory;
import org.wfmc.wapi.*;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by Ioan.Ivan on 3/23/2015.
 */
public class DemoFluxHotarareConsiliuLocalAprobatAudit {

    public static void main(String[] args) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, WMWorkflowException {

        WMConnectInfo wmConnectInfo = new WMConnectInfo("Administrator@Administrator", "elo@RENNS2015", "Wfmc Test", "http://10.6.38.90:8080/ix-elo/ix");
        WMConnectInfo wmConnectInfoAndra = new WMConnectInfo("Andra@Administrator", "elo@RENNS2015", "Wfmc Test", "http://10.6.38.90:8080/ix-elo/ix");
        String processDefinitionId = "5";
        String processInstanceName = "Audit hotarare consiliu local";
        String currentWorkItemId = null;

        ResourceBundle configBundle = ResourceBundle.getBundle("wapi-elo-renns_audit");
        WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(convertResourceBundleToProperties(configBundle));
        WfmcService wfmcService = wfmcServiceFactory.getInstance();

        //Conectare la ELO Service.
        wfmcService.connect(wmConnectInfo);

        //Creare instanta de proces
        String tempProcessInstance = wfmcService.createProcessInstance(processDefinitionId, processInstanceName);

        //Atribuire valoare pe atributul UAT cat timp procesul nu este pornit
        wfmcService.assignProcessInstanceAttribute(tempProcessInstance, "UAT", "5");

        //Start process
        String activeProcessInstanceId = wfmcService.startProcess(tempProcessInstance);

        //Atribuire valoare pe atributul TIP DRUM dupa ce a fost pornit procesul
        wfmcService.assignProcessInstanceAttribute(activeProcessInstanceId, "TIPDRUM", "STRADA");

        WMFilter wmFilter = WMFilterBuilder.createWMFilterWorkItem().addWorkItemParticipantName("ELO Service").
                addWorkItemName(FluxHotarareConsiliuLocalNodes.AUTOMATIZARE_ASTEPTARE);

        WMWorkItemIterator wmWorkItemIterator = wfmcService.listWorkItems(wmFilter, true);
        while (wmWorkItemIterator.hasNext()){
            WMWorkItem wmWorkItem = wmWorkItemIterator.tsNext();
            System.out.println("Task for user " + wmWorkItem.getParticipant().getName() + " are : " + wmWorkItem.getName());
            if (wmWorkItem.getProcessInstanceId().equals(activeProcessInstanceId)) {
                currentWorkItemId = wmWorkItem.getId();
                //Reassign work item catre Andra
                wfmcService.reassignWorkItem("ELO Service", "Andra", activeProcessInstanceId, currentWorkItemId);
            }
        }

        WMFilter wmFilterForAndra = WMFilterBuilder.createWMFilterWorkItem().addWorkItemParticipantName("Andra").
                addWorkItemName(FluxHotarareConsiliuLocalNodes.AUTOMATIZARE_ASTEPTARE);
        WMWorkItemIterator wmWorkItemIteratorForAndra = wfmcService.listWorkItems(wmFilterForAndra, true);
        while (wmWorkItemIteratorForAndra.hasNext()) {
            WMWorkItem wmWorkItem = wmWorkItemIteratorForAndra.tsNext();
            currentWorkItemId = wmWorkItem.getId();
            System.out.println("Task for user " + wmWorkItem.getParticipant().getName() + " are : " + wmWorkItem.getName() );
        }

        WMProcessInstance wmProcessInstance = wfmcService.getProcessInstance(activeProcessInstanceId);
        System.out.println(wmProcessInstance == null ? "null process instance" : "Process instance id = " + wmProcessInstance.getId());
        System.out.println(wmProcessInstance == null ? "null process instance" : "Process instance name = " + wmProcessInstance.getName());
        System.out.println(wmProcessInstance == null || wmProcessInstance.getState() == null ? "Process instance state = " + "null": "Process instance state = " + wmProcessInstance.getState().stringValue() );

        //Reconect cu utilizatorul Andra
        wfmcService.disconnect();
        wfmcService.connect(wmConnectInfoAndra);

        //Atribuire atribut pe sord cand procesul se afla in primul work item
        wfmcService.assignWorkItemAttribute(activeProcessInstanceId, currentWorkItemId, "UAT", "15");

        List<WMWorkItem> nextSteps = wfmcService.getNextSteps(activeProcessInstanceId, currentWorkItemId);

        for (WMWorkItem wmWorkItem : nextSteps) {
            if (FluxHotarareConsiliuLocalNodes.APROBAT.equals(wmWorkItem.getName())) {
                //Atribuire atribut care sa specifice nodul urmator din proces
                wfmcService.assignWorkItemAttribute(activeProcessInstanceId, currentWorkItemId, WMWorkItemAttributeNames.TRANSITION_NEXT_WORK_ITEM_ID.toString(), wmWorkItem.getId());
            }
        }

        //Finalizare work item si trimiterea fluxului catre nodul precizat anterior
        wfmcService.completeWorkItem(activeProcessInstanceId, currentWorkItemId);

        WMFilter wmFilter2 = WMFilterBuilder.createWMFilterProcessInstance().isActive(false).addProcessInstanceName(processInstanceName);
        WMProcessInstanceIterator wmProcessInstanceIterator = wfmcService.listProcessInstances(wmFilter2, true);
        while (wmProcessInstanceIterator.hasNext()) {
            WMProcessInstance wmProcessInstanceTemp = wmProcessInstanceIterator.tsNext();
            System.out.println(wmProcessInstanceTemp == null ? "null process instance" : "Process instance id = " + wmProcessInstanceTemp.getId());
            System.out.println(wmProcessInstanceTemp == null ? "null process instance" : "Process instance name = " + wmProcessInstanceTemp.getName());
            System.out.println(wmProcessInstanceTemp == null || wmProcessInstance.getState() == null ? "Process instance state = " + "null": "Process instance state = " + wmProcessInstanceTemp.getState().stringValue() );
        }
    }

    static Properties convertResourceBundleToProperties(ResourceBundle resource) {
        Properties properties = new Properties();

        Enumeration<String> keys = resource.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            properties.put(key, resource.getString(key));
        }

        return properties;
    }


}
