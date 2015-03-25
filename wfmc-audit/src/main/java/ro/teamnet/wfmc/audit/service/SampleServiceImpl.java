package ro.teamnet.wfmc.audit.service;

import org.springframework.stereotype.Service;
import ro.teamnet.wfmc.audit.annotation.Auditable;
import ro.teamnet.wfmc.audit.domain.SampleEntity;
import ro.teamnet.wfmc.audit.repository.SampleEntityRepository;

import javax.inject.Inject;

/**
 * Sample service implementation.
 */
@Service
public class SampleServiceImpl implements SampleService {
    @Inject
    private SampleEntityRepository sampleEntityRepository;

    @Override
    @Auditable("sample service")
    public SampleEntity saveSampleEntity(SampleEntity sampleEntity) {
        return sampleEntityRepository.save(sampleEntity);
    }
}
