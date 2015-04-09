package ro.teamnet.wfmc.audit.domain;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "WM_ERROR_AUDIT")
public class WMErrorAudit {

    @Id
    @SequenceGenerator(name = "WM_ERROR_WORK_ITEM", sequenceName = "WM_ERROR_AUDIT_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WM_ERROR_WORK_ITEM")
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "AUDITED_OPERATION")
    private String auditedOperation;

    @Column(name = "STACK_TRACE")
    @Lob
    private String stackTrace;

    @Column(name = "OCCURRENCE_TIME")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime occurrenceTime;

    @ManyToOne
    @JoinColumn(name = "WM_PROCESS_INSTANCE_ERROR_ID")
    private WMProcessInstanceAudit wmProcessInstanceAudit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuditedOperation() {
        return auditedOperation;
    }

    public void setAuditedOperation(String auditedOperation) {
        this.auditedOperation = auditedOperation;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public DateTime getOccurrenceTime() {
        return occurrenceTime;
    }

    public void setOccurrenceTime(DateTime occurrenceTime) {
        this.occurrenceTime = occurrenceTime;
    }

    public WMProcessInstanceAudit getWmProcessInstanceAudit() {
        return wmProcessInstanceAudit;
    }

    public void setWmProcessInstanceAudit(WMProcessInstanceAudit wmProcessInstanceAudit) {
        this.wmProcessInstanceAudit = wmProcessInstanceAudit;
    }

    @Override
    public String toString() {
        return "WMErrorAudit{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", message='" + message + '\'' +
                ", auditedOperation='" + auditedOperation + '\'' +
                ", stackTrace=" + stackTrace +
                ", occurrenceTime=" + occurrenceTime +
                ", wmProcessInstanceAudit=" + wmProcessInstanceAudit +
                '}';
    }
}
