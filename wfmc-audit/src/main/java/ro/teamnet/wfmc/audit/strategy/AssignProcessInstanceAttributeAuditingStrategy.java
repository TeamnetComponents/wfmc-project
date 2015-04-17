package ro.teamnet.wfmc.audit.strategy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ro.teamnet.audit.strategy.MethodAuditingStrategy;
import ro.teamnet.audit.util.AuditInfo;
import ro.teamnet.wfmc.audit.constants.WfmcAuditedMethod;
import ro.teamnet.wfmc.audit.constants.WfmcAuditedParameter;
import ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit;
import ro.teamnet.wfmc.audit.service.WfmcAuditQueryService;
import ro.teamnet.wfmc.audit.service.WfmcAuditService;
import ro.teamnet.wfmc.audit.util.HMethods;
import ro.teamnet.wfmc.audit.service.WMAuditErrorService;

import javax.inject.Inject;

@Component
@Qualifier(WfmcAuditedMethod.ASSIGN_PROCESS_INSTANCE_ATTRIBUTE)
public class AssignProcessInstanceAttributeAuditingStrategy extends HMethods implements MethodAuditingStrategy {

    @Inject
    private WfmcAuditService wfmcAuditService;
    @Inject
    private WMAuditErrorService auditErrorService;
    @Inject
    private WfmcAuditQueryService wfmcAuditQueryService;

    private WMProcessInstanceAudit processInstanceAudit;

    @Override
    public void auditMethodBeforeInvocation() {
        processInstanceAudit = getWmProcessInstanceAudit();
        String username = getUserIdentification(auditInfo);
        wfmcAuditService.convertAndSaveAssignAttributeAudit(
                (String) getMethodParameter(WfmcAuditedParameter.ATTRIBUTE_NAME),
                getMethodParameter(WfmcAuditedParameter.ATTRIBUTE_VALUE),
                username,
                processInstanceAudit
        );
    }

    @Override
    public void auditMethodAfterInvocation(Object o) {
        //it doesn't execute because it doesn't need any update
    }

    @Override
    public void auditMethodInvocationError(Throwable throwable) {
        auditErrorService.saveErrorIntoEntityWmErrorAudit(throwable, processInstanceAudit, auditInfo.getMethod().getName());
    }

    private String getUserIdentification(AuditInfo auditInfo) {
        return (String) auditInfo.invokeMethodOnInstance("getUserIdentification");
    }

    private WMProcessInstanceAudit getWmProcessInstanceAudit() {
        Object procInstId = getMethodParameter(WfmcAuditedParameter.PROCESS_INSTANCE_ID);
        return wfmcAuditQueryService.findWMProcessInstanceAuditByProcessInstanceId(procInstId.toString());
    }
}
