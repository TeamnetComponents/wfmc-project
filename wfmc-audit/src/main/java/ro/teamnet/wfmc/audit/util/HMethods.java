package ro.teamnet.wfmc.audit.util;

import ro.teamnet.audit.strategy.MethodAuditingStrategy;
import ro.teamnet.audit.util.AuditInfo;
import ro.teamnet.wfmc.audit.constants.WfmcAuditedParameter;
import ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit;
import ro.teamnet.wfmc.audit.service.WfmcAuditQueryService;

import javax.inject.Inject;

public abstract class HMethods implements MethodAuditingStrategy {


    @Inject
    public WfmcAuditQueryService wfmcAuditQueryService;

    /**
     * hold information about audited method
     */
    public AuditInfo auditInfo;

    @Override
    public void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
    }

    /**
     * Determine the value of an argument base on its descriptions
     *
     * @param parameter a string which determine the name of the argument
     * @return an argument by its descriptions
     */
    public Object getMethodParameter(String parameter) {
        return auditInfo.getArgumentsByParameterDescription().get(parameter);
    }

    /**
     * Determine the user who call actual method
     *
     * @param auditInfo hold information about audited method
     * @return the username
     */
    public String getUserIdentification(AuditInfo auditInfo) {
        return (String) auditInfo.invokeMethodOnInstance("getUserIdentification");
    }

    /**
     * Find an {@link ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit} object by its {@link ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit#processInstanceId}
     *
     * @return the {@link ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit}
     */
    public WMProcessInstanceAudit getWmProcessInstanceAudit() {
        Object procInstId = getMethodParameter(WfmcAuditedParameter.PROCESS_INSTANCE_ID);
        return wfmcAuditQueryService.findWMProcessInstanceAuditByProcessInstanceId(procInstId.toString());
    }
}
