package ro.teamnet.wfmc.audit.service;


import org.springframework.stereotype.Service;
import ro.teamnet.wfmc.audit.build.AuditEntityBuilder;
import ro.teamnet.wfmc.audit.domain.*;
import ro.teamnet.wfmc.audit.repository.*;

import javax.inject.Inject;

@Service
public class WfmcAuditServiceImpl implements WfmcAuditService {

    @Inject
    private EventAuditWorkItemRepository eventAuditWorkItemRepository;
    @Inject
    private EventAuditAttributeRepository eventAuditAttributeRepository;
    @Inject
    private AttributeAuditProcessInstanceRepository attributeAuditProcessInstanceRepository;
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
     * Save an {@link WMEventAuditAttribute} into the {@link WMEventAudit} table with the discriminator column AT, for the {@link ro.teamnet.wfmc.audit.strategy.AssignProcessInstanceAttributeAuditingStrategy}
     * @param attributeValue the value of the attribute assigned
     * @param username the username who call the method
     * @param wmAttributeAuditProcessInstance to set the value property {@link WMAttributeAudit#attributeName} from {@link WMAttributeAudit} entity
     * @return the object for further operations
     */
    @Override
    public WMEventAuditAttribute saveWmEventAuditAttribute(Object attributeValue, String username, WMAttributeAuditProcessInstance wmAttributeAuditProcessInstance) {
        return eventAuditAttributeRepository.save(auditEntityBuilder.createwmEventAuditAttribute(attributeValue, username, wmAttributeAuditProcessInstance));
    }

    /**
     * Save an {@link WMAttributeAuditProcessInstance} into the table {@link WMAttributeAudit} table with the discriminator column PI, for the {@link ro.teamnet.wfmc.audit.strategy.AssignProcessInstanceAttributeAuditingStrategy}
     * @param attributeName the name of the attribute to assign
     * @param wmProcessInstanceAudit save the connection to the {@link WMProcessInstanceAudit}
     * @return the object for further operations
     */
    @Override
    public WMAttributeAuditProcessInstance saveWmAttributeAuditProcessInstance(String attributeName, WMProcessInstanceAudit wmProcessInstanceAudit) {
        return attributeAuditProcessInstanceRepository.save(auditEntityBuilder.createwmAttributeAudit(attributeName, wmProcessInstanceAudit));
    }

    /**
     * Save an {@link WMWorkItemAudit} object on its own table, for the {@link ro.teamnet.wfmc.audit.strategy.AssignWorkItemAttributeAuditingStrategy}, {@link ro.teamnet.wfmc.audit.strategy.ReassignWorkItemAuditingStrategy}
     * @param workItemId the work item id
     * @param wmProcessInstanceAudit the link to the {@link WMProcessInstanceAudit}
     * @return the object for further operations
     */
    @Override
    public WMWorkItemAudit savewmWorkItemAudit(String workItemId, WMProcessInstanceAudit wmProcessInstanceAudit) {
        return workItemAuditRepository.save(auditEntityBuilder.createwmWorkItemAudit(workItemId, wmProcessInstanceAudit));
    }

    /**
     * Save an {@link WMAttributeAuditWorkItem} into the {@link WMAttributeAudit} with the discriminator column WI, for the {@link ro.teamnet.wfmc.audit.strategy.AssignWorkItemAttributeAuditingStrategy}
     * @param attributeName the attribute name
     * @param wmWorkItemAudit the link to the {@link WMWorkItemAudit}
     * @return the object for further operations
     */
    @Override
    public WMAttributeAuditWorkItem savewmAttributeAuditWorkItem(String attributeName, WMWorkItemAudit wmWorkItemAudit) {
        return attributeAuditWorkItemRepository.save(auditEntityBuilder.createwmAttributeAudit(attributeName, wmWorkItemAudit));
    }

    /**
     * Save an {@link WMEventAuditProcessInstance} into the {@link WMEventAudit} table with the discriminator column PI, for the {@link ro.teamnet.wfmc.audit.strategy.AbortProcessInstanceAuditingStrategy}
     * @param wmProcessInstanceAudit the link to the {@link WMProcessInstanceAudit}
     * @param eventCode the specific event code
     * @param username the username who call the method
     * @return the object for further operations
     */
    @Override
    public WMEventAuditProcessInstance saveWmEventAuditProcessInstance(WMProcessInstanceAudit wmProcessInstanceAudit, Integer eventCode, String username) {

        return eventAuditProcessInstanceRepository.save(auditEntityBuilder.createwmEventAuditProcessInstance(wmProcessInstanceAudit, eventCode, username));
    }

    /**
     * Save an {@link WMEventAuditAttribute} into the {@link WMEventAudit} table with the discriminator column AT, for the {@link ro.teamnet.wfmc.audit.strategy.AssignWorkItemAttributeAuditingStrategy}
     * @param attributeValue the value of the attribute
     * @param username the username who call the method
     * @param wmAttributeAuditWorkItem the link to the {@link WMAttributeAudit} to populate the {@link WMAttributeAudit#attributeName}
     * @return
     */
    @Override
    public WMEventAuditAttribute savewmEventAuditAttribute(Object attributeValue, String username, WMAttributeAuditWorkItem wmAttributeAuditWorkItem) {

        return eventAuditAttributeRepository.save(auditEntityBuilder.createwmEventAuditAttribute(attributeValue, username, wmAttributeAuditWorkItem));
    }

    /**
     * Save an {@link ro.teamnet.wfmc.audit.domain.WMEventAuditWorkItem} object into the {@link ro.teamnet.wfmc.audit.domain.WMEventAudit} with the discriminator column WI, for the {@link ro.teamnet.wfmc.audit.strategy.CompleteWorkItemAuditingStrategy}, {@link ro.teamnet.wfmc.audit.strategy.ReassignWorkItemAuditingStrategy}
     * @param username the username who call the method
     * @param wmWorkItemAudit the link to the {@link ro.teamnet.wfmc.audit.domain.WMWorkItemAudit}
     * @return the object for further operations
     */
    @Override
    public WMEventAuditWorkItem savewmEventAuditWorkItem(String username, WMWorkItemAudit wmWorkItemAudit) {
        return eventAuditWorkItemRepository.save(auditEntityBuilder.createwmEventAuditWorkItem(username, wmWorkItemAudit));
    }

    /**
     * Save an {@link ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit} object, for the {@link ro.teamnet.wfmc.audit.strategy.CreateProcessInstanceAuditingStrategy}
     * @param procDefId the process definition id
     * @param procInstName the name of the process instance
     * @param processInstanceId the id returned by the {@link org.wfmc.wapi.WAPI#createProcessInstance}
     * @return the object for further operations
     */
    @Override
    public WMProcessInstanceAudit saveProcessInstanceAudit(String procDefId, String procInstName, String processInstanceId) {

        return processInstanceAuditRepository.save(auditEntityBuilder.createwmProcessInstanceAudit(processInstanceId, procDefId, procInstName));
    }

    /**
     * Save a {@link WMEventAuditProcessInstance} object into {@link WMEventAudit} table from database, for the {@link ro.teamnet.wfmc.audit.strategy.CreateProcessInstanceAuditingStrategy}, {@link ro.teamnet.wfmc.audit.strategy.StartProcessAuditingStrategy}
     *
     * @param wmProcessInstanceAudit represent the link between {@link WMEventAuditProcessInstance} and {@link WMProcessInstanceAudit}
     * @param eventCode specify the code for actual event
     * @param username the current username
     * @return
     */
    @Override
    public WMEventAuditProcessInstance saveEventAuditProcessInstance(WMProcessInstanceAudit wmProcessInstanceAudit, int eventCode, String username) {

        return eventAuditProcessInstanceRepository.save(auditEntityBuilder.createwmEventAuditProcessInstance(wmProcessInstanceAudit, eventCode, username));
    }

    /**
     * Update a {@link WMProcessInstanceAudit} object
     *
     * @param wmProcessInstanceAudit object that have to be updated
     * @return updated object
     */
    @Override
    public WMProcessInstanceAudit updateProcessInstance(WMProcessInstanceAudit wmProcessInstanceAudit) {

        return processInstanceAuditRepository.save(wmProcessInstanceAudit);
    }
}
