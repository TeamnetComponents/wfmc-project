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
    @Qualifier(WfmcAuditedMethod.START_PROCESS)
    StartProcessAuditingStrategy startProcessAuditingStrategy;

    @Inject
    @Qualifier(WfmcAuditedMethod.ASSIGN_WORK_ITEM_ATTRIBUTE)
    AssignWorkItemAttributeAuditingStrategy assignWorkItemAttributeAuditingStrategy;

    @Inject
    @Qualifier(WfmcAuditedMethod.REASSIGN_WORK_ITEM)
    ReassignWorkItemAuditingStrategy reassignWorkItemAuditingStrategy;

    @Inject
    @Qualifier(WfmcAuditedMethod.ABORT_PROCESS_INSTANCE)
    AbortProcessInstanceAuditingStrategy abortProcessInstanceAuditingStrategy;

    @Inject
    @Qualifier(WfmcAuditedMethod.COMPLETE_WORK_ITEM)
    CompleteWorkItemAuditingStrategy completeWorkItemAuditingStrategy;

    @Inject
    @Qualifier("default")
    DefaultMethodAuditingStrategy defaultStrategy;

    public MethodAuditingStrategy getStrategy(String methodName) {

        switch (methodName) {
            case WfmcAuditedMethod.CREATE_PROCESS_INSTANCE:
                return createProcessInstance;
            case WfmcAuditedMethod.START_PROCESS:
                return startProcessAuditingStrategy;
            case WfmcAuditedMethod.ASSIGN_PROCESS_INSTANCE_ATTRIBUTE:
                return assignProcessInstanceAttribute;
            case WfmcAuditedMethod.ASSIGN_WORK_ITEM_ATTRIBUTE:
                return assignWorkItemAttributeAuditingStrategy;
            case WfmcAuditedMethod.REASSIGN_WORK_ITEM:
                return reassignWorkItemAuditingStrategy;
            case WfmcAuditedMethod.ABORT_PROCESS_INSTANCE:
                return abortProcessInstanceAuditingStrategy;
            case WfmcAuditedMethod.COMPLETE_WORK_ITEM:
                return completeWorkItemAuditingStrategy;
            default:
                return defaultStrategy;
        }
    }
}
