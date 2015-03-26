package ro.teamnet.wfmc.audit.service;

import org.wfmc.audit.WMACreateProcessInstanceData;

/**
 * Created by Ioan.Ivan on 3/26/2015.
 */
public interface WfmcAuditService {

    public WMACreateProcessInstanceData convertCreateProcessToWMACreateProcessInstanceData(String processDefinitionId, String processInstanceName, String tempProcessInstanceId, String username);

}
