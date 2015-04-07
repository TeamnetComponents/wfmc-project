package ro.teamnet.wfmc.audit.domain;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

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

    @Column(name = "age")
    private Long age;

    @Column(name = "name")
    private String name;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "creation", nullable = false)
    private DateTime creation;

    public AuditSample(Long age, String name) {
        this.age = age;
        this.name = name;
        this.creation = new DateTime();
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getCreation() {
        return creation;
    }

    public void setCreation(DateTime creation) {
        this.creation = creation;
    }

    @Override
    public String toString() {
        return "AuditSample{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", creation='" + creation + '\'' +
                '}';
    }
}
