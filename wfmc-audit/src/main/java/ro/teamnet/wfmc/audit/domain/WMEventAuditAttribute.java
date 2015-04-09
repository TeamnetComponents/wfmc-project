package ro.teamnet.wfmc.audit.domain;

import javax.persistence.*;

/**
 * Created by Ioan.Ivan on 3/25/2015.
 */

@Entity
@DiscriminatorValue(value = "AT")
public class WMEventAuditAttribute extends WMEventAudit {

    @Column(name = "ATTR_VALUE")
    private String attributeValue;

    @ManyToOne
    @JoinColumn(name = "WM_ATTRIBUTE_AUDIT_ID")
    private WMAttributeAudit wmAttributeAudit;

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public WMAttributeAudit getWmAttributeAudit() {
        return wmAttributeAudit;
    }

    public void setWmAttributeAudit(WMAttributeAudit wmAttributeAudit) {
        this.wmAttributeAudit = wmAttributeAudit;
    }
}
