package ro.teamnet.wfmc.audit.aop;

import ro.teamnet.audit.util.AuditInfo;

public interface MethodAuditingStrategy {

    void setAuditInfo(AuditInfo auditInfo);

    void saveMethodInfoBeforeCalling();

    void updateMethodInfo( Object returnValue);

    void saveError(Throwable throwable);
}
