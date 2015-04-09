package ro.teamnet.wfmc.audit.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Ioan.Ivan on 3/27/2015.
 */

@Entity
@Table(name = "WM_ATTRIBUTE_AUDIT")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DISCRIMINATOR")

public class WMAttributeAudit implements Serializable {

    @Id
    @SequenceGenerator(name = "WM_ATTRIBUTE_AUDIT_GENERATOR", sequenceName = "WM_ATTRIBUTE_AUDIT_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WM_ATTRIBUTE_AUDIT_GENERATOR")
    @Column(name = "ID")
    private Long id;

    @Column(name = "ATTRIBUTE_NAME")
    private String attributeName;

    @Transient
    @OneToMany(mappedBy = "wmAttributeAudit")
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

    public List<WMEventAuditAttribute> getWmEventAuditAttributes() {
        return wmEventAuditAttributes;
    }

    public void setWmEventAuditAttributes(List<WMEventAuditAttribute> wmEventAuditAttributes) {
        this.wmEventAuditAttributes = wmEventAuditAttributes;
    }
}
