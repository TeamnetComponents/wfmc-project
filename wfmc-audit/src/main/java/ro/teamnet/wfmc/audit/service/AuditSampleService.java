package ro.teamnet.wfmc.audit.service;

import ro.teamnet.wfmc.audit.annotation.AnnotationforParams;
import ro.teamnet.wfmc.audit.domain.AuditSample;

/**
 * Sample service.
 */
public interface AuditSampleService {
    AuditSample saveSampleEntity(AuditSample sampleEntity);
    String convertIdToString(Long age, String name);
    String myMethod(@AnnotationforParams("age") Long age, @AnnotationforParams("name") String name);
}
