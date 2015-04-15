package ro.teamnet.wfmc.audit.service;


import org.springframework.stereotype.Service;
import ro.teamnet.wfmc.audit.build.AuditEntityBuilder;
import ro.teamnet.wfmc.audit.domain.*;
import ro.teamnet.wfmc.audit.repository.*;

import javax.inject.Inject;

/**
 * Created by Ioan.Ivan on 3/26/2015.
 */
@Service
public class WfmcAuditServiceImpl implements WfmcAuditService {


    @Inject
    private EventAuditWorkItemRepository eventAuditWorkItemRepository;
    @Inject
    private EventAuditAttributeRepository eventAuditAttributeRepository;
    @Inject
    private AttributeAuditWorkItemRepository attributeAuditWorkItemRepository;
    @Inject
    private ProcessInstanceAuditRepository processInstanceAuditRepository;
    @Inject
    private EventAuditProcessInstanceRepository eventAuditProcessInstanceRepository;
    @Inject
    private WorkItemAuditRepository workItemAuditRepository;

    AuditEntityBuilder auditEntityBuilder = new AuditEntityBuilder();

    /**
     * Save an {@link WMEventAuditAttribute} object into the correct tables from database.
     *
     * @param attributeName          the attribute name to assign
     * @param attributeValue         the value to assign to the attribute
     * @param username               the current username
     * @param wmProcessInstanceAudit
     * @return the instance for further operations.
     */
    @Override
    public WMEventAuditAttribute convertAndSaveAssignAttributeAudit(String attributeName, Object attributeValue, String username, WMProcessInstanceAudit wmProcessInstanceAudit) {

        WMAttributeAuditProcessInstance wmAttributeAudit = auditEntityBuilder.createwmAttributeAudit(attributeName, wmProcessInstanceAudit);
        return eventAuditAttributeRepository.save(auditEntityBuilder.createwmEventAuditAttribute(attributeValue, username, wmAttributeAudit));
    }


    public WMWorkItemAudit savewmWorkItemAudit(String workItemId, WMProcessInstanceAudit wmProcessInstanceAudit) {
        return workItemAuditRepository.save(auditEntityBuilder.createwmWorkItemAudit(workItemId, wmProcessInstanceAudit));
    }

    @Override
    public WMAttributeAuditWorkItem savewmAttributeAuditWorkItem(String attributeName, WMWorkItemAudit wmWorkItemAudit) {
        return attributeAuditWorkItemRepository.save(auditEntityBuilder.createwmAttributeAudit(attributeName, wmWorkItemAudit));
    }

    /**
     * Save an {@link WMEventAuditWorkItem} object.
     *
     * @param processInstanceId
     * @param workItemId
     * @param sourceUser
     * @param targetUser
     * @param username
     * @param processDefinitionId
     * @return an instance for further operations.
     */
    @Override
    public WMEventAuditWorkItem convertAndSaveReassignWorkItem(String processInstanceId, String workItemId, String sourceUser, String targetUser, String username, String processDefinitionId, String processBusinessName) {


        WMProcessInstanceAudit wmProcessInstanceAudit = processInstanceAuditRepository.save(auditEntityBuilder.createwmProcessInstanceAudit(processInstanceId, processDefinitionId, processBusinessName));

        WMWorkItemAudit wmWorkItemAudit = workItemAuditRepository.save(auditEntityBuilder.createwmWorkItemAudit(workItemId, wmProcessInstanceAudit));

        return eventAuditWorkItemRepository.save(auditEntityBuilder.createwmEventAuditWorkItemForReassign(username, wmWorkItemAudit));
    }

    /**
     * @param wmProcessInstanceAudit
     * @param previousState
     * @param eventCode
     * @param username
     * @return
     */
    public WMEventAuditProcessInstance convertAndSaveAbortProcessInstance(WMProcessInstanceAudit wmProcessInstanceAudit, String previousState, Integer eventCode, String username) {

        return eventAuditProcessInstanceRepository.save(auditEntityBuilder.createwmEventAuditProcessInstance(wmProcessInstanceAudit, previousState, eventCode, username));
    }


    @Override
    public WMEventAuditAttribute savewmEventAuditAttribute(Object attributeValue, String username, WMAttributeAuditWorkItem wmAttributeAuditWorkItem) {

        return eventAuditAttributeRepository.save(auditEntityBuilder.createwmEventAuditAttribute(attributeValue, username, wmAttributeAuditWorkItem));
    }

    @Override
    public WMProcessInstanceAudit saveProcessInstanceAudit(String procDefId, String procInstName, String processInstanceId) {

        return processInstanceAuditRepository.save(auditEntityBuilder.createwmProcessInstanceAudit(processInstanceId, procDefId, procInstName));
    }

    @Override
    public WMEventAuditProcessInstance saveEventAuditProcessInstance(WMProcessInstanceAudit wmProcessInstanceAudit, String previousState, int eventCode, String username) {

        return eventAuditProcessInstanceRepository.save(auditEntityBuilder.createwmEventAuditProcessInstance(wmProcessInstanceAudit, previousState, eventCode, username));
    }

    @Override
    public WMProcessInstanceAudit updateProcessInstance(WMProcessInstanceAudit wmProcessInstanceAudit) {

        return processInstanceAuditRepository.save(wmProcessInstanceAudit);
    }

//    @Override
//    public WMEventAuditWorkItem convertAndSaveCompleteWorkItem(String processInstanceId, String workItemId, String username, String processDefinitionId) {
//
//        WMProcessInstanceAudit wmProcessInstanceAudit = processInstanceAuditRepository.save(auditEntityBuilder.createwmProcessInstanceAudit(processInstanceId, processDefinitionId, null));
//
//        WMWorkItemAudit wmWorkItemAudit = workItemAuditRepository.save(auditEntityBuilder.createwmWorkItemAudit(workItemId, wmProcessInstanceAudit));
//
//        return eventAuditWorkItemRepository.save(auditEntityBuilder.createwmEventAuditWorkItemForComplete(username, wmWorkItemAudit));
//    }
}
