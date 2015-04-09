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
    private AttributeAuditWorkItemRepository attributeAuditWorkItemRepository;
    @Inject
    private ProcessInstanceAuditRepository processInstanceAuditRepository;
    @Inject
    private EventAuditProcessInstanceRepository eventAuditProcessInstanceRepository;

    AuditEntityBuilder auditEntityBuilder = new AuditEntityBuilder();


    public WMEventAuditProcessInstance convertAndSaveCreateProcessInstance(String procDefId, String procInstName, String processInstanceId, String previousState, int eventCode, String username) {

        WMProcessInstanceAudit wmProcessInstanceAudit = processInstanceAuditRepository.save(auditEntityBuilder.createwmProcessInstanceAudit3(processInstanceId, procDefId, procInstName));

        return eventAuditProcessInstanceRepository.save(auditEntityBuilder.createwmEventAuditProcessInstance(wmProcessInstanceAudit, previousState, eventCode, username));
    }

    public WMProcessInstanceAudit updateProcessInstance(WMProcessInstanceAudit wmProcessInstanceAudit) {

        return processInstanceAuditRepository.save(wmProcessInstanceAudit);
    }


    public WMEventAuditWorkItem convertAndSaveCompleteWorkItem(String processInstanceId, String workItemId, String username, String processDefinitionId) {

        WMProcessInstanceAudit wmProcessInstanceAudit = processInstanceAuditRepository.save(auditEntityBuilder.createwmProcessInstanceAudit(processInstanceId, processDefinitionId));

        WMWorkItemAudit wmWorkItemAudit = workItemAuditRepository.save(auditEntityBuilder.createwmWorkItemAudit(workItemId, wmProcessInstanceAudit));

        return eventAuditWorkItemRepository.save(auditEntityBuilder.createwmEventAuditWorkItem(username, wmWorkItemAudit));
    }

    public WMEventAuditWorkItem convertAndSaveReassignWorkItem(String processInstanceId, String workItemId, String sourceUser, String targetUser, String username, String processDefinitionId) {

        WMProcessInstanceAudit wmProcessInstanceAudit = processInstanceAuditRepository.save(auditEntityBuilder.createwmProcessInstanceAudit(processInstanceId, processDefinitionId));

        WMWorkItemAudit wmWorkItemAudit = workItemAuditRepository.save(auditEntityBuilder.createwmWorkItemAudit(workItemId, wmProcessInstanceAudit));

        return eventAuditWorkItemRepository.save(auditEntityBuilder.createwmEventAuditWorkItem2(username, wmWorkItemAudit));
    }

    public WMEventAuditAttribute convertAndSaveAssignWorkItemAttribute(String processInstanceId, String workItemId, String attributeName, Object attributeValue, String username) {

        WMProcessInstanceAudit wmProcessInstanceAudit = processInstanceAuditRepository.save(auditEntityBuilder.createwmProcessInstanceAudit2(processInstanceId));

        @SuppressWarnings("unchecked")
        WMAttributeAudit wmAttributeAudit = (WMAttributeAudit) attributeAuditWorkItemRepository.save((Iterable<WMAttributeAuditWorkItem>) auditEntityBuilder.createwmAttributeAudit(attributeName));

        return eventAuditAttributeRepository.save(auditEntityBuilder.createwmEventAuditAttribute(attributeValue, username, wmAttributeAudit));
    }
}
