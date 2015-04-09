package ro.teamnet.wfmc.audit.domain;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Created by Ioan.Ivan on 3/27/2015.
 */

@Entity
@Table(name = "WM_EVENT_AUDIT")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DISCRIMINATOR")
public class WMEventAudit {

    @Id
    @SequenceGenerator(name = "WM_EVENT_AUDIT_GENERATOR", sequenceName = "WM_EVENT_AUDIT_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WM_EVENT_AUDIT_GENERATOR")
    @Column(name = "ID")
    private Long id;

    @Column(name = "EVENT_CODE")
    private Integer eventCode;

    @Column(name = "USERNAME")
    private String username;


    @Column(name = "CURRENT_DATE")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Transient
    private DateTime currentDate;

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

    public DateTime getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(DateTime currentDate) {
        this.currentDate = currentDate;
    }
}
