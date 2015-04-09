package ro.teamnet.wfmc.audit.aop;

import org.springframework.stereotype.Service;
import org.wfmc.audit.WMAEventCode;
import ro.teamnet.audit.util.AuditInfo;
import ro.teamnet.wfmc.audit.constants.WfmcAuditedParameter;
import ro.teamnet.wfmc.audit.domain.WMEventAuditProcessInstance;
import ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit;
import ro.teamnet.wfmc.audit.service.WfmcAuditService;
import ro.teamnet.wfmc.audit.util.WMAuditErrorUtil;
import ro.teamnet.wfmc.audit.util.WfmcPreviousState;

import javax.inject.Inject;

@Service
public class MyService {

    @Inject
    private WfmcAuditService wfmcAuditService;
    @Inject
    private WMAuditErrorUtil auditErrorUtil;

    /**
     * Update {@link WfmcAuditingAspect#auditMethod returnValue} by calling the audited method
     *
     * @param wmEventAuditProcessInstance
     * @param returnValue
     */
    public void updateMethodInfo(WMEventAuditProcessInstance wmEventAuditProcessInstance, Object returnValue) {
        WMProcessInstanceAudit wmProcessInstanceAudit = wmEventAuditProcessInstance.getWmProcessInstanceAudit();
        wmProcessInstanceAudit.setProcessInstanceId(returnValue.toString());
        wfmcAuditService.updateProcessInstance(wmProcessInstanceAudit);
    }

    /**
     * Save eventual errors about proceeding and also provide informations about
     * {@link ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit}
     *
     * @param auditInfo hold informations about audited method
     * @param wmEventAuditProcessInstance
     * @param throwable
     */
    public void saveError(AuditInfo auditInfo, WMEventAuditProcessInstance wmEventAuditProcessInstance, Throwable throwable) {
        WMProcessInstanceAudit wmProcessInstanceAudit = wmEventAuditProcessInstance.getWmProcessInstanceAudit();
        auditErrorUtil.saveErrorIntoEntityWmErrorAudit(throwable, wmProcessInstanceAudit, auditInfo.getMethod().getName());
    }

    /**
     * Save audited method inforamtions before calling
     *
     * @param auditInfo hold informations about audited method
     * @return an {@link WMEventAuditProcessInstance} object
     */
    public WMEventAuditProcessInstance saveMethodInfoBeforeCalling(AuditInfo auditInfo) {
        String username = getUserIdentification(auditInfo);
        WMProcessInstanceAudit wmProcessInstanceAudit = wfmcAuditService.saveProcessInstanceAudit(
                (String) auditInfo.getArgumentsByParameterDescription().get(WfmcAuditedParameter.PROCESS_DEFINITION_ID),
                (String) auditInfo.getArgumentsByParameterDescription().get(WfmcAuditedParameter.PROCESS_INSTANCE_NAME),null);

        return wfmcAuditService.saveEventAuditProcessInstance(
                wmProcessInstanceAudit, WfmcPreviousState.CREATE_PROCESS_INSTANCE,
                WMAEventCode.CREATED_PROCESS_INSTANCE_INT, username);
    }

    private String getUserIdentification(AuditInfo auditInfo) {
        return (String) auditInfo.invokeMethodOnInstance("getUserIdentification");
    }
}
