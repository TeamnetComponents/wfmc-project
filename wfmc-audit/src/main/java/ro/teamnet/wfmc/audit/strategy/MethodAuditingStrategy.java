package ro.teamnet.wfmc.audit.strategy;

import ro.teamnet.audit.util.AuditInfo;

public interface MethodAuditingStrategy {

    void setAuditInfo(AuditInfo auditInfo);

    /**
     * Save audited method information before calling
     */
    void saveMethodInfoBeforeCalling();

    /**
     * Update audited method {@link ro.teamnet.wfmc.audit.aop.WfmcAuditingAspect#auditMethod returnValue} after calling
     *
     * @param returnValue
     */
    void updateMethodInfo(Object returnValue);

    /**
     * Save eventual errors about proceeding and also provide information about
     * {@link ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit}
     *
     * @param throwable
     */
    void saveError(Throwable throwable);
}
