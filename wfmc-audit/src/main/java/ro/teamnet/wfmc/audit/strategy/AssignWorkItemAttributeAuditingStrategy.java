package ro.teamnet.wfmc.audit.strategy;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ro.teamnet.audit.strategy.MethodAuditingStrategy;
import ro.teamnet.audit.util.AuditInfo;
import ro.teamnet.wfmc.audit.constants.WfmcAuditedMethod;
import ro.teamnet.wfmc.audit.constants.WfmcAuditedParameter;
import ro.teamnet.wfmc.audit.domain.WMAttributeAuditWorkItem;
import ro.teamnet.wfmc.audit.domain.WMEventAuditAttribute;
import ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit;
import ro.teamnet.wfmc.audit.domain.WMWorkItemAudit;
import ro.teamnet.wfmc.audit.service.WMAuditErrorService;
import ro.teamnet.wfmc.audit.service.WfmcAuditQueryService;
import ro.teamnet.wfmc.audit.service.WfmcAuditService;
import ro.teamnet.wfmc.audit.util.HMethods;

import javax.inject.Inject;

import static ro.teamnet.wfmc.audit.constants.WfmcAuditedParameter.*;

@Component
@Qualifier(value = WfmcAuditedMethod.ASSIGN_WORK_ITEM_ATTRIBUTE)
public class AssignWorkItemAttributeAuditingStrategy extends HMethods implements MethodAuditingStrategy {

    @Inject
    private WfmcAuditService wfmcAuditService;
    @Inject
    private WMAuditErrorService auditErrorService;

    private WMProcessInstanceAudit processInstanceAudit;

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

    @Override
    public void auditMethodAfterInvocation(Object o) {
        //it doesn't execute because it doesn't need any update
    }

    @Override
    public void auditMethodInvocationError(Throwable throwable) {
        auditErrorService.saveErrorIntoEntityWmErrorAudit(throwable, processInstanceAudit, auditInfo.getMethod().getName());
    }
}

