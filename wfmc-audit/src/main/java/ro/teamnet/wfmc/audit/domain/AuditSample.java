package ro.teamnet.wfmc.audit.domain;

import javax.persistence.*;

/**
 * A sample entity.
 */
@Entity
@Table(name = "SAMPLE_TABLE")
public class AuditSample {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SampleEntity{" +
                "id=" + id +
                '}';
    }
}
