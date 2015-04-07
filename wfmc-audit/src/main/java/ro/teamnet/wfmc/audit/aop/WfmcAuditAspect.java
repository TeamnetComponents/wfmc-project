package ro.teamnet.wfmc.audit.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.teamnet.audit.annotation.Auditable;
import ro.teamnet.audit.annotation.AuditedParameter;
import ro.teamnet.wfmc.audit.domain.AuditSample;
import ro.teamnet.wfmc.audit.service.AuditSampleService;
import ro.teamnet.wfmc.audit.service.WfmcAuditService;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;

/**
* Audit aspect.
*/
@Aspect
public class WfmcAuditAspect {


    @Inject
    public WfmcAuditService wfmcAuditService;

    @Inject
    public AuditSampleService sampleService;

    private Logger log = LoggerFactory.getLogger(WfmcAuditAspect.class);

    @Pointcut("execution(@ro.teamnet.audit.annotation.Auditable * *(..))")
    public void auditableMethod() {
    }

//    @Before("auditableMethod() && @annotation(auditable)")
//    public void beforeAuditable(JoinPoint joinPoint, WfmcAuditable auditable) {
//        log.info("Before audit : " + auditable.value());
//    }
//
//    @After("auditableMethod() && @annotation(auditable))")
//    public void afterAuditable(JoinPoint joinPoint, WfmcAuditable auditable) {
//        log.info("After audit : " + auditable.value());
//    }

    @Around("auditableMethod() && @annotation(auditable))")
    public Object wrapAroundAuditable(ProceedingJoinPoint proceedingJoinPoint, Auditable auditable) throws ClassNotFoundException {
        log.info("Started auditing around : " + auditable.strategy());
        //Object auditableType = proceedingJoinPoint.getThis();
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method auditedMethod = methodSignature.getMethod();
        //if(proceedingJoinPoint.getSourceLocation().getWithinType().getName().getClass() == WfmcServiceEloImpl.class)
            if(Objects.equals(auditedMethod.getName(), "myMethod")){
                /**if(Objects.equals(auditedMethod.getName(), "completeWorkItem")){*/
                if(methodSignature.getReturnType().getName().getClass() == java.lang.String.class){
                    /**if(methodSignature.getReturnType().getName().getClass().equals((WMEventAuditWorkItem.class).toString())){*/
                    Object[] args = proceedingJoinPoint.getArgs();
                    /**Object[] wfmcArgs = proceedingJoinPoint.getArgs();*/
                    ArrayList<Object> myArguments = new ArrayList<>();
                    /**ArrayList<Object> myWfmcArguments = new ArrayList<>();*/
                    Annotation[][] parameterAnnotations = auditedMethod.getParameterAnnotations();
                    /**Annotation[][] wfmcParameterAnnotations = auditedMethod.getParameterAnnotations();*/
                    Class[] parameterTypes = auditedMethod.getParameterTypes();
                    /**Class<?>[] wfmcParameterTypes = auditedMethod.getParameterTypes();*/
                    int j=0;
                    for(Annotation[] annotations : parameterAnnotations) {
                        /**for(Annotation[] wfmcAnnotations : wfmcParameterAnnotations) {*/
                        Class parameterType = parameterTypes[j++];
                        /**Class wfmcParameterType = wfmcParameterTypes[j++];*/
                        for (Annotation annotation : annotations) {
                            /**for (Annotation wfmcAnnotation : wfmcAnnotations) {*/
                            if (annotation instanceof AuditedParameter) {
                                /**if (wfmcAnnotation instanceof AnnotationforParams) {*/
                                AuditedParameter annotationForParams = (AuditedParameter) annotation;
                                /**AnnotationforParams wfmcAnnotationForParams = (AnnotationforParams) wfmcAnnotation;*/
                                log.info("Parameter's type no. "+j+" is : "+ parameterType.getName());
                                /**log.info("Parameter's type no. "+j+" is : "+ wfmcParameterType.getName());*/
                                log.info("Parameter's name no. "+j+" is : "+ annotationForParams.annotationType());
                                /**log.info("Parameter's name no. "+j+" is : "+ wfmcAnnotationForParams.value());*/
                            }
                        }
                    }

                    for (int i = 0; i < parameterTypes.length; i++) {
                        /**for (int i = 0; i < wfmcParameterTypes.length; i++) {*/
                        Object argumentValue = Class.forName(parameterTypes[i].getName()).cast(args[i]);
                        /**Object wfmcArgumentValue = Class.forName(wfmcParameterTypes[i].getName()).cast(wfmcArgs[i]);*/
                        myArguments.add(argumentValue);
                        /**myWfmcArguments.add(wfmcArgumentValue)*/
                    }
                    //TODO : use AnnotationForParams to identify and map the argumetns
                    AuditSample auditSample = new AuditSample((Long)myArguments.get(0),(String)myArguments.get(1));
                    AuditSample audit1 = sampleService.saveSampleEntity(auditSample);
                    log.info("Has been saved following user : Id : "+audit1.getId() + ", Name : " + audit1.getName() + ", Age : " + audit1.getAge());
                    /**WMEventAuditWorkItem convertAndSaveCompleteWorkItem((String)myArguments.get(0), (String)myArguments.get(1), (String)myArguments.get(2), (String)myArguments.get(3));*/
                }
            }

        /**
         *
         * WMEventAuditWorkItem convertAndSaveCompleteWorkItem(String processInstanceId, String workItemId, String username, String processDefinitionId)
         */
//        log.info("Class: {}; Method name: {}; Return type: {}", proceedingJoinPoint.getSourceLocation().getWithinType().getName(),
//                auditedMethod.getName(), methodSignature.getReturnType().getName());
//        Object thisObject = Class.forName(proceedingJoinPoint.getSignature().getDeclaringTypeName()).cast(auditableType);
//        log.info("This: {}", thisObject);
//        Class<?>[] parameterTypes1 = auditedMethod.getParameterTypes();
          Object[] args = proceedingJoinPoint.getArgs();
//        for (int i = 0; i < parameterTypes1.length; i++) {
//            Object argumentValue = Class.forName(parameterTypes1[i].getName()).cast(args[i]);
//            log.info("Argument " + i + " = " + argumentValue);
//        }
        Object returnValue = null;
        try {
            returnValue = proceedingJoinPoint.proceed(args);
        } catch (Throwable throwable) {
            log.warn("Could not proceed: ", throwable);
        }
        log.info("Finished auditing around: " + auditable.strategy());
        return returnValue;
    }
}
