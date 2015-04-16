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
import ro.teamnet.wfmc.audit.util.WMAuditErrorUtil;

import javax.inject.Inject;

@Component
@Qualifier(WfmcAuditedMethod.ASSIGN_PROCESS_INSTANCE_ATTRIBUTE)
public class AssignProcessInstanceAttributeAuditingStrategy implements MethodAuditingStrategy {

    private WMProcessInstanceAudit processInstanceAudit;
    @Inject
    private WfmcAuditService wfmcAuditService;
    @Inject
    private WMAuditErrorUtil auditErrorUtil;
    @Inject
    private WfmcAuditQueryService wfmcAuditQueryService;

    private AuditInfo auditInfo;

    @Override
    public void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
    }

    @Override
    public void auditMethodBeforeInvocation() {
        processInstanceAudit = getWmProcessInstanceAudit();
        String username = getUserIdentification(auditInfo);
        wfmcAuditService.convertAndSaveAssignAttributeAudit(
                (String) auditInfo.getArgumentsByParameterDescription().get(WfmcAuditedParameter.ATTRIBUTE_NAME),
                auditInfo.getArgumentsByParameterDescription().get(WfmcAuditedParameter.ATTRIBUTE_VALUE),
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
        auditErrorUtil.saveErrorIntoEntityWmErrorAudit(throwable, processInstanceAudit, auditInfo.getMethod().getName());
    }

    private String getUserIdentification(AuditInfo auditInfo) {
        return (String) auditInfo.invokeMethodOnInstance("getUserIdentification");
    }

    private WMProcessInstanceAudit getWmProcessInstanceAudit() {
        Object procInstId = auditInfo.getArgumentsByParameterDescription().get(WfmcAuditedParameter.PROCESS_INSTANCE_ID);
        return wfmcAuditQueryService.findByProcessInstanceId(procInstId.toString());
    }
}
