package edu.uw.citw.persistence.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Represents a row from the LAUGH_TYPES_PER_PARTICIPANT table.
 *
 * Created by miles on 2/12/17.
 */
@Entity
@Table(name = "laugh_types_per_participant", schema = "dbo")
public class ParticipantType implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TYPE_ID", nullable = false)
    private Long typeId;

    @Column(name = "PARTICIPANT_ID", nullable = false)
    private Long participantId;

    public ParticipantType() {}

    public ParticipantType(Long typeId, Long participantId) {
        this.typeId = typeId;
        this.participantId = participantId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParticipantType that = (ParticipantType) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (typeId != null ? !typeId.equals(that.typeId) : that.typeId != null) return false;
        return participantId != null ? participantId.equals(that.participantId) : that.participantId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (typeId != null ? typeId.hashCode() : 0);
        result = 31 * result + (participantId != null ? participantId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ParticipantType{" +
                "id=" + id +
                ", typeId=" + typeId +
                ", participantId=" + participantId +
                '}';
    }
}
