package org.wfmc;

import de.elo.ix.client.*;
import org.wfmc.impl.base.WMWorkItemAttributeNames;
import org.wfmc.impl.base.filter.WMFilterBuilder;
import org.wfmc.service.WfmcService;
import org.wfmc.service.WfmcServiceFactory;
import org.wfmc.wapi.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

/**
 * Created by Ioan.Ivan on 3/23/2015.
 */
public class AddressAdd {

    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException, WMWorkflowException {

        String currentWorkItemId = null;
        String lastNodeName = null;
        String processDefinitionId = "11";
        String processInstanceName = "Address add";

        ResourceBundle configBundle = ResourceBundle.getBundle("wapi-elo-renns_audit");
        WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(convertResourceBundleToProperties(configBundle));
        WfmcService wfmcService = wfmcServiceFactory.getInstance();

        wfmcService.connect(new WMConnectInfo("Andra@Administrator", "elo@RENNS2015", "Wfmc Test", "http://10.6.38.90:8080/ix-elo/ix"));

        String tempProcessInstance = wfmcService.createProcessInstance(processDefinitionId, processInstanceName);

        wfmcService.assignProcessInstanceAttribute(tempProcessInstance, "NUME_JUDET", "BUZAU");

        String activeProcessInstanceId = wfmcService.startProcess(tempProcessInstance);

        WMFilter wmFilter = new WMFilterBuilder().createWMFilterWorkItem().addWorkItemName("ADDRESS_ADD_DRAFT").
                addWorkItemParticipantName("UAT_${ID_UAT}.REFERENT");
        WMWorkItemIterator wmWorkItemIterator = wfmcService.listWorkItems(wmFilter, true);

        while (wmWorkItemIterator.hasNext()) {
            WMWorkItem wmWorkItem = wmWorkItemIterator.tsNext();
            if (wmWorkItem.getProcessInstanceId().equals(activeProcessInstanceId)) {
                currentWorkItemId = wmWorkItem.getId();
                lastNodeName = wmWorkItem.getName();
                System.out.println("Task for user " + wmWorkItem.getParticipant().getName() + " are : " + wmWorkItem.getName());
            }
        }

        List<WMWorkItem> nextSteps = wfmcService.getNextSteps(activeProcessInstanceId, currentWorkItemId);
        for (WMWorkItem wmWorkItem : nextSteps) {
            wfmcService.assignWorkItemAttribute(activeProcessInstanceId, currentWorkItemId, WMWorkItemAttributeNames.TRANSITION_NEXT_WORK_ITEM_ID.toString(), wmWorkItem.getId());
            System.out.println("Next step for " + lastNodeName + " is " + wmWorkItem.getName());
        }
        wfmcService.completeWorkItem(activeProcessInstanceId, currentWorkItemId);

        wfmcService.assignWorkItemAttribute(activeProcessInstanceId, currentWorkItemId, "NUME_JUDET", "ILFOV");
        wfmcService.assignWorkItemAttribute(activeProcessInstanceId, currentWorkItemId, "NUME_UAT", "SECTOR 5");

        WMFilter wmFilter1 = new WMFilterBuilder().createWMFilterWorkItem().addWorkItemName("ADDRESS_ADD_RECEIVED").
                addWorkItemParticipantName("UAT_${ID_UAT}.CONSILIUL_LOCAL");
        WMWorkItemIterator wmWorkItemIterator1 = wfmcService.listWorkItems(wmFilter1, true);

        while (wmWorkItemIterator1.hasNext()) {
            WMWorkItem wmWorkItem = wmWorkItemIterator1.tsNext();
            lastNodeName = wmWorkItem.getName();
            System.out.println("Task for user " + wmWorkItem.getParticipant().getName() + " are : " + wmWorkItem.getName());
            if (wmWorkItem.getProcessInstanceId().equals(activeProcessInstanceId)) {
                currentWorkItemId = wmWorkItem.getId();
            }
        }

        List<WMWorkItem> nextSteps1 = wfmcService.getNextSteps(activeProcessInstanceId, currentWorkItemId);
        for (WMWorkItem wmWorkItem : nextSteps1) {
            System.out.println("Next step for " + lastNodeName + " is " + wmWorkItem.getName());
            if (wmWorkItem.getName().equals("ADDRESS_ADD_APROBAT")) {
                wfmcService.assignWorkItemAttribute(activeProcessInstanceId, currentWorkItemId, WMWorkItemAttributeNames.TRANSITION_NEXT_WORK_ITEM_ID.toString(), wmWorkItem.getId());
            }
        }

        WFDiagram wfDiagram = getConnection().ix().checkoutWorkFlow(activeProcessInstanceId, WFTypeC.ACTIVE, WFDiagramC.mbAll, LockC.NO);
        String objId = wfDiagram.getObjId();
        Sord sord = getConnection().ix().checkoutSord(objId, SordC.mbAll, LockC.NO);
        ObjKey[] objKeys = sord.getObjKeys();
        for (ObjKey objKey : objKeys) {
            System.out.println("Nume atribut = " + objKey.getName() + " si valoarea = " + Arrays.toString(objKey.getData()));
        }
        wfmcService.completeWorkItem(activeProcessInstanceId, currentWorkItemId);

        WMFilter wmFilter2 = WMFilterBuilder.createWMFilterProcessInstance().isActive(false).addProcessInstanceName(processInstanceName);
        WMProcessInstanceIterator wmProcessInstanceIterator = wfmcService.listProcessInstances(wmFilter2, true);
        while (wmProcessInstanceIterator.hasNext()) {
            WMProcessInstance processInstance = wmProcessInstanceIterator.tsNext();
            System.out.println(processInstance == null ? "null process instance" : "Process instance id = " + processInstance.getId());
            System.out.println(processInstance == null ? "null process instance" : "Process instance name = " + processInstance.getName());
            System.out.println(processInstance == null || processInstance.getState() == null ? "Process instance state = " + "null": "Process instance state = " + processInstance.getState().stringValue() );
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

    public static IXConnection getConnection() throws RemoteException {

        IXConnFactory factory = new IXConnFactory("http://10.6.38.90:8080/ix-elo/ix", "TestApp", "1.0");
        IXConnection iXConnection = factory.create("Administrator", "elo@RENNS2015", null, null);
        return iXConnection;
    }

}
