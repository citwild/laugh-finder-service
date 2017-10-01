package edu.uw.citw.persistence.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A participant in laughter, the type of laughter, and its intensity.
 *
 * Created by miles on 2/11/17.
 */
@Entity
@Table(name = "participating", schema = "dbo")
public class Participant implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PRESENT_ID", nullable = false)
    private Long presentId;

    @Column(name = "INTENSITY", nullable = false)
    private Integer intensity;

    public Participant() {}

    public Participant(Long presentId, Integer intensity) {
        this.presentId = presentId;
        this.intensity = intensity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPresentId() {
        return presentId;
    }

    public void setPresentId(Long presentId) {
        this.presentId = presentId;
    }

    public Integer getIntensity() {
        return intensity;
    }

    public void setIntensity(Integer intensity) {
        this.intensity = intensity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Participant that = (Participant) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (presentId != null ? !presentId.equals(that.presentId) : that.presentId != null) return false;
        return intensity != null ? intensity.equals(that.intensity) : that.intensity == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (presentId != null ? presentId.hashCode() : 0);
        result = 31 * result + (intensity != null ? intensity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id=" + id +
                ", presentId=" + presentId +
                ", intensity=" + intensity +
                '}';
    }
}
