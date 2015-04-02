package ro.teamnet.wfmc.audit.service;

import org.springframework.stereotype.Service;
import ro.teamnet.wfmc.audit.annotation.WfmcAuditable;
import ro.teamnet.wfmc.audit.domain.AuditSample;
import ro.teamnet.wfmc.audit.repository.AuditSampleRepository;

import javax.inject.Inject;

/**
 * Sample service implementation.
 */
@Service
public class AuditSampleServiceImpl implements AuditSampleService {
    @Inject
    private AuditSampleRepository sampleEntityRepository;

    @Override
    @WfmcAuditable("sample service")
    public AuditSample saveSampleEntity(AuditSample sampleEntity) {
        return sampleEntityRepository.save(sampleEntity);
    }
}
