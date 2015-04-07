package ro.teamnet.wfmc.audit.domain;


import javax.persistence.*;
import java.sql.Clob;

@Entity
@Table(name = "WM_ERROR_AUDIT")
public class WMErrorAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "message")
    private String message;

    @Column(name = "stackTrace")
    private Clob stackTrace;

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

    public Clob getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(Clob stackTrace) {
        this.stackTrace = stackTrace;
    }

    @Override
    public String toString() {
        return "ErrorAudit{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", message='" + message + '\'' +
                ", stackTrace=" + stackTrace +
                '}';
    }
}
