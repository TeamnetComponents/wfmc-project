package ro.teamnet.audit.aop;

import org.aspectj.lang.annotation.Pointcut;

/**
 * Specifies pointcuts for the audit implementation.
 */
public abstract class AbstractAuditingAspect {
    @Pointcut("execution(@ro.teamnet.audit.annotation.Auditable * *(..))")
    public void auditableMethod() {
    }
}
