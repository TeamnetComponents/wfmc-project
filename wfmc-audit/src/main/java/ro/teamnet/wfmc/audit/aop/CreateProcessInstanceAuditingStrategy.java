package ro.teamnet.wfmc.audit.aop;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
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
@Qualifier(value = "createProcessInstanceAuditingStrategy")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CreateProcessInstanceAuditingStrategy {// implements MethodAuditingStrategy {

    @Inject
    private WfmcAuditService wfmcAuditService;
    @Inject
    private WMAuditErrorUtil auditErrorUtil;

    private WMProcessInstanceAudit processInstanceAudit;

    private WMEventAuditProcessInstance eventAuditProcessInstance;



    /**
     *  hold informations about audited method
     */
    private AuditInfo auditInfo;

    public AuditInfo getAuditInfo() {

        return auditInfo;
    }

    public void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
    }


    /**
     * Update {@link WfmcAuditingAspect#auditMethod returnValue} by calling the audited method
     *
     * @param returnValue
     */
    public void updateMethodInfo( Object returnValue) {

        processInstanceAudit.setProcessInstanceId(returnValue.toString());
        wfmcAuditService.updateProcessInstance(processInstanceAudit);
    }

    /**
     * Save eventual errors about proceeding and also provide informations about
     * {@link ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit}
     *
     * @param throwable
     */
    public void saveError(Throwable throwable) {

        auditErrorUtil.saveErrorIntoEntityWmErrorAudit(throwable, processInstanceAudit, auditInfo.getMethod().getName());
    }

    /**
     * Save audited method inforamtions before calling
     *
     */
    public void saveMethodInfoBeforeCalling() {
        String username = getUserIdentification(auditInfo);
        processInstanceAudit = wfmcAuditService.saveProcessInstanceAudit(
                (String) auditInfo.getArgumentsByParameterDescription().get(WfmcAuditedParameter.PROCESS_DEFINITION_ID),
                (String) auditInfo.getArgumentsByParameterDescription().get(WfmcAuditedParameter.PROCESS_INSTANCE_NAME),null);

        eventAuditProcessInstance = wfmcAuditService.saveEventAuditProcessInstance(
                processInstanceAudit, WfmcPreviousState.CREATE_PROCESS_INSTANCE,
                WMAEventCode.CREATED_PROCESS_INSTANCE_INT, username);
    }

    private String getUserIdentification(AuditInfo auditInfo) {
        return (String) auditInfo.invokeMethodOnInstance("getUserIdentification");
    }
}
