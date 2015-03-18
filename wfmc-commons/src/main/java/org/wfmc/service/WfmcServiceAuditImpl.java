package org.wfmc.service;

import org.wfmc.impl.utils.DatabaseUtils;
import org.wfmc.wapi.WMWorkflowException;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Lucian.Dragomir on 3/16/2015.
 */
    public class WfmcServiceAuditImpl extends WfmcServiceImpl_Abstract {
    private WfmcService internalService;
    private DataSource  dataSource;

    @Override
    public void __initialize(Properties context) throws IOException {
        super.__initialize(context);
        dataSource = DatabaseUtils.getDataSource(context, "oracle");
    }

    public WfmcService getInternalService() {
        return internalService;
    }

    public void setInternalService(WfmcService internalService) {
        this.internalService = internalService;
    }

    public DataSource getDataSource() {
        return dataSource;
    }



    public String createProcessInstance(String procDefId, String procInstName) throws WMWorkflowException {
        String tempProcessInstanceId = null;
        String status= "OK";
        try {
            tempProcessInstanceId =  internalService.createProcessInstance(procDefId, procInstName);
        } catch(Exception ex){
            status = ex.getMessage();
            return null;
        }
        finally {
            AuditWorkflowHandler auditWorkflowHandler = new AuditWorkflowHandler();
//            String session = internalService.getSession();
            auditWorkflowHandler.createProcessInstanceAudit(procDefId, procInstName, tempProcessInstanceId, getDataSource());
           // TODO apel AuditWorkflowHandler pentru operatia de create PI

        }
        //log after (including error catching )
        return tempProcessInstanceId;
    }

}
