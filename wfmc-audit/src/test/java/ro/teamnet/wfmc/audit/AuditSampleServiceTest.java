package ro.teamnet.wfmc.audit;


import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ro.teamnet.wfmc.audit.domain.AuditSample;
import ro.teamnet.wfmc.audit.domain.WMEventAudit;
import ro.teamnet.wfmc.audit.domain.WMEventAuditWorkItem;
import ro.teamnet.wfmc.audit.repository.AuditSampleRepository;
import ro.teamnet.wfmc.audit.repository.EventAuditRepository;
import ro.teamnet.wfmc.audit.service.AuditSampleService;
import ro.teamnet.wfmc.audit.service.WfmcAuditService;

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
    @Inject
    private WfmcAuditService wfmcAuditService;
    @Inject
    private EventAuditRepository eventAuditRepository;

    @Test
    @Ignore
    public void completeWorkItemTest () {

        /**
         * somewhere will call this method : completeWorkItem
         */

        WMEventAuditWorkItem wmEventAuditWorkItem = wfmcAuditService.convertAndSaveCompleteWorkItem("sdaa", "sad", "sda", "sda");

        Assert.assertNotNull(wmEventAuditWorkItem.getId());

        WMEventAudit wmEventAudit = new WMEventAudit();
        wmEventAudit.setUsername("user");
        eventAuditRepository.save(wmEventAudit);
    }

    @Test
    @Ignore
    public void test() {
        int expectedCount = 0;
        AuditSample savedEntity = sampleService.saveSampleEntity(new AuditSample(1l,""));
        expectedCount++;
        Assert.assertNotNull(savedEntity.getId());


        String myId = sampleService.convertIdToString(savedEntity.getId(),"myCeva");
        System.out.println(myId);

        expectedCount++;
        List<AuditSample> all = repository.findAll();
       Assert.assertEquals(expectedCount, all.size());
    }

    @Test
    @Ignore
    public void myTestIdAndSmth() {

        String result = sampleService.myMethod(25L, "USERNAME");
    }
}
