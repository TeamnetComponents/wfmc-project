package ro.teamnet.wfmc.audit.strategy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ro.teamnet.audit.strategy.MethodAuditingStrategy;
import ro.teamnet.audit.util.AuditInfo;
import ro.teamnet.wfmc.audit.constants.WfmcAuditedMethod;
import ro.teamnet.wfmc.audit.constants.WfmcAuditedParameter;
import ro.teamnet.wfmc.audit.domain.WMEventAuditWorkItem;
import ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit;
import ro.teamnet.wfmc.audit.domain.WMWorkItemAudit;
import ro.teamnet.wfmc.audit.service.WfmcAuditQueryService;
import ro.teamnet.wfmc.audit.service.WfmcAuditService;
import ro.teamnet.wfmc.audit.util.HMethods;
import ro.teamnet.wfmc.audit.service.WMAuditErrorService;

import javax.inject.Inject;

@Component
@Qualifier(WfmcAuditedMethod.REASSIGN_WORK_ITEM)
public class ReassignWorkItemAuditingStrategy extends HMethods implements MethodAuditingStrategy {

    @Inject
    private WfmcAuditService wfmcAuditService;
    @Inject
    private WMAuditErrorService auditErrorService;

    private WMProcessInstanceAudit processInstanceAudit;
    private WMWorkItemAudit wmWorkItemAudit;
    private WMEventAuditWorkItem wmEventAuditWorkItem;

    //TODO @Andra: de stabilit cum se salveaza valorile pentru SOURCE_USER si TARGET_USER in structura de auditare exitenta sau de modificat structura pentru a le include si pe ele.
  
    @Override
    public void auditMethodBeforeInvocation() {
        String username = getUserIdentification(auditInfo);
        processInstanceAudit = getWmProcessInstanceAudit();
        wmWorkItemAudit = wfmcAuditService.savewmWorkItemAudit(
                (String) getMethodParameter(WfmcAuditedParameter.WORK_ITEM_ID),
                processInstanceAudit);
        wmEventAuditWorkItem = wfmcAuditService.savewmEventAuditWorkItem(username, wmWorkItemAudit);

    }
    @Override
    public void auditMethodAfterInvocation(Object o) {
        //it doesn't execute because it doesn't need any update
    }
    @Override
    public void auditMethodInvocationError(Throwable throwable) {
        auditErrorService.saveErrorIntoEntityWmErrorAudit(throwable, processInstanceAudit, auditInfo.getMethod().getName());
    }
}
