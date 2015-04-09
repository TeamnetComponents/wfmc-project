package ro.teamnet.wfmc.audit.domain;

import javax.persistence.*;

/**
 * Created by Ioan.Ivan on 3/24/2015.
 */
@Entity
@DiscriminatorValue(value = "PI")
public class WMEventAuditProcessInstance extends WMEventAudit {

    @Column(name = "PREVIOUS_STATE")
    private String previousState;

    @ManyToOne
    @Transient
    @JoinColumn(name = "WM_PROCESS_INSTANCE_AUDIT_ID")
    private WMProcessInstanceAudit wmProcessInstanceAudit;

    public String getPreviousState() {
        return previousState;
    }

    public void setPreviousState(String previousState) {
        this.previousState = previousState;
    }

    public WMProcessInstanceAudit getWmProcessInstanceAudit() {
        return wmProcessInstanceAudit;
    }

    public void setWmProcessInstanceAudit(WMProcessInstanceAudit wmProcessInstanceAudit) {
        this.wmProcessInstanceAudit = wmProcessInstanceAudit;
    }
}
