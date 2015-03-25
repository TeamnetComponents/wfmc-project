package ro.teamnet.wfmc.audit.domain;

import javax.persistence.*;
import java.util.Date;

/**
* Created by Ioan.Ivan on 3/24/2015.
*/
@Entity
@Table(name = "WM_EVENT_AUDIT")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DISCRIMINATOR")
@DiscriminatorValue(value = "PI")
public class WMEventAuditProcessInstance {

    @Id
    @SequenceGenerator(name = "WM_EVENT_PROCESS_INSTANCE", sequenceName = "WM_EVENT_AUDIT_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="WM_EVENT_PROCESS_INSTANCE")
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

    @ManyToOne
    @JoinColumn(name = "WM_PROCESS_INSTANCE_ID")
    private WMProcessInstanceAudit wmProcessInstanceAudit;

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

    public WMProcessInstanceAudit getWmProcessInstanceAudit() {
        return wmProcessInstanceAudit;
    }

    public void setWmProcessInstanceAudit(WMProcessInstanceAudit wmProcessInstanceAudit) {
        this.wmProcessInstanceAudit = wmProcessInstanceAudit;
    }
}
