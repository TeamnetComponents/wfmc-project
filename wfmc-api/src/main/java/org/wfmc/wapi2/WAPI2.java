/*
 *
 * Copyright (c) 2002 Adrian Price.  All rights reserved.
 */

package org.wfmc.wapi2;

import org.wfmc.wapi.*;

/**
 * Additional functions described in WfMC-TC-1009 Version 2.0e (Beta).
 *
 * @author Adrian Price
 */
public interface WAPI2 extends WAPI {
    boolean isWorkListHandlerProfileSupported();

    boolean isProcessControlStatusProfileSupported();

    boolean isProcessDefinitionProfileSupported();

    boolean isProcessAdminProfileSupported();

    boolean isActivityControlStatusProfileSupported();

    boolean isActivityAdminProfileSupported();

    boolean isEntityHandlerProfileSupported();

    boolean isAuditRecordProfileSupported();

    boolean isToolAgentProfileSupported();

    /**
     * <em>Not yet implemented - do not call</em>. This is one of the online
     * Process Definition Profile methods.
     *
     * @param scopingEntity
     * @param entityClass
     * @param entityName
     * @return The workflow entity.
     * @throws WMWorkflowException
     */
    WMEntity createEntity(WMEntity scopingEntity, String entityClass,
        String entityName) throws WMWorkflowException;

    /**
     * <em>Not yet implemented - do not call</em>. This is one of the online
     * Process Definition Profile methods.
     *
     * @param scopingEntity
     * @param filter
     * @param countFlag
     * @return An iterator for retrieving the selected entities.
     * @throws WMWorkflowException
     */
    WMEntityIterator listEntities(WMEntity scopingEntity, WMFilter filter,
        boolean countFlag) throws WMWorkflowException;

    /**
     * <em>Not yet implemented - do not call</em>. This is one of the online
     * Process Definition Profile methods.
     *
     * @param scopingEntity
     * @param entityId
     * @throws WMWorkflowException
     */
    void deleteEntity(WMEntity scopingEntity, String entityId)
        throws WMWorkflowException;

    /**
     * <em>Not yet implemented - do not call</em>. This is one of the online
     * Process Definition Profile methods.
     *
     * @param scopingEntity
     * @param entityId
     * @param filter
     * @param countFlag
     * @return An iterator for retrieving the selected entity attributes.
     * @throws WMWorkflowException
     */
    WMAttributeIterator listEntityAttributes(WMEntity scopingEntity,
        String entityId, WMFilter filter, boolean countFlag)
        throws WMWorkflowException;

    // Spec. says param 3 is a WMEntity... does that really make sense?

    /**
     * <em>Not yet implemented - do not call</em>. This is one of the online
     * Process Definition Profile methods.
     *
     * @param scopingEntity
     * @param entityHandle
     * @param attributeName
     * @return The entity attribute value.
     * @throws WMWorkflowException
     */
    WMAttribute getEntityAttributeValue(WMEntity scopingEntity,
        WMEntity entityHandle, String attributeName) throws WMWorkflowException;

    // These methods are for retrieving multi-valued entity attributes.

    /**
     * <em>Not yet implemented - do not call</em>. This is one of the online
     * Process Definition Profile methods.
     *
     * @param scopingEntity
     * @param entityHandle
     * @param attributeName
     * @return An iterator for retrieving the selected entity attribute values.
     * @throws WMWorkflowException
     */
    WMAttributeIterator listEntityAttributeValues(WMEntity scopingEntity,
        String entityHandle, String attributeName) throws WMWorkflowException;

    /**
     * <em>Not yet implemented - do not call</em>. This is one of the online
     * Process Definition Profile methods.
     *
     * @param entityHandle
     * @param attributeName
     * @param attributeType
     * @param attributeValue
     * @throws WMWorkflowException
     */
    void assignEntityAttributeValue(WMEntity entityHandle, String attributeName,
        int attributeType, String attributeValue) throws WMWorkflowException;

    /**
     * <em>Not yet implemented - do not call</em>. This is one of the online
     * Process Definition Profile methods.
     *
     * @param entityHandle
     * @param attributeName
     * @throws WMWorkflowException
     */
    void clearEntityAttributeList(WMEntity entityHandle, String attributeName)
        throws WMWorkflowException;

    /**
     * <em>Not yet implemented - do not call</em>. This is one of the online
     * Process Definition Profile methods.
     *
     * @param entityHandle
     * @param attributeName
     * @param attributeType
     * @param attributeValue
     * @throws WMWorkflowException
     */
    void addEntityAttributeValue(WMEntity entityHandle, String attributeName,
        int attributeType, String attributeValue) throws WMWorkflowException;

    /**
     * <em>Not yet implemented - do not call</em>. This is one of the online
     * Process Definition Profile methods.
     *
     * @param name
     * @param scope
     * @return The workflow definition entity.
     * @throws WMWorkflowException
     */
    WMEntity openWorkflowDefinition(String name, String scope)
        throws WMWorkflowException;

    /**
     * <em>Not yet implemented - do not call</em>. This is one of the online
     * Process Definition Profile methods.
     *
     * @param workflowDefinitionHandle
     * @throws WMWorkflowException
     */
    void closeWorkflowDefinition(WMEntity workflowDefinitionHandle)
        throws WMWorkflowException;

    /**
     * <em>Not yet implemented - do not call</em>. This is one of the online
     * Process Definition Profile methods.
     *
     * @return The package Id.
     * @throws WMWorkflowException
     */
    String createPackage() throws WMWorkflowException;

    /**
     * <em>Not yet implemented - do not call</em>. This is one of the online
     * Process Definition Profile methods.
     *
     * @param processDefinitionId
     * @throws WMWorkflowException
     */
    void deleteProcessDefinition(String processDefinitionId)
        throws WMWorkflowException;

    // Spec. says param 2 is WMTPProcDefinition, but this isn't defined
    // anywhere.  In any case, for consistency it should be the procDefId.

    /**
     * <em>Not yet implemented - do not call</em>. This is one of the online
     * Process Definition Profile methods.
     *
     * @param procDefId
     * @return The process definition entity.
     * @throws WMWorkflowException
     */
    WMEntity openProcessDefinition(String procDefId) throws WMWorkflowException;

    /**
     * <em>Not yet implemented - do not call</em>. This is one of the online
     * Process Definition Profile methods.
     *
     * @param procModelHandle
     * @throws WMWorkflowException
     */
    void closeProcessDefinition(WMEntity procModelHandle)
        throws WMWorkflowException;

    /**
     * <em>Not yet implemented - do not call</em>. This is one of the online
     * Process Definition Profile methods.
     *
     * @param procModelId
     * @param sourceActDefId
     * @param targetActDefId
     * @return The transition entity.
     * @throws WMWorkflowException
     */
    WMEntity addTransition(String procModelId, String sourceActDefId,
        String targetActDefId) throws WMWorkflowException;

    /**
     * <em>Not yet implemented - do not call</em>. This is one of the online
     * Process Definition Profile methods.
     *
     * @param procModelId
     * @param procDataId
     * @param attributeName
     * @param attributeType
     * @param attributeLength
     * @param attributeValue
     * @throws WMWorkflowException
     */
    void addProcessDataAttribute(String procModelId, String procDataId,
        String attributeName, int attributeType, int attributeLength,
        String attributeValue) throws WMWorkflowException;

    /**
     * <em>Not yet implemented - do not call</em>. This is one of the online
     * Process Definition Profile methods.
     *
     * @param procModelId
     * @param procDataId
     * @param attributeName
     * @throws WMWorkflowException
     */
    void removeProcessDataAttribute(String procModelId, String procDataId,
        String attributeName) throws WMWorkflowException;
}