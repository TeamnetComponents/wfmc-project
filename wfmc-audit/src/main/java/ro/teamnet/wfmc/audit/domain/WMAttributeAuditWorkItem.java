package ro.teamnet.wfmc.audit.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
* Created by Ioan.Ivan on 3/24/2015.
*/
@Entity
@DiscriminatorValue(value = "WI")
public class WMAttributeAuditWorkItem extends WMAttributeAudit{

    @ManyToOne
    @JoinColumn(name = "WM_WORK_ITEM_ID")
    private WMWorkItemAudit wmWorkItemAudit;

    public WMWorkItemAudit getWmWorkItemAudit() {
        return wmWorkItemAudit;
    }

    public void setWmWorkItemAudit(WMWorkItemAudit wmWorkItemAudit) {
        this.wmWorkItemAudit = wmWorkItemAudit;
    }

}
