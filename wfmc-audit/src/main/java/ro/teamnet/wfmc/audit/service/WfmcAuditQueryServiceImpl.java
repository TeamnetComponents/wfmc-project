package ro.teamnet.wfmc.audit.service;

import org.springframework.stereotype.Service;
import ro.teamnet.wfmc.audit.domain.*;
import ro.teamnet.wfmc.audit.repository.*;

import javax.inject.Inject;

@Service
public class WfmcAuditQueryServiceImpl implements WfmcAuditQueryService {

    @Inject
    private ProcessInstanceAuditRepository processInstanceAuditRepository;
    @Inject
    private EventAuditRepository eventAuditRepository;
    @Inject
    private ErrorAuditRepository errorAuditRepository;
    @Inject
    private EventAuditAttributeRepository eventAuditAttributeRepository;
    @Inject
    private AttributeAuditProcessInstanceRepository attributeAuditProcessInstanceRepository;
    @Inject
    private WorkItemAuditRepository workItemAuditRepository;
    @Inject
    private EventAuditWorkItemRepository eventAuditWorkItemRepository;
    @Inject
    private AttributeAuditWorkItemRepository attributeAuditWorkItemRepository;
    @Inject
    private EventAuditProcessInstanceRepository eventAuditProcessInstanceRepository;

    //TODO: de redenumit toate findBy-urile in findCevaBy - ca sa fie clar din numele metodei ce cauta
    @Override
    public WMProcessInstanceAudit findWMProcessInstanceAuditByProcessInstanceId(String processInstanceId) {
        return processInstanceAuditRepository.findByProcessInstanceId(processInstanceId);
    }

    @Override
    public WMProcessInstanceAudit findWMProcessInstanceAuditByProcessDefinitionBusinessName(String processDefinitionBusinessName) {
        return processInstanceAuditRepository.findByProcessDefinitionBusinessName(processDefinitionBusinessName);
    }

    @Override
    public WMEventAudit[] findWMEventAuditByUsername(String username) {
        return eventAuditRepository.findByUsername(username);
    }

    @Override
    public WMEventAudit findWMEventAuditByEventCode(int eventCode) {
        return eventAuditRepository.findByEventCode(eventCode);
    }

    @Override
    public WMErrorAudit findWMErrorAuditByWmProcessInstanceAudit(WMProcessInstanceAudit wmProcessInstanceAudit) {
        return errorAuditRepository.findByWmProcessInstanceAudit(wmProcessInstanceAudit);
    }

    @Override
    public WMEventAuditAttribute findWMEventAuditAttributeByAttributeValue(String attributeValue) {
        return eventAuditAttributeRepository.findByAttributeValue(attributeValue);
    }

    @Override
    public WMAttributeAuditProcessInstance findWMAttributeAuditProcessInstanceByWMProcessInstanceAudit(WMProcessInstanceAudit wmProcessInstanceAudit) {
        return attributeAuditProcessInstanceRepository.findByWmProcessInstanceAudit(wmProcessInstanceAudit);
    }

    @Override
    public WMWorkItemAudit findWMWorkItemAuditByWorkItemId(String workItemId) {
        return workItemAuditRepository.findByWorkItemId(workItemId);
    }

    @Override
    public WMEventAuditWorkItem findWMEventAuditWorkItemByWmWorkItemAudit(WMWorkItemAudit wmWorkItemAudit) {
        return eventAuditWorkItemRepository.findByWmWorkItemAudit(wmWorkItemAudit);
    }

    @Override
    public WMAttributeAuditWorkItem findWMAttributeAuditWorkItemByWmWorkItemAudit(WMWorkItemAudit wmWorkItemAudit) {
        return attributeAuditWorkItemRepository.findByWmWorkItemAudit(wmWorkItemAudit);
    }

    @Override
    public WMEventAuditProcessInstance findWMEventAuditProcessInstanceByWmProcessInstanceAudit(WMProcessInstanceAudit wmProcessInstanceAudit) {
        return eventAuditProcessInstanceRepository.findByWmProcessInstanceAudit(wmProcessInstanceAudit);
    }
}
