package ro.teamnet.wfmc.audit.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by Ioan.Ivan on 3/25/2015.
 */

@Entity
@DiscriminatorValue(value = "PI")
public class WMAttributeAuditProcessInstance extends WMAttributeAudit {

    @ManyToOne
    @JoinColumn(name = "WM_PROCESS_INSTANCE_AUDIT_ID")
    private WMProcessInstanceAudit wmProcessInstanceAudit;

    public WMProcessInstanceAudit getWmProcessInstanceAudit() {
        return wmProcessInstanceAudit;
    }

    public void setWmProcessInstanceAudit(WMProcessInstanceAudit wmProcessInstanceAudit) {
        this.wmProcessInstanceAudit = wmProcessInstanceAudit;
    }
}
