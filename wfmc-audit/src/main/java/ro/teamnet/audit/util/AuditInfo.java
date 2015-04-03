package ro.teamnet.audit.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * POJO for holding Audit information.
 */
public class AuditInfo {
    private String auditableType;
    private Method method;
    private Object instance;
    private Object[] methodArguments;
    private Object[][] argumentAnnotations;

    public static AuditInfo getInstance(String auditableType, JoinPoint joinPoint) {
        Method auditedMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
        return new AuditInfo(auditableType, auditedMethod, joinPoint.getThis(), joinPoint.getArgs(), auditedMethod.getParameterAnnotations());
    }

    public AuditInfo(String auditableType, Method method, Object instance, Object[] methodArguments, Object[][] argumentAnnotations) {
        this.auditableType = auditableType;
        this.method = method;
        this.instance = instance;
        this.methodArguments = methodArguments;
        this.argumentAnnotations = argumentAnnotations;
    }

    public String getAuditableType() {
        return auditableType;
    }

    public Method getMethod() {
        return method;
    }

    public Object getInstance() {
        return instance;
    }

    public Object[] getMethodArguments() {
        return methodArguments;
    }

    public Object[][] getArgumentAnnotations() {
        return argumentAnnotations;
    }
}
