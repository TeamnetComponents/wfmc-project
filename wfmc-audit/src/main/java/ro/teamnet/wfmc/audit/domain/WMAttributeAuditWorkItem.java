package ro.teamnet.wfmc.audit.domain;

import javax.persistence.*;
import java.util.List;

/**
* Created by Ioan.Ivan on 3/24/2015.
*/
@Entity
@Table(name = "WM_ATTRIBUTE_AUDIT")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DISCRIMINATOR")
@DiscriminatorValue(value = "WI")
public class WMAttributeAuditWorkItem {

    @Id
    @SequenceGenerator(name = "WM_ATTRIBUTE_WORK_ITEM", sequenceName = "WM_ATTRIBUTE_AUDIT_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="WM_ATTRIBUTE_WORK_ITEM")
    @Column(name = "ID")
    private Long id;

    @Column(name = "ATTRIBUTE_NAME")
    private String attributeName;

    @ManyToOne
    @JoinColumn(name = "WM_WORK_ITEM_ID")
    private WMWorkItemAudit wmWorkItemAudit;

    @OneToMany(mappedBy = "wmAttributeAuditWorkItem")
    private List<WMEventAuditAttribute> wmEventAuditAttributes;

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

    public WMWorkItemAudit getWmWorkItemAudit() {
        return wmWorkItemAudit;
    }

    public void setWmWorkItemAudit(WMWorkItemAudit wmWorkItemAudit) {
        this.wmWorkItemAudit = wmWorkItemAudit;
    }

    public List<WMEventAuditAttribute> getWmEventAuditAttributes() {
        return wmEventAuditAttributes;
    }

    public void setWmEventAuditAttributes(List<WMEventAuditAttribute> wmEventAuditAttributes) {
        this.wmEventAuditAttributes = wmEventAuditAttributes;
    }
}
