package ro.teamnet.wfmc.audit.strategy;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ro.teamnet.audit.strategy.MethodAuditingStrategy;
import ro.teamnet.audit.util.AuditInfo;
import ro.teamnet.wfmc.audit.constants.WfmcAuditedMethod;
import ro.teamnet.wfmc.audit.constants.WfmcAuditedParameter;
import ro.teamnet.wfmc.audit.domain.*;
import ro.teamnet.wfmc.audit.service.WfmcAuditQueryService;
import ro.teamnet.wfmc.audit.service.WfmcAuditService;
import ro.teamnet.wfmc.audit.util.WMAuditErrorUtil;

import javax.inject.Inject;

@Component
@Qualifier(value = WfmcAuditedMethod.ASSIGN_WORK_ITEM_ATTRIBUTE)
public class AssignWorkItemAttributeAuditingStrategy implements MethodAuditingStrategy {

    @Inject
    private WfmcAuditService wfmcAuditService;
    @Inject
    private WMAuditErrorUtil auditErrorUtil;
    @Inject
    private WfmcAuditQueryService wfmcAuditQueryService;

    private WMEventAuditAttribute wmEventAuditAttribute;
    private  WMWorkItemAudit wmWorkItemAudit;
    private WMAttributeAuditWorkItem wmAttributeAuditWorkItem;
    private AuditInfo auditInfo;

    @Override
    public void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
    }

    @Override
    public void auditMethodBeforeInvocation() {
        String username = getUserIdentification(auditInfo);

        wmWorkItemAudit = wfmcAuditService.savewmWorkItemAudit(
               (String) auditInfo.getArgumentsByParameterDescription().get(WfmcAuditedParameter.WORK_ITEM_ID),
                getWmProcessInstanceAudit());
        wmAttributeAuditWorkItem = wfmcAuditService.savewmAttributeAuditWorkItem(
                (String) auditInfo.getArgumentsByParameterDescription().get(WfmcAuditedParameter.ATTRIBUTE_NAME),
               wmWorkItemAudit);
        wmEventAuditAttribute = wfmcAuditService.savewmEventAuditAttribute(auditInfo.getArgumentsByParameterDescription().
                get(WfmcAuditedParameter.ATTRIBUTE_VALUE), username, wmAttributeAuditWorkItem);
    }

    @Override
    public void auditMethodAfterInvocation(Object o) {
        //it doesn't execute because it doesn't need any update
    }

    @Override
    public void auditMethodInvocationError(Throwable throwable) {
        auditErrorUtil.saveErrorIntoEntityWmErrorAudit(throwable, getWmProcessInstanceAudit(), auditInfo.getMethod().getName());
    }

    private String getUserIdentification(AuditInfo auditInfo) {
        return (String) auditInfo.invokeMethodOnInstance("getUserIdentification");
    }

    private WMProcessInstanceAudit getWmProcessInstanceAudit() {
        Object procInstId = auditInfo.getArgumentsByParameterDescription().get(WfmcAuditedParameter.PROCESS_INSTANCE_ID);
        return wfmcAuditQueryService.findByProcessInstanceId(procInstId.toString());
    }
}

