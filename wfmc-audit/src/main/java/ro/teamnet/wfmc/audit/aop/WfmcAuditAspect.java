package ro.teamnet.wfmc.audit.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import ro.teamnet.wfmc.audit.annotation.Auditable;

import java.lang.reflect.Method;
//import java.lang.reflect.Parameter;

/**
 * Audit aspect.
 */

@Aspect
public class WfmcAuditAspect {

    @Before("execution(* *(..)) && @annotation(auditable))")
    public void beforeAuditable(Auditable auditable) {
        System.out.println("++++++++++++++++++++++++++++ Before audit : " + auditable.value());
    }

    @After("execution(* *(..)) && @annotation(auditable))")
    public void afterAuditable(Auditable auditable) {
        System.out.println("++++++++++++++++++++++++++++ After audit : " + auditable.value());
    }

    @Around("execution(* *(..)) && @annotation(auditable))")
    public Object wrapAroundAuditable(ProceedingJoinPoint pjp, Auditable auditable) throws Throwable {
        System.out.println("############################################\nStarted auditing : " + auditable.value());
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method auditedMethod = methodSignature.getMethod();
        System.out.printf("Class: %s\nMethod name: %s\nReturn type: %s\nArguments:\n",
                pjp.getSourceLocation().getWithinType().getName(), auditedMethod.getName(), methodSignature.getReturnType().getName());
        //Parameter[] parameters = auditedMethod.getParameters();
//        for (int i = 0; i < parameters.length; i++) {
//            Parameter parameter = parameters[i];
//            Object argumentValue = Class.forName(parameter.getType().getName()).cast(pjp.getArgs()[i]);
//            System.out.println("\t" + parameter.getName() + " = " + argumentValue);
//        }
        Object returnValue = pjp.proceed(pjp.getArgs());
        System.out.println("############################################\nFinished auditing : " + auditable.value());
        return returnValue;
    }
}
