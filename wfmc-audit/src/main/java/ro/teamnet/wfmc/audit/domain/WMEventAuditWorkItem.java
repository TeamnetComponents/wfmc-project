package ro.teamnet.wfmc.audit.domain;

import javax.persistence.*;

/**
 * Created by Ioan.Ivan on 3/25/2015.
 */
@Entity
@DiscriminatorValue(value = "WI")
public class WMEventAuditWorkItem extends WMEventAudit {

    @Column(name = "WORK_ITEM_STATE")
    private String workItemState;

    @ManyToOne
    @JoinColumn(name = "WM_WORK_ITEM_AUDIT_ID")
    private WMWorkItemAudit wmWorkItemAudit;

    public String getWorkItemState() {
        return workItemState;
    }

    public void setWorkItemState(String workItemState) {
        this.workItemState = workItemState;
    }

    public WMWorkItemAudit getWmWorkItemAudit() {
        return wmWorkItemAudit;
    }

    public void setWmWorkItemAudit(WMWorkItemAudit wmWorkItemAudit) {
        this.wmWorkItemAudit = wmWorkItemAudit;
    }
}
