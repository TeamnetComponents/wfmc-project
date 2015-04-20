package ro.teamnet.wfmc.audit.strategy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.wfmc.audit.WMAEventCode;
import ro.teamnet.audit.strategy.MethodAuditingStrategy;
import ro.teamnet.wfmc.audit.constants.WfmcAuditedMethod;
import ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit;
import ro.teamnet.wfmc.audit.service.WMAuditErrorService;
import ro.teamnet.wfmc.audit.service.WfmcAuditService;
import ro.teamnet.wfmc.audit.util.HMethods;

import javax.inject.Inject;

@Component
@Qualifier(WfmcAuditedMethod.ABORT_PROCESS_INSTANCE)
public class AbortProcessInstanceAuditingStrategy extends HMethods implements MethodAuditingStrategy {

    @Inject
    private WfmcAuditService wfmcAuditService;
    @Inject
    private WMAuditErrorService wmAuditErrorService;

    private WMProcessInstanceAudit processInstanceAudit;

    @Override
    public void auditMethodBeforeInvocation() {
        String username = getUserIdentification(auditInfo);
        processInstanceAudit = getWmProcessInstanceAudit();
        wfmcAuditService.saveWmEventAuditProcessInstance(
                processInstanceAudit,
                WMAEventCode.ABORTED_ACTIVITY_INSTANCE.value(),
                username
        );
    }

    @Override
    public void auditMethodAfterInvocation(Object o) {
        //it doesn't execute because it doesn't need any update
    }

    @Override
    public void auditMethodInvocationError(Throwable throwable) {
        wmAuditErrorService.saveErrorIntoEntityWmErrorAudit(throwable, processInstanceAudit, auditInfo.getMethod().getName());
    }
}
