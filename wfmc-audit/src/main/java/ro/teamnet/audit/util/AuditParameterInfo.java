package ro.teamnet.audit.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.teamnet.audit.annotation.AuditedParameter;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;

public class AuditParameterInfo {

    private Logger log = LoggerFactory.getLogger(AuditParameterInfo.class);

    public ArrayList<Object> doSomething(AuditInfo auditInfo, JoinPoint joinPoint) throws ClassNotFoundException {
        ArrayList<Object> myArguments = new ArrayList<>();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        if (Objects.equals(auditInfo.getMethod().getName(), "createProcessInstance")) {
            //if(methodSignature.getReturnType().getName().getClass().equals((WMEventAuditWorkItem.class).toString())){
                Object[] args = joinPoint.getArgs();
                Annotation[][] parameterAnnotations = auditInfo.getMethod().getParameterAnnotations();
                Class[] parameterTypes = auditInfo.getMethod().getParameterTypes();
                int j = 0;
                for (Annotation[] annotations : parameterAnnotations) {
                    Class parameterType = parameterTypes[j++];
                    for (Annotation annotation : annotations) { //nu sunt elemente
                        if (annotation instanceof AuditedParameter) {
                            AuditedParameter paramAnnotation = (AuditedParameter) annotation;
                            //System.out.println("Tipul parametrului nr. "+j+" este : "+ parameterType.getName());
                            log.info("Parameter's type no. " + j + " is : " + parameterType.getName());
                            //System.out.println("Numele parametrului nr. "+j+" este : "+ paramAnnotation.valueParam());
                            log.info("Parameter's name no. " + j + " is : " + paramAnnotation.description());
                        }
                    }
                }

                for (int i = 0; i < parameterTypes.length; i++) {
                    Object argumentValue = Class.forName(parameterTypes[i].getName()).cast(args[i]);
                    myArguments.add(argumentValue);
                }
            //}
        }
        return myArguments;
    }

    private String getUserIdentification(ProceedingJoinPoint joinPoint) {
        String userIdentification = "";
        Object auditedInstance = joinPoint.getThis();
        Method getUserIdentification = null;
        try {
            getUserIdentification = auditedInstance.getClass().getMethod("getUserIdentification");
        } catch (NoSuchMethodException e) {
            log.warn("Audited object does not provide an accessor for the user identification: {}", e);
        }
        if (getUserIdentification != null) {
            try {
                userIdentification = (String) getUserIdentification.invoke(auditedInstance);
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.warn("Accessing user identification failed: {}", e);;
            }
        }
        return userIdentification;
    }

}





