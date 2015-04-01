package ro.teamnet.wfmc.audit.service;

import ro.teamnet.wfmc.audit.domain.AuditSample;

/**
 * Sample service.
 */
public interface AuditSampleService {
    AuditSample saveSampleEntity(AuditSample sampleEntity);
}
