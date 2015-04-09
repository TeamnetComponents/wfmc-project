package ro.teamnet.wfmc.audit.aop;

import ro.teamnet.audit.util.AuditInfo;

public class OtherMethodAuditingStrategy implements MethodAuditingStrategy {


    @Override
    public void setAuditInfo(AuditInfo auditInfo) {

    }

    @Override
    public void saveMethodInfoBeforeCalling() {

    }

    @Override
    public void updateMethodInfo(Object returnValue) {

    }

    @Override
    public void saveError(Throwable throwable) {

    }
}
