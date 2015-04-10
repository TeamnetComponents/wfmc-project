package ro.teamnet.audit.strategy;

/**
 * A factory for creating MethodAuditingStrategy instances.
 */
public interface MethodAuditingStrategyFactory {
    MethodAuditingStrategy getStrategy(String methodName);
}
