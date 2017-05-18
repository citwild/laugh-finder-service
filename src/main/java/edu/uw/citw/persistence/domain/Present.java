package edu.uw.citw.persistence.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "present", schema = "uwbwfe")
public class Present implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "INSTANCE_ID")
    private Long instanceId;

    @Column(name = "PERSON_ID")
    private Long personId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Long instanceId) {
        this.instanceId = instanceId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Present present = (Present) o;

        if (id != null ? !id.equals(present.id) : present.id != null) return false;
        if (instanceId != null ? !instanceId.equals(present.instanceId) : present.instanceId != null) return false;
        return personId != null ? personId.equals(present.personId) : present.personId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (instanceId != null ? instanceId.hashCode() : 0);
        result = 31 * result + (personId != null ? personId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Present{" +
                "id=" + id +
                ", instanceId=" + instanceId +
                ", personId=" + personId +
                '}';
    }
}
