package ro.teamnet.wfmc.audit.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Ioan.Ivan on 3/25/2015.
 */
@Entity
@Table(name = "WM_EVENT_AUDIT")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DISCRIMINATOR")
@DiscriminatorValue(value = "WI")
public class WMEventAuditWorkItem {

    @Id
    @SequenceGenerator(name = "WM_EVENT_WORK_ITEM", sequenceName = "WM_EVENT_AUDIT_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="WM_EVENT_WORK_ITEM")
    @Column(name = "ID")
    private Long id;

    @Column(name = "EVENT_CODE")
    private Integer eventCode;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "CURRENT_DATE")
    private Date currentDate;

    @Column(name = "PREVIOUS_STATE")
    private String previousState;

    @Column(name = "WORK_ITEM_STATE")
    private String workItemState;

    @ManyToOne
    @JoinColumn(name = "WM_WORK_ITEM_AUDIT_ID")
    private WMWorkItemAudit wmWorkItemAudit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEventCode() {
        return eventCode;
    }

    public void setEventCode(Integer eventCode) {
        this.eventCode = eventCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public String getPreviousState() {
        return previousState;
    }

    public void setPreviousState(String previousState) {
        this.previousState = previousState;
    }

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
