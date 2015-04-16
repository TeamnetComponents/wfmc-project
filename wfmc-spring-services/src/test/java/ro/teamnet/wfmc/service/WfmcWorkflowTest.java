package ro.teamnet.wfmc.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.wfmc.impl.base.WMWorkItemAttributeNames;
import org.wfmc.wapi.WMConnectInfo;
import org.wfmc.wapi.WMWorkflowException;
import ro.teamnet.wfmc.audit.service.WfmcAuditQueryService;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WfmcAuditTestApplication.class})
@IntegrationTest
@ActiveProfiles("test-oracle")
public class WfmcWorkflowTest {
    @Inject
    private WfmcAuditQueryService wfmcAuditQueryService;
    @Inject
    private WfmcAuditedService wfmcService;

    private Logger log = LoggerFactory.getLogger(WfmcAuditedServiceTest.class);

    //TODO: de completat acest test cu asserturi
    @Test
    @Transactional("wfmcAuditTransactionManager")
    public void testAllWorkflowOperations() throws WMWorkflowException {
        String processDefinitionId = "3";
        String workItemId = "1";
        String attrName = "nume atribut";
        String[] attrValue = new String[]{"atribut 1", "atribut 2"};
        String newAttrValue = "new value";
        String nextNodeTrimite = "7";
        String nextNodeRespinge = "8";
        String processInstanceName = "TestAudit";

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
}
