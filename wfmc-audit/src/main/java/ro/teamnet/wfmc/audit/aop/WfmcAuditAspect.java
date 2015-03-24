package ro.teamnet.wfmc.audit.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import ro.teamnet.wfmc.audit.annotation.Auditable;

/**
 * Audit aspect.
 */

@Aspect
public class WfmcAuditAspect {

    @After("execution(* *(..)) && @annotation(auditable))")
    public void audit(Auditable auditable) {
        System.out.println("Auditing : " + auditable.value());
    }
}
