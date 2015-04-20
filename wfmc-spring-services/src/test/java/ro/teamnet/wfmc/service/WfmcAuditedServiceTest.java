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
import org.wfmc.audit.WMAEventCode;
import org.wfmc.wapi.WMConnectInfo;
import org.wfmc.wapi.WMWorkItemState;
import org.wfmc.wapi.WMWorkflowException;
import ro.teamnet.wfmc.audit.constants.WfmcAuditedParameter;
import ro.teamnet.wfmc.audit.domain.*;
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
    private static final String PROC_INST_NAME = "p_name";
    private String processInstanceId;

    @Inject
    private WfmcAuditQueryService wfmcAuditQueryService;
    @Inject
    private WfmcAuditedService wfmcService;

    private Logger log = LoggerFactory.getLogger(WfmcAuditedServiceTest.class);

    @Before
    public void prepareTestsForAuditing() throws WMWorkflowException {
        wfmcService.connect(new WMConnectInfo(USER_IDENTIFICATION, "", "", ""));
        processInstanceId = wfmcService.createProcessInstance(PROC_DEF_ID, PROC_INST_NAME);
        Assert.assertEquals("The process instance id isn't the same!", WfmcAuditedParameter.PROCESS_INSTANCE_ID, processInstanceId);
    }

    @After
    public void endTestsForAuditing() throws WMWorkflowException {
        wfmcService.disconnect();
    }

    @Test
    @Transactional("wfmcAuditTransactionManager")
    public void testCreateProcessInstance() throws WMWorkflowException {

        WMProcessInstanceAudit wmProcessInstanceAudit = wfmcAuditQueryService.findWMProcessInstanceAuditByProcessDefinitionBusinessName(PROC_INST_NAME);
        WMEventAudit[] wmEventAudit = wfmcAuditQueryService.findWMEventAuditByUsername(USER_IDENTIFICATION);

        Assert.assertEquals("CreateProcess: The process instance id isn't the same!", wmProcessInstanceAudit.getProcessInstanceId(), WfmcAuditedParameter.PROCESS_INSTANCE_ID);
        log.info("Process instance id: {}", wmProcessInstanceAudit.getProcessInstanceId());
        Assert.assertEquals("CreateProcess: The process definition id isn't the same!", wmProcessInstanceAudit.getProcessDefinitionId(),PROC_DEF_ID);
        log.info("Process definition id: {}", wmProcessInstanceAudit.getProcessDefinitionId());
        Assert.assertEquals("CreateProcess: The process instance name isn't the same!", wmProcessInstanceAudit.getProcessDefinitionBusinessName(), PROC_INST_NAME);
        log.info("Process instance name: {}", wmProcessInstanceAudit.getProcessDefinitionBusinessName());
        Assert.assertEquals("CreateProcess: The username isn't the same!", wmEventAudit[0].getUsername(), USER_IDENTIFICATION);
        log.info("Username: {}", wmEventAudit[0].getUsername());
    }

    @Test
    @Transactional("wfmcAuditTransactionManager")
    public void testAssignProcessInstanceAttribute() throws WMWorkflowException {

        wfmcService.assignProcessInstanceAttribute(processInstanceId, "attr1", "1");

        WMProcessInstanceAudit wmProcessInstanceAudit = wfmcAuditQueryService.findWMProcessInstanceAuditByProcessDefinitionBusinessName(PROC_INST_NAME);
        WMEventAuditAttribute wmEventAuditAttribute = wfmcAuditQueryService.findWMEventAuditAttributeByAttributeValue("1");
        WMAttributeAuditProcessInstance wmAttributeAuditProcessInstance = wfmcAuditQueryService.findWMAttributeAuditProcessInstanceByWMProcessInstanceAudit(wmProcessInstanceAudit);

        Assert.assertEquals("AssignnProcessInstanceAttribute: The process instance id isn't the same!", wmAttributeAuditProcessInstance.getWmProcessInstanceAudit().getProcessInstanceId(), WfmcAuditedParameter.PROCESS_INSTANCE_ID);
        log.info("Process instance id: {}", wmAttributeAuditProcessInstance.getWmProcessInstanceAudit().getProcessInstanceId());
        Assert.assertEquals("AssignnProcessInstanceAttribute: The process definition id isn't the same!", wmAttributeAuditProcessInstance.getWmProcessInstanceAudit().getProcessDefinitionId(), PROC_DEF_ID);
        log.info("Process definition id: {}", wmProcessInstanceAudit.getProcessDefinitionId());
        Assert.assertEquals("AssignnProcessInstanceAttribute: The process definition business name isn't the same!", wmAttributeAuditProcessInstance.getWmProcessInstanceAudit().getProcessDefinitionBusinessName(), PROC_INST_NAME);
        log.info("Process definition business name: {}", wmAttributeAuditProcessInstance.getWmProcessInstanceAudit().getProcessDefinitionBusinessName());
        Assert.assertEquals("AssignnProcessInstanceAttribute: The attribute value isn't the same!", wmEventAuditAttribute.getAttributeValue(), "1");
        log.info("Attribute value: {}", wmEventAuditAttribute.getAttributeValue());
        Assert.assertEquals("AssignnProcessInstanceAttribute: The attribute name isn't the same!", wmEventAuditAttribute.getWmAttributeAudit().getAttributeName(), "attr1");
        log.info("Attribute name: {}", wmEventAuditAttribute.getWmAttributeAudit().getAttributeName());
    }

    @Test
    @Transactional("wfmcAuditTransactionManager")
    public void testStartProcess() throws WMWorkflowException {

        String newProcessInstanceId = wfmcService.startProcess(processInstanceId);

        WMProcessInstanceAudit wmProcessInstanceAudit = wfmcAuditQueryService.findWMProcessInstanceAuditByProcessDefinitionBusinessName(PROC_INST_NAME);
        WMEventAudit wmEventAudit = wfmcAuditQueryService.findWMEventAuditByEventCode(WMAEventCode.STARTED_PROCESS_INSTANCE_INT);

        Assert.assertEquals("StartProcess: The new process instance id isn't the same!", newProcessInstanceId, WfmcServiceMockImpl.NEW_PROCESS_INSTANCE_ID);
        log.info("New process instance id: {}", newProcessInstanceId);
        Assert.assertEquals("StartProcess: The process definition id isn't the same!", PROC_DEF_ID,wmProcessInstanceAudit.getProcessDefinitionId());
        log.info("Process definition id: {}", wmProcessInstanceAudit.getProcessDefinitionId());
        Assert.assertEquals("StartProcess: The process instance name isn't the same!", PROC_INST_NAME,wmProcessInstanceAudit.getProcessDefinitionBusinessName());
        log.info("Process instance name: {}", wmProcessInstanceAudit.getProcessDefinitionBusinessName());
        Assert.assertEquals("StartProcess: The old process instance id isn't the same!", WfmcServiceMockImpl.PROCESS_INSTANCE_ID, processInstanceId);
        log.info("Old process instance id: {}", wmProcessInstanceAudit.getProcessInstanceId());
        Assert.assertEquals("Not equal username for startProcess",USER_IDENTIFICATION, wmEventAudit.getUsername());
        log.info("Username: {}", wmEventAudit.getUsername());
        Assert.assertNotNull(wmEventAudit.getEventCode());
        log.info("Event code: {}", wmEventAudit.getEventCode());
    }

    @Test
    @Transactional("wfmcAuditTransactionManager")
    public void testAbortProcessInstance() throws WMWorkflowException {

        wfmcService.abortProcessInstance(processInstanceId);

        WMProcessInstanceAudit wmProcessInstanceAudit = wfmcAuditQueryService.findWMProcessInstanceAuditByProcessDefinitionBusinessName(PROC_INST_NAME);

        Assert.assertEquals("AbortProcessInstance: The process definition id isn't the same!", PROC_DEF_ID,wmProcessInstanceAudit.getProcessDefinitionId());
        log.info("Process definition id: {}", wmProcessInstanceAudit.getProcessDefinitionId());
        Assert.assertEquals("AbortProcessInstance: The process definition business name isn't the same!", PROC_INST_NAME, wmProcessInstanceAudit.getProcessDefinitionBusinessName());
        log.info("Process instance name: {}", wmProcessInstanceAudit.getProcessDefinitionBusinessName());
        Assert.assertEquals("AbortProcessInstance: The process instance id isn't the same!", WfmcAuditedParameter.PROCESS_INSTANCE_ID, wmProcessInstanceAudit.getProcessInstanceId());
        log.info("Process instance id: {}", wmProcessInstanceAudit.getProcessInstanceId());

    }

    @Test
    @Transactional("wfmcAuditTransactionManager")
    public void testAssignWorkItemAttribute() throws WMWorkflowException {

        wfmcService.assignWorkItemAttribute(WfmcAuditedParameter.PROCESS_INSTANCE_ID, WORK_ITEM_ID, "attr1", "1");

        WMEventAuditAttribute wmEventAuditAttribute = wfmcAuditQueryService.findWMEventAuditAttributeByAttributeValue("1");
        WMWorkItemAudit wmWorkItemAudit = wfmcAuditQueryService.findWMWorkItemAuditByWorkItemId(WORK_ITEM_ID);

        Assert.assertEquals("AssignWorkItemAttribute: The attribute value isn't the same!", wmEventAuditAttribute.getAttributeValue(), "1");
        log.info("Attribute value: {}", wmEventAuditAttribute.getAttributeValue());
        Assert.assertEquals("AssignWorkItemAttribute: The attribute name isn't the same!", wmEventAuditAttribute.getWmAttributeAudit().getAttributeName(), "attr1");
        log.info("Attribute audit name: {}", wmEventAuditAttribute.getWmAttributeAudit().getAttributeName());
        Assert.assertEquals("AssignWorkItemAttribute: The workItemId isn't the same!", wmWorkItemAudit.getWorkItemId(), WORK_ITEM_ID);
        log.info("WorkItemId : {}", wmWorkItemAudit.getWorkItemId());
        Assert.assertEquals("AssignWorkItemAttribute: the process instance id isn't the same!", WfmcAuditedParameter.PROCESS_INSTANCE_ID, wmWorkItemAudit.getWmProcessInstanceAudit().getProcessInstanceId());
        log.info("Process instance id: {}", wmWorkItemAudit.getWmProcessInstanceAudit().getProcessInstanceId());
        Assert.assertEquals("AssignWorkItemAttribute: the process definition id isn't the same!", PROC_DEF_ID, wmWorkItemAudit.getWmProcessInstanceAudit().getProcessDefinitionId());
        log.info("Process definition id: {}", wmWorkItemAudit.getWmProcessInstanceAudit().getProcessDefinitionId());
        Assert.assertEquals("AssignWorkItemAttribute: the process definition business name isn't the same!", PROC_INST_NAME, wmWorkItemAudit.getWmProcessInstanceAudit().getProcessDefinitionBusinessName());
        log.info("Process definition business name: {}", wmWorkItemAudit.getWmProcessInstanceAudit().getProcessDefinitionBusinessName());
    }

    @Test
    @Transactional("wfmcAuditTransactionManager")
    public void testReassignWorkItem() throws WMWorkflowException {

        wfmcService.reassignWorkItem("source_user", "target_user", processInstanceId, WORK_ITEM_ID);

        WMWorkItemAudit wmWorkItemAudit = wfmcAuditQueryService.findWMWorkItemAuditByWorkItemId(WORK_ITEM_ID);
        WMEventAuditWorkItem wmEventAuditWorkItem = wfmcAuditQueryService.findWMEventAuditWorkItemByWmWorkItemAudit(wmWorkItemAudit);

        Assert.assertEquals("ReassignWorkItem: the work item id isn't the same!", wmWorkItemAudit.getWorkItemId(), WORK_ITEM_ID);
        log.info("Work item id: {}", wmWorkItemAudit.getWorkItemId());
        Assert.assertEquals("ReassignWorkItem: the process instance id isn't the same!", wmWorkItemAudit.getWmProcessInstanceAudit().getProcessInstanceId(), processInstanceId);
        log.info("Process instance id: {}", wmWorkItemAudit.getWmProcessInstanceAudit().getProcessInstanceId());
        log.info("Process definition id: {}", wmWorkItemAudit.getWmProcessInstanceAudit().getProcessDefinitionId());
        log.info("Process definition business name: {}", wmWorkItemAudit.getWmProcessInstanceAudit().getProcessDefinitionBusinessName());
        Assert.assertEquals("ReassignWorkItem: The event audit work item state isn't the same!", wmEventAuditWorkItem.getWorkItemState(), WMWorkItemState.OPEN_RUNNING_TAG);
        log.info("The event audit work item state: {}", wmEventAuditWorkItem.getWorkItemState());
        Assert.assertEquals("ReassignWorkItem: The event audit work item id isn't the same!", wmEventAuditWorkItem.getWmWorkItemAudit().getWorkItemId(), WORK_ITEM_ID);
        log.info("The event audit work item id: {}", wmEventAuditWorkItem.getWmWorkItemAudit().getWorkItemId());
        Assert.assertEquals("ReassignWorkItem: The username isn't the same!", wmEventAuditWorkItem.getUsername(), "testUser");
        log.info("Username: {}", wmEventAuditWorkItem.getUsername());
        Assert.assertEquals("ReassignWorkItem: The event code isn't the same!", wmEventAuditWorkItem.getEventCode(), Integer.valueOf(WMAEventCode.REASSIGNED_WORK_ITEM_INT));
        log.info("Event code: {}", wmEventAuditWorkItem.getEventCode());
    }

    @Test
    @Transactional("wfmcAuditTransactionManager")
    public void testCompleteWorkItem() throws WMWorkflowException {

        wfmcService.completeWorkItem(WfmcAuditedParameter.PROCESS_INSTANCE_ID, WORK_ITEM_ID);

        WMWorkItemAudit wmWorkItemAudit = wfmcAuditQueryService.findWMWorkItemAuditByWorkItemId(WORK_ITEM_ID);
        WMEventAuditWorkItem wmEventAuditWorkItem = wfmcAuditQueryService.findWMEventAuditWorkItemByWmWorkItemAudit(wmWorkItemAudit);

        Assert.assertEquals("CompleteWorkItem: the work item id isn't the same!", wmWorkItemAudit.getWorkItemId(), WORK_ITEM_ID);
        log.info("Work item id: {}", wmWorkItemAudit.getWorkItemId());
        Assert.assertEquals("CompleteWorkItem: the process instance id isn't the same!", wmWorkItemAudit.getWmProcessInstanceAudit().getProcessInstanceId(), processInstanceId);
        log.info("Process instance id: {}", wmWorkItemAudit.getWmProcessInstanceAudit().getProcessInstanceId());
        log.info("Process definition id: {}", wmWorkItemAudit.getWmProcessInstanceAudit().getProcessDefinitionId());
        log.info("Process definition business name: {}", wmWorkItemAudit.getWmProcessInstanceAudit().getProcessDefinitionBusinessName());
        Assert.assertEquals("CompleteWorkItem: The event audit work item state isn't the same!", wmEventAuditWorkItem.getWorkItemState(), WMWorkItemState.OPEN_RUNNING_TAG);
        log.info("The event audit work item state: {}", wmEventAuditWorkItem.getWorkItemState());
        Assert.assertEquals("CompleteWorkItem: The event audit work item id isn't the same!", wmEventAuditWorkItem.getWmWorkItemAudit().getWorkItemId(), WORK_ITEM_ID);
        log.info("The event audit work item id: {}", wmEventAuditWorkItem.getWmWorkItemAudit().getWorkItemId());
        Assert.assertEquals("CompleteWorkItem: The username isn't the same!", wmEventAuditWorkItem.getUsername(), "testUser");
        log.info("Username: {}", wmEventAuditWorkItem.getUsername());
        Assert.assertEquals("CompleteWorkItem: The event code isn't the same!", wmEventAuditWorkItem.getEventCode(), Integer.valueOf(String.valueOf(WMAEventCode.COMPLETED_WORK_ITEM.value())));
        log.info("Event code: {}", wmEventAuditWorkItem.getEventCode());
    }


}
