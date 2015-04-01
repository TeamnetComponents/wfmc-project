package ro.teamnet.wfmc.audit;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ro.teamnet.wfmc.audit.domain.AuditSample;
import ro.teamnet.wfmc.audit.repository.AuditSampleRepository;
import ro.teamnet.wfmc.audit.service.AuditSampleService;

import javax.inject.Inject;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WfmcAuditTestApplication.class})
@IntegrationTest
@ActiveProfiles("dev")
@Transactional("wfmcAuditTransactionManager")
public class AuditSampleServiceTest {


    @Inject
    private AuditSampleService sampleService;
    @Inject
    private AuditSampleRepository repository;

    @Test
    public void test() {
        int expectedCount = 0;
        AuditSample savedEntity = sampleService.saveSampleEntity(new AuditSample());
        expectedCount++;
        Assert.assertNotNull(savedEntity.getId());
        sampleService.saveSampleEntity(new AuditSample());
        expectedCount++;
        List<AuditSample> all = repository.findAll();
        Assert.assertEquals(expectedCount, all.size());
    }
}
