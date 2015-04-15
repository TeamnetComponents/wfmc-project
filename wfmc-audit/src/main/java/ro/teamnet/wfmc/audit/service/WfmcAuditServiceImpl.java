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
    private WorkItemAuditRepository workItemAuditRepository;
    @Inject
    private EventAuditWorkItemRepository eventAuditWorkItemRepository;
    @Inject
    private EventAuditAttributeRepository eventAuditAttributeRepository;
    @Inject
    private ProcessInstanceAuditRepository processInstanceAuditRepository;
    @Inject
    private EventAuditProcessInstanceRepository eventAuditProcessInstanceRepository;

    AuditEntityBuilder auditEntityBuilder = new AuditEntityBuilder();

    /**
     * Save an {@link WMEventAuditAttribute} object into the correct tables from database.
     * @param attributeName the attribute name to assign
     * @param attributeValue the value to assign to the attribute
     * @param username the current username
     * @param processDefinitionBusinessName used to find the WMProcessInstanceAudit
     * @return the instance for further operations.
     */
    @Override
    public WMEventAuditAttribute convertAndSaveAssignAttributeAudit(String attributeName, Object attributeValue, String username, String processDefinitionBusinessName, WMProcessInstanceAudit wmProcessInstanceAudit) {

        WMAttributeAuditProcessInstance wmAttributeAudit = auditEntityBuilder.createwmAttributeAudit(attributeName, wmProcessInstanceAudit);
        return eventAuditAttributeRepository.save(auditEntityBuilder.createwmEventAuditAttribute(attributeValue, username, wmAttributeAudit));
    }
    /**
     * Update the list of {@link WMAttributeAudit#wmEventAuditAttributes} with {@link WMEventAuditAttribute}
     * @param wmEventAuditAttribute the object to update
     * @return the instance for further operations.
     */
    @Override
    public WMEventAuditAttribute updateEventAuditAttributes(WMEventAuditAttribute wmEventAuditAttribute) {

        return eventAuditAttributeRepository.save(wmEventAuditAttribute);
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

    @Override
    public WMEventAuditWorkItem convertAndSaveCompleteWorkItem(String processInstanceId, String workItemId, String username, String processDefinitionId) {

        WMProcessInstanceAudit wmProcessInstanceAudit = processInstanceAuditRepository.save(auditEntityBuilder.createwmProcessInstanceAudit(processInstanceId, processDefinitionId, null));

        WMWorkItemAudit wmWorkItemAudit = workItemAuditRepository.save(auditEntityBuilder.createwmWorkItemAudit(workItemId, wmProcessInstanceAudit));

        return eventAuditWorkItemRepository.save(auditEntityBuilder.createwmEventAuditWorkItemForComplete(username, wmWorkItemAudit));
    }

    @Override
    public WMEventAuditWorkItem convertAndSaveReassignWorkItem(String processInstanceId, String workItemId, String sourceUser, String targetUser, String username, String processDefinitionId) {

        WMProcessInstanceAudit wmProcessInstanceAudit = processInstanceAuditRepository.save(auditEntityBuilder.createwmProcessInstanceAudit(processInstanceId, processDefinitionId, null));

        WMWorkItemAudit wmWorkItemAudit = workItemAuditRepository.save(auditEntityBuilder.createwmWorkItemAudit(workItemId, wmProcessInstanceAudit));

        return eventAuditWorkItemRepository.save(auditEntityBuilder.createwmEventAuditWorkItemForReassign(username, wmWorkItemAudit));
    }

    /*public WMEventAuditAttribute convertAndSaveAssignWorkItemAttribute(String processInstanceId, String workItemId, String attributeName, Object attributeValue, String username) {

        @SuppressWarnings("unchecked")
        WMAttributeAudit wmAttributeAudit = (WMAttributeAudit) attributeAuditWorkItemRepository.save((Iterable<WMAttributeAuditWorkItem>) auditEntityBuilder.createwmAttributeAudit(attributeName));

        return eventAuditAttributeRepository.save(auditEntityBuilder.createwmEventAuditAttribute(attributeValue, username, wmAttributeAudit));
    }*/
}
