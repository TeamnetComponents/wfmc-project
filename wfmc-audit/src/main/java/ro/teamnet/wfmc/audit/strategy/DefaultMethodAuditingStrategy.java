package ro.teamnet.wfmc.audit.strategy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ro.teamnet.audit.strategy.MethodAuditingStrategy;
import ro.teamnet.audit.util.AuditInfo;


@Component
@Qualifier("default")
public class DefaultMethodAuditingStrategy implements MethodAuditingStrategy {

    @Override
    public void setAuditInfo(AuditInfo auditInfo) {

    }

    @Override
    public void auditMethodBeforeInvocation() {

    }

    @Override
    public void auditMethodAfterInvocation(Object auditedMethodReturnValue) {

    }

    @Override
    public void auditMethodInvocationError(Throwable throwable) {

    }
}
