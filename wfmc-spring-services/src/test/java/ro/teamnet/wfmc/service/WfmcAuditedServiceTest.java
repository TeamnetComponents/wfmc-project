package ro.teamnet.wfmc.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.wfmc.wapi.WMConnectInfo;
import org.wfmc.wapi.WMWorkflowException;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WfmcAuditTestApplication.class})
@IntegrationTest
@ActiveProfiles("dev")
@Transactional("wfmcAuditTransactionManager")
public class WfmcAuditedServiceTest {
    @Inject
    private WfmcAuditedService wfmcService;

    @Test
    public void testCreateProcessInstance() throws WMWorkflowException {
        wfmcService.connect(new WMConnectInfo("Andra@Administrator", "elo@RENNS2015", "Wfmc Test", "http://10.6.38.90:8080/ix-elo/ix"));
        wfmcService.createProcessInstance("1", "my procInstName");
        wfmcService.disconnect();
    }

}
