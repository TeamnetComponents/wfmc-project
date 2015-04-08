package ro.teamnet.wfmc.audit.service;

import ro.teamnet.wfmc.audit.domain.WMErrorAudit;
import ro.teamnet.wfmc.audit.domain.WMEventAudit;
import ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit;
import ro.teamnet.wfmc.audit.repository.ErrorAuditRepository;
import ro.teamnet.wfmc.audit.repository.EventAuditRepository;
import ro.teamnet.wfmc.audit.repository.ProcessInstanceAuditRepository;

import javax.inject.Inject;

public class WfmcAuditQueryServiceImpl implements WfmcAuditQueryService {

    @Inject
    private ProcessInstanceAuditRepository processInstanceAuditRepository;

    @Inject
    private EventAuditRepository eventAuditRepository;
    
    @Inject
    private ErrorAuditRepository errorAuditRepository;

    @Override
    public WMProcessInstanceAudit findWMProcessInstanceAuditByProcessInstanceId(String processInstanceId) {
        return processInstanceAuditRepository.findOne(Long.valueOf(processInstanceId));
    }

    @Override
    public WMProcessInstanceAudit findWMProcessInstanceAuditById(Long id) {
        return processInstanceAuditRepository.findOne(id);
    }

    @Override
    public WMProcessInstanceAudit findWMProcessInstanceAuditByProcessDefinitionBusinessName(String processDefinitionBusinessName) {
        return processInstanceAuditRepository.findByProcessDefinitionBusinessName(processDefinitionBusinessName);
    }

    @Override
    public WMEventAudit findWMEventAuditByUsername(String username) {
        return eventAuditRepository.findByUsername(username);
    }

    @Override
    public WMErrorAudit findWMErrorAuditByWmProcessInstanceAudit(WMProcessInstanceAudit wmProcessInstanceAudit) {
        return errorAuditRepository.findByWmProcessInstanceAudit(wmProcessInstanceAudit);
    }
}
