package ro.teamnet.wfmc.audit.strategy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.wfmc.audit.WMAEventCode;
import ro.teamnet.audit.strategy.MethodAuditingStrategy;
import ro.teamnet.wfmc.audit.constants.WfmcAuditedMethod;
import ro.teamnet.wfmc.audit.domain.WMEventAuditProcessInstance;
import ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit;
import ro.teamnet.wfmc.audit.service.WMAuditErrorService;
import ro.teamnet.wfmc.audit.service.WfmcAuditService;
import ro.teamnet.wfmc.audit.util.HMethods;

import javax.inject.Inject;

@Component
@Qualifier(WfmcAuditedMethod.START_PROCESS)
public class StartProcessAuditingStrategy extends HMethods implements MethodAuditingStrategy {

    @Inject
    private WfmcAuditService wfmcAuditService;
    @Inject
    private WMAuditErrorService auditErrorService;

    private WMProcessInstanceAudit processInstanceAudit;
    private WMEventAuditProcessInstance eventAuditProcessInstance;

    public void auditMethodBeforeInvocation() {
        String username = getUserIdentification(auditInfo);
        processInstanceAudit = getWmProcessInstanceAudit();
        eventAuditProcessInstance = wfmcAuditService.saveEventAuditProcessInstance(
                processInstanceAudit,
                WMAEventCode.STARTED_PROCESS_INSTANCE_INT,
                username
        );
    }

    public void auditMethodAfterInvocation(Object auditedMethodReturnValue) {

        processInstanceAudit.setProcessInstanceId(auditedMethodReturnValue.toString());
        wfmcAuditService.updateProcessInstance(processInstanceAudit);
    }

    public void auditMethodInvocationError(Throwable throwable) {

        auditErrorService.saveErrorIntoEntityWmErrorAudit(throwable, processInstanceAudit, auditInfo.getMethod().getName());
    }
}
