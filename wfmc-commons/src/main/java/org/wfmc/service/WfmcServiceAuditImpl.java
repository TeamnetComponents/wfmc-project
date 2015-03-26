package org.wfmc.service;

import org.wfmc.impl.utils.DatabaseUtils;
import org.wfmc.wapi.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Created by Lucian.Dragomir on 3/16/2015.
 */
public class WfmcServiceAuditImpl extends WfmcServiceAbstract {
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
            String username = getUsername();
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
            String username = getUsername();
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
            String username = getUsername();
            auditWorkflowHandler.assignProcessInstanceAttributeAudit(procInstId, attrName, attrValue, getDataSource(), username, processInstance, previousProcessInstanceAttributeValue);
        }
    }

    @Override
    public void abortProcessInstance(String procInstId) throws WMWorkflowException {
        WMProcessInstance processInstance = null;
        String status = "OK";
        try {
            processInstance = internalService.getProcessInstance(procInstId);
            internalService.abortProcessInstance(procInstId);
        } catch (Exception ex) {
            status = ex.getMessage();
        } finally {
            AuditWorkflowHandler auditWorkflowHandler = new AuditWorkflowHandler();
            String username = getUsername();
            auditWorkflowHandler.abortProcessInstanceAudit(procInstId, getDataSource(), username, processInstance);
        }
    }

    @Override
    public void assignWorkItemAttribute(String procInstId, String workItemId, String attrName, Object attrValue) throws WMWorkflowException {
        WMProcessInstance processInstance = null;
        String status = "OK";
        Object previousProcessInstanceAttributeValue = null;
        try {
            try {
                WMAttribute processInstanceAttribute = internalService.getWorkItemAttributeValue(procInstId, workItemId, attrName);
                previousProcessInstanceAttributeValue = processInstanceAttribute.getValue();
            } catch (Exception ex) {
                previousProcessInstanceAttributeValue = null;
            }
            processInstance = internalService.getProcessInstance(procInstId);
            internalService.assignWorkItemAttribute(procInstId, workItemId, attrName, attrValue);
        } catch (Exception ex) {
            status = ex.getMessage();
        } finally {
            AuditWorkflowHandler auditWorkflowHandler = new AuditWorkflowHandler();
            String username = getUsername();
            auditWorkflowHandler.assignWorkItemAttributeAudit(procInstId, workItemId, attrName, attrValue, getDataSource(), username, processInstance, previousProcessInstanceAttributeValue);
        }
    }

    @Override
    public void reassignWorkItem(String sourceUser, String targetUser, String procInstId, String workItemId) throws WMWorkflowException {
        WMProcessInstance processInstance = null;
        String status = "OK";

        try {
            processInstance = internalService.getProcessInstance(procInstId);
            internalService.reassignWorkItem(sourceUser, targetUser, procInstId, workItemId);
        } catch (Exception ex) {
            status = ex.getMessage();
        } finally {
            AuditWorkflowHandler auditWorkflowHandler = new AuditWorkflowHandler();
            String username = getUsername();
            auditWorkflowHandler.reassignWorkItemAudit(sourceUser, targetUser, procInstId, workItemId, getDataSource(), username, processInstance);
        }
    }

    @Override
    public void completeWorkItem(String procInstId, String workItemId) throws WMWorkflowException {
        WMProcessInstance processInstance = null;
        String status = "OK";

        try {
            processInstance = internalService.getProcessInstance(procInstId);
            internalService.completeWorkItem(procInstId, workItemId);
        } catch (Exception ex) {
            status = ex. getMessage();
        } finally {
            AuditWorkflowHandler auditWorkflowHandler = new AuditWorkflowHandler();
            String username = getUsername();
            auditWorkflowHandler.completeWorkItemAudit(procInstId, workItemId, getDataSource(), username, processInstance);
        }
    }

    @Override
    public WMWorkItemIterator listWorkItems(WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return this.internalService.listWorkItems(filter, countFlag);
    }

    @Override
    public List<WMWorkItem> getNextSteps(String processInstanceId, String workItemId) throws WMUnsupportedOperationException {
        try {
            return this.internalService.getNextSteps(processInstanceId, workItemId);
        } catch (WMWorkflowException e) {
            return null;
        }
    }

    @Override
    public WMProcessInstance getProcessInstance(String procInstId) throws WMWorkflowException {
        return this.internalService.getProcessInstance(procInstId);
    }

    @Override
    public WMProcessInstanceIterator listProcessInstances(WMFilter filter, boolean countFlag) throws WMWorkflowException {
        return this.internalService.listProcessInstances(filter, countFlag);
    }

    public String getUsername() {
        String[] users = this.internalService.getWmConnectInfo().getUserIdentification().split("@");
        return users[0];
    }
}
