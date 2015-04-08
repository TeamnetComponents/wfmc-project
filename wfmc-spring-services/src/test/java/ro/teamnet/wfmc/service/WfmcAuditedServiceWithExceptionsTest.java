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
import org.wfmc.wapi.WMConnectInfo;
import org.wfmc.wapi.WMWorkflowException;
import ro.teamnet.wfmc.audit.domain.WMErrorAudit;
import ro.teamnet.wfmc.audit.domain.WMEventAudit;
import ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit;
import ro.teamnet.wfmc.audit.service.WfmcAuditQueryService;
import ro.teamnet.wfmc.service.mock.WfmcServiceMockImpl;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WfmcAuditTestApplication.class})
@IntegrationTest
@ActiveProfiles("test-error")
public class WfmcAuditedServiceWithExceptionsTest {

    public static final String PROC_DEF_ID = "pd_id";
    public static final String USER_IDENTIFICATION = "testUser";
    public static final String PROC_INST_ID = "pi_id";
    public static final String WORK_ITEM_ID = "wi_id";

    @Inject
    private WfmcAuditQueryService wfmcAuditQueryService;
    @Inject
    private WfmcAuditedService wfmcServiceWithExceptions;


    private Logger log = LoggerFactory.getLogger(WfmcAuditedServiceWithExceptionsTest.class);


    @Test
    @Transactional("wfmcAuditTransactionManager")
    public void testCreateProcessInstance() throws WMWorkflowException {
        wfmcServiceWithExceptions.connect(new WMConnectInfo(USER_IDENTIFICATION, "", "", ""));
        wfmcServiceWithExceptions.createProcessInstance(PROC_DEF_ID, "my procInstName");
        WMProcessInstanceAudit wmProcessInstanceAudit = wfmcAuditQueryService.findWMProcessInstanceAuditByProcessDefinitionBusinessName("my procInstName");
        WMErrorAudit wmErrorAudit = wfmcAuditQueryService.findWMErrorAuditByWmProcessInstanceAudit(wmProcessInstanceAudit);
        Assert.assertNotNull(wmErrorAudit);
        log.info("Description: {}", wmErrorAudit.getDescription());
        log.info("Message: {}", wmErrorAudit.getMessage());
        log.info("Audited Op: {}", wmErrorAudit.getAuditedOperation());
        log.info("Stack Trace: {}", wmErrorAudit.getStackTrace());

        wfmcServiceWithExceptions.disconnect();
    }

    @Test
    @Transactional("wfmcAuditTransactionManager")
    public void testAssignProcessInstanceAttribute() throws WMWorkflowException {
        wfmcServiceWithExceptions.assignProcessInstanceAttribute(PROC_INST_ID, "attr1", "1");
    }

    @Test
    @Transactional("wfmcAuditTransactionManager")
    public void testStartProcess() throws WMWorkflowException {
        String newProcessInstanceId = wfmcServiceWithExceptions.startProcess(PROC_INST_ID);
        Assert.assertNull(newProcessInstanceId);
    }

    @Test
    @Transactional("wfmcAuditTransactionManager")
    public void testAbortProcessInstance() throws WMWorkflowException {
        wfmcServiceWithExceptions.abortProcessInstance(PROC_INST_ID);
    }

    @Test
    @Transactional("wfmcAuditTransactionManager")
    public void testAssignWorkItemAttribute() throws WMWorkflowException {
        wfmcServiceWithExceptions.assignWorkItemAttribute(PROC_INST_ID, WORK_ITEM_ID, "attr1", "1");
    }

    @Test
    @Transactional("wfmcAuditTransactionManager")
    public void testReassignWorkItem() throws WMWorkflowException {
        wfmcServiceWithExceptions.reassignWorkItem("sourceUser", "targetUser", PROC_INST_ID, WORK_ITEM_ID);
    }

    @Test
    @Transactional("wfmcAuditTransactionManager")
    public void testCompleteWorkItem() throws WMWorkflowException {
        wfmcServiceWithExceptions.completeWorkItem(PROC_INST_ID, WORK_ITEM_ID);
    }

}
