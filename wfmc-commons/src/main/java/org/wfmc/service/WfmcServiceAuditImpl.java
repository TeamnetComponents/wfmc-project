package org.wfmc.service;

import org.wfmc.impl.utils.DatabaseUtils;
import org.wfmc.wapi.WMAttribute;
import org.wfmc.wapi.WMConnectInfo;
import org.wfmc.wapi.WMProcessInstance;
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

    @Override
    public void connect(WMConnectInfo connectInfo) throws WMWorkflowException {
        this.internalService.connect(connectInfo);
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
            String username = getUserNameFormInternalServiceCache();
            auditWorkflowHandler.createProcessInstanceAudit(procDefId, procInstName, tempProcessInstanceId, getDataSource(),username);
        }
        //log after (including error catching )
        return tempProcessInstanceId;
    }

    @Override
    public String startProcess(String procInstId) throws WMWorkflowException {
        String currentProcessInstanceId =  null;
        String status = "OK";
        WMProcessInstance processInstance = null;
        try {
            processInstance = internalService.getProcessInstance(procInstId);
            currentProcessInstanceId = internalService.startProcess(procInstId);
        } catch (Exception ex) {
            status = ex.getMessage();
            return null;
        } finally {
            AuditWorkflowHandler auditWorkflowHandler = new AuditWorkflowHandler();
            String username = getUserNameFormInternalServiceCache();
            auditWorkflowHandler.startProcessInstanceAudit(procInstId, currentProcessInstanceId, getDataSource(), username, processInstance);
        }

        return currentProcessInstanceId;
    }

    @Override
    public void assignProcessInstanceAttribute(String procInstId, String attrName, Object attrValue) throws WMWorkflowException {
        WMProcessInstance processInstance = null;
        String status = "OK";
        Object previousProcessInstanceAttributeValue = null;
        try {
            try {
                WMAttribute processInstanceAttribute = internalService.getProcessInstanceAttributeValue(procInstId, attrName);
                previousProcessInstanceAttributeValue = processInstanceAttribute.getValue();
            } catch (Exception ex) {
                previousProcessInstanceAttributeValue = null;
            }
            processInstance = internalService.getProcessInstance(procInstId);
            internalService.assignProcessInstanceAttribute(procInstId, attrName, attrValue);
        } catch (Exception ex) {
            status = ex.getMessage();
        } finally {
            AuditWorkflowHandler auditWorkflowHandler = new AuditWorkflowHandler();
            String username = getUserNameFormInternalServiceCache();
            auditWorkflowHandler.assignProcessInstanceAttributeAudit(procInstId, attrName, attrValue, getDataSource(), username, processInstance, previousProcessInstanceAttributeValue);
        }
    }

    private String getUserNameFormInternalServiceCache() {
            return internalService.getSessionUsername();
    }

}
