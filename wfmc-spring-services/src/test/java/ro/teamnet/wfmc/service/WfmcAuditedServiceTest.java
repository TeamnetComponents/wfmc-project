package ro.teamnet.wfmc.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.wfmc.wapi.WMConnectInfo;
import org.wfmc.wapi.WMWorkflowException;
import ro.teamnet.wfmc.audit.domain.WMEventAudit;
import ro.teamnet.wfmc.audit.domain.WMEventAuditProcessInstance;
import ro.teamnet.wfmc.service.mock.WfmcServiceMockImpl;
import ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit;
import ro.teamnet.wfmc.audit.service.WfmcAuditQueryService;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WfmcAuditTestApplication.class})
@IntegrationTest
public class WfmcAuditedServiceTest {

    @Inject
    private WfmcAuditQueryService wfmcAuditQueryService;
    @Inject
    private WfmcAuditedService wfmcService;


    private Logger log = LoggerFactory.getLogger(WfmcAuditedServiceTest.class);

    @Test
    public void testCreateProcessInstance() throws WMWorkflowException {
        wfmcService.connect(new WMConnectInfo("Andra@Administrator", "elo@RENNS2015", "Wfmc Test", "http://10.6.38.90:8080/ix-elo/ix"));
        wfmcService.createProcessInstance("1", "my procInstName");
        WMProcessInstanceAudit wmProcessInstanceAudit = wfmcAuditQueryService.findWMProcessInstanceAuditByProcessDefinitionBusinessName("my procInstName");
        WMEventAudit wmEventAudit = wfmcAuditQueryService.findWMEventAuditByUsername("Gogu");
        log.info("Process definition id: {}", wmProcessInstanceAudit.getProcessDefinitionId());
        log.info("Process instance id: {}", wmProcessInstanceAudit.getProcessInstanceId());
        wfmcService.disconnect();
    }

    @Test
    public void testCreateProcessInstanceWithMock() throws WMWorkflowException {
        String processInstanceId = wfmcService.createProcessInstance("pd_id", "my procInstName");
        Assert.assertEquals(WfmcServiceMockImpl.PROCESS_INSTANCE_ID, processInstanceId);
    }

    @Test
    public void testAssignProcessInstanceAttribute() throws WMWorkflowException {
        wfmcService.assignProcessInstanceAttribute("pi_id", "attr1", "1");
    }

    @Test
    public void testStartProcess() throws WMWorkflowException {
        String newProcessInstanceId = wfmcService.startProcess("pi_id");
        Assert.assertEquals(WfmcServiceMockImpl.NEW_PROCESS_INSTANCE_ID, newProcessInstanceId);
    }

    @Test
    public void testAbortProcessInstance() throws WMWorkflowException {
        wfmcService.abortProcessInstance("pi_id");
    }

    @Test
    public void testAssignWorkItemAttribute() throws WMWorkflowException {
        wfmcService.assignWorkItemAttribute("pi_id", "wi_id", "attr1", "1");
    }

    @Test
    public void testReassignWorkItem() throws WMWorkflowException {
        wfmcService.reassignWorkItem("source_user", "target_user", "pi_id", "wi_id");
    }

    @Test
    public void testCompleteWorkItem() throws WMWorkflowException {
        wfmcService.completeWorkItem("pi_id", "wi_id");
    }


}
