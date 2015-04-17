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
import ro.teamnet.wfmc.audit.service.WMAuditErrorService;

import javax.inject.Inject;

@Component
@Qualifier(WfmcAuditedMethod.REASSIGN_WORK_ITEM)
public class ReassignWorkItemAuditingStrategy implements MethodAuditingStrategy {

    @Inject
    private WfmcAuditService wfmcAuditService;
    @Inject
    private WMAuditErrorService auditErrorUtil;
    @Inject
    private WfmcAuditQueryService wfmcAuditQueryService;

    private AuditInfo auditInfo;
    private WMProcessInstanceAudit ProcessInstanceAudit;

    @Override
    public void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
    }

    //TODO @Andra: de stabilit cum se salveaza valorile pentru SOURCE_USER si TARGET_USER in structura de auditare exitenta sau de modificat structura pentru a le include si pe ele.
    @Override
    public void auditMethodBeforeInvocation() {
        String username = getUserIdentification(auditInfo);
        ProcessInstanceAudit = getWmProcessInstanceAudit();
        wfmcAuditService.convertAndSaveReassignWorkItem(
                ProcessInstanceAudit,
                (String) auditInfo.getArgumentsByParameterDescription().get(WfmcAuditedParameter.PROCESS_INSTANCE_ID),
                (String) auditInfo.getArgumentsByParameterDescription().get(WfmcAuditedParameter.WORK_ITEM_ID),
                (String) auditInfo.getArgumentsByParameterDescription().get(WfmcAuditedParameter.SOURCE_USER),//nu e salvat
                (String) auditInfo.getArgumentsByParameterDescription().get(WfmcAuditedParameter.TARGET_USER), // nu e salvat
                username
        );
    }

    @Override
    public void auditMethodAfterInvocation(Object o) {
        //it doesn't execute because it doesn't need any update
    }

    @Override
    public void auditMethodInvocationError(Throwable throwable) {
        auditErrorUtil.saveErrorIntoEntityWmErrorAudit(throwable, ProcessInstanceAudit, auditInfo.getMethod().getName());
    }

    private String getUserIdentification(AuditInfo auditInfo) {
        return (String) auditInfo.invokeMethodOnInstance("getUserIdentification");
    }

    private WMProcessInstanceAudit getWmProcessInstanceAudit() {
        Object procInstId = auditInfo.getArgumentsByParameterDescription().get(WfmcAuditedParameter.PROCESS_INSTANCE_ID);
        return wfmcAuditQueryService.findWMProcessInstanceAuditByProcessInstanceId(procInstId.toString());
    }
}
