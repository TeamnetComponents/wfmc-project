package ro.teamnet.wfmc.service;

import org.junit.Assert;
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
import ro.teamnet.wfmc.audit.domain.WMEventAudit;
import ro.teamnet.wfmc.audit.domain.WMEventAuditAttribute;
import ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit;
import ro.teamnet.wfmc.audit.service.WfmcAuditQueryService;

import javax.inject.Inject;

import static junit.framework.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WfmcAuditTestApplication.class})
@IntegrationTest
@ActiveProfiles("test-oracle")
public class WfmcWorkflowTest {

    @Inject
    private WfmcAuditQueryService wfmcAuditQueryService;
    @Inject
    private WfmcAuditedService wfmcService;

    private Logger log = LoggerFactory.getLogger(WfmcWorkflowTest.class);

    //TODO : JavaDoc pentru clasele si metodele publice
    @Test
    @Transactional("wfmcAuditTransactionManager")
    public void testAllWorkflowOperations() throws WMWorkflowException {
        String processDefinitionId = "3";
        String workItemId = "1";
        String attrName = "attribute name";
        String attrValue = "attribute value";
        String newAttrValue = "new value";
        String nextNodeTrimite = "7";
        String nextNodeRespinge = "8";
        String processInstanceName = "TestAudit";
        String username = "Andra@Administrator";

        wfmcService.connect(new WMConnectInfo(username, "elo@RENNS2015", "Wfmc Test", "http://10.6.38.90:8080/ix-elo/ix"));

        String procInstIdTemp = wfmcService.createProcessInstance(processDefinitionId, processInstanceName);
        WMProcessInstanceAudit wmProcessInstanceAudit = wfmcAuditQueryService.findWMProcessInstanceAuditByProcessDefinitionBusinessName(processInstanceName);
        assertEquals("CreateProcessInstance: The process definition busniess name isn't the same!", processInstanceName, wmProcessInstanceAudit.getProcessDefinitionBusinessName());
        log.info("Process definition business name is : {}", wmProcessInstanceAudit.getProcessDefinitionBusinessName());
        assertEquals("CreateProcessInstance: The process definition id isn't the same!", processDefinitionId, wmProcessInstanceAudit.getProcessDefinitionId());
        log.info("Process definition id is : {}", wmProcessInstanceAudit.getProcessDefinitionId());
        log.info("Process instance id is : {}", wmProcessInstanceAudit.getProcessInstanceId());


        wfmcService.assignProcessInstanceAttribute(procInstIdTemp, attrName, attrValue);
        WMEventAuditAttribute wmEventAuditAttribute = wfmcAuditQueryService.findWMEventAuditAttributeByAttributeValue(attrValue);
        assertEquals("AssignProcessInstanceAttribute: The attribute value isn't the same!", attrValue, wmEventAuditAttribute.getAttributeValue());
        log.info("Attribute value: {}", wmEventAuditAttribute.getAttributeValue());
        assertEquals("AssignProcessInstanceAttribute: The attribute name isn't the same!", attrName, wmEventAuditAttribute.getWmAttributeAudit().getAttributeName());
        log.info("Attribute name: {}", wmEventAuditAttribute.getWmAttributeAudit().getAttributeName());



        wfmcService.assignProcessInstanceAttribute(procInstIdTemp, attrName, newAttrValue);
        WMEventAuditAttribute wmEventAuditAttribute1 = wfmcAuditQueryService.findWMEventAuditAttributeByAttributeValue(attrValue);
        assertEquals("AssignProcessInstanceAttribute: The attribute value isn't the same!", attrValue, wmEventAuditAttribute1.getAttributeValue());
        log.info("Attribute value: {}", wmEventAuditAttribute1.getAttributeValue());
        assertEquals("AssignProcessInstanceAttribute: The attribute name isn't the same!", attrName, wmEventAuditAttribute1.getWmAttributeAudit().getAttributeName());
        log.info("Attribute name: {}", wmEventAuditAttribute1.getWmAttributeAudit().getAttributeName());

        String currentProcessInstanceId = wfmcService.startProcess(procInstIdTemp);
        WMEventAudit[] wmEventAudit = wfmcAuditQueryService.findWMEventAuditByUsername(username);
        Assert.assertEquals("StartProcess: The new process instance id isn't the same!", currentProcessInstanceId, wmProcessInstanceAudit.getProcessInstanceId());
        log.info("New process instance id: {}", wmProcessInstanceAudit.getProcessInstanceId());
        Assert.assertEquals("StartProcess: The username isn't the same!", username, wmEventAudit[wmEventAudit.length - 1].getUsername());
        log.info("Username: {}", wmEventAudit[wmEventAudit.length - 1].getUsername());
        Assert.assertNotNull(wmEventAudit[wmEventAudit.length - 1].getEventCode());
        log.info("Event code: {}", wmEventAudit[wmEventAudit.length - 1].getEventCode());

        wfmcService.reassignWorkItem("Andra", "Daniel", currentProcessInstanceId, workItemId);
        wfmcService.assignWorkItemAttribute(currentProcessInstanceId, workItemId, WMWorkItemAttributeNames.TRANSITION_NEXT_WORK_ITEM_ID.toString(), nextNodeTrimite);
        wfmcService.assignWorkItemAttribute(currentProcessInstanceId, workItemId, WMWorkItemAttributeNames.TRANSITION_NEXT_WORK_ITEM_ID.toString(), nextNodeRespinge);
        wfmcService.completeWorkItem(currentProcessInstanceId, workItemId);
        wfmcService.abortProcessInstance(currentProcessInstanceId);

    }
}
