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

    /**
     * Invokes the given method on the audited instance.
     *
     * @param methodName the name of the method to be invoked
     * @param parameters the parameters of the method
     * @return the result of calling the given method on the audited instance with the given parameters;
     * {@code null} if the method is void or an exception is encountered on invocation.
     */
    public Object invokeMethodOnInstance(String methodName, Object... parameters) {
        return invokeMethodOnInstance(null, methodName, parameters);
    }

    /**
     * Invokes the given method on the audited instance.
     *
     * @param defaultReturnValue the default value to return if the method is void or an exception is encountered on
     *                           invocation
     * @param methodName         the name of the method to be invoked
     * @param parameters         the parameters of the method
     * @return the result of calling the given method on the audited instance with the given parameters;
     * {@code defaultReturnValue} if the method is void or an exception is encountered on invocation.
     */
    public Object invokeMethodOnInstance(Object defaultReturnValue, String methodName, Object... parameters) {
        Object returnValue = defaultReturnValue;
        Method invokedMethod = null;
        Class[] parameterTypes = getParameterTypes(parameters);
        try {
            invokedMethod = instance.getClass().getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            log.warn("Audited object does not provide the invoked method {} : ", methodName, e);
        }
        if (invokedMethod != null) {
            try {
                returnValue = (String) invokedMethod.invoke(instance, parameters);
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.warn("Invoking {} method on the audited instance failed: ", methodName, e);
            }
        }
        return returnValue;
    }

    /**
     * Retrieves the parameter types for the given method parameters.
     * @param parameters the method parameters
     * @return an array of types
     */
    private Class[] getParameterTypes(Object[] parameters) {
        Class[] parameterTypes = new Class[parameters.length];
        if (parameters.length > 0) {
            for (int i = 0; i < parameterTypes.length; i++) {
                parameterTypes[i] = parameters[i].getClass();
            }
        }
        return parameterTypes;
    }
}
