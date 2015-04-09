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
 * An object holding audit information.
 */
public class AuditInfo {

    private Logger log = LoggerFactory.getLogger(AuditInfo.class);

    private String auditableType;
    private Method method;
    private Object auditedInstance;
    private List<Object> methodArguments;
    private Map<Object, List<Annotation>> argumentAnnotations;

    /**
     * @deprecated Use constructor {@link AuditInfo#AuditInfo(String, java.lang.reflect.Method, Object, Object[])}
     */
    @Deprecated
    public static AuditInfo getInstance(String auditableType, JoinPoint joinPoint) {
        Method auditedMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
        return new AuditInfo(auditableType, auditedMethod, joinPoint.getThis(), joinPoint.getArgs());
    }

    /**
     * Creates an instance containing audited information.
     * @param auditableType the type of the audited method, as specified by {@link ro.teamnet.audit.annotation.Auditable#type}
     * @param method the audited method
     * @param auditedInstance the instance on which the audited method was called
     * @param methodArguments the arguments used in the audited method call
     */
    public AuditInfo(String auditableType, Method method, Object auditedInstance, Object[] methodArguments) {
        this.auditableType = auditableType;
        this.method = method;
        this.auditedInstance = auditedInstance;
        this.methodArguments = new ArrayList<>();
        setArguments(method, methodArguments);
    }

    /**
     * Sets the method arguments and any available argument annotations.
     * @param method the audited method
     * @param methodArguments the arguments used in the audited method call
     */
    private void setArguments(Method method, Object[] methodArguments) {
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

    /**
     * Getter for the auditable type.
     * @return the type of the audited method, as specified by {@link ro.teamnet.audit.annotation.Auditable#type}
     */
    public String getAuditableType() {
        return auditableType;
    }

    /**
     * Getter for the audited method.
     * @return the audited method
     */
    public Method getMethod() {
        return method;
    }

    /**
     * Getter for the audited instance.
     * @return the instance on which the audited method was called
     */
    public Object getAuditedInstance() {
        return auditedInstance;
    }

    /**
     * Getter for the arguments of the audited method.
     * @return the arguments used in the audited method call
     */
    public List<Object> getMethodArguments() {
        return methodArguments;
    }

    /**
     * Getter for the audited method argument annotations.
     * @return a map of the annotations found on each of the audited method's arguments
     */
    public Map<Object, List<Annotation>> getArgumentAnnotations() {
        return argumentAnnotations;
    }

    /**
     * Invokes the given method on the audited auditedInstance.
     *
     * @param methodName the name of the method to be invoked
     * @param parameters the parameters of the method
     * @return the result of calling the given method on the audited auditedInstance with the given parameters;
     * {@code null} if the method is void or an exception is encountered on invocation.
     */
    public Object invokeMethodOnInstance(String methodName, Object... parameters) {
        Object returnValue = null;
        Method invokedMethod = null;
        Class[] parameterTypes = getParameterTypes(parameters);
        try {
            invokedMethod = auditedInstance.getClass().getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            log.warn("Audited object does not provide the invoked method {} : ", methodName, e);
        }
        if (invokedMethod != null) {
            try {
                returnValue = (String) invokedMethod.invoke(auditedInstance, parameters);
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.warn("Invoking {} method on the audited auditedInstance failed: ", methodName, e);
            }
        }
        return returnValue;
    }

    /**
     * Retrieves the parameter types for the given method parameters.
     *
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
