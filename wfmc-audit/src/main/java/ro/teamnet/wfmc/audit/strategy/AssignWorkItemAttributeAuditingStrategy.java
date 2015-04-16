package ro.teamnet.wfmc.audit.strategy;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ro.teamnet.audit.strategy.MethodAuditingStrategy;
import ro.teamnet.audit.util.AuditInfo;
import ro.teamnet.wfmc.audit.constants.WfmcAuditedMethod;
import ro.teamnet.wfmc.audit.constants.WfmcAuditedParameter;
import ro.teamnet.wfmc.audit.domain.WMAttributeAuditWorkItem;
import ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit;
import ro.teamnet.wfmc.audit.domain.WMWorkItemAudit;
import ro.teamnet.wfmc.audit.service.WfmcAuditQueryService;
import ro.teamnet.wfmc.audit.service.WfmcAuditService;
import ro.teamnet.wfmc.audit.util.WMAuditErrorUtil;

import javax.inject.Inject;

import static ro.teamnet.wfmc.audit.constants.WfmcAuditedParameter.*;

@Component
@Qualifier(value = WfmcAuditedMethod.ASSIGN_WORK_ITEM_ATTRIBUTE)
public class AssignWorkItemAttributeAuditingStrategy implements MethodAuditingStrategy {

    @Inject
    private WfmcAuditService wfmcAuditService;
    @Inject
    private WMAuditErrorUtil auditErrorUtil;
    @Inject
    private WfmcAuditQueryService wfmcAuditQueryService;

    private AuditInfo auditInfo;
    private WMProcessInstanceAudit processInstanceAudit;

    @Override
    public void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
    }

    @Override
    public void auditMethodBeforeInvocation() {
        processInstanceAudit = getWmProcessInstanceAudit();
        String username = getUserIdentification(auditInfo);

        WMWorkItemAudit wmWorkItemAudit = wfmcAuditService.savewmWorkItemAudit(
                (String) getMethodParameter(WORK_ITEM_ID), processInstanceAudit);
        WMAttributeAuditWorkItem wmAttributeAuditWorkItem = wfmcAuditService.savewmAttributeAuditWorkItem(
                (String) getMethodParameter(ATTRIBUTE_NAME), wmWorkItemAudit);
        wfmcAuditService.savewmEventAuditAttribute(getMethodParameter(ATTRIBUTE_VALUE), username,
                wmAttributeAuditWorkItem);
    }

    //TODO: extrageti get-urile lungi in variabile locale sau metode de tip getter si reformatati codul, va fi mai usor de citit
    // de exemplu, refactorizati strategiile cu metoda de mai jos:
    // ca sa nu avem duplicare de cod, putem crea un template de strategie - o clasa abstracta in care nu implementam metodele din interfata, dar in care putem avea metode ajutatoare ca aceasta de mai jos
    private Object getMethodParameter(String parameter) {
        return auditInfo.getArgumentsByParameterDescription().get(parameter);
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
        Object procInstId = getMethodParameter(WfmcAuditedParameter.PROCESS_INSTANCE_ID);
        return wfmcAuditQueryService.findByProcessInstanceId(procInstId.toString());
    }
}

