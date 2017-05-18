package edu.uw.citw.persistence.domain;

        import javax.persistence.*;
        import java.io.Serializable;

/**
 * A participant in laughter, the type of laughter, and its intensity.
 *
 * Created by miles on 2/11/17.
 *
 * DEPRECATED: Should be phased out for Participant object
 */
@Entity
@Table(name = "laugh_instance_participants", schema = "uwbwfe")
public class InstanceParticipant implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "INSTANCE_ID", nullable = false)
    private Long instanceId;

    @Column(name = "PARTICIPANT_NAME", nullable = false)
    private String participantName;

    @Column(name = "INTENSITY", nullable = false)
    private Integer intensity;

    public InstanceParticipant() {}

    public InstanceParticipant(Long id, Long instanceId, String participantName, Integer intensity) {
        this.id = id;
        this.instanceId = instanceId;
        this.participantName = participantName;
        this.intensity = intensity;
    }

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

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
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

        InstanceParticipant that = (InstanceParticipant) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (instanceId != null ? !instanceId.equals(that.instanceId) : that.instanceId != null) return false;
        if (participantName != null ? !participantName.equals(that.participantName) : that.participantName != null)
            return false;
        return intensity != null ? intensity.equals(that.intensity) : that.intensity == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (instanceId != null ? instanceId.hashCode() : 0);
        result = 31 * result + (participantName != null ? participantName.hashCode() : 0);
        result = 31 * result + (intensity != null ? intensity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "InstanceParticipant{" +
                "id=" + id +
                ", instanceId=" + instanceId +
                ", participantName='" + participantName + '\'' +
                ", intensity=" + intensity +
                '}';
    }
}
