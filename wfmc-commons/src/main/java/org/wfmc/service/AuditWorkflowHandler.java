package org.wfmc.service;

import javax.sql.DataSource;

import org.wfmc.audit.WMACreateProcessInstanceData;

/**
 * Created by andras on 3/17/2015.
 */
public class AuditWorkflowHandler {

    public void createProcessInstanceAudit(String procDefId, String procInstName, DataSource dataSource){
        WMACreateProcessInstanceData wmaCreateProcessInstanceData = new WMACreateProcessInstanceData();
        wmaCreateProcessInstanceData.setProcessDefinitionId(procDefId);
       //todo - set all
       //todo 2 - insert into database
    }
}
