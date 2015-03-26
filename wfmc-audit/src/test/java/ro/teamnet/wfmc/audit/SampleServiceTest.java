package ro.teamnet.wfmc.audit;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ro.teamnet.wfmc.audit.domain.SampleEntity;
import ro.teamnet.wfmc.audit.repository.SampleEntityRepository;
import ro.teamnet.wfmc.audit.service.SampleService;

import javax.inject.Inject;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WfmcAuditTestApplication.class})
@IntegrationTest
@ActiveProfiles("dev")
@Transactional
public class SampleServiceTest {


    @Inject
    private SampleService sampleService;
    @Inject
    private SampleEntityRepository repository;

    @Test
    public void test() {
        SampleEntity savedEntity = sampleService.saveSampleEntity(new SampleEntity());
        Assert.assertNotNull(savedEntity.getId());
        sampleService.saveSampleEntity(new SampleEntity());
        sampleService.saveSampleEntity(new SampleEntity());
        List<SampleEntity> all = repository.findAll();
        Assert.assertEquals(3, all.size());
    }
}
