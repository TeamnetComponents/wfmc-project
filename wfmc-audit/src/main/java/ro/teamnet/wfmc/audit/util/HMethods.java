package ro.teamnet.wfmc.audit.util;

import ro.teamnet.audit.strategy.MethodAuditingStrategy;
import ro.teamnet.audit.util.AuditInfo;

public abstract class HMethods implements MethodAuditingStrategy {

    public AuditInfo auditInfo;
    @Override
    public void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
    }

    public Object getMethodParameter(String parameter) {
        return auditInfo.getArgumentsByParameterDescription().get(parameter);
    }
}
