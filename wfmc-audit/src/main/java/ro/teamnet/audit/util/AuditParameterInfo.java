package ro.teamnet.audit.util;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.teamnet.audit.annotation.AuditedParameter;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Objects;

public class AuditParameterInfo {

    private Logger log = LoggerFactory.getLogger(AuditParameterInfo.class);

    public ArrayList<Object> doSomething(AuditInfo auditInfo, JoinPoint joinPoint) throws ClassNotFoundException {
        ArrayList<Object> myArguments = new ArrayList<>();
        if (Objects.equals(auditInfo.getMethod().getName(), "createProcessInstance")) {
            myArguments = populateMyArguments(auditInfo, joinPoint);
        }
        return myArguments;
    }
    
    public ArrayList<Object> populateMyArguments (AuditInfo auditInfo, JoinPoint joinPoint) throws ClassNotFoundException {
        ArrayList<Object> myArguments = new ArrayList<>();
        Object[] args = joinPoint.getArgs();
        Annotation[][] parameterAnnotations = auditInfo.getMethod().getParameterAnnotations();
        Class[] parameterTypes = auditInfo.getMethod().getParameterTypes();
        int j = 0;
        for (Annotation[] annotations : parameterAnnotations) {
            Class parameterType = parameterTypes[j++];
            for (Annotation annotation : annotations) { //nu sunt elemente
                if (annotation instanceof AuditedParameter) {
                    AuditedParameter paramAnnotation = (AuditedParameter) annotation;
                    log.info("Parameter's type no. " + j + " is : " + parameterType.getName());
                    log.info("Parameter's name no. " + j + " is : " + paramAnnotation.description());
                }
            }
        }

        int dimensionOfMyArguments = parameterTypes.length;
        //myArguments.add(dimensionOfMyArguments); //pentru a sti cati parametri va avea functia auditata
        for (int i = 0; i < dimensionOfMyArguments; i++) {
            Object argumentValue = Class.forName(parameterTypes[i].getName()).cast(args[i]);
            myArguments.add(argumentValue);
        }
        return myArguments;
    }
}





