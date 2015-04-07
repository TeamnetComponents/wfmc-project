package ro.teamnet.audit.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * POJO for holding Audit information.
 */
public class AuditInfo {

    private Logger log = LoggerFactory.getLogger(AuditInfo.class);

    private String auditableType;
    private Method method;
    private Object instance;
    private List<Object> methodArguments;
    private Map<Object, List<Annotation>> argumentAnnotations;

    public static AuditInfo getInstance(String auditableType, JoinPoint joinPoint) {
        Method auditedMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
        return new AuditInfo(auditableType, auditedMethod, joinPoint.getThis(), joinPoint.getArgs());
    }

    public AuditInfo(String auditableType, Method method, Object instance, Object[] methodArguments) {
        this.auditableType = auditableType;
        this.method = method;
        this.instance = instance;
        this.methodArguments = new ArrayList<>();
        this.argumentAnnotations = new HashMap<>();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < methodArguments.length; i++) {
            Object methodArgument = methodArguments[i];
            this.methodArguments.add(methodArgument);
            if (i < parameterAnnotations.length) {
                this.argumentAnnotations.put(methodArgument, Arrays.asList(parameterAnnotations[i]));
            }
        }
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

    public List<Object> getMethodArguments() {
        return methodArguments;
    }

    public Map<Object, List<Annotation>> getArgumentAnnotations() {
        return argumentAnnotations;
    }

    public Object invokeMethodOnInstance(String methodName) {
        return invokeMethodOnInstance(methodName, null);
    }

    public Object invokeMethodOnInstance(String methodName, Object defaultReturnValue) {
        Object returnValue = defaultReturnValue;
        Method invokedMethod = null;
        try {
            invokedMethod = instance.getClass().getMethod(methodName);
        } catch (NoSuchMethodException e) {
            log.warn("Audited object does not provide the invoked method {} : ", methodName, e);
        }
        if (invokedMethod != null) {
            try {
                returnValue = (String) invokedMethod.invoke(instance);
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.warn("Invoking {} method on the audited instance failed: ", methodName, e);
            }
        }
        return returnValue;
    }
}
