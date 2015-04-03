package ro.teamnet.wfmc.audit.service;

import ro.teamnet.audit.annotation.AuditedParameter;
import ro.teamnet.wfmc.audit.domain.AuditSample;

/**
 * Sample service.
 */
public interface AuditSampleService {
    AuditSample saveSampleEntity(AuditSample sampleEntity);

    String convertIdToString(Long age, String name);

    String myMethod(@AuditedParameter(description = "age") Long age,
                    @AuditedParameter(description = "name") String name);
}
