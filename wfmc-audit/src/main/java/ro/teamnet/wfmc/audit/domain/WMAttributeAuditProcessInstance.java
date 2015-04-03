package ro.teamnet.wfmc.audit.domain;

import javax.persistence.*;

/**
 * Created by Ioan.Ivan on 3/25/2015.
 */

@Entity
@DiscriminatorValue(value = "PI")
public class WMAttributeAuditProcessInstance extends WMAttributeAudit{


    @ManyToOne
    @Transient
    @JoinColumn(name = "WM_PROCESS_INSTANCE_AUDIT_ID")
    private WMProcessInstanceAudit wmProcessInstanceAudit;

    public WMProcessInstanceAudit getWmProcessInstanceAudit() {
        return wmProcessInstanceAudit;
    }

    public void setWmProcessInstanceAudit(WMProcessInstanceAudit wmProcessInstanceAudit) {
        this.wmProcessInstanceAudit = wmProcessInstanceAudit;
    }
}
