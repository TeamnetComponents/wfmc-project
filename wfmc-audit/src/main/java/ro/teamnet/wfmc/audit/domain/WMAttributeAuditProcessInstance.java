package ro.teamnet.wfmc.audit.domain;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Ioan.Ivan on 3/25/2015.
 */

@Entity
@Table(name = "WM_ATTRIBUTE_AUDIT")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DISCRIMINATOR")
@DiscriminatorValue(value = "PI")
public class WMAttributeAuditProcessInstance {

    @Id
    @SequenceGenerator(name="WM_ATTRIBUTE_PROCESS_INSTANCE", sequenceName="WM_ATTRIBUTE_AUDIT_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="WM_ATTRIBUTE_PROCESS_INSTANCE")
    @Column(name = "ID")
    private Long id;

    @Column(name = "ATTRIBUTE_NAME")
    private String attributeName;

    @ManyToOne
    @JoinColumn(name = "WM_PROCESS_INSTANCE_AUDIT_ID")
    private WMProcessInstanceAudit wmProcessInstanceAudit;

    @OneToMany(mappedBy = "wmAttributeAuditWorkItem")
    private List<WMEventAuditAttribute> wmEventAuditAttributeList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public WMProcessInstanceAudit getWmProcessInstanceAudit() {
        return wmProcessInstanceAudit;
    }

    public void setWmProcessInstanceAudit(WMProcessInstanceAudit wmProcessInstanceAudit) {
        this.wmProcessInstanceAudit = wmProcessInstanceAudit;
    }

    public List<WMEventAuditAttribute> getWmEventAuditAttributeList() {
        return wmEventAuditAttributeList;
    }

    public void setWmEventAuditAttributeList(List<WMEventAuditAttribute> wmEventAuditAttributeList) {
        this.wmEventAuditAttributeList = wmEventAuditAttributeList;
    }
}
