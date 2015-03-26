package ro.teamnet.wfmc.audit.service;

import org.wfmc.audit.WMACreateProcessInstanceData;
import ro.teamnet.wfmc.audit.domain.WMEventAuditProcessInstance;
import ro.teamnet.wfmc.audit.domain.WMProcessInstanceAudit;

import java.util.Date;

/**
 * Created by Florin.Cojocaru on 3/26/2015.
 */
public class StrategyConvertingServiceImpl implements StrategyConvertingService {


    public WMEventAuditProcessInstance convertFromWFMCtoEntities(WMACreateProcessInstanceData wmaCreateProcessInstanceData) {


        WMProcessInstanceAudit wmProcessInstanceAudit = new WMProcessInstanceAudit();
        wmProcessInstanceAudit.setProcessDefinitionBusinessName(wmaCreateProcessInstanceData.getProcessDefinitionBusinessName());
        wmProcessInstanceAudit.setProcessDefinitionId(wmaCreateProcessInstanceData.getProcessDefinitionId());
        wmProcessInstanceAudit.setProcessInstanceId(wmaCreateProcessInstanceData.getCurrentProcessInstanceId());

        WMEventAuditProcessInstance wmEventAuditProcessInstance = new WMEventAuditProcessInstance();
        wmEventAuditProcessInstance.setWmProcessInstanceAudit(wmProcessInstanceAudit);
        wmEventAuditProcessInstance.setCurrentDate(new Date());
        wmEventAuditProcessInstance.setEventCode(wmaCreateProcessInstanceData.getEventCode().value());
        wmEventAuditProcessInstance.setUsername(wmaCreateProcessInstanceData.getUserId());

        return wmEventAuditProcessInstance;
    }
}
