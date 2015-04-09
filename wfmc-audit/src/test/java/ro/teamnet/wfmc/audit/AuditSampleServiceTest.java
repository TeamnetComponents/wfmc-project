package ro.teamnet.wfmc.audit;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ro.teamnet.wfmc.audit.domain.AuditSample;
import ro.teamnet.wfmc.audit.domain.WMErrorAudit;
import ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit;
import ro.teamnet.wfmc.audit.repository.AuditSampleRepository;
import ro.teamnet.wfmc.audit.repository.ErrorAuditRepository;
import ro.teamnet.wfmc.audit.repository.ProcessInstanceAuditRepository;
import ro.teamnet.wfmc.audit.service.AuditSampleService;

import javax.inject.Inject;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WfmcAuditTestApplication.class})
@IntegrationTest
@Transactional("wfmcAuditTransactionManager")
public class AuditSampleServiceTest {

    @Inject
    private ErrorAuditRepository errorAuditRepository;
    @Inject
    private ProcessInstanceAuditRepository processInstanceAuditRepository;
    @Inject
    private AuditSampleRepository auditSampleRepository;
    @Inject
    private AuditSampleService auditSampleService;

    private Logger log = LoggerFactory.getLogger(AuditSampleServiceTest.class);
    @Inject
    private AuditSampleService sampleService;

    @Inject
    private AuditSampleRepository sampleRepository;

    @Test
    public void test() {
        int expectedCount = 0;
        final AuditSample sampleEntity = new AuditSample(0L, "");
        AuditSample savedEntity = sampleService.saveSampleEntity(sampleEntity);
        expectedCount++;
        Assert.assertNotNull(savedEntity.getId());
        sampleService.saveSampleEntity(new AuditSample(1L, "aaa"));
        expectedCount++;
        List<AuditSample> all = sampleRepository.findAll();
        Assert.assertEquals(expectedCount, all.size());
    }

    @Test
    public void testErrorAuditEntity() {
        WMErrorAudit errorAudit = new WMErrorAudit();
        errorAudit.setMessage("Message");
        errorAudit.setAuditedOperation("Audited operation");
        errorAudit.setDescription("Description");
        DateTime now = new DateTime();
        errorAudit.setOccurrenceTime(now);

        WMProcessInstanceAudit process = processInstanceAuditRepository.findOne(1l);
        errorAudit.setWmProcessInstanceAudit(process);

        WMErrorAudit ea = errorAuditRepository.save(errorAudit);
        Assert.assertNotNull(ea.getId());

        WMErrorAudit checkData = errorAuditRepository.findOne(ea.getId());
        Assert.assertEquals("Message error!", checkData.getMessage(), "Message");
        Assert.assertEquals("Description error!", checkData.getDescription(), "Description");
        Assert.assertEquals("Audited operation error!", checkData.getAuditedOperation(), "Audited operation");
        Assert.assertEquals("Timestamp error!", checkData.getOccurrenceTime(), now);
        Assert.assertEquals("ProcessInstanceAudit error!", checkData.getWmProcessInstanceAudit(), process);
    }

    @Test
    public void testSaveExceptionIntoWMErrorAudit() {

        try {
            /**
             * Force an exception, for testing!
             */
            Object object = null;
            object.toString();
        } catch (Throwable e) {

            WMErrorAudit errorAudit = new WMErrorAudit();
            errorAudit.setDescription(e.toString());
            errorAudit.setMessage(e.getMessage());
            errorAudit.setStackTrace(ExceptionUtils.getStackTrace(e));
            errorAudit.setAuditedOperation("TODO");
            errorAudit.setOccurrenceTime(new DateTime());
            WMProcessInstanceAudit process = processInstanceAuditRepository.findOne(100l);
            errorAudit.setWmProcessInstanceAudit(process);
            WMErrorAudit ea = errorAuditRepository.save(errorAudit);
            Assert.assertNotNull(ea.getId());

            WMErrorAudit checkData = errorAuditRepository.findOne(ea.getId());
            log.info("Clob message: {}", checkData.getStackTrace());
            log.info("Description: {}", checkData.getDescription());
            log.info("Message: {}", checkData.getMessage());
            log.info("Timestamp: {}", checkData.getOccurrenceTime());
            log.info("Audited operation: {}", checkData.getAuditedOperation());
            log.info("Process instance audit: {}", checkData.getWmProcessInstanceAudit());
        }
    }

    @Test
    public void testSampleForProcessInstance() {

        auditSampleService.myMethod(1l, "myMethod");
    }
}
