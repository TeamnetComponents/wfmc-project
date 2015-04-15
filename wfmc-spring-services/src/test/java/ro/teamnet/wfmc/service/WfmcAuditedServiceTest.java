package ro.teamnet.wfmc.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.wfmc.wapi.WMConnectInfo;
import org.wfmc.wapi.WMWorkflowException;
import ro.teamnet.wfmc.audit.domain.WMAttributeAuditProcessInstance;
import ro.teamnet.wfmc.audit.domain.WMEventAudit;
import ro.teamnet.wfmc.audit.domain.WMEventAuditAttribute;
import ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit;
import ro.teamnet.wfmc.audit.service.WfmcAuditQueryService;
import ro.teamnet.wfmc.service.mock.WfmcServiceMockImpl;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WfmcAuditTestApplication.class})
@IntegrationTest
@ActiveProfiles("test")
public class WfmcAuditedServiceTest {

    private static final String PROC_DEF_ID = "pd_id";
    private static final String USER_IDENTIFICATION = "testUser";
    private static final String PROC_INST_ID = "pi_id";
    private static final String WORK_ITEM_ID = "wi_id";

    @Inject
    private WfmcAuditQueryService wfmcAuditQueryService;
    @Inject
    private WfmcAuditedService wfmcService;

    private Logger log = LoggerFactory.getLogger(WfmcAuditedServiceTest.class);

    @Before
    public void prepareTestsForAuditing() throws WMWorkflowException {
        wfmcService.connect(new WMConnectInfo(USER_IDENTIFICATION, "", "", ""));
    }

    @After
    public void endTestsForAuditing() throws WMWorkflowException {
        wfmcService.disconnect();
    }

    @Test
    @Transactional("wfmcAuditTransactionManager")
    public void testCreateProcessInstance() throws WMWorkflowException {
        String processInstanceId = wfmcService.createProcessInstance(PROC_DEF_ID, "my procInstName");
        Assert.assertEquals(WfmcServiceMockImpl.PROCESS_INSTANCE_ID, processInstanceId);
        WMProcessInstanceAudit wmProcessInstanceAudit = wfmcAuditQueryService.findWMProcessInstanceAuditByProcessDefinitionBusinessName("my procInstName");
        WMEventAudit wmEventAudit = wfmcAuditQueryService.findWMEventAuditByUsername(USER_IDENTIFICATION);
        Assert.assertEquals("The process definition id isn't the same!", wmProcessInstanceAudit.getProcessDefinitionId(), PROC_DEF_ID);
        log.info("Process definition id: {}", wmProcessInstanceAudit.getProcessDefinitionId());
        Assert.assertEquals("The process instance name isn't the same!", wmProcessInstanceAudit.getProcessDefinitionBusinessName(), "my procInstName");
        log.info("Process instance name: {}", wmProcessInstanceAudit.getProcessDefinitionBusinessName());
        Assert.assertEquals("The process instance id isn't the same!", wmProcessInstanceAudit.getProcessInstanceId(), "procInstId");
        log.info("Process instance id: {}", wmProcessInstanceAudit.getProcessInstanceId());
        Assert.assertEquals("The username isn't the same!", wmEventAudit.getUsername(), "testUser");
        log.info("Username: {}", wmEventAudit.getUsername());
    }

    @Test
    @Transactional("wfmcAuditTransactionManager")
    public void testAssignProcessInstanceAttribute() throws WMWorkflowException {
        String processInstanceId = wfmcService.createProcessInstance(PROC_DEF_ID, "my procInstName");
        WMProcessInstanceAudit wmProcessInstanceAudit = wfmcAuditQueryService.findWMProcessInstanceAuditByProcessDefinitionBusinessName("my procInstName");
        log.info("Process definition id: {}", wmProcessInstanceAudit.getProcessDefinitionId());
        Assert.assertEquals(WfmcServiceMockImpl.PROCESS_INSTANCE_ID, processInstanceId);

        wfmcService.assignProcessInstanceAttribute(processInstanceId, "attr1", "1");
        WMEventAuditAttribute wmEventAuditAttribute = wfmcAuditQueryService.findWMEventAuditAttributeByAttributeValue("1");
        Assert.assertEquals("The attribute value isn't the same!", wmEventAuditAttribute.getAttributeValue(), "1");
        log.info("Attribute value: {}", wmEventAuditAttribute.getAttributeValue());
        Assert.assertEquals("The attribute name isn't the same!", wmEventAuditAttribute.getWmAttributeAudit().getAttributeName(), "attr1");
        log.info("Attribute audit name: {}", wmEventAuditAttribute.getWmAttributeAudit().getAttributeName());
        WMAttributeAuditProcessInstance wmAttributeAuditProcessInstance = wfmcAuditQueryService.findWMAttributeAuditProcessInstanceByWMProcessInstanceAudit(wmProcessInstanceAudit);
        Assert.assertEquals("The process instance id isn't the same!", wmAttributeAuditProcessInstance.getWmProcessInstanceAudit().getProcessInstanceId(),
                wmProcessInstanceAudit.getProcessInstanceId());
        log.info("Process instance id: {}", wmAttributeAuditProcessInstance.getWmProcessInstanceAudit().getProcessInstanceId());
        Assert.assertEquals("The process definition id isn't the same!", wmAttributeAuditProcessInstance.getWmProcessInstanceAudit().getProcessDefinitionId(),
                wmProcessInstanceAudit.getProcessDefinitionId());
        log.info("Process definiton id: {}", wmAttributeAuditProcessInstance.getWmProcessInstanceAudit().getProcessDefinitionId());
        Assert.assertEquals("The process definition business name isn't the same!", wmAttributeAuditProcessInstance.getWmProcessInstanceAudit().getProcessDefinitionBusinessName(),
                wmProcessInstanceAudit.getProcessDefinitionBusinessName());
        log.info("Process definition business name: {}", wmAttributeAuditProcessInstance.getWmProcessInstanceAudit().getProcessDefinitionBusinessName());
}

    @Test
    @Transactional("wfmcAuditTransactionManager")
    public void testStartProcess() throws WMWorkflowException {
        String newProcessInstanceId = wfmcService.startProcess(PROC_INST_ID);
        Assert.assertEquals(WfmcServiceMockImpl.NEW_PROCESS_INSTANCE_ID, newProcessInstanceId);
    }

    @Test
    @Transactional("wfmcAuditTransactionManager")
    public void testAbortProcessInstance() throws WMWorkflowException {
        wfmcService.abortProcessInstance(PROC_INST_ID);
    }

    @Test
    @Transactional("wfmcAuditTransactionManager")
    public void testAssignWorkItemAttribute() throws WMWorkflowException {
        wfmcService.assignWorkItemAttribute(PROC_INST_ID, WORK_ITEM_ID, "attr1", "1");
    }

    @Test
    @Transactional("wfmcAuditTransactionManager")
    public void testReassignWorkItem() throws WMWorkflowException {
        wfmcService.reassignWorkItem("source_user", "target_user", PROC_INST_ID, WORK_ITEM_ID);
    }

    @Test
    @Transactional("wfmcAuditTransactionManager")
    public void testCompleteWorkItem() throws WMWorkflowException {
        wfmcService.completeWorkItem(PROC_INST_ID, WORK_ITEM_ID);
    }


}
