package ro.teamnet.wfmc.audit.strategy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ro.teamnet.audit.util.AuditInfo;


@Component
@Qualifier("default")
public class DefaultMethodAuditingStrategy implements MethodAuditingStrategy {

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
