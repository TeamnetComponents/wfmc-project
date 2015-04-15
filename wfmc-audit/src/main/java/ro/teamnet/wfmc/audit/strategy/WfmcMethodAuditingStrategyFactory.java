package ro.teamnet.wfmc.audit.strategy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ro.teamnet.audit.strategy.MethodAuditingStrategy;
import ro.teamnet.audit.strategy.MethodAuditingStrategyFactory;
import ro.teamnet.wfmc.audit.constants.WfmcAuditStrategy;
import ro.teamnet.wfmc.audit.constants.WfmcAuditedMethod;

import javax.inject.Inject;

@Component
@Qualifier(WfmcAuditStrategy.WFMC)
public class WfmcMethodAuditingStrategyFactory implements MethodAuditingStrategyFactory {

    @Inject
    @Qualifier(WfmcAuditedMethod.CREATE_PROCESS_INSTANCE)
    CreateProcessInstanceAuditingStrategy createProcessInstance;

    @Inject
    @Qualifier(WfmcAuditedMethod.ASSIGN_PROCESS_INSTANCE_ATTRIBUTE)
    AssignProcessInstanceAttributeAuditingStrategy assignProcessInstanceAttribute;

    @Inject
    @Qualifier("default")
    DefaultMethodAuditingStrategy defaultStrategy;

    public MethodAuditingStrategy getStrategy(String methodName) {

        switch (methodName) {
            case WfmcAuditedMethod.CREATE_PROCESS_INSTANCE:
                return createProcessInstance;
            case WfmcAuditedMethod.ASSIGN_PROCESS_INSTANCE_ATTRIBUTE:
                return assignProcessInstanceAttribute;
            default:
                return defaultStrategy;
        }
    }
}
