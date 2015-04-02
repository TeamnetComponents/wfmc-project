package ro.teamnet.wfmc.audit.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.teamnet.wfmc.audit.annotation.WfmcAuditable;

import java.lang.reflect.Method;

/**
 * Audit aspect.
 */
@Aspect
public class WfmcAuditAspect {

    private Logger log = LoggerFactory.getLogger(WfmcAuditAspect.class);

    @Pointcut("execution(@ro.teamnet.wfmc.audit.annotation.WfmcAuditable * *(..))")
    public void auditableMethod() {
    }

    @Before("auditableMethod() && @annotation(auditable)")
    public void beforeAuditable(JoinPoint joinPoint, WfmcAuditable auditable) {
        log.info("Before audit : " + auditable.value());
    }

    @After("auditableMethod() && @annotation(auditable))")
    public void afterAuditable(JoinPoint joinPoint, WfmcAuditable auditable) {
        log.info("After audit : " + auditable.value());
    }

    @Around("auditableMethod() && @annotation(auditable))")
    public Object wrapAroundAuditable(ProceedingJoinPoint proceedingJoinPoint, WfmcAuditable auditable) throws ClassNotFoundException {
        log.info("Started auditing around : " + auditable.value());
        Object auditableType = proceedingJoinPoint.getThis();
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method auditedMethod = methodSignature.getMethod();
        log.info("Class: {}; Method name: {}; Return type: {}", proceedingJoinPoint.getSourceLocation().getWithinType().getName(),
                auditedMethod.getName(), methodSignature.getReturnType().getName());
        Object thisObject = Class.forName(proceedingJoinPoint.getSignature().getDeclaringTypeName()).cast(auditableType);
        log.info("This: {}", thisObject);
        Class<?>[] parameterTypes = auditedMethod.getParameterTypes();
        Object[] args = proceedingJoinPoint.getArgs();
        for (int i = 0; i < parameterTypes.length; i++) {
            Object argumentValue = Class.forName(parameterTypes[i].getName()).cast(args[i]);
            log.info("Argument " + i + " = " + argumentValue);
        }
        Object returnValue = null;
        try {
            returnValue = proceedingJoinPoint.proceed(args);
        } catch (Throwable throwable) {
            log.warn("Could not proceed: ", throwable);
        }
        log.info("Finished auditing around: " + auditable.value());
        return returnValue;
    }
}
