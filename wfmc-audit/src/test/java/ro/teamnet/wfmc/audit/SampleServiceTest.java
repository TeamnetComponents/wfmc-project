package ro.teamnet.wfmc.audit;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ro.teamnet.wfmc.audit.domain.SampleEntity;
import ro.teamnet.wfmc.audit.service.SampleService;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WfmcAuditApplication.class)
@IntegrationTest
public class SampleServiceTest {


    @Inject
    SampleService sampleService;

    @Test
    public void test() {
        SampleEntity sampleEntity = new SampleEntity();
        Assert.assertNull(sampleEntity.getId());
        SampleEntity savedEntity = sampleService.saveSampleEntity(sampleEntity);
        Assert.assertNotNull(savedEntity.getId());
        Assert.assertEquals(sampleEntity.getId(), savedEntity.getId());
    }
}
