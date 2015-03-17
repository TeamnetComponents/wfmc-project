package org.wfmc.service;

import org.wfmc.audit.WMACreateProcessInstanceData;
import org.wfmc.impl.utils.DatabaseUtils;
import org.wfmc.wapi.*;
import org.wfmc.wapi2.WMEntity;
import org.wfmc.wapi2.WMEntityIterator;
import org.wfmc.xpdl.model.workflow.WorkflowProcess;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import javax.sql.DataSource;

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


    @Override
    public String createProcessInstance(String procDefId, String procInstName) throws WMWorkflowException {

        String status= "OK";
        try {
            String tempProcessInstanceId =  internalService.createProcessInstance(procDefId, procInstName);
        } catch(Exception ex){
            status = ex.getMessage();
        }
        finally {
            //log

           // TODO apel AuditWorkflowHandler pentru operatia de create PI

        }
        //log after (including error catching )
        return null;
    }

}
