package ro.teamnet.wfmc.audit.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.teamnet.wfmc.audit.annotation.Auditable;

import java.lang.reflect.Method;

/**
 * Audit aspect.
 */
@Aspect
public class WfmcAuditAspect {

    Logger log = LoggerFactory.getLogger(WfmcAuditAspect.class);

    @Before("execution(* *(..)) && @annotation(auditable))")
    public void beforeAuditable(Auditable auditable) {
        log.info("Before audit : " + auditable.value());
    }

    @After("execution(* *(..)) && @annotation(auditable))")
    public void afterAuditable(Auditable auditable) {
        log.info("After audit : " + auditable.value());
    }

    @Around("execution(* *(..)) && @annotation(auditable))")
    public Object wrapAroundAuditable(ProceedingJoinPoint pjp, Auditable auditable) throws Throwable {
        log.info("Started auditing : " + auditable.value());
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method auditedMethod = methodSignature.getMethod();
        log.info("Class: {}; Method name: {}; Return type: {}", pjp.getSourceLocation().getWithinType().getName(),
                auditedMethod.getName(), methodSignature.getReturnType().getName());
        Class<?>[] parameterTypes = auditedMethod.getParameterTypes();
        Object[] args = pjp.getArgs();
        for (int i = 0; i < parameterTypes.length; i++) {
            Object argumentValue = Class.forName(parameterTypes[i].getName()).cast(args[i]);
            log.info("Argument " + i + " = " + argumentValue);
        }
        Object returnValue = pjp.proceed(args);
        log.info("Finished auditing : " + auditable.value());
        return returnValue;
    }
}
