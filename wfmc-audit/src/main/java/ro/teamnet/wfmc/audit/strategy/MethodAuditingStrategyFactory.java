package ro.teamnet.wfmc.audit.strategy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ro.teamnet.wfmc.audit.constants.WfmcAuditedMethod;

import javax.inject.Inject;

@Component
public class MethodAuditingStrategyFactory {

    @Inject
    @Qualifier(WfmcAuditedMethod.CREATE_PROCESS_INSTANCE)
    CreateProcessInstanceAuditingStrategy createProcessInstance;

    @Inject
    @Qualifier("default")
    DefaultMethodAuditingStrategy defaultStrategy;

    public MethodAuditingStrategy getStrategy(String methodName) {

        switch (methodName) {
            case WfmcAuditedMethod.CREATE_PROCESS_INSTANCE:
                return createProcessInstance;
            default : return defaultStrategy;
        }
    }
}
