package ro.teamnet.wfmc.audit.service;

import org.wfmc.audit.WMAEventCode;
import ro.teamnet.wfmc.audit.domain.*;

/**
 * Created by Ioan.Ivan on 3/26/2015.
 */
public interface WfmcAuditService {

    WMProcessInstanceAudit saveProcessInstanceAudit(String procDefId, String procInstName, String processInstanceId);

    WMEventAuditProcessInstance saveEventAuditProcessInstance(WMProcessInstanceAudit wmProcessInstanceAudit, int eventCode, String username);

    WMProcessInstanceAudit updateProcessInstance(WMProcessInstanceAudit wmProcessInstanceAudit);

    WMEventAuditProcessInstance saveWmEventAuditProcessInstance(WMProcessInstanceAudit wmProcessInstanceAudit, Integer eventCode, String username);

    WMWorkItemAudit savewmWorkItemAudit(String workItemId, WMProcessInstanceAudit wmProcessInstanceAudit);

    WMAttributeAuditWorkItem savewmAttributeAuditWorkItem(String attributeName, WMWorkItemAudit wmWorkItemAudit);

    WMEventAuditAttribute savewmEventAuditAttribute(Object attributeValue, String username, WMAttributeAuditWorkItem wmAttributeAuditWorkItem);

    WMEventAuditWorkItem savewmEventAuditWorkItem(String workItemState, WMWorkItemAudit wmWorkItemAudit, WMAEventCode eventCode);

    WMEventAuditAttribute saveWmEventAuditAttribute(Object attributeValue, String username, WMAttributeAuditProcessInstance wmAttributeAuditProcessInstance);

    WMAttributeAuditProcessInstance saveWmAttributeAuditProcessInstance(String attributeName, WMProcessInstanceAudit wmProcessInstanceAudit);
}
