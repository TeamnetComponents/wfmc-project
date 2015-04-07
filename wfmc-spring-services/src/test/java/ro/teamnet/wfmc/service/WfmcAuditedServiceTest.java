package ro.teamnet.wfmc.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wfmc.wapi.WMConnectInfo;
import org.wfmc.wapi.WMWorkflowException;
import ro.teamnet.wfmc.audit.domain.WMEventAudit;
import ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit;
import ro.teamnet.wfmc.audit.service.WfmcAuditQueryService;
import ro.teamnet.wfmc.service.mock.WfmcServiceMockImpl;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WfmcAuditTestApplication.class})
@IntegrationTest
public class WfmcAuditedServiceTest {

    public static final String PROC_DEF_ID = "pd_id";
    public static final String USER_IDENTIFICATION = "testUser";
    public static final String PROC_INST_ID = "pi_id";
    public static final String WORK_ITEM_ID = "wi_id";

    @Inject
    private WfmcAuditQueryService wfmcAuditQueryService;
    @Inject
    private WfmcAuditedService wfmcService;


    private Logger log = LoggerFactory.getLogger(WfmcAuditedServiceTest.class);

    @Test
    public void testCreateProcessInstance() throws WMWorkflowException {
        wfmcService.connect(new WMConnectInfo(USER_IDENTIFICATION, "", "", ""));
        String processInstanceId = wfmcService.createProcessInstance(PROC_DEF_ID, "my procInstName");
        Assert.assertEquals(WfmcServiceMockImpl.PROCESS_INSTANCE_ID, processInstanceId);
        WMProcessInstanceAudit wmProcessInstanceAudit = wfmcAuditQueryService.findWMProcessInstanceAuditByProcessDefinitionBusinessName("my procInstName");
        WMEventAudit wmEventAudit = wfmcAuditQueryService.findWMEventAuditByUsername(USER_IDENTIFICATION);
        log.info("Process definition id: {}", wmProcessInstanceAudit.getProcessDefinitionId());
        log.info("Process instance id: {}", wmProcessInstanceAudit.getProcessInstanceId());
        wfmcService.disconnect();
    }

    @Test
    public void testAssignProcessInstanceAttribute() throws WMWorkflowException {
        wfmcService.assignProcessInstanceAttribute(PROC_INST_ID, "attr1", "1");
    }

    @Test
    public void testStartProcess() throws WMWorkflowException {
        String newProcessInstanceId = wfmcService.startProcess(PROC_INST_ID);
        Assert.assertEquals(WfmcServiceMockImpl.NEW_PROCESS_INSTANCE_ID, newProcessInstanceId);
    }

    @Test
    public void testAbortProcessInstance() throws WMWorkflowException {
        wfmcService.abortProcessInstance(PROC_INST_ID);
    }

    @Test
    public void testAssignWorkItemAttribute() throws WMWorkflowException {
        wfmcService.assignWorkItemAttribute(PROC_INST_ID, WORK_ITEM_ID, "attr1", "1");
    }

    @Test
    public void testReassignWorkItem() throws WMWorkflowException {
        wfmcService.reassignWorkItem("sourceUser", "targetUser", PROC_INST_ID, WORK_ITEM_ID);
    }

    @Test
    public void testCompleteWorkItem() throws WMWorkflowException {
        wfmcService.completeWorkItem(PROC_INST_ID, WORK_ITEM_ID);
    }


}
