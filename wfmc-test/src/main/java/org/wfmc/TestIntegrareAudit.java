package org.wfmc;

import org.wfmc.impl.base.WMWorkItemAttributeNames;
import org.wfmc.service.WfmcService;
import org.wfmc.service.WfmcServiceFactory;
import org.wfmc.wapi.WMConnectInfo;
import org.wfmc.wapi.WMWorkflowException;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by Ioan.Ivan on 3/18/2015.
 */
public class TestIntegrareAudit {

    public static void main(String[] args)
        throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, WMWorkflowException {

        String processDefinitionId= "3";
        String workItemId = "1";
        String attrName = "nume atribut";
        String[] attrValue = new String[] {"atribut 1", "atribut 2"};
        String newAttrValue = "new value";
        String nextNodeTrimite = "7";
        String nextNodeRespinge = "8";
        String processInstanceName = "Instanta flux aprobare operatiuni 3";
        ResourceBundle configBundle = ResourceBundle.getBundle("wapi-elo-renns_audit");
        WfmcServiceFactory wfmcServiceFactory = new WfmcServiceFactory(convertResourceBundleToProperties(configBundle));
        WfmcService wfmcService = wfmcServiceFactory.getInstance();

        wfmcService.connect(new WMConnectInfo("Andra@Administrator", "elo@RENNS2015", "Wfmc Test", "http://10.6.38.90:8080/ix-elo/ix"));


        String procInstIdTemp = wfmcService.createProcessInstance(processDefinitionId, processInstanceName);
        wfmcService.assignProcessInstanceAttribute(procInstIdTemp, attrName, attrValue);
        wfmcService.assignProcessInstanceAttribute(procInstIdTemp, attrName, newAttrValue);
        String currentProcessInstanceId = wfmcService.startProcess(procInstIdTemp);
        wfmcService.reassignWorkItem("Andra", "Daniel", currentProcessInstanceId, workItemId);
        wfmcService.assignWorkItemAttribute(currentProcessInstanceId, workItemId, WMWorkItemAttributeNames.TRANSITION_NEXT_WORK_ITEM_ID.toString(), nextNodeTrimite);
        wfmcService.assignWorkItemAttribute(currentProcessInstanceId, workItemId, WMWorkItemAttributeNames.TRANSITION_NEXT_WORK_ITEM_ID.toString(), nextNodeRespinge);
        wfmcService.completeWorkItem(currentProcessInstanceId, workItemId);
        wfmcService.abortProcessInstance(currentProcessInstanceId);

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
