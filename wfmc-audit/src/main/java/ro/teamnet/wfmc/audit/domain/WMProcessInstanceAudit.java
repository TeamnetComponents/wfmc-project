package ro.teamnet.wfmc.audit.domain;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Ioan.Ivan on 3/24/2015.
 */

@Entity
@Table(name = "WM_PROCESS_INSTANCE_AUDIT")
public class WMProcessInstanceAudit {

    @Id
    @SequenceGenerator(name = "WM_PROCESS_INSTANCE", sequenceName = "WM_PROCESS_INSTANCE_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WM_PROCESS_INSTANCE")
    @Column(name = "ID")
    private Long id;

    @Column(name = "PROCESS_INSTANCE_ID")
    private String processInstanceId;

    @Column(name = "PROCESS_DEFINITION_ID")
    private String processDefinitionId;

    @Column(name = "PROCESS_DEF_BUSINESS_NAME")
    private String processDefinitionBusinessName;

    @Transient
    @OneToMany(mappedBy = "wmProcessInstanceAudit")
    private List<WMErrorAudit> wmErrorAuditList;

    @Transient
    @OneToMany(mappedBy = "wmProcessInstanceAudit")
    private List<WMWorkItemAudit> wmWorkItemAuditList;

    @Transient
    @OneToMany(mappedBy = "wmProcessInstanceAudit")
    private List<WMAttributeAuditProcessInstance> wmAttributeAuditProcessInstanceList;

    @Transient
    @OneToMany(mappedBy = "wmProcessInstanceAudit")
    private List<WMEventAuditProcessInstance> wmEventAuditProcessInstanceList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getProcessDefinitionBusinessName() {
        return processDefinitionBusinessName;
    }

    public void setProcessDefinitionBusinessName(String processDefinitionBusinessName) {
        this.processDefinitionBusinessName = processDefinitionBusinessName;
    }

    public List<WMWorkItemAudit> getWmWorkItemAuditList() {
        return wmWorkItemAuditList;
    }

    public void setWmWorkItemAuditList(List<WMWorkItemAudit> wmWorkItemAuditList) {
        this.wmWorkItemAuditList = wmWorkItemAuditList;
    }

    public List<WMEventAuditProcessInstance> getWmEventAuditProcessInstanceList() {
        return wmEventAuditProcessInstanceList;
    }

    public void setWmEventAuditProcessInstanceList(List<WMEventAuditProcessInstance> wmEventAuditProcessInstanceList) {
        this.wmEventAuditProcessInstanceList = wmEventAuditProcessInstanceList;
    }

    public List<WMAttributeAuditProcessInstance> getWmAttributeAuditProcessInstanceList() {
        return wmAttributeAuditProcessInstanceList;
    }

    public void setWmAttributeAuditProcessInstanceList(List<WMAttributeAuditProcessInstance> wmAttributeAuditProcessInstanceList) {
        this.wmAttributeAuditProcessInstanceList = wmAttributeAuditProcessInstanceList;
    }

    public List<WMErrorAudit> getWmErrorAuditList() {
        return wmErrorAuditList;
    }

    public void setWmErrorAuditList(List<WMErrorAudit> wmErrorAuditList) {
        this.wmErrorAuditList = wmErrorAuditList;
    }
}
